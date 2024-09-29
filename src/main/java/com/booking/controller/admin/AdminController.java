package com.booking.controller.admin;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.context.support.HttpRequestHandlerServlet;

import com.booking.bean.pojo.admin.Admin;
import com.booking.bean.dto.admin.AdminDTO;
import com.booking.service.admin.AdminService;
import com.booking.utils.JsonUtil;
import com.booking.utils.Listable;
import com.booking.utils.Result;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

	/**
	 * 返回指定管理員的 JSON 數據
	 */
	@GetMapping("/json/{id}")
	@ResponseBody
	public String getAdminJSON(@PathVariable("id") Integer adminId) {
		Result<AdminDTO> adminServiceResult = adminService.getAdminById(adminId);
		if (adminServiceResult.isFailure()) {
			return null;
		}
		return JsonUtil.toJson(adminServiceResult.getData());
	}

	/**
	 * 轉到查詢
	 * 
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@GetMapping("/select/jsp")
	private String sendSelectJsp() 
			throws ServletException, IOException {
		return "/adminsystem/admin/admin-select";
	}

	/**
	 * @param model
	 * @return
	 */
	@GetMapping("")
	public String adminIndex(Model model) {
		Result<List<Listable>> adminServiceResult = adminService.getAdminAll();
		if (adminServiceResult.isFailure()) {
			return "";
		}
		List<Listable> admins = adminServiceResult.getData();
		model.addAttribute("lists", admins);
		model.addAttribute("pageInfos", AdminDTO.pageInfos);
		model.addAttribute("listInfos", AdminDTO.listInfos);
		model.addAttribute("manageListName",AdminDTO.manageListName);
		return "adminsystem/index";
	}

	/**
	 * 轉去create.jsp
	 * 
	 * @return
	 */
	@GetMapping("/create/jsp")
	private String sendCreateJsp() throws ServletException, IOException {
		return "/adminsystem/admin/admin-create";
	}

	/**
	 * 轉去edit.jsp
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/jsp")
	private String sendEditJsp(@RequestParam Integer adminId, HttpSession session, Model model) {
		session.setAttribute("adminId", adminId);
		Result<AdminDTO> adminServiceResult = adminService.getAdminById(adminId);

		if (adminServiceResult.isFailure()) {
			return "";
		}

		model.addAttribute("admin", adminServiceResult.getData());
		return "/adminsystem/admin/admin-edit";
	}

//	/**
//	 * 模糊查詢管理員
//	 */
//	@PostMapping("/select")
//	public String selectAdmins(@RequestParam(value = "admin-capacity", required = false) Integer adminId,
//			@RequestParam(value = "admin-account", required = false) String adminAccount,
//			@RequestParam(value = "admin-name", required = false) String adminName,
//			@RequestParam(value = "admin-mail", required = false) String adminMail,
//			@RequestParam(value = "hiredate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate hireDate,
//			@RequestParam(value = "admin-status", required = false) Integer adminStatus, Model model) {
//
//		Admin admin = new Admin(adminId, adminAccount, adminName, adminMail, hireDate, adminStatus);
//		Result<List<Listable>> adminServiceResult = adminService.getAdmins(admin);
//
//		if (!adminServiceResult.isSuccess()) {
//			model.addAttribute("errorMessage", adminServiceResult.getMessage());
//			return "error";
//		}
//
//		model.addAttribute("lists", adminServiceResult.getData());
//		model.addAttribute("pageInfos", AdminDTO.pageInfos);
//		model.addAttribute("listInfos", AdminDTO.listInfos);
//		return "adminsystem/index";
//	}

	
	/**
	 * 
	 * 新增
	 * @param admin
	 * @param model
	 * @param request
	 * @return
	 */
	@PostMapping("/create")
	public String createAdmin(Admin admin, Model model, HttpRequestHandlerServlet request) {
		// 檢查帳號是否已存在
		Result<Admin> checkResult = adminService.checkAccountExists(admin.getAdminAccount());
		if (checkResult.isFailure()) {
			model.addAttribute("errorMessage", "此帳號已存在");
			return "adminsystem/admin/admin-create";
		}
		// 添加新管理員
		Result<Integer> adminServiceResult = adminService.addAdmin(admin);
		if (!adminServiceResult.isSuccess()) {
			model.addAttribute("errorMessage", adminServiceResult.getMessage());
			return "adminsystem/admin/admin-create";
		}

		return "redirect:/admin";
	}

	/**
	 * 刪除管理員
	 */
	@PostMapping("/delete")
	public String deleteAdmin(@RequestParam Integer adminId, Model model) {
		Result<Integer> adminServiceResult = adminService.softRemoveAdmin(adminId);
		if (adminServiceResult.isFailure()) {
			model.addAttribute("errorMessage", adminServiceResult.getMessage());
			return "";
		}
		return "redirect:/admin";
	}

	@PostMapping("/update")
	private String update(Admin admin, @SessionAttribute Integer adminId) {
		admin.setAdminId(adminId);
		Result<String> result = adminService.updateAdmin(admin);

		if (result.isFailure()) {
			return "";
		}
		return "redirect:/admin";
	}

	////////////////////////////////////////////////////

	/**
	 * 顯示登入表單
	 */
	@GetMapping("/login")
	public String showLoginForm() {
		return "adminsystem/admin/login";
	}

	/**
	 * 處理登入請求
	 */
	@PostMapping("/login")
	public String loginAdmin(@RequestParam("admin-account") String adminAccount,
			@RequestParam("admin-password") String adminPassword, Model model, HttpSession session) {
		Result<Admin> loginResult = adminService.loginAdmin(adminAccount, adminPassword);
		if (!loginResult.isSuccess()) {
			model.addAttribute("errorMessage", loginResult.getMessage());
			return "adminsystem/admin/login";
		}
		// 登入成功，將管理員信息存入 Session
		session.setAttribute("admin", loginResult.getData());
		return "redirect:/admin";
	}

	/**
	 * 顯示註冊表單
	 */
	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("admin", new Admin());
		return "adminsystem/admin/register";
	}

	/**
	 * 處理註冊請求
	 */
	@PostMapping("/register")
	public String registerAdmin(@ModelAttribute Admin admin, Model model) {
		Result<Admin> checkResult = adminService.checkAccountExists(admin.getAdminAccount());
		if (!checkResult.isSuccess()) {
			model.addAttribute("errorMessage", checkResult.getMessage());
			return "adminsystem/admin/register";
		}

		Result<Integer> addResult = adminService.addNewAdmin(admin);
		if (!addResult.isSuccess()) {
			model.addAttribute("errorMessage", "註冊失敗");
			return "adminsystem/admin/register";
		}

		return "redirect:/admin/login";
	}

}
