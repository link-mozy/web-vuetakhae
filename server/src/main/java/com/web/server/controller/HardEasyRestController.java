package com.web.server.controller;

import com.web.server.dto.Board;
import com.web.server.dto.HardEasy;
import com.web.server.service.HardEasyService;
import com.web.server.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class HardEasyRestController {

    @Autowired
    HardEasyService hardEasyService;

    @Autowired
    JwtService jwtService;


    @PostMapping(value = "/boards/{boardId}")
    public ResponseEntity<Map<String, Object>> Choice(@PathVariable int boardId,@RequestBody Map<String, Object> map, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            String token = req.getHeader("jwt-auth-token");
            String email = jwtService.getEamil(token);
            HardEasy choiceselect = new HardEasy();
            int pressedBtn = (int) map.get("pressedBtn");
            choiceselect.setEmail(email);
            choiceselect.setBoardId(boardId);
            choiceselect.setPressedBtn(pressedBtn);
            System.out.println(choiceselect);
            hardEasyService.createChoice(choiceselect);
            status = HttpStatus.OK;
            resultMap.put("status", status.value());
            resultMap.put("Message","저장 완료");
        } catch (Exception e) {
            System.out.println("catch에러");
            status = HttpStatus.BAD_REQUEST;
            resultMap.put("status", status.value());
            resultMap.put("message", e.getMessage());
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
