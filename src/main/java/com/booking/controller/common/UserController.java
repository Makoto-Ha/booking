package com.booking.controller.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class UserController {
	@GetMapping("/")
	private String sendIndex() {
		return "frontend/index";
	}
	
	@GetMapping("/search")
	private String sendUserSearch() {
		return "/frontend/booking/user-search";
	}
	
	@GetMapping("/room/detail")
	public String sendRoomDetail() {
		return "/frontend/booking/room-detail";
	}
	
}