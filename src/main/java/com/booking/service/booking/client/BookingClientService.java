package com.booking.service.booking.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.booking.BookingOrderItemRespository;

@Service
public class BookingClientService {
	@Autowired
	private BookingOrderItemRespository boiRepo;
	
	/**
	 * 獲取被訂滿的日期
	 * @return
	 */
	public List<?> findFullyBookedDates(Integer roomtypeId) {
		return boiRepo.findFullyBookedDates(roomtypeId);
	}
}
