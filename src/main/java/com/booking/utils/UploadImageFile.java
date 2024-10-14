package com.booking.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

// 上傳圖片工具
public class UploadImageFile {
	public static Result<String> upload(MultipartFile imageFile) {
		if(imageFile.isEmpty()) {
			return Result.failure("檔案是空的");
		}
		
		try {
			String uploadDir = "uploads/";
			String fileName = imageFile.getOriginalFilename();
			Path filePath = Paths.get(uploadDir + fileName);
			
			// 檢查有沒有資料夾，沒有就創建
			Path parentDir = filePath.getParent();
			if (parentDir != null && !Files.exists(parentDir)) {
			    Files.createDirectories(parentDir); 
			}
			
			Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return Result.success("圖片上傳成功");
	}
}
