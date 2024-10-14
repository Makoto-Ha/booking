package com.booking.utils;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

public class AWSImageUtil {
	private static final String BUCEKT_NAME = "bookingdb";
	private static final Region region = Region.AP_EAST_1;
	private static S3Client s3;

	public static void uploadFile(String keyName, String filePath) {

		s3 = S3Client.builder().region(region)
		.credentialsProvider(ProfileCredentialsProvider.create()).build();
		
		try {
			// 上傳文件到 S3
			PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(BUCEKT_NAME).key(keyName).build();

			s3.putObject(putObjectRequest, RequestBody.fromFile(Paths.get(filePath)));

			System.out.println("成功上傳到S3: " + keyName);

			s3.close();

		} catch (Exception e) {
			System.err.println("上傳失敗: " + e.getMessage());
		}
	}

	public static List<String> listImagesInFolder(String folderPrefix) {
		s3 = S3Client.builder().region(region)
				.credentialsProvider(ProfileCredentialsProvider.create()).build();
		try {
			// 創建 ListObjectsV2Request 並設置前綴來模擬文件夾
			ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(BUCEKT_NAME).prefix(folderPrefix)
					.build();

			// 列出對象
			ListObjectsV2Response response = s3.listObjectsV2(request);

			// 過濾圖片文件（根據文件擴展名，例如 jpg、png）
			List<S3Object> objects = response.contents().stream().sorted((o1, o2) -> o2.lastModified().compareTo(o1.lastModified())).collect(Collectors.toList());
			List<String> imgs = new ArrayList<>();
			for (S3Object obj : objects) {
				imgs.add(obj.key());
//	                if (obj.key().endsWith(".jpg") || obj.key().endsWith(".png") || obj.key().endsWith(".jpeg")) {
//	                    System.out.println("Image found: " + obj.key());
//	                }
			}
			
			s3.close();
			
			return imgs;

		} catch (Exception e) {
			System.err.println("Failed to list images: " + e.getMessage());
			return null;
		}
	}

	public static void deleteImage(String imageKey) {
		s3 = S3Client.builder().region(region)
				.credentialsProvider(ProfileCredentialsProvider.create()).build();
		try {
			// 創建刪除請求
			DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(BUCEKT_NAME).key(imageKey) // 指定要刪除的圖片路徑
					.build();

			// 刪除圖片
			s3.deleteObject(deleteObjectRequest);

			System.out.println("Image deleted successfully: " + imageKey);
			
			s3.close();

		} catch (Exception e) {
			System.err.println("Failed to delete image: " + e.getMessage());
		}
	}
}