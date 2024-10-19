package com.booking.controller.booking.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.dto.booking.client.RoomtypeKeywordSearchDTO;
import com.booking.service.booking.client.RoomtypeClientService;

@RestController
@RequestMapping(path = "/client/api",  produces = "application/json")
public class BookingClientJsonHandler {
	
	@Autowired
	private RoomtypeClientService rtClientService;
	
	@GetMapping("/roomtype")
	private ResponseEntity<?> searchRoomtypes(RoomtypeKeywordSearchDTO roomtypeKeywordSearchDTO) {
		Page<RoomtypeDTO> page = rtClientService.searchRoomtypesByKeyword(roomtypeKeywordSearchDTO);
		return ResponseEntity.ok(page);
	}
}
