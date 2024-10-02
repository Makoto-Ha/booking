package com.booking.controller.booking;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.booking.bean.dto.booking.BookingOrderDTO;
import com.booking.service.booking.BookingService;
import com.booking.utils.Result;

@Controller
@RequestMapping("/management/bookingOrder")
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
		model.addAttribute("bookingOrderDTO", bookingOrderDTO);
		return "management-system/booking/booking-order-list";
	}
	
	/**
	 * 新增預定訂單
	 * @param requestParameters
	 * @param bookingOrderDTO
	 * @param model
	 * @return
	 */
	@PostMapping("/create")
	private String saveBookingOrder(
			@RequestParam Map<String, String> requestParameters,
			BookingOrderDTO bookingOrderDTO
		) {
		
		Result<String> saveBookingOrderResult = bookingService.saveBookingOrder(bookingOrderDTO);
		if(saveBookingOrderResult.isFailure()) {
			return "";
		}
		
		return "redirect:/management/bookingOrder";
	}
}