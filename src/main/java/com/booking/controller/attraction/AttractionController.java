package com.booking.controller.attraction;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import com.booking.bean.attraction.Attraction;
import com.booking.dto.attraction.AttractionDTO;
import com.booking.service.attraction.AttractionService;
import com.booking.utils.Listable;
import com.booking.utils.RequestParamUtils;
import com.booking.utils.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { 
		"/attraction/create", 
		"/attraction/delete", 
		"/attraction/update", 
		"/attraction/select",
		"/attraction/create.jsp",
		"/attraction/edit.jsp",
		"/attraction/select.jsp",
		"/attraction",
		"/getattractionjson"})
public class AttractionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private AttractionService attractionService;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		String[] splitURI = requestURI.split("/");
		String path = splitURI[splitURI.length - 1];

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=utf8");
		
		Session session = (Session) request.getAttribute("hibernateSession");
		attractionService = new AttractionService(session);

		switch (path) {
		case "create" -> create(request, response);
		case "delete" -> delete(request, response);
		case "update" -> update(request, response);
		case "select" -> select(request, response);
		case "create.jsp" -> sendCreateJsp(request, response);
		case "edit.jsp" -> sendEditJsp(request, response);
		case "select.jsp" -> sendSelectJsp(request, response);
		case "attraction" -> attraction(request, response);
		case "getattractionjson" -> getAttractionJSON(request, response);
		}
	}

	/**
	 * 返回所有景點的數據
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getAttractionJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer attractionId = Integer.parseInt(request.getParameter("attraction-id"));
		Result<Attraction> attractionServiceResult = attractionService.getAttractionById(attractionId);
		if(attractionServiceResult.isFailure()) {
			response.getWriter().write(attractionServiceResult.getMessage());
			return;
		}
		Gson gson = new GsonBuilder().create();
		String jsonData = gson.toJson(attractionServiceResult.getData());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonData);
	}
	
	
	/**
	 * 轉到景點首頁
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void attraction(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Result<List<Listable>> attractionServiceResult = attractionService.getAttractionAll();
		if(attractionServiceResult.isFailure()) {
			response.getWriter().write(attractionServiceResult.getMessage()); 
			return;
		}
		List<Listable> attractions = attractionServiceResult.getData();
		request.setAttribute("lists", attractions);
		request.setAttribute("pageInfos", AttractionDTO.pageInfos);
		request.setAttribute("listInfos", AttractionDTO.listInfos);
		request.setAttribute("manageListName", AttractionDTO.manageListName);
		request.getRequestDispatcher("adminsystem/index.jsp").forward(request, response);
	}
	
	/**
	 * 轉到查詢
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void sendSelectJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		request.getRequestDispatcher("../adminsystem/attraction/attraction-select.jsp").forward(request, response);
	}

	
	/**
	 * 轉去create.jsp
	 * @param request
	 * @param response
	 * @throws ServletExceptions
	 * @throws IOException
	 */
	private void sendCreateJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("../adminsystem/attraction/attraction-create.jsp").forward(request, response);
	}
	

	/**
	 * 轉去edit.jsp
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void sendEditJsp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer attractionId = Integer.parseInt(request.getParameter("attraction-id"));
		request.getSession().setAttribute("attraction-id", attractionId);
		Result<Attraction> attractionServiceResult = attractionService.getAttractionById(attractionId);
		
		if(attractionServiceResult.isFailure()) {
			response.getWriter().write("回寫編輯資料失敗");
			return;
		}
		
		request.setAttribute("attraction", attractionServiceResult.getData());
		request.getRequestDispatcher("../adminsystem/attraction/attraction-edit.jsp").forward(request, response);
	}
	
	/**
	 * 模糊查詢
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String attractionName = RequestParamUtils.getParameter(request, "attraction-name", String.class);
		String attractionCity = RequestParamUtils.getParameter(request, "attraction-city", String.class);
		String address = RequestParamUtils.getParameter(request, "address", String.class);
		String openingHours = RequestParamUtils.getParameter(request, "opening-hours", String.class);
		String attractionType = RequestParamUtils.getParameter(request, "attraction-type", String.class);
		String attractionDescription = RequestParamUtils.getParameter(request, "attraction-description", String.class);
		Result<List<Listable>> attractionServiceResult = attractionService.getAttractions(
						new Attraction(
						attractionName,
						attractionCity,
						address,
						openingHours,
						attractionType,
						attractionDescription));
		
		if(attractionServiceResult.isFailure()) {
			response.getWriter().write(attractionServiceResult.getMessage());
			return;
		}
		
		request.setAttribute("lists", attractionServiceResult.getData());
		request.setAttribute("pageInfos", AttractionDTO.pageInfos);
		request.setAttribute("listInfos", AttractionDTO.listInfos);
		request.getRequestDispatcher("../adminsystem/index.jsp").forward(request, response);	
	}
	
	/**
	 * 新增景點
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String attractionName = RequestParamUtils.getParameter(request, "attraction-name", String.class);
		String attractionCity = RequestParamUtils.getParameter(request, "attraction-city", String.class);
		String address = RequestParamUtils.getParameter(request, "address", String.class);
		String openingHour = RequestParamUtils.getParameter(request, "opening-hour", String.class);
		String attractionType = RequestParamUtils.getParameter(request, "attraction-type", String.class);
		String attractionDescription = RequestParamUtils.getParameter(request, "attraction-description", String.class);

		Attraction attraction = new Attraction(attractionName,attractionCity , address, openingHour,
				attractionType, attractionDescription);
		Result<Integer> result = attractionService.addAttraction(attraction);
		if (result.isFailure()) {
			response.getWriter().write(result.getMessage());
			return;
		}
		response.sendRedirect("/booking/attraction");
		
	}

	/**
	 * 刪除景點
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer attractionId = Integer.parseInt(request.getParameter("attraction-id"));

		Result<String> result = attractionService.removeAttraction(attractionId);
		response.getWriter().write(result.getMessage());
	}

	/**
	 * 更新景點
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {

		Integer attractionId = (Integer) request.getSession().getAttribute("attraction-id");
		String attractionName = RequestParamUtils.getParameter(request, "attraction-name", String.class);
		String attractionCity = RequestParamUtils.getParameter(request, "attraction-city", String.class);
		String address = RequestParamUtils.getParameter(request, "address", String.class);
		String openingHour = RequestParamUtils.getParameter(request, "opening-hour", String.class);
		String attractionType = RequestParamUtils.getParameter(request, "attraction-type", String.class);
		String attractionDescription = RequestParamUtils.getParameter(request, "attraction-description", String.class);
		Attraction attraction = new Attraction(attractionId, attractionName, attractionCity, address,
				openingHour, attractionType, attractionDescription);
		Result<String> result = attractionService.updateAttraction(attraction);
		
		if(result.isFailure()) {
			response.getWriter().write(result.getMessage());
			return;
		}
		response.sendRedirect("/booking/attraction");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
