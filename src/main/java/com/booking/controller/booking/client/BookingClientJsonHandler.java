package com.booking.controller.booking.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.dto.booking.client.RoomtypeKeywordSearchDTO;
import com.booking.bean.dto.booking.client.RoomtypeSearchDTO;
import com.booking.service.booking.client.RoomtypeClientService;

@RestController
@RequestMapping(path = "/client/api",  produces = "application/json")
public class BookingClientJsonHandler {
	
	@Autowired
	private RoomtypeClientService rtClientService;
	
	/**
	 * 根據用戶多種篩選做模糊查詢
	 * @param roomtypeSearchDTO
	 * @return
	 */
	@PostMapping("/roomtype")
	private ResponseEntity<?> searchRoomtypes(@RequestBody RoomtypeSearchDTO roomtypeSearchDTO) {
		System.out.println(roomtypeSearchDTO);
		Page<RoomtypeDTO> page = rtClientService.searchRoomtypes(roomtypeSearchDTO);
		return ResponseEntity.ok(page);
	}
	
	/**
	 * 根據輸入的關鍵字做模糊查詢
	 * @param roomtypeKeywordSearchDTO
	 * @return
	 */
	@GetMapping("/roomtype")
	private ResponseEntity<?> searchRoomtypesByKeywords(RoomtypeKeywordSearchDTO roomtypeKeywordSearchDTO) {
		Page<RoomtypeDTO> page = rtClientService.searchRoomtypesByKeyword(roomtypeKeywordSearchDTO);
		return ResponseEntity.ok(page);
	}
	
}