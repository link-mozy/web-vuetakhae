package com.web.server.controller;

import java.util.*;
import java.util.stream.Collectors;

import com.web.server.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.web.server.service.StorageService;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FileRestController {

    private static final Logger logger = LoggerFactory.getLogger(FileRestController.class);

    private StorageService storageService;

    @Autowired
    private JwtService jwtService;

    public FileRestController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/files")
    public String listAllFiles(Model model) {

        model.addAttribute("files", storageService.loadAll().map(
                path -> ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/download/")
                        .path(path.getFileName().toString())
                        .toUriString())
                .collect(Collectors.toList()));

        return "listFiles";
    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

        Resource resource = storageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @PostMapping("/api/mypage/image")
    public ResponseEntity<Map<String, Object>> uploadProfileImage(final HttpServletRequest req,
                                                                  @RequestParam("file") MultipartFile file) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            String email = jwtService.getEamil(req.getHeader("jwt-auth-token"));
            String name = storageService.storeProfileImage(file, email);

            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/file/")
                    .path(name)
                    .toUriString();
            status = HttpStatus.OK;
            // body json add
            resultMap.put("image", uri);
            resultMap.put("success", true);
        }
        catch (RuntimeException e) {
            logger.info("ERROR message : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("success", false);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/file")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("file") MultipartFile file) {

        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;

        try {
            String name = storageService.store(file);
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/file/")
                    .path(name)
                    .toUriString();
            status = HttpStatus.OK;
            // body json add
            resultMap.put("image", uri);
            resultMap.put("success", true);
        }
        catch (RuntimeException e) {
            logger.info("ERROR message : {}", e.getMessage());
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("success", false);
        }

        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/api/files")
    @ResponseBody
    public List<ResponseEntity<Map<String, Object>>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.stream(files)
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }
}