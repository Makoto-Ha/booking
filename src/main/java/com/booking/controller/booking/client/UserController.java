package com.booking.controller.booking.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.dto.booking.client.RoomtypeKeywordSearchDTO;
import com.booking.service.booking.client.RoomtypeClientService;

@Controller
public class UserController {
	
	@Autowired
	private RoomtypeClientService rtClientService;
	
	/**
	 * 轉去首頁
	 * @return
	 */
	@GetMapping("/")
	private String sendIndex() {
		return "client/index";
	}
	
	/**
	 * 多條件搜尋房型
	 * @param roomtpyeSearchDTO
	 * @return
	 */
	@GetMapping("/search/roomtype")
	@ResponseBody
	private Page<RoomtypeDTO> roomtypeSearch(RoomtypeKeywordSearchDTO roomtpyeSearchDTO) {	
		return null;
	}
	
	/**
	 * 根據關鍵字搜尋房型
	 * @param roomtypeKeywordSearchDTO
	 * @return
	 */
	@GetMapping("/search/roomtype/keyword")
	@ResponseBody
	private List<RoomtypeDTO> roomtypeKeywordSearch(RoomtypeKeywordSearchDTO roomtypeKeywordSearchDTO) {
		Page<RoomtypeDTO> page = rtClientService.searchRoomtypes(roomtypeKeywordSearchDTO);
		return page.getContent();
	}
	
	/**
	 * 轉去房型頁面
	 * @return
	 */
	@GetMapping("/room/detail")
	public String sendRoomDetail() {
		return "/client/booking/room-detail";
	}
	
}