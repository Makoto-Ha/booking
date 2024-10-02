package com.booking.service.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.booking.BookingOrderDTO;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.dao.booking.BookingRepository;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepo;
	
	/**
	 * 新增訂單
	 * @param bookingOrderDTO
	 * @return
	 */
	public Result<String> saveBookingOrder(BookingOrderDTO bookingOrderDTO) {
		BookingOrder bookingOrder = new BookingOrder();
		LocalDateTime now = LocalDateTime.now();
		bookingOrder.setUpdatedTime(now);
		bookingOrder.setCreatedTime(now);
		
		BeanUtils.copyProperties(bookingOrderDTO, bookingOrder);

		bookingRepo.save(bookingOrder);
		
		return Result.success("訂單新增成功");
	}
	
	/**
	 * 獲取所有訂單
	 * @param bookingOrderDTO
	 * @return
	 */
	public Result<PageImpl<BookingOrderDTO>> findBookingOrderAll(BookingOrderDTO bookingOrderDTO) {
		Integer pageNumber = bookingOrderDTO.getPageNumber();
		String attrOrderBy = bookingOrderDTO.getAttrOrderBy();
		Boolean selectedSort = bookingOrderDTO.getSelectedSort();
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
		Page<BookingOrder> page = bookingRepo.findAll(pageable);
		
		List<BookingOrder> bookingOrders = page.getContent();
		List<BookingOrderDTO> boDTOs = new ArrayList<>();
		for(BookingOrder bookingOrder : bookingOrders) {
			BookingOrderDTO responseBookingOrderDTO = new BookingOrderDTO();
			BeanUtils.copyProperties(bookingOrder, responseBookingOrderDTO);
			boDTOs.add(responseBookingOrderDTO);
		}
		
		Pageable newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		
		return Result.success(new PageImpl<>(boDTOs, newPageable, page.getTotalElements()));
	}

}
