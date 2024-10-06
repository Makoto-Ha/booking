package com.booking.controller.booking;

import java.util.Map;

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
import com.booking.bean.dto.booking.BookingOrderSearchDTO;
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
	private String sendOrderPage(Model model) {
		BookingOrderDTO bookingOrderDTO = new BookingOrderDTO();
		Result<PageImpl<BookingOrderDTO>> findBookingOrderAllResult = bookingService.findBookingOrderAll(bookingOrderDTO);
		
		if(findBookingOrderAllResult.isFailure()) {
			return "";
		}
		
		PageImpl<BookingOrderDTO> page = findBookingOrderAllResult.getData();
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
		
		BookingOrderDTO bookingOrder = bookingOrderResult.getData();
		
		model.addAttribute("bookingOrder", bookingOrder);
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
	private String saveBookingOrder(BookingOrderDTO bookingOrderDTO) {
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
	@PostMapping("/delete")
	private ResponseEntity<String> deleteById(Integer bookingId) {
		Result<String> deleteByBookingIdResult = bookingService.deleteByBookingId(bookingId);
		
		String message = deleteByBookingIdResult.getMessage();
		
		if(deleteByBookingIdResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		
		return ResponseEntity.ok(message);
	}
}