package com.booking.controller.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.dto.admin.AdminDTO;
import com.booking.bean.pojo.admin.Admin;
import com.booking.service.admin.AdminService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 轉到查詢
	 */
	@GetMapping("/select/page")
	private String sendSelectPage() {
		return "/management-system/admin/admin-select";
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
		model.addAttribute("adminDTO", adminDTO);
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
	 * @param
	 */
	@PostMapping("/create")
	public String saveAdmin(Admin admin) {
		Result<String> saveAdminResult = adminService.saveAdmin(admin);
		if (saveAdminResult.isFailure()) {
			return "";
		}
		return "redirect:/management/admin";
	}

	
	//更新
	@PostMapping("/update")
	private String update(Admin admin, @SessionAttribute Integer adminId) {
		admin.setAdminId(adminId);
		Result<String> updateAdminResult = adminService.updateAdmin(admin);

		if (updateAdminResult.isFailure()) {
			return "";
		}
		return "redirect:/management/admin";
	}

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
