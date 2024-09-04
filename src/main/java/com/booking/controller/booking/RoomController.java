package com.booking.controller.booking;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import com.booking.bean.booking.Room;
import com.booking.dto.booking.RoomDTO;
import com.booking.service.booking.RoomService;
import com.booking.utils.Listable;
import com.booking.utils.LocalDateTimeAdapter;
import com.booking.utils.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { 
		"/room/select",
		"/room/create", 
		"/room/update",
		"/room/delete",
		"/room",
		"/getroomjson"
})
public class RoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private RoomService roomService = new RoomService();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String requestURI = request.getRequestURI();
		String[] splitURI = requestURI.split("/");
		String path = splitURI[splitURI.length - 1];
		
		switch (path) {
			case "select" -> select(request, response);
			case "create" -> create(request, response);
			case "update" -> update(request, response);
			case "delete" -> delete(request, response);
			case "room" -> room(request, response);
			case "getroomjson" -> getRoomJSON(request, response);
		}
	}
	
	/**
	 * 房間首頁
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void room(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		Result<List<Listable>> roomServiceResult = roomService.getRoomAll();
		if(!roomServiceResult.isSuccess()) {
			response.getWriter().write(roomServiceResult.getMessage()); 
			return;
		}
		List<Listable> rooms = roomServiceResult.getData();
		request.setAttribute("lists", rooms);
		request.setAttribute("pageInfos", RoomDTO.pageInfos);
		request.setAttribute("listInfos", RoomDTO.listInfos);
		request.getRequestDispatcher("adminsystem/index.jsp").forward(request, response);
	}
	
	/**
	 * 返回room的json數據
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void getRoomJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer roomId = Integer.parseInt(request.getParameter("room-id"));
		Result<Room> roomServiceResult = roomService.getRoom(roomId);
		if(!roomServiceResult.isSuccess()) {
			response.getWriter().write(roomServiceResult.getMessage());
			return;
		}
		
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();
		String jsonData = gson.toJson(roomServiceResult.getData());
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jsonData);
	}
	
	/**
	 * 查找多筆房間
	 * @param request
	 * @param response
	 */
	private void select(HttpServletRequest request, HttpServletResponse response) {
		roomService.getRooms(1);
	}
	
	/**
	 * 創建房間
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void create(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer roomtypeId = Integer.parseInt(request.getParameter("roomtype-id"));
		Integer roomStatus = Integer.parseInt(request.getParameter("room-status"));
		String roomNumber = request.getParameter("room-number");
		String roomDescription = request.getParameter("room-description");
		LocalDateTime updatedTime = LocalDateTime.now();
		LocalDateTime createdTime = LocalDateTime.now();
		
		Result<Integer> roomServiceResult = roomService.addRoom(new Room(roomtypeId, roomNumber, roomStatus, roomDescription, updatedTime, createdTime));
		if(!roomServiceResult.isSuccess()) {
			response.getWriter().write(roomServiceResult.getMessage());
			return;
		}
	}
	
	/**
	 * 刪除房間
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer roomId = Integer.parseInt(request.getParameter("room-id"));

		Result<String> removeRoomtypeResult = roomService.removeRoom(roomId);
		response.getWriter().write(removeRoomtypeResult.getMessage());	
	}

	/**
	 * 更新房間
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer roomId = Integer.parseInt(request.getParameter("room-id"));
		Integer roomtypeId = Integer.parseInt(request.getParameter("roomtype-id"));
		Integer roomStatus = Integer.parseInt(request.getParameter("room-status"));
		String roomNumber = request.getParameter("room-number");
		String roomDescription = request.getParameter("room-description");
		
		roomService.updateRoom(new Room(roomId, roomtypeId, roomNumber, roomStatus, roomDescription));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doGet(request, response);
	}
}
