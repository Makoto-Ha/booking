package com.booking.controller.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.booking.bean.admin.Admin;
import com.booking.dto.admin.AdminDTO;
import com.booking.service.admin.AdminService;
import com.booking.utils.Listable;
import com.booking.utils.LocalDateAdapter;
import com.booking.utils.LocalDateTimeAdapter;
import com.booking.utils.RequestParamUtils;
import com.booking.utils.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/admin/create", "/admin/delete", "/admin/update", "/admin/select", "/admin/login",
        "/admin/register", "/admin/create.jsp", "/admin/delete.jsp", "/admin/update.jsp", "/admin/select.jsp", "/admin",
        "/getadminjson", "/admin/edit.jsp" })

public class AdminController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminService adminService = new AdminService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String[] splitURI = requestURI.split("/");
        String path = splitURI[splitURI.length - 1];

        response.setHeader("Access-Control-Allow-Origin", "*");

        switch (path) {
        case "select" -> select(request, response);
        case "create" -> create(request, response);
        case "delete" -> delete(request, response);
        case "update" -> update(request, response);
        case "login" -> {
            if (request.getMethod().equalsIgnoreCase("GET")) {
                request.setAttribute("errorMessage", null);
                request.getRequestDispatcher("/adminsystem/admin/login.jsp").forward(request, response);
            } else if (request.getMethod().equalsIgnoreCase("POST")) {
                login(request, response);
            }
        }
        case "register" -> register(request, response);
        case "admin" -> admin(request, response);
        case "select.jsp" -> sendSelectJsp(request, response);
        case "create.jsp" -> sendCreateJsp(request, response);
        case "edit.jsp" -> sendEditJsp(request, response);
        case "getadminjson" -> getAdminJSON(request, response);
        }
    }

    private void getAdminJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Integer adminId = Integer.parseInt(request.getParameter("admin-id"));
        Result<Admin> adminServiceResult = adminService.getAdminById(adminId);
        if (!adminServiceResult.isSuccess()) {
            response.getWriter().write(adminServiceResult.getMessage());
            return;
        }

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
        String jsonData = gson.toJson(adminServiceResult.getData());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonData);
    }

    private void admin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Result<List<Listable>> adminServiceResult = adminService.getAdminAll();
        if (!adminServiceResult.isSuccess()) {
            response.getWriter().write(adminServiceResult.getMessage());
            return;
        }
        List<Listable> admins = adminServiceResult.getData();
        request.setAttribute("lists", admins);
        request.setAttribute("pageInfos", AdminDTO.pageInfos);
        request.setAttribute("listInfos", AdminDTO.listInfos);
        request.setAttribute("manageListName", AdminDTO.manageListName);
        request.getRequestDispatcher("adminsystem/index.jsp").forward(request, response);
    }

    private void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer adminId = RequestParamUtils.getParameter(request, "admin-capacity", Integer.class);
        String adminAccount = RequestParamUtils.getParameter(request, "admin-account", String.class);
        String adminName = RequestParamUtils.getParameter(request, "admin-name", String.class);
        String adminMail = RequestParamUtils.getParameter(request, "admin-mail", String.class);
        LocalDate hireDate = (LocalDate) RequestParamUtils.getParameterDate(request, "hiredate", LocalDate.class);
        Integer adminStatus = RequestParamUtils.getParameter(request, "admin-status", Integer.class);
        Result<List<Listable>> adminServiceResult = adminService
                .getAdmins(new Admin(adminId, adminAccount, adminName, adminMail, hireDate, adminStatus));

        if (!adminServiceResult.isSuccess()) {
            response.getWriter().write(adminServiceResult.getMessage());
            return;
        }
        request.setAttribute("lists", adminServiceResult.getData());
        request.setAttribute("pageInfos", AdminDTO.pageInfos);
        request.setAttribute("listInfos", AdminDTO.listInfos);
        request.getRequestDispatcher("../adminsystem/index.jsp").forward(request, response);
    }

    private void sendSelectJsp(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("../adminsystem/admin/admin-select.jsp").forward(request, response);
    }

//	/**
//	 * 新增管理員資料
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws ServletException
//	 * @throws IOException
//	 */
	

//	/**
//	 * 新增管理員資料
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws ServletException
//	 * @throws IOException
//	 */
	private void sendCreateJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("../adminsystem/admin/admin-create.jsp").forward(request, response);
	}

    private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminAccount = request.getParameter("admin-account");
        String adminPassword = request.getParameter("admin-password");
        String adminName = request.getParameter("admin-name");
        String adminMail = request.getParameter("admin-mail");
        LocalDate hiredate = LocalDate.parse(request.getParameter("hiredate"));
        Integer adminStatus = Integer.parseInt(request.getParameter("admin-status"));

        Admin admin = new Admin(adminAccount, adminPassword, adminName, adminMail, hiredate, adminStatus);
        
        // 调用 Hibernate Service 检查账号是否存在
        Result<Admin> checkResult = adminService.checkAccountExists(adminAccount);
        if (!checkResult.isSuccess()) {
            request.setAttribute("error-Message", "此帳號已存在" );
            request.getRequestDispatcher("../adminsystem/admin/admin-create.jsp").forward(request, response);
            return;
        }
        
        Result<Integer> adminServiceResult = adminService.addAdmin(admin);
        if (!adminServiceResult.isSuccess()) {
            response.getWriter().write(adminServiceResult.getMessage());
            return;
        }

        response.sendRedirect("/booking/admin");
    }

//	/**
//	 * 刪除房間類型
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Integer adminId = Integer.parseInt(request.getParameter("admin-id"));
		Result<Integer> adminServiceResult = adminService.softRemoveAdmin(adminId);
		if (!adminServiceResult.isSuccess()) {
			response.getWriter().write(adminServiceResult.getMessage());
			return;
		}
		response.sendRedirect("/booking/admin");
	}

//	/**
//	 * 轉去edit.jsp
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws ServletException
//	 * @throws IOException
//	 */
	private void sendEditJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer adminId = Integer.parseInt(request.getParameter("admin-id"));
		request.getSession().setAttribute("admin-id", adminId);
		Result<Admin> adminServiceResult = adminService.getAdminById(adminId);

        if (!adminServiceResult.isSuccess()) {
            response.getWriter().write("回寫編輯資料失敗");
        }

        request.setAttribute("admin", adminServiceResult.getData());
        request.getRequestDispatcher("../adminsystem/admin/admin-edit.jsp").forward(request, response);
    }

//	/**
//	 * 更新員工狀態
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Integer adminId = (Integer) request.getSession().getAttribute("admin-id");
		String adminAccount = RequestParamUtils.getParameter(request, "admin-account", String.class);
		String adminPassword = RequestParamUtils.getParameter(request, "admin-password", String.class);
		String adminName = RequestParamUtils.getParameter(request, "admin-name", String.class);
		String adminMail = RequestParamUtils.getParameter(request, "admin-mail", String.class);
		LocalDate hiredate = LocalDate.parse(request.getParameter("hiredate"));
		Integer adminStatus = (Integer) request.getSession().getAttribute("admin-status");

        Admin admin = new Admin(adminId, adminAccount, adminPassword, adminName, adminMail, hiredate, adminStatus);
        Result<Integer> adminServiceResult = adminService.updateAdmin(admin);
        if (!adminServiceResult.isSuccess()) {
            response.getWriter().write(adminServiceResult.getMessage());
            return;
        }
        response.sendRedirect("/booking/admin");
    }

//	/**
//	 * 登入
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 */
	private void login(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, IndexOutOfBoundsException {
		String adminAccount = request.getParameter("admin-account");
		String adminPassword = request.getParameter("admin-password");

        Result<Admin> loginResult = adminService.loginAdmin(adminAccount, adminPassword);
        if (!loginResult.isSuccess()) {
            request.setAttribute("errorMessage", loginResult.getMessage());
            request.getRequestDispatcher("/adminsystem/admin/login.jsp").forward(request, response);
            return;
        }
        response.sendRedirect("/booking/admin");
    }

//	/**
//	 * 註冊
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 */
	private void register(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String adminAccount = request.getParameter("admin-account");
		String adminPassword = request.getParameter("admin-password");
		String adminName = request.getParameter("admin-name");
		String adminMail = request.getParameter("admin-mail");
		LocalDate hiredate = LocalDate.parse(request.getParameter("hiredate"));
		Integer adminStatus = Integer.parseInt(request.getParameter("admin-status"));

        Admin admin = new Admin(null, adminAccount, adminPassword, adminName, adminMail, hiredate, adminStatus);
        Result<Admin> checkResult = adminService.checkAccountExists(adminAccount);
        if (checkResult.isSuccess()) {
            admin = new Admin(adminAccount, adminPassword, adminName, adminMail, hiredate, adminStatus);
            Result<Integer> addResult = adminService.addNewAdmin(admin);
            if (addResult.isSuccess()) {
                request.setAttribute("successMessage", "註冊成功");
                request.getRequestDispatcher("/adminsystem/admin/login.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "註冊失敗");
                request.getRequestDispatcher("/adminsystem/admin/register.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", checkResult.getMessage());
            request.getRequestDispatcher("/adminsystem/admin/register.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
