package com.booking.controller.booking.client;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.dto.booking.client.RoomtypeKeywordSearchDTO;
import com.booking.bean.pojo.common.Amenity;
import com.booking.service.booking.BookingService;
import com.booking.service.booking.client.RoomtypeClientService;
import com.booking.service.common.AmenityService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
public class BookingClientController {
	
	@Autowired
	private RoomtypeClientService rtClientService;
	@Autowired
	private AmenityService amenityService;
	@Autowired
	private BookingService bookingService;;
	
	
	/**
	 * 轉去首頁
	 * @return
	 */
	@GetMapping("/")
	private String sendIndex() {
		return "client/index";
	}
	
	/**
	 * 轉去房型List
	 * @return
	 */
	@GetMapping("/user/search/roomtype")
	private String sendRoomtypeDetails(RoomtypeKeywordSearchDTO roomtypeKeywordSearchDTO, Model model) {
		Page<RoomtypeDTO> page = rtClientService.userSearchRoomtypes(roomtypeKeywordSearchDTO);
		List<Amenity> amenities = amenityService.findAll();
		List<RoomtypeDTO> roomtypes = page.getContent();
		
		model.addAttribute("searchKeywords", roomtypeKeywordSearchDTO);
		model.addAttribute("roomtypes", roomtypes);
		model.addAttribute("amenities", amenities);
		return "client/booking/roomtype-search-result";
	}
	
	/**
	 * 訂單新增後的成功頁面
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/user/order/success")
	private String sendOrderSuccess(HttpSession session, Model model) {
		Integer bookingId = (Integer) session.getAttribute("bookingId");
		Result<Map<String, Object>> findBookingInfoResult = bookingService.findBookingInfo(bookingId);
		
		if(findBookingInfoResult.isFailure()) {
			return "";
		}
		
		Map<String, Object> bookingOrderInfo = findBookingInfoResult.getData();
		
		model.addAttribute("bookingOrderInfo", bookingOrderInfo);
		
		return "client/booking/order-success";
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
		Page<RoomtypeDTO> page = rtClientService.searchRoomtypesByKeyword(roomtypeKeywordSearchDTO);
		return page.getContent();
	}
	
	/**
	 * 轉去房型資訊頁面
	 * @param roomtypeId
	 * @param model
	 * @return
	 */
	@GetMapping("/room/detail")
	public String sendRoomDetail(@RequestParam Integer roomtypeId, Model model) {
		Result<RoomtypeDTO> findByIdReseult = rtClientService.findById(roomtypeId);
		RoomtypeDTO roomtype = findByIdReseult.getData();
		model.addAttribute("roomtype", roomtype);
		return "/client/booking/roomtype-detail";
	}

	/**
	 * 跳轉checkout-page頁面
	 * 
	 */
	@GetMapping("/checkout")
	public String sendCheckout(Integer roomtypeId, Model model) {
		Result<RoomtypeDTO> findByIdResult = rtClientService.findById(roomtypeId);
		RoomtypeDTO roomtypeDTO = findByIdResult.getData();
		model.addAttribute("roomtype", roomtypeDTO);
		return "/client/booking/checkout";
	}
	
}