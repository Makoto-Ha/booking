package com.booking.controller.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.dto.booking.RoomDTO;
import com.booking.dto.booking.RoomtypeDTO;
import com.booking.service.booking.RoomService;
import com.booking.service.booking.RoomtypeService;
import com.booking.utils.JsonUtil;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/json",  produces = "application/json")
public class JsonHandler {
	@Autowired
	private RoomtypeService roomtypeService;
	
	@Autowired
	private RoomService roomService;
	
	// ===== Roomtype =====
	
	/**
	 * 返回所有房間類型
	 * @param roomtypeId
	 * @return
	 */
	@GetMapping("/roomtype/{id}")
	private String getRoomtypeById(@PathVariable Integer id) {
		Result<RoomtypeDTO> roomtypeServiceResult = roomtypeService.getRoomtype(id);
		if (roomtypeServiceResult.isFailure()) {
			return null;
		}
		
		return JsonUtil.toJson(roomtypeServiceResult.getData());
	}
	
	/**
	 * 根據房間類型名稱獲取房間類型
	 * @param name
	 * @return
	 */
	@GetMapping("/roomtype")
	private String getRoomtypesByName(@RequestParam String name, HttpServletResponse response) {
		
		Result<List<RoomtypeDTO>> roomtypeServiceResult = roomtypeService.getRoomtypesByName(name);
		
		if(roomtypeServiceResult.isFailure()) {
			return null;
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
	@ResponseBody
	private String getRoomById(@PathVariable Integer id) {
		Result<RoomDTO> roomServiceResult = roomService.getRoomById(id);
		if(roomServiceResult.isFailure()) {
			return "";
		}
		
		JsonUtil.setNonNull();
		return JsonUtil.toJson(roomServiceResult.getData());	
	}
	
}
