package com.booking.controller.activity;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.booking.bean.activity.Activity;
import com.booking.dto.activity.ActivityDTO;
import com.booking.service.activity.ActivityService;
import com.booking.utils.Listable;
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
import com.booking.utils.LocalDateAdapter;

@WebServlet(urlPatterns = { "/activity/create", "/activity/delete", "/activity/get",
		"/activity/update", "/activity/select", "/activity", "/getactivityjson", "/activity/create.jsp",
		"/activity/edit.jsp", "/activity/select.jsp" })
public class ActivityController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ActivityService activityService = new ActivityService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		String[] splitURI = requestURI.split("/");
		String path = splitURI[splitURI.length - 1];

		switch (path) {
		case "create" -> createActivity(request, response);
		case "delete" -> deleteActivity(request, response);
		case "select" -> select(request, response);
		case "get" -> getActivity(request, response);
		case "activity" -> activity(request, response);	
		case "update" -> update(request, response);
		case "create.jsp" -> sendCreateJsp(request, response);
		case "edit.jsp" -> sendEditJsp(request, response);
		case "select.jsp" -> sendSelectJsp(request, response);
		case "getactivityjson" -> getActivityJSON(request, response);

		}
	}


	
//	/**
//	 * 新增
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 */
	private void createActivity(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			String activityName = request.getParameter("activity-name");
			LocalDate startDate = LocalDate.parse(request.getParameter("start-date"));
			LocalDate deadline = LocalDate.parse(request.getParameter("deadline"));
			Integer limitOfTimes = Integer.parseInt(request.getParameter("limit-of-times"));
			String discountCode = request.getParameter("discount-code");
			String activityDetail = request.getParameter("activity-detail");

			Activity createActivity = new Activity(null, activityName, startDate, deadline, limitOfTimes, discountCode,
					activityDetail);
			Result<Integer> result = activityService.insertActivity(createActivity);
			if (!result.isSuccess()) {
				response.getWriter().write(result.getMessage());
				return;
			}
			response.sendRedirect("/booking/activity");
		} catch (Exception e) {
		}
	}
	
//	/**
//	 * 刪除活動
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 */
	private void deleteActivity(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			Integer activityId = Integer.parseInt(request.getParameter("activity-id"));
			Result<String> result = activityService.deleteActivity(activityId);

			request.setAttribute("message", result.getMessage());
			request.getRequestDispatcher("/adminsystem/booking/deleteActivityResult.jsp").forward(request, response);
		} catch (Exception e) {
			request.setAttribute("message", "Error deleting activity: " + e.getMessage());
			request.getRequestDispatcher("/adminsystem/booking/deleteActivityResult.jsp").forward(request, response);
		}
	}

	
//	/**
//	 * 查詢所有活動
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 */
	private void activity(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Result<List<Listable>> activityServiceResult = activityService.getAllActivitys();
		if (!activityServiceResult.isSuccess()) {
			response.getWriter().write(activityServiceResult.getMessage());
			return;
		}
		List<Listable> activities = activityServiceResult.getData();
		request.setAttribute("lists", activities);
		request.setAttribute("pageInfos", ActivityDTO.pageInfos);
		request.setAttribute("listInfos", ActivityDTO.listInfos);
		request.getRequestDispatcher("adminsystem/index.jsp").forward(request, response);
	}

	
	
	
//	/**
//	 * 查詢單筆活動
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 */
	private void getActivity(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try {
			Integer activityId = Integer.parseInt(request.getParameter("activity-id"));
			Result<Activity> result = activityService.getActivity(activityId);

			if (result.isSuccess()) {
				request.setAttribute("activity", result.getData());
				request.getRequestDispatcher("/adminsystem/activity/getActivityResult.jsp").forward(request, response);
			} else {
				request.setAttribute("message", result.getMessage());
				request.getRequestDispatcher("/adminsystem/activity/getActivityResult.jsp").forward(request, response);
			}
		} catch (Exception e) {
			request.setAttribute("message", "查詢活動出錯: " + e.getMessage());
			request.getRequestDispatcher("/adminsystem/activity/getActivityResult.jsp").forward(request, response);
		}
	}



//	/**
//	 * 多重模糊查詢
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws ServletException
//	 * @throws IOException
//	 */
	private void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String activityName = RequestParamUtils.getParameter(request, "activity-name", String.class);
		LocalDate startDate = RequestParamUtils.getParameter(request, "start-date", LocalDate.class);
		LocalDate deadline = RequestParamUtils.getParameter(request, "deadline", LocalDate.class);
		Integer limitOfTimes = RequestParamUtils.getParameter(request, "limit-of-times", Integer.class);
		String discountCode = RequestParamUtils.getParameter(request, "discount-code", String.class);
		String activityDetail = RequestParamUtils.getParameter(request, "activity-detail", String.class);

		Result<List<Listable>> activityServiceResult = activityService.getActivitylike(
				new Activity(activityName, startDate, deadline, limitOfTimes, discountCode, activityDetail));

		if (!activityServiceResult.isSuccess()) {
			response.getWriter().write(activityServiceResult.getMessage());
			return;
		}

		request.setAttribute("lists", activityServiceResult.getData());
		request.setAttribute("pageInfos", ActivityDTO.pageInfos);
		request.setAttribute("listInfos", ActivityDTO.listInfos);
		request.getRequestDispatcher("/adminsystem/index.jsp").forward(request, response);
	}

//	/**
//	 * 更新
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Integer activityId = (Integer) request.getSession().getAttribute("activity-id");
		String activityName = RequestParamUtils.getParameter(request, "activity-name", String.class);
		Integer limitOfTimes = RequestParamUtils.getParameter(request, "limit-of-times", Integer.class);
		String discountCode = RequestParamUtils.getParameter(request, "discount-code", String.class);
		String activityDetail = RequestParamUtils.getParameter(request, "activity-detail", String.class);
		LocalDate startDate = RequestParamUtils.getParameter(request, "start-date", LocalDate.class);
		LocalDate deadline = RequestParamUtils.getParameter(request, "deadline", LocalDate.class);
		Activity activity = new Activity(activityId, activityName, startDate, deadline, limitOfTimes, discountCode,
				activityDetail);
		Result<String> result = activityService.updateActivity(activity);

		if (!result.isSuccess()) {
			response.getWriter().write(result.getMessage());
			return;
		}
		response.sendRedirect("/booking/activity");
	}

//	/**
//	 * 轉去create.jsp
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws ServletException
//	 * @throws IOException
//	 */
	private void sendCreateJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("../adminsystem/activity/activity-create.jsp").forward(request, response);
	}

//	/**
//	 * 轉到查詢
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 */
	private void sendSelectJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("../adminsystem/activity/activity-select.jsp").forward(request, response);
	}

//	/**
//	 * 轉去edit.jsp
//	 * 
//	 * @param request
//	 * @param response
//	 * @throws IOException
//	 * @throws ServletException
//	 * @throws ClassNotFoundException
//	 * @throws Exception
//	 */
	private void sendEditJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer activityId = Integer.parseInt(request.getParameter("activity-id"));
		request.getSession().setAttribute("activity-id", activityId);
		Result<Activity> activityServiceResult = activityService.getActivity(activityId);
		if (!activityServiceResult.isSuccess()) {
			response.getWriter().write("回寫編輯資料失敗");
		}
		request.setAttribute("activity", activityServiceResult.getData());
		request.getRequestDispatcher("../adminsystem/activity/activity-update.jsp").forward(request, response);
	}

	//**
	// * 返回所有activity的JSON數據
	// * 
	// * @param request
	// * @param response
	// * @throws IOException
	// * @throws Exception
	 //*/
	private void getActivityJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer activityId = Integer.parseInt(request.getParameter("activity-id"));

		Result<Activity> activityServiceResult = activityService.getActivity(activityId);
		if (!activityServiceResult.isSuccess()) {
			response.getWriter().write(activityServiceResult.getMessage());
			return;
		}

		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
				.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
		String jsonData = gson.toJson(activityServiceResult.getData());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonData);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

}
