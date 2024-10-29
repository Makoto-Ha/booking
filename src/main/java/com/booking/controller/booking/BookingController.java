package com.booking.controller.booking;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.dto.booking.BookingOrderDTO;
import com.booking.bean.dto.booking.BookingOrderSearchDTO;
import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.booking.BookingOrderItemId;
import com.booking.service.booking.BookingService;
import com.booking.service.booking.RoomtypeService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@Autowired
	private RoomtypeService roomtypeService;
	
	/**
	 * 查詢所有被預訂的房型總數，進行分組
	 * @param model
	 * @return
	 */
	@GetMapping("/dashboard")
	private String sendDashBoard(Model model, RoomtypeDTO roomtypeDTO) {
		List<Map<String, Object>> bookedRoomtypes = roomtypeService.findMostBookedRoomtypes(roomtypeDTO);
		List<Map<String, Object>> bookedCapacity = roomtypeService.findMostBookedCapacity();
		List<Map<String, Object>> countBookings = bookingService.countBookingsByCheckInDate();
		
		model.addAttribute("bookedRoomtypes", bookedRoomtypes);
		model.addAttribute("bookedCapacity", bookedCapacity);
		model.addAttribute("countBookings", countBookings);
		return "management-system/booking/dashboard";
	}
	
	/**
	 * 轉發到預定訂單列表
	 * @param model
	 * @return
	 */
	@GetMapping
	private String sendOrderPage(Model model) {
		BookingOrderDTO bookingOrderDTO = new BookingOrderDTO();
		Result<Page<BookingOrderDTO>> findBookingOrderAllResult = bookingService.findBookingOrderAll(bookingOrderDTO);
		
		if(findBookingOrderAllResult.isFailure()) {
			return "";
		}
		
		Page<BookingOrderDTO> page = findBookingOrderAllResult.getData();
		model.addAttribute("page", page);
		model.addAttribute("bookingOrder", bookingOrderDTO);
		return "management-system/booking/order-list";
	}
	
	/**
	 * 轉送到創建訂單
	 * @return
	 */
	@GetMapping("/create/page")
	private String sendOrderCreatePage() {	
		return "management-system/booking/order-create";
	}
	
	/**
	 * 轉送到編輯頁面
	 * @return
	 */
	@GetMapping("/edit/page")
	private String sendOrderEditPage(@RequestParam Integer bookingId, HttpSession session, Model model) {	
		session.setAttribute("bookingId", bookingId);
		Result<BookingOrderDTO> bookingOrderResult = bookingService.findBookingOrderById(bookingId);
		
		if(bookingOrderResult.isFailure()) {
			return "";
		}
		
		BookingOrderDTO bookingOrderDTO = bookingOrderResult.getData();
		model.addAttribute("bookingOrder", bookingOrderDTO);
		return "management-system/booking/order-edit";
	}
	
	/**
	 * 轉送到查詢頁面
	 * @param param
	 * @return
	 */
	@GetMapping("/select/page")
	public String sendOrderSearchPage() {
		return "management-system/booking/order-select";
	}
	
	/**
	 * 模糊查詢預定訂單
	 * @param bookingOrderDTO
	 * @return
	 */
	@GetMapping("/select")
	private String findBookingOrders(
			@RequestParam Map<String, String> requestParameters,
			BookingOrderSearchDTO bookingOrderSearchDTO,
			// 創建BookingOrderDTO主要用於分頁資訊
			BookingOrderDTO bookingOrderDTO,
			Model model
	) {
		Result<PageImpl<BookingOrderDTO>> findBookingOrdersResult = bookingService.findBookingOrders(bookingOrderSearchDTO, bookingOrderDTO);
		if(findBookingOrdersResult.isFailure()) {
			return "";
		}
		
		PageImpl<BookingOrderDTO> page = findBookingOrdersResult.getData();
		
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("page", page);
		model.addAttribute("bookingOrder", bookingOrderDTO);
		return "management-system/booking/order-list";
	}
	
	/**
	 * 新增預定訂單
	 * @param requestParameters
	 * @param bookingOrderDTO
	 * @param model
	 * @return
	 */
	@PostMapping("/create")
	private ResponseEntity<?> saveBookingOrder(@RequestBody BookingOrderDTO boDTO, HttpSession session, Authentication authentication) {	
		String loginAccount = authentication.getName();
		Result<BookingOrder> saveBookingOrderResult = bookingService.saveBookingOrder(boDTO, loginAccount);
		String message = saveBookingOrderResult.getMessage();
		
		if(saveBookingOrderResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		
		BookingOrder bookingOrder = saveBookingOrderResult.getData();
		session.setAttribute("bookingId", bookingOrder.getBookingId());
	
		return ResponseEntity.ok(message);
	}
	
	/**
	 * 修改預定訂單資訊
	 * @param bookingId
	 * @param bookingOrderDTO
	 * @return
	 */
	@PostMapping("/update")
	private String updateBookingOrder(@RequestBody BookingOrderDTO bookingOrderDTO, @SessionAttribute Integer bookingId) {
		bookingOrderDTO.setBookingId(bookingId);
		Result<String> updateBookingOrderResult = bookingService.updateBookingOrder(bookingOrderDTO);
		if(updateBookingOrderResult.isFailure()) {
			return "";
		}

		return "redirect:/management/booking";
	}
	
	/**
	 * /**
	 * 根據bookingId刪除訂單
	 * @param bookingId
	 * @return
	 */
	@PostMapping("/delete")
	private ResponseEntity<?> deleteById(Integer bookingId) {
		Result<String> deleteByBookingIdResult = bookingService.deleteByBookingId(bookingId);
		
		String message = deleteByBookingIdResult.getMessage();
		
		if(deleteByBookingIdResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		
		return ResponseEntity.ok(message);
	}
	
	/**
	 * 入住請求
	 * @param id
	 * @return
	 */
	@PostMapping("/checkIn")
	@ResponseBody
	private ResponseEntity<?> checkIn(@RequestBody BookingOrderItemId id) {
		Result<Object> checkInResult = bookingService.checkIn(id);
		String message = checkInResult.getMessage();
		if(checkInResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		
		String checkInTime = (String) checkInResult.getExtraData("checkInTime");
		
		return ResponseEntity.ok(checkInTime);
	}
	
	/**
	 * 退房請求 
	 * @param id
	 * @return
	 */
	@PostMapping("/checkOut")
	@ResponseBody
	private ResponseEntity<?> checkOut(@RequestBody BookingOrderItemId id) {
		Result<Integer> checkOutResult = bookingService.checkOut(id);
		String message = checkOutResult.getMessage();
		if(checkOutResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		Integer checkOutStatus = checkOutResult.getData();
		String checkOutTime = (String) checkOutResult.getExtraData("checkOutTime");
		Map<String, Object> resData = new HashMap<>();
		resData.put("checkOutTime", checkOutTime);
		resData.put("checkOutStatus", checkOutStatus);
		return ResponseEntity.ok(resData);
	}
}