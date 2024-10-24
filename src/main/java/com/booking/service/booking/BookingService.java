package com.booking.service.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.booking.bean.dto.booking.BookingOrderItemDTO;
import com.booking.bean.dto.booking.BookingOrderSearchDTO;
import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.bean.pojo.user.User;
import com.booking.dao.booking.BookingOrderItemRespository;
import com.booking.dao.booking.BookingRepository;
import com.booking.dao.booking.BookingSpecification;
import com.booking.dao.booking.RoomtypeRepository;
import com.booking.dao.user.UserRepository;
import com.booking.utils.MyModelMapper;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class BookingService {

	@Autowired
	private BookingRepository bookingRepo;
	
	@Autowired
	private RoomtypeRepository roomtypeRepo;
	
	@Autowired
	private BookingOrderItemRespository boiRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Transactional
	public Result<BookingOrder> saveBookingOrder(BookingOrderDTO boDTO) {
		// 先獲得房型
		Roomtype roomtype = roomtypeRepo.findById(boDTO.getRoomtypeId()).orElse(null);
		
		// 根據房型查所有房間
		List<Room> rooms = roomtype.getRooms();
		
		// 獲取請求的所有訂單項目
		List<BookingOrderItemDTO> boiDTOs = boDTO.getBookingOrderItems();
		
		// 請求日期重疊的房間就是預定，需要過濾掉
		
		List<Room> filterRooms = rooms.stream().filter(room -> {
			List<BookingOrderItem> bois = room.getBookingOrderItems();
			for(BookingOrderItem boi : bois) {
				LocalDate checkInDate = boi.getCheckInDate();
				LocalDate checkOutDate = boi.getCheckOutDate();
				for(BookingOrderItemDTO boiDTO : boiDTOs) {
					LocalDate reqCheckInDate = boiDTO.getCheckInDate();
					LocalDate reqCheckOutDate = boiDTO.getCheckOutDate();
					
					if(reqCheckOutDate.isAfter(checkInDate) && reqCheckInDate.isBefore(checkOutDate) && boi.getBookingStatus() == 1) {
						return false;
					}
					
				}
			}
			return true;
		}).collect(Collectors.toList());
		
	
		// 新增BookingOrder用於給BookingOrderItem設置
		BookingOrder bo = new BookingOrder();
		// 先新增BookingOrder獲取到有bookingId的saveBo
		BookingOrder saveBo = bookingRepo.save(bo);
		User user = userRepo.findById(1).orElse(null);
		
		if(user == null) {
			return Result.failure("找不到使用者");
		}
		
		saveBo.setUser(user);
		
		// 用於JPA新增所有BookingOrderItem
		List<BookingOrderItem> bois = new ArrayList<>();
		// 總訂單金額
		long totalPrice = 0;
		// 設置更新時間
		LocalDateTime now = LocalDateTime.now();
		// 設置所有BookingOrderItem的屬性
		for(int i=0; i<boiDTOs.size(); i++) {
			BookingOrderItemDTO boiDTO = boiDTOs.get(i);
			BookingOrderItem boi = new BookingOrderItem();
			// 設置屬性
			BeanUtils.copyProperties(boiDTO, boi);
			boi.setUpdatedTime(now);
			
			Room room = filterRooms.get(i);
			// 設置訂單項目的狀態為預訂
			boi.setBookingStatus(1);
			// 設置訂單項目的金額
			Long boiPrice = calcTotalPrice(boi, roomtype);
			boi.setPrice(boiPrice);
			// 累積總訂單金額
			totalPrice += boiPrice;
			
			// 設置中間表需要的物件
			boi.setId(saveBo.getBookingId(), room.getRoomId());
			boi.setRoom(room);
			boi.setBookingOrder(saveBo);
			boi.setRoomtype(roomtype);	
			bois.add(boi);
		}
		
		// 設置訂單總金額
		saveBo.setTotalPrice(totalPrice);
		
		// 新增所有BookingOrderItem
		boiRepo.saveAll(bois);
			
		return Result.success(saveBo);
	}
	
	// 根據房型計算訂單項目所需要金額
	private Long calcTotalPrice(BookingOrderItem boi, Roomtype roomtype) {
		LocalDate checkInDate = boi.getCheckInDate();
		LocalDate checkOutDate = boi.getCheckOutDate();
		Integer roomtypePrice = roomtype.getRoomtypePrice();
		long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
		return daysBetween * roomtypePrice;
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
		List<BookingOrderItem> bois = bookingOrder.getBookingOrderItems();
		Roomtype roomtype = bois.get(0).getRoomtype();
		
		MyModelMapper.map(bookingOrderDTO, bookingOrder);
		
		for(BookingOrderItem boi : bois) {
			Long price = calcTotalPrice(boi, roomtype);
			boi.setPrice(price);
		}
		
		Long totalPrice = calcTotalPrice(bois);
		bookingOrder.setTotalPrice(totalPrice);
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
		List<BookingOrderItem> bois = bookingOrder.getBookingOrderItems();
		List<BookingOrderItemDTO> boiDTOs = new ArrayList<>();
		for(BookingOrderItem boi : bois) {
			BookingOrderItemDTO responseBoiDTO = new BookingOrderItemDTO();
			BeanUtils.copyProperties(boi, responseBoiDTO);
			boiDTOs.add(responseBoiDTO);
		}
		bookingOrderDTO.setBookingOrderItems(boiDTOs);
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
					.and(BookingSpecification.orderNumberContains(bosDTO.getOrderNumber(), bosDTO.getBookingName()))
					.and(BookingSpecification.orderStatusContains(bosDTO.getOrderStatus()))
					.and(BookingSpecification.totalPriceContains(bosDTO.getTotalPrice()));
		
		Pageable pageable = MyPageRequest.of(boDTO.getPageNumber(), 10, boDTO.getSelectedSort(), boDTO.getAttrOrderBy());
		Page<BookingOrder> page = bookingRepo.findAll(spec, pageable);
		List<BookingOrder> bookingOrders = page.getContent();
		List<BookingOrderDTO> boDTOs = new ArrayList<>();
		for(BookingOrder bo : bookingOrders) {
			BookingOrderDTO responseBoDTO = new BookingOrderDTO();
			List<BookingOrderItem> bois = bo.getBookingOrderItems();
			BeanUtils.copyProperties(bo, responseBoDTO);
			responseBoDTO.setTotalPrice(calcTotalPrice(bois));
			boDTOs.add(responseBoDTO);		
		}
		Pageable newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		
		return Result.success(new PageImpl<>(boDTOs, newPageable, page.getTotalElements()));
	}
	
	// 計算訂單總金額
	private Long calcTotalPrice(List<BookingOrderItem> bois) {
		long totalPrice = 0;
		for(BookingOrderItem boi : bois) {
			totalPrice += boi.getPrice();
		}
		return totalPrice;
	}

	/**
	 * 根據bookingId查找訂單
	 * @param bookingId
	 * @return
	 */
	public Result<BookingOrderDTO> findById(Integer bookingId) {
		BookingOrder bookingOrder = bookingRepo.findById(bookingId).orElse(null);
		
		if(bookingOrder == null) {
			return Result.failure("查找不到訂單");
		}
		
		BookingOrderDTO bookingOrderDTO = new BookingOrderDTO();
		
		BeanUtils.copyProperties(bookingOrder, bookingOrderDTO);
		
		return Result.success(bookingOrderDTO);
	}
	
	/**
	 * 根據訂單查找訂單項目
	 * @return
	 */
	public Result<Map<String, Object> > findBookingInfo(Integer bookingId) {
		BookingOrder bookingOrder = bookingRepo.findById(bookingId).orElse(null);
		
		if(bookingOrder == null) {
			return Result.failure("查找不到訂單");
		}
		
		List<BookingOrderItem> bois = bookingOrder.getBookingOrderItems();

		if(bois.size() <= 0) {
			return Result.failure("該訂單沒有訂單項目");
		}
		
		List<BookingOrderItemDTO> boiDTOs = new ArrayList<>();
		
		BookingOrderDTO bookingOrderDTO = new BookingOrderDTO();
		BeanUtils.copyProperties(bookingOrder, bookingOrderDTO);
		
		LocalDate localDate = bookingOrder.getCreatedTime().toLocalDate();
		
		
		Roomtype roomtype = bois.get(0).getRoomtype();
		RoomtypeDTO roomtypeDTO = new RoomtypeDTO();
		BeanUtils.copyProperties(roomtype, roomtypeDTO);

		
		for(BookingOrderItem boi : bois) {
			BookingOrderItemDTO boiDTO = new BookingOrderItemDTO();
			BeanUtils.copyProperties(boi, boiDTO);
			boiDTOs.add(boiDTO);
		}
		
		Map<String, Object> bookingOrderInfo = new HashMap<>();
		bookingOrderInfo.put("bookingOrder", bookingOrderDTO);
		bookingOrderInfo.put("bookingOrderItems", boiDTOs);
		bookingOrderInfo.put("roomtype", roomtypeDTO);
		bookingOrderInfo.put("createdDate", localDate);
		
		return Result.success(bookingOrderInfo);
	}

}
