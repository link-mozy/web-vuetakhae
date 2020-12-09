package com.web.server.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class FileRepository {

	public static Map<String, String> saveFile(MultipartFile multipartFile, String rootPath) {
		Map<String, String> map = new HashMap<String, String>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		String saveFolder = "static/images";//dateFormat.format(new Date());
		String totalSaveFolder = rootPath + File.separator + saveFolder;
		System.out.println("totalSaveFolder >>> "+totalSaveFolder);
		File dir = new File(totalSaveFolder);
		if(!dir.exists())
		  dir.mkdirs();
		
		String originFileName = multipartFile.getOriginalFilename();
//		String saveFileName = UUID.randomUUID().toString() + originFileName.substring(originFileName.lastIndexOf('.'));
		String saveFileName = dateFormat.format(new Date())+originFileName;//.substring(originFileName.lastIndexOf('.'));
		long fileSize = 0;
		try {
			File file = new File(totalSaveFolder, saveFileName);
			multipartFile.transferTo(file);
			fileSize = file.length();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		map.put("saveFolder", saveFolder);
		map.put("saveFileName", saveFileName);
		map.put("saveFileSize", fileSize + "");
		return map;
	}
	
}

