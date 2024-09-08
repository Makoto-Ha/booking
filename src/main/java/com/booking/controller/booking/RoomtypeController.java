package com.booking.controller.booking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Session;

import com.booking.bean.booking.Roomtype;
import com.booking.dto.booking.RoomtypeDTO;
import com.booking.service.booking.RoomtypeService;
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

@WebServlet(urlPatterns = { "/roomtype/create", "/roomtype/delete", "/roomtype/update", "/roomtype/select",
		"/roomtype/create.jsp", "/roomtype/edit.jsp", "/roomtype/select.jsp", "/roomtype", "/getroomtypejson" })
public class RoomtypeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomtypeService roomtypeService;;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		String[] splitURI = requestURI.split("/");
		String path = splitURI[splitURI.length - 1];

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html; charset=utf8");
		
		Session session = (Session) request.getAttribute("hibernateSession");
		roomtypeService = new RoomtypeService(session);
		
		switch (path) {
		case "select" -> select(request, response);
		case "create" -> create(request, response);
		case "delete" -> delete(request, response);
		case "update" -> update(request, response);
		case "roomtype" -> sendRoomtypeIndex(request, response);
		case "create.jsp" -> sendCreateJsp(request, response);
		case "edit.jsp" -> sendEditJsp(request, response);
		case "select.jsp" -> sendSelectJsp(request, response);
		case "getroomtypejson" -> getRoomtypeJSON(request, response);
		}
	}

	/**
	 * 返回所有roomtype的JSON數據
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getRoomtypeJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer roomtypeId = Integer.parseInt(request.getParameter("roomtype-id"));
		Result<Roomtype> roomtypeServiceResult = roomtypeService.getRoomtype(roomtypeId);
		if (roomtypeServiceResult.isFailure()) {
			response.getWriter().write(roomtypeServiceResult.getMessage());
			return;
		}

		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
		String jsonData = gson.toJson(roomtypeServiceResult.getData());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonData);
	}

	/**
	 * 轉到查詢
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void sendSelectJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("../adminsystem/booking/roomtype-select.jsp").forward(request, response);
	}

	/**
	 * 房間類型管理首頁
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void sendRoomtypeIndex(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String requestPage = request.getParameter("switch-page");
		Integer switchPage = Integer.parseInt(requestPage != null ? requestPage : "1");

		Result<List<Listable>> roomtypeServiceResult = roomtypeService.getRoomtypeAll(switchPage);
		if (roomtypeServiceResult.isFailure()) {
			response.getWriter().write(roomtypeServiceResult.getMessage());
			return;
		}

		List<Listable> roomtypes = roomtypeServiceResult.getData();
		Map<String, Integer> pageNumber = new HashMap<>();
		pageNumber.put("currentPage", switchPage);
		
		int totalCounts = roomtypes.isEmpty() ? 0 : roomtypes.get(0).getTotalCounts();
		int pageSize = 10; // 每頁顯示的記錄數量
		int totalPages = (totalCounts + pageSize - 1) / pageSize; // 向上取整的頁數計算
		
		pageNumber.put("totalPages", totalPages);
		request.setAttribute("lists", roomtypes);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("attrOrderBy", "roomtype_price");
		request.setAttribute("pageInfos", RoomtypeDTO.pageInfos);
		request.setAttribute("listInfos", RoomtypeDTO.listInfos);
		request.setAttribute("manageListName", RoomtypeDTO.manageListName);
		request.getRequestDispatcher("adminsystem/index.jsp").forward(request, response);
	}

	/**
	 * 轉去create.jsp
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void sendCreateJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("../adminsystem/booking/roomtype-create.jsp").forward(request, response);
	}

	/**
	 * 轉去edit.jsp
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void sendEditJsp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer roomtypeId = Integer.parseInt(request.getParameter("roomtype-id"));
		request.getSession().setAttribute("roomtype-id", roomtypeId);
		Result<Roomtype> roomtypeServiceResult = roomtypeService.getRoomtype(roomtypeId);

		if (roomtypeServiceResult.isFailure()) {
			response.getWriter().write("回寫編輯資料失敗");
		}

		request.setAttribute("roomtype", roomtypeServiceResult.getData());
		request.getRequestDispatcher("../adminsystem/booking/roomtype-edit.jsp").forward(request, response);
	}

	/**
	 * 多重模糊查詢
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomtypeName = RequestParamUtils.getParameter(request, "roomtype-name", String.class);
		Integer roomtypeCapacity = RequestParamUtils.getParameter(request, "roomtype-capacity", Integer.class);
		Integer roomtypePrice = RequestParamUtils.getParameter(request, "roomtype-price", Integer.class);
		Integer roomtypeQuantity = RequestParamUtils.getParameter(request, "roomtype-quantity", Integer.class);
		String roomtypeDescription = RequestParamUtils.getParameter(request, "roomtype-description", String.class);
		String roomtypeAddress = RequestParamUtils.getParameter(request, "roomtype-address", String.class);
		String roomtypeCity = RequestParamUtils.getParameter(request, "roomtype-city", String.class);
		String roomtypeDistrict = RequestParamUtils.getParameter(request, "roomtype-district", String.class);
		
		String requestPage = request.getParameter("switch-page");
		Integer switchPage = Integer.parseInt(requestPage != null ? requestPage : "1");
		
		String roomtypeMaxMoney = Optional.ofNullable(request.getParameter("max-money")).orElse("0");
		Integer maxMoney = Optional.of(roomtypeMaxMoney)
		                           .map(Integer::parseInt)
		                           .filter(money -> money != 0)
		                           .orElse(null);
	
		String roomtypeMinMoney = Optional.ofNullable(request.getParameter("min-money")).orElse("0");
		Integer minMoney = Optional.of(roomtypeMinMoney)
		                           .map(Integer::parseInt)
		                           .filter(money -> money != 0)
		                           .orElse(null);
		String attrOrderBy = RequestParamUtils.getParameter(request, "attr-order-by", String.class);
		attrOrderBy = attrOrderBy != null ? attrOrderBy : "roomtypePrice";
		
		String selectedSort = RequestParamUtils.getParameter(request, "selected-sort", String.class);
		selectedSort = selectedSort != null ? selectedSort : "asc";
		
		Roomtype roomtype = new Roomtype(roomtypeName, roomtypeCapacity, roomtypePrice, roomtypeQuantity, roomtypeDescription,roomtypeAddress,roomtypeCity,roomtypeDistrict);
		Map<String, Object> extraValues = new HashMap<>();
		extraValues.put("maxMoney", maxMoney);
		extraValues.put("minMoney", minMoney);
		extraValues.put("switchPage", switchPage);
		extraValues.put("attrOrderBy", attrOrderBy);
		extraValues.put("selectedSort", selectedSort);
		Result<List<Listable>> roomtypeServiceResult = roomtypeService.getRoomtypes(roomtype, extraValues);
		List<Listable> roomtypes = roomtypeServiceResult.getData();
		if(roomtypeServiceResult.isFailure()) {
			response.getWriter().write(roomtypeServiceResult.getMessage());
			return;
		}
		Map<String, Object> requestParameters = new HashMap<>();
		requestParameters.put("paramters", roomtype);
		requestParameters.put("extraValues", extraValues);
		
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
		String jsonData = gson.toJson(requestParameters);
		
		Map<String, Integer> pageNumber = new HashMap<>();
		pageNumber.put("currentPage", switchPage);
		Integer totalCounts = roomtypes.isEmpty() ? 0 : roomtypes.get(0).getTotalCounts();
		int pageSize = 10; // 每頁顯示的記錄數量
		Integer totalPages = (totalCounts + pageSize - 1) / pageSize; // 向上取整的頁數計算
		pageNumber.put("totalPages", totalPages);
		request.setAttribute("attrOrderBy", attrOrderBy);
		request.setAttribute("selectedSort", selectedSort);
		request.setAttribute("requestParameters", jsonData);
		request.setAttribute("pageNumber", pageNumber);
		request.setAttribute("lists", roomtypes);
		request.setAttribute("pageInfos", RoomtypeDTO.pageInfos);
		request.setAttribute("listInfos", RoomtypeDTO.listInfos);
		request.setAttribute("manageListName", RoomtypeDTO.manageListName);
		request.getRequestDispatcher("../adminsystem/index.jsp").forward(request, response);	
	}

	/**
	 * 新建單筆房間類型
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String roomtypeName = RequestParamUtils.getParameter(request, "roomtype-name", String.class);
		Integer roomtypeCapacity = RequestParamUtils.getParameter(request, "roomtype-capacity", Integer.class);
		Integer roomtypePrice = RequestParamUtils.getParameter(request, "roomtype-price", Integer.class);
		Integer roomtypeQuantity = RequestParamUtils.getParameter(request, "roomtype-quantity", Integer.class);
		String roomtypeDescription = RequestParamUtils.getParameter(request, "roomtype-description", String.class);
		String roomtypeAddress = RequestParamUtils.getParameter(request, "roomtype-address", String.class);
		String roomtypeCity = RequestParamUtils.getParameter(request, "roomtype-city", String.class);
		String roomtypeDistrict = RequestParamUtils.getParameter(request, "roomtype-district", String.class);
		LocalDateTime updatedTime = LocalDateTime.now();
		LocalDateTime createdTime = LocalDateTime.now();

		Roomtype roomtype = new Roomtype(roomtypeName, roomtypeCapacity, roomtypePrice, roomtypeQuantity,
				roomtypeDescription, roomtypeAddress, roomtypeCity, roomtypeDistrict, updatedTime, createdTime);
		Result<Integer> result = roomtypeService.addRoomtype(roomtype);
		if (result.isFailure()) {
			response.getWriter().write(result.getMessage());
			return;
		}
		response.sendRedirect("/booking/roomtype");
	}

	/**
	 * 刪除房間類型
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer roomtypeId = Integer.parseInt(request.getParameter("roomtype-id"));

		Result<String> removeRoomtypeResult = roomtypeService.removeRoomtype(roomtypeId);
		response.getWriter().write(removeRoomtypeResult.getMessage());
	}

	/**
	 * 更新房間類型
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Integer roomtypeId = (Integer) request.getSession().getAttribute("roomtype-id");
		String roomtypeName = RequestParamUtils.getParameter(request, "roomtype-name", String.class);
		Integer roomtypeCapacity = RequestParamUtils.getParameter(request, "roomtype-capacity", Integer.class);
		Integer roomtypePrice = RequestParamUtils.getParameter(request, "roomtype-price", Integer.class);
		Integer roomtypeQuantity = RequestParamUtils.getParameter(request, "roomtype-quantity", Integer.class);
		String roomtypeDescription = RequestParamUtils.getParameter(request, "roomtype-description", String.class);
		String roomtypeAddress = RequestParamUtils.getParameter(request, "roomtype-address", String.class);
		String roomtypeCity = RequestParamUtils.getParameter(request, "roomtype-city", String.class);
		String roomtypeDistrict = RequestParamUtils.getParameter(request, "roomtype-district", String.class);
		Roomtype roomtype = new Roomtype(roomtypeId, roomtypeName, roomtypeCapacity, roomtypePrice, roomtypeQuantity,
				roomtypeDescription, roomtypeAddress, roomtypeCity, roomtypeDistrict);
		Result<String> result = roomtypeService.updateRoomtype(roomtype);

		if (result.isFailure()) {
			response.getWriter().write(result.getMessage());
			return;
		}
		response.sendRedirect("/booking/roomtype");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
