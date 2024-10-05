package com.booking.controller.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.booking.BookingOrderDTO;
import com.booking.bean.dto.booking.RoomDTO;
import com.booking.bean.dto.booking.RoomDetailDTO;
import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.service.booking.BookingService;
=======
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.booking.RoomDTO;
import com.booking.bean.dto.booking.RoomtypeDTO;
>>>>>>> 82764d2 (黃威誠.新增: BookingOrder的模糊查詢功能)
import com.booking.service.booking.RoomService;
import com.booking.service.booking.RoomtypeService;
import com.booking.utils.JsonUtil;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api",  produces = "application/json")
public class BookingJsonHandler {
	@Autowired
	private RoomtypeService roomtypeService;
	
	@Autowired
	private RoomService roomService;
	
<<<<<<< HEAD
	@Autowired
	private BookingService bookingService;
	
=======
>>>>>>> 82764d2 (黃威誠.新增: BookingOrder的模糊查詢功能)
	// ===== Roomtype =====
	
	/**
	 * 返回所有房間類型
	 * @param roomtypeId
	 * @return
	 */
	@GetMapping("/roomtype/{id}")
	private String findRoomtypeById(@PathVariable Integer id) {
		Result<RoomtypeDTO> roomtypeServiceResult = roomtypeService.findRoomtypeById(id);
		if (roomtypeServiceResult.isFailure()) {
<<<<<<< HEAD
			return roomtypeServiceResult.getMessage();
=======
			return null;
>>>>>>> 82764d2 (黃威誠.新增: BookingOrder的模糊查詢功能)
		}
		
		return JsonUtil.toJson(roomtypeServiceResult.getData());
	}
	
	/**
	 * 根據房間類型名稱獲取房間類型
	 * @param name
	 * @return
	 */
	@GetMapping("/roomtype")
	private String findRoomtypesByName(@RequestParam String name, HttpServletResponse response) {
		
		Result<List<RoomtypeDTO>> roomtypeServiceResult = roomtypeService.findRoomtypesByName(name);
		
		if(roomtypeServiceResult.isFailure()) {
<<<<<<< HEAD
			return roomtypeServiceResult.getMessage();
=======
			return null;
>>>>>>> 82764d2 (黃威誠.新增: BookingOrder的模糊查詢功能)
		}
		
		return JsonUtil.toJson(roomtypeServiceResult.getData());
	}
	
	// ===== Room =====
		
	/**
	 * 根據id返回room的json數據
	 * @param roomId
	 * @return
	 */
	@GetMapping("/room/{id}")
<<<<<<< HEAD
	private String findRoomById(@PathVariable Integer id, @ModelAttribute RoomDTO roomDTO) {
		Result<RoomDetailDTO> roomServiceResult = roomService.findRoomById(id, roomDTO);
		if(roomServiceResult.isFailure()) {
			return roomServiceResult.getMessage();
=======
	@ResponseBody
	private String findRoomById(@PathVariable Integer id) {
		Result<RoomDTO> roomServiceResult = roomService.findRoomById(id);
		if(roomServiceResult.isFailure()) {
			return "";
>>>>>>> 82764d2 (黃威誠.新增: BookingOrder的模糊查詢功能)
		}
		
		return JsonUtil.toJson(roomServiceResult.getData());	
	}
<<<<<<< HEAD
	
	// ===== BookingOrder =====
	
	/**
	 * 根據bookingId獲取預定訂單
	 * @param id
	 * @return
	 */
	@GetMapping("/booking/{id}")
	private String findBookingOrderById(@PathVariable Integer id) {
		Result<BookingOrderDTO> findBookingOrderByIdResult = bookingService.findBookingOrderById(id);
		if(findBookingOrderByIdResult.isFailure()) {
			return findBookingOrderByIdResult.getMessage();
		}
		
		return JsonUtil.toJson(findBookingOrderByIdResult.getData());	
	}
=======
>>>>>>> 82764d2 (黃威誠.新增: BookingOrder的模糊查詢功能)

}
