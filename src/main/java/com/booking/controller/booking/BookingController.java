package com.booking.controller.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.dto.booking.BookingOrderDTO;
import com.booking.service.booking.BookingService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/booking")
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	/**
	 * 轉發到預定訂單列表
	 * @param model
	 * @return
	 */
	@GetMapping
	private String sendBookingOrderPage(Model model) {
		BookingOrderDTO bookingOrderDTO = new BookingOrderDTO();
		Result<PageImpl<BookingOrderDTO>> findBookingOrderAllResult = bookingService.findBookingOrderAll(bookingOrderDTO);
		
		if(findBookingOrderAllResult.isFailure()) {
			return "";
		}
		
		PageImpl<BookingOrderDTO> page = findBookingOrderAllResult.getData();
		model.addAttribute("page", page);
		model.addAttribute("bookingOrder", bookingOrderDTO);
		return "management-system/booking/booking-order-list";
	}
	
	/**
	 * 轉送到創建訂單
	 * @return
	 */
	@GetMapping("/create/page")
	private String sendBookingOrderCreatePage() {	
		return "management-system/booking/booking-order-create";
	}
	
	/**
	 * 轉送到編輯頁面
	 * @return
	 */
	@GetMapping("/edit/page")
	private String sendBookingOrderEditPage(@RequestParam Integer bookingId, HttpSession session, Model model) {	
		session.setAttribute("bookingId", bookingId);
		Result<BookingOrderDTO> bookingOrderResult = bookingService.findBookingOrderById(bookingId);
		
		if(bookingOrderResult.isFailure()) {
			return "";
		}
		
		BookingOrderDTO bookingOrder = bookingOrderResult.getData();
		
		model.addAttribute("bookingOrder", bookingOrder);
		return "management-system/booking/booking-order-edit";
	}
	
	/**
	 * 新增預定訂單
	 * @param requestParameters
	 * @param bookingOrderDTO
	 * @param model
	 * @return
	 */
	@PostMapping("/create")
	private String saveBookingOrder(BookingOrderDTO bookingOrderDTO) {
		System.out.println(bookingOrderDTO);
		
		Result<String> saveBookingOrderResult = bookingService.saveBookingOrder(bookingOrderDTO);
		if(saveBookingOrderResult.isFailure()) {
			return "";
		}
		
		return "redirect:/management/booking";
	}
	
	/**
	 * 修改預定訂單資訊
	 * @param bookingId
	 * @param bookingOrderDTO
	 * @return
	 */
	@PostMapping("/update")
	private String updateBookingOrder(@SessionAttribute Integer bookingId, BookingOrderDTO bookingOrderDTO) {
		
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
	@PostMapping("delete")
	private ResponseEntity<String> deleteById(Integer bookingId) {
		Result<String> deleteByBookingIdResult = bookingService.deleteByBookingId(bookingId);
		
		String message = deleteByBookingIdResult.getMessage();
		if(deleteByBookingIdResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		
		return ResponseEntity.ok(message);
	}
}