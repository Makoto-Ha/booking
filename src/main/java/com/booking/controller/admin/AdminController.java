package com.booking.controller.admin;

import java.io.IOException;
<<<<<<< HEAD
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
=======
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
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

<<<<<<< HEAD
import com.booking.bean.dto.admin.AdminDTO;
import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.bean.pojo.admin.Admin;
=======
import com.booking.bean.pojo.admin.Admin;
import com.booking.bean.pojo.attraction.Attraction;
import com.booking.bean.dto.admin.AdminDTO;
import com.booking.bean.dto.attraction.AttractionDTO;
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
import com.booking.service.admin.AdminService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

<<<<<<< HEAD
    @Autowired
    private JavaMailSender mailSender;

    // 显示注册页面
    @GetMapping("/register")
    public String showRegisterPage() {
        return "management-system/admin/register"; // register.html
    }

    // 处理注册请求
    @PostMapping("/register")
    public String register(@ModelAttribute Admin admin, Model model) {
    	System.out.println(admin);
        try {
            adminService.register(admin);
            model.addAttribute("message", "Registration successful");
            return "management-system/admin/login"; // 注册成功后跳转到登录页面
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "management-system/admin/register";
        }
    }

    // 显示登录页面
    @GetMapping("/login")
    public String showLoginPage() {
        return "management-system/admin/login"; // login.html
    }

    // 处理登录请求
    @PostMapping("/login")
    public String login(@RequestParam String adminAccount, @RequestParam String adminPassword, Model model) {
        try {
            adminService.login(adminAccount, adminPassword);
            model.addAttribute("message", "Login successful");
            return "redirect:/management/admin"; // 登录成功跳转主页
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "management-system/admin/login";
        }
    }

    // 显示忘记密码页面
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "management-system/admin/forgot-password"; // forgot-password.html
    }

    // 处理忘记密码请求
    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        try {
            String resetToken = adminService.createResetToken(email);
            String resetLink = "http://localhost:8080/auth/reset-password?token=" + resetToken;
            sendResetEmail(email, resetLink);
            model.addAttribute("message", "Password reset link sent to your email.");
            return "forgot-password";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "forgot-password";
        }
    }

    // 显示重置密码页面
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password"; // reset-password.html
    }

    // 处理重置密码请求
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, Model model) {
        try {
            adminService.resetPassword(token, newPassword);
            model.addAttribute("message", "Password reset successful.");
            return "login"; // 重置成功跳转登录页面
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "reset-password";
        }
    }

    // 发送密码重置邮件
    private void sendResetEmail(String to, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Password Reset Request");
        message.setText("Click the link below to reset your password:\n" + resetLink);
        mailSender.send(message);
    }
	

	
	//////////////////////////////////////////////////////////////////////////////////////////////
=======
	/**
	 * 轉到管理員首頁
	 * 
	 * @param model
	 */
	@GetMapping
	private String admin(Model model) {

		AdminDTO adminDTO = new AdminDTO();
		Result<PageImpl<AdminDTO>> findAdminAllResult = adminService.findAdminAll(adminDTO);
		if (findAdminAllResult.isFailure()) {
			return "";
		}
		Page<AdminDTO> page = findAdminAllResult.getData();
		model.addAttribute("adminDTO", adminDTO);
		model.addAttribute("page", page);
		return "management-system/admin/admin-list";
	}
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))

	/**
	 * 轉到查詢
	 */
	@GetMapping("/select/page")
	private String sendSelectPage() {
		return "/management-system/admin/admin-select";
<<<<<<< HEAD
	}

	/**
	 * 轉到管理員首頁
	 * 
	 * @param model
	 */
	@GetMapping
	private String admin(Model model) {

		AdminDTO adminDTO = new AdminDTO();
		Result<PageImpl<AdminDTO>> findAdminAllResult = adminService.findAdminAll(adminDTO);
		if (findAdminAllResult.isFailure()) {
			return "";
		}
		Page<AdminDTO> page = findAdminAllResult.getData();
		model.addAttribute("adminDTO", adminDTO);
		model.addAttribute("page", page);
		return "management-system/admin/admin-list";
	}

	/**
	 * 轉去create-page
	 */
	@GetMapping("/create/page")
	private String sendCreatePage() {
		return "management-system/admin/admin-create";
	}

	/**
	 * 轉去edit-page
	 * 
	 * @param
	 * @param model
	 */
=======
	}

	
	/**
	 * 轉去create-page
	 */
	@GetMapping("/create/page")
	private String sendCreatePage() {
		return "management-system/admin/admin-create";
	}

	/**
	 * 轉去edit-page
	 * 
	 * @param
	 * @param model
	 */
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
	@GetMapping("/edit/page")
	private String sendEditPage(@RequestParam Integer adminId, HttpSession session, Model model) {
		session.setAttribute("adminId", adminId);
		Result<AdminDTO> adminServiceResult = adminService.findAdminById(adminId);

		if (adminServiceResult.isFailure()) {
			return "";
		}

		model.addAttribute("admin", adminServiceResult.getData());
		return "/management-system/admin/admin-edit";
	}

	/**
	 * 模糊查詢
	 * 
	 * @param
	 * @param
	 */
	@GetMapping("/select")
	private String select(@RequestParam Map<String, String> requestParameters, AdminDTO adminDTO, Model model) {

		Result<PageImpl<AdminDTO>> adminServiceResult = adminService.findAdmins(adminDTO);

		if (adminServiceResult.isFailure()) {
			return "";
		}

		Page<AdminDTO> page = adminServiceResult.getData();

		model.addAttribute("requestParameters", requestParameters);
<<<<<<< HEAD
		
		model.addAttribute("adminDTO", adminDTO);
	
=======
		model.addAttribute("adminDTO", adminDTO);
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
		model.addAttribute("page", page);

		return "/management-system/admin/admin-list";
	}

	/**
	 * 刪除管理員
	 */
	@PostMapping("/delete")
	public ResponseEntity<?> deleteAdmin(@RequestParam Integer adminId) {
		Result<String> adminServiceResult = adminService.softRemoveAdmin(adminId);
		String message = adminServiceResult.getMessage();
		if (adminServiceResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}

		return ResponseEntity.ok(message);
	}

	/**
	 * 新增管理員
	 * 
<<<<<<< HEAD
	 * @param
	 */
	@PostMapping("/create")
	private String saveAdmin(AdminDTO adminDTO,@RequestParam(required = false) MultipartFile imageFile) {
		Result<String> saveAdminResult = adminService.saveAdmin(adminDTO, imageFile);
		if (saveAdminResult.isFailure()) {
			return "";
		}
		return "redirect:/management/admin";
		
	}

	
	/**
	 * 更新
	 * @param attraction
	 * @param attractionId
	 */
=======
	 * @param 
	 */
	   @PostMapping("/create")
	    public String createAdmin(@ModelAttribute Admin admin, Model model) {
	        if (adminService.checkAccountExists(admin.getAdminAccount()) != null) {
	            model.addAttribute("errorMessage", "此帳號已存在");
	            return "management-system/admin/admin-create";
	        }
	        adminService.saveAdmin(admin);
	        return "redirect:/management/admin";
	    }
	
	

>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
	@PostMapping("/update")
	private String updateAdminById(AdminDTO adminDTO, @SessionAttribute Integer adminId,
			@RequestParam(required = false) MultipartFile imageFile ) {
		adminDTO.setAdminId(adminId);
		Result<String> updateAdminResult = adminService.updateAdmin(adminDTO, imageFile);
		
		if(updateAdminResult.isFailure()) {
			return "";
		}
		return "redirect:/management/admin";
	}
<<<<<<< HEAD
	
	
	/**
	 * 根據ID上傳圖片
	 * @param imageFile
	 * @param attractionId
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseEntity<String> uploadImageById(@RequestParam MultipartFile imageFile, Integer adminId) {
		Result<String> uploadImageResult = adminService.uploadImageById(imageFile, adminId);
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
	@GetMapping("/image/{adminId}")
	public ResponseEntity<?> findImageById(@PathVariable Integer adminId) throws IOException {
		Result<UrlResource> findImageByIdResult = adminService.findImageById(adminId);
		
		if(findImageByIdResult.isFailure()) {
			return ResponseEntity.badRequest().body(findImageByIdResult.getMessage());
		}
		
		Path path = (Path) findImageByIdResult.getExtraData("path");
		UrlResource resource = findImageByIdResult.getData();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.body(resource);
				
	}
=======

>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
	//////////////////////////////////////////////////////////////////////////////
//
//	/**
//	 * 顯示登入表單
//	 */
//	@GetMapping("/login")
//	public String showLoginForm() {
//		return "adminsystem/admin/login";
//	}
//
//	/**
//	 * 處理登入請求
//	 */
//	@PostMapping("/login")
//	public String loginAdmin(@RequestParam("admin-account") String adminAccount,
//			@RequestParam("admin-password") String adminPassword, Model model, HttpSession session) {
//		Result<Admin> loginResult = adminService.loginAdmin(adminAccount, adminPassword);
//		if (!loginResult.isSuccess()) {
//			model.addAttribute("errorMessage", loginResult.getMessage());
//			return "adminsystem/admin/login";
//		}
//		// 登入成功，將管理員信息存入 Session
//		session.setAttribute("admin", loginResult.getData());
//		return "redirect:/admin";
//	}
//
//	/**
//	 * 顯示註冊表單
//	 */
//	@GetMapping("/register")
//	public String showRegisterForm(Model model) {
//		model.addAttribute("admin", new Admin());
//		return "adminsystem/admin/register";
//	}
//
//	/**
//	 * 處理註冊請求
//	 */
//	@PostMapping("/register")
//	public String registerAdmin(@ModelAttribute Admin admin, Model model) {
//		Result<Admin> checkResult = adminService.checkAccountExists(admin.getAdminAccount());
//		if (!checkResult.isSuccess()) {
//			model.addAttribute("errorMessage", checkResult.getMessage());
//			return "adminsystem/admin/register";
//		}
//
//		Result<Integer> addResult = adminService.addNewAdmin(admin);
//		if (!addResult.isSuccess()) {
//			model.addAttribute("errorMessage", "註冊失敗");
//			return "adminsystem/admin/register";
//		}
//
//		return "redirect:/admin/login";
//	}

}
