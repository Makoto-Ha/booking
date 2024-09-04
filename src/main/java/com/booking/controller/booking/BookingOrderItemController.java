package com.booking.controller.booking;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.booking.bean.booking.BookingOrderItem;
import com.booking.service.booking.BookingOrderItemService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { 
		"/orderitem/select",
		"/orderitem/create",
		"/orderitem/delete",
		"/orderitem/deletebybookingid",
		"/orderitem/update"
})
public class BookingOrderItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookingOrderItemService bookingOrderItemService = new BookingOrderItemService();

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestURI = request.getRequestURI();
		String[] splitURI = requestURI.split("/");
		String path = splitURI[splitURI.length - 1];
		response.setCharacterEncoding("utf8");
		response.setContentType("text/html; charset=utf8");
		
		switch (path) {
			case "select" -> select(request, response);
			case "create" -> create(request, response);
			case "delete" -> delete(request,response);
			case "deletebybookingid" -> deleteByBookingId(request, response);
			case "update" -> update(request, response);
		}
	}
	
	/**
	 * 查找多筆房間預定紀錄
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	private void select(HttpServletRequest request, HttpServletResponse response) throws IOException {
		bookingOrderItemService.getBookingOrderItem(1, 1);
	}
	
	/**
	 * 新增房間預定紀錄
	 * @param request
	 * @param response
	 */
	private void create(HttpServletRequest request, HttpServletResponse response) {
		Integer bookingId = Integer.parseInt(request.getParameter("booking-id"));
		Integer roomId = Integer.parseInt(request.getParameter("room-id"));
		Integer roomtypeId = Integer.parseInt(request.getParameter("roomtype-id"));
		Integer price = Integer.parseInt(request.getParameter("price"));
		Integer quantity = Integer.parseInt(request.getParameter("quantity"));
		LocalDate checkInDate = LocalDate.parse(request.getParameter("check-in-date"));
		LocalDate checkOutDate = LocalDate.parse(request.getParameter("check-out-date"));
		LocalDateTime checkInTime = LocalDateTime.parse(request.getParameter("check-in-time"));
		LocalDateTime checkOutTime = LocalDateTime.parse(request.getParameter("check-out-time"));
		BookingOrderItem bookingOrderItem = new BookingOrderItem(bookingId, roomId, roomtypeId, price, quantity, checkInDate, checkOutDate, checkInTime, checkOutTime);
		bookingOrderItemService.addBookingOrderItem(bookingOrderItem);
	}
	
	/**
	 * 刪除單筆房間預定紀錄
	 * @param request
	 * @param response
	 */
	private void delete(HttpServletRequest request, HttpServletResponse response) {
		Integer bookingId = Integer.parseInt(request.getParameter("booking-id"));
		Integer roomId = Integer.parseInt(request.getParameter("room-id"));
		
		bookingOrderItemService.removeBookingOrderItem(bookingId, roomId);
	}
	
	/**
	 * 刪除多筆房間預定紀錄
	 * @param request
	 * @param response
	 */
	private void deleteByBookingId(HttpServletRequest request, HttpServletResponse response) {
		Integer bookingId = Integer.parseInt(request.getParameter("booking-id"));
		
		bookingOrderItemService.removeBookingOrderItems(bookingId);
	}

	/**
	 * 更新房間預定紀錄
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer bookingId = Integer.parseInt(request.getParameter("booking-id"));
		Integer roomId = Integer.parseInt(request.getParameter("room-id"));
		Integer roomtypeId = Integer.parseInt(request.getParameter("roomtype-id"));
		Integer price = Integer.parseInt(request.getParameter("price"));
		Integer quantity = Integer.parseInt(request.getParameter("quantity"));
		LocalDate checkInDate = LocalDate.parse(request.getParameter("check-in-date"));
		LocalDate checkOutDate = LocalDate.parse(request.getParameter("check-out-date"));
		LocalDateTime checkInTime = LocalDateTime.parse(request.getParameter("check-in-time"));
		LocalDateTime checkOutTime = LocalDateTime.parse(request.getParameter("check-out-time"));
		
		BookingOrderItem bookingOrderItem = new BookingOrderItem(bookingId, roomId, roomtypeId, price, quantity, checkInDate, checkOutDate, checkInTime, checkOutTime);
		bookingOrderItemService.updateBookingOrderItem(bookingOrderItem);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
}