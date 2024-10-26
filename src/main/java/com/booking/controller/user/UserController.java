package com.booking.controller.user;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;



import com.booking.bean.dto.user.UserDTO;

import com.booking.service.user.UserService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

	@Controller
	@RequestMapping("/management/user")
	public class UserController {

		@Autowired
		private UserService userService;


		/**
		 * 轉到查詢
		 */
		@GetMapping("/select/page")
		private String sendSelectPage() {
			return "/management-system/user/user-select";
		}

		/**
		 * 轉到管理員首頁
		 * 
		 * @param model
		 */
		@GetMapping
		private String user(Model model) {

			UserDTO userDTO = new UserDTO();
			Result<PageImpl<UserDTO>> findUserAllResult = userService.findUserAll(userDTO);
			if (findUserAllResult.isFailure()) {
				return "";
			}
			Page<UserDTO> page = findUserAllResult.getData();
			model.addAttribute("userDTO", userDTO);
			model.addAttribute("page", page);
			return "management-system/user/user-list";
		}

		/**
		 * 轉去create-page
		 */
		@GetMapping("/create/page")
		private String sendCreatePage() {
			return "management-system/user/user-create";
		}

		/**
		 * 轉去edit-page
		 * 
		 * @param
		 * @param model
		 */
		@GetMapping("/edit/page")
		private String sendEditPage(@RequestParam Integer userId, HttpSession session, Model model) {
			session.setAttribute("userId", userId);
			Result<UserDTO> userServiceResult = userService.findUserById(userId);

			if (userServiceResult.isFailure()) {
				return "";
			}

			model.addAttribute("user", userServiceResult.getData());
			return "/management-system/user/user-edit";
		}

		/**
		 * 模糊查詢
		 * 
		 * @param
		 * @param
		 */
		@GetMapping("/select")
		private String select(@RequestParam Map<String, String> requestParameters, UserDTO userDTO, Model model) {

			Result<PageImpl<UserDTO>> userServiceResult = userService.findUsers(userDTO);

			if (userServiceResult.isFailure()) {
				return "";
			}

			Page<UserDTO> page = userServiceResult.getData();

			model.addAttribute("requestParameters", requestParameters);
			
			model.addAttribute("userDTO", userDTO);
		
			model.addAttribute("page", page);

			return "/management-system/user/user-list";
		}

		/**
		 * 刪除管理員
		 */
		@PostMapping("/delete")
		private ResponseEntity<?> deleteUserById(@RequestParam Integer userId) {
			Result<String> deleteUserResult = userService.deledeUserById(userId);
			String message = deleteUserResult.getMessage();
			if(deleteUserResult.isFailure()) {
				return ResponseEntity.badRequest().body(message);
			}
			
			return ResponseEntity.ok(message);
		}

		/**
		 * 新增管理員
		 * 
		 * @param
		 */
		@PostMapping("/create")
		private String saveUser(UserDTO userDTO,@RequestParam(required = false) MultipartFile imageFile) {
			Result<String> saveUserResult = userService.saveUser(userDTO, imageFile);
			if (saveUserResult.isFailure()) {
				return "";
			}
			return "redirect:/management/user";
			
		}

		
		/**
		 * 更新
		 * @param attraction
		 * @param attractionId
		 */
		@PostMapping("/update")
		private String updateAdminById(UserDTO userDTO, @SessionAttribute Integer userId,
				@RequestParam(required = false) MultipartFile imageFile ) {
			userDTO.setUserId(userId);
			Result<String> updateUserResult = userService.updateUser(userDTO, imageFile);
			
			if(updateUserResult.isFailure()) {
				return "";
			}
			return "redirect:/management/user";
		}
		
		
		/**
		 * 根據ID上傳圖片
		 * @param imageFile
		 * @param attractionId
		 * @return
		 */
		@PostMapping("/upload")
		public ResponseEntity<String> uploadImageById(@RequestParam MultipartFile imageFile, Integer userId) {
			Result<String> uploadImageResult = userService.uploadImageById(imageFile, userId);
			String message = uploadImageResult.getMessage();
			if(uploadImageResult.isFailure()) {
				return ResponseEntity.badRequest().body(message);
			}
			
			return ResponseEntity.ok(message);
		}
		
		
		
		/**
		 * 根據ID取得圖片
		 * @param attractionId
		 * @return
		 * @throws IOException
		 */
		@GetMapping("/image/{userId}")
		public ResponseEntity<?> findImageById(@PathVariable Integer userId) throws IOException {
			Result<UrlResource> findImageByIdResult = userService.findImageById(userId);
			
			if(findImageByIdResult.isFailure()) {
				return ResponseEntity.badRequest().body(findImageByIdResult.getMessage());
			}
			
			Path path = (Path) findImageByIdResult.getExtraData("path");
			UrlResource resource = findImageByIdResult.getData();

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
					.body(resource);
					
		}

	}
	
	



