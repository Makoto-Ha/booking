package com.booking.service.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.booking.BookingOrderDTO;
import com.booking.bean.dto.booking.BookingOrderSearchDTO;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.dao.booking.BookingRepository;
import com.booking.dao.booking.BookingSpecification;
import com.booking.utils.MyModelMapper;
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
	@Transactional
	public Result<String> saveBookingOrder(BookingOrderDTO bookingOrderDTO) {
		BookingOrder bookingOrder = new BookingOrder();
		
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
		// 獲取pageable
		Pageable pageable = MyPageRequest.of(
				bookingOrderDTO.getPageNumber(), 
				10,
				bookingOrderDTO.getSelectedSort(),
				bookingOrderDTO.getAttrOrderBy()			
		);
		
		// 查找全部booking
		Page<BookingOrder> page = bookingRepo.findAll(pageable);
		
		// 根據bookingOrder轉DTO
		List<BookingOrder> bookingOrders = page.getContent();
		List<BookingOrderDTO> boDTOs = new ArrayList<>();
		for(BookingOrder bookingOrder : bookingOrders) {
			BookingOrderDTO responseBookingOrderDTO = new BookingOrderDTO();
			BeanUtils.copyProperties(bookingOrder, responseBookingOrderDTO);
			boDTOs.add(responseBookingOrderDTO);
		}
		
		// 創建新的Page返回
		Pageable newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		
		return Result.success(new PageImpl<>(boDTOs, newPageable, page.getTotalElements()));
	}

	/**
	 * 修改預定訂單資訊
	 * @param bookingOrderDTO
	 * @return
	 */
	public Result<String> updateBookingOrder(BookingOrderDTO bookingOrderDTO) {
		BookingOrder bookingOrder = bookingRepo.findById(bookingOrderDTO.getBookingId()).orElse(null);
		MyModelMapper.map(bookingOrderDTO, bookingOrder);
		bookingRepo.save(bookingOrder);
		return Result.success("更新預定訂單成功");
	}
	
	/**
	 * 根據bookingId查找預定訂單
	 * @param bookingId
	 * @return
	 */
	public Result<BookingOrderDTO> findBookingOrderById(Integer bookingId) {
		Optional<BookingOrder> optional = bookingRepo.findById(bookingId);
		
		if(optional.isEmpty()) {
			return Result.failure("找不到預定訂單");
		}
		
		BookingOrder bookingOrder = optional.get();
		BookingOrderDTO bookingOrderDTO = new BookingOrderDTO();
		BeanUtils.copyProperties(bookingOrder, bookingOrderDTO);
		
		return Result.success(bookingOrderDTO);	
	}
	
	/**
	 * 根據bookingId刪除訂單
	 * @param bookingId
	 * @return
	 */
	public Result<String> deleteByBookingId(Integer bookingId) {
		BookingOrder bookingOrder = bookingRepo.findById(bookingId).orElse(null);
		
		if(bookingOrder.getOrderStatus() != 0) {
			return Result.failure("預定訂單非預定狀態，無法刪除");
		}
		
		bookingRepo.deleteById(bookingId);
		return Result.success("預定訂單刪除成功");
	}

	/**
	 * 模糊查詢訂單資訊
	 * @param bosDTO
	 */
	public Result<PageImpl<BookingOrderDTO>> findBookingOrders(BookingOrderSearchDTO bosDTO, BookingOrderDTO boDTO) {
		Specification<BookingOrder> spec = Specification.where(BookingSpecification.checkInDateContains(bosDTO.getFromCheckInDate(), bosDTO.getToCheckInDate()))
					.and(BookingSpecification.checkOutDateContains(bosDTO.getFromCheckOutDate(), bosDTO.getToCheckOutDate()))
					.and(BookingSpecification.checkInTimeContains(bosDTO.getFromCheckInTime(), bosDTO.getToCheckInTime()))
					.and(BookingSpecification.checkOutTimeContains(bosDTO.getFromCheckInTime(), bosDTO.getToCheckInTime()))
					.and(BookingSpecification.orderNumberContains(bosDTO.getOrderNumber()))
					.and(BookingSpecification.orderStatusContains(bosDTO.getOrderStatus()))
					.and(BookingSpecification.totalPriceContains(bosDTO.getTotalPrice()));
		
		Pageable pageable = MyPageRequest.of(boDTO.getPageNumber(), 10, boDTO.getSelectedSort(), boDTO.getAttrOrderBy());
		Page<BookingOrder> page = bookingRepo.findAll(spec, pageable);
		List<BookingOrder> bookingOrders = page.getContent();
		List<BookingOrderDTO> boDTOs = new ArrayList<>();
		for(BookingOrder bo : bookingOrders) {
			BookingOrderDTO responseBoDTO = new BookingOrderDTO();
			BeanUtils.copyProperties(bo, responseBoDTO);
			boDTOs.add(responseBoDTO);		
		}
		Pageable newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		
		return Result.success(new PageImpl<>(boDTOs, newPageable, page.getTotalElements()));
	}

}
