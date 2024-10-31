package com.booking.service.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import com.booking.bean.dto.booking.RoomDetailDTO;
import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.dto.user.UserDTO;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.BookingOrderItemId;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.bean.pojo.user.User;
import com.booking.config.NgrokUrlConfig;
import com.booking.dao.booking.BookingOrderItemRespository;
import com.booking.dao.booking.BookingRepository;
import com.booking.dao.booking.BookingSpecification;
import com.booking.dao.booking.RoomtypeRepository;
import com.booking.dao.user.UserRepository;
import com.booking.utils.MyModelMapper;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

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
	
	@Autowired
	private NgrokUrlConfig ngrokUrlConfig;
	
	@Transactional
	public Result<BookingOrder> saveBookingOrder(BookingOrderDTO boDTO, String loginAccount) {
	    Roomtype roomtype = roomtypeRepo.findById(boDTO.getRoomtypeId()).orElse(null);
	    if (roomtype == null) {
	        return Result.failure("找不到指定房型");
	    }

	    List<Room> allRooms = roomtype.getRooms();
	    List<BookingOrderItemDTO> boiDTOs = boDTO.getBookingOrderItems();
	    
	    // 對每個預訂項目分配房間
	    List<Room> assignedRooms = new ArrayList<>();
	    
	    for (BookingOrderItemDTO boiDTO : boiDTOs) {
	        // 獲取該時段可用的房間
	        List<Room> availableRooms = allRooms.stream()
	            .filter(room -> isRoomAvailable(room, boiDTO.getCheckInDate(), 
	                                          boiDTO.getCheckOutDate()))
	            .collect(Collectors.toList());
	            
	        // 從已分配的房間中移除
	        availableRooms.removeAll(assignedRooms);
	        
	        if (availableRooms.isEmpty()) {
	            return Result.failure("指定時段沒有足夠的房間可用");
	        }
	        
	        // 分配第一個可用房間
	        assignedRooms.add(availableRooms.get(0));
	    }

	    // 創建訂單
	    BookingOrder bo = new BookingOrder();
	    BookingOrder saveBo = bookingRepo.save(bo);
	    saveBo.setRoomtype(roomtype);
	    User user = userRepo.findByUserAccount(loginAccount).orElse(null);

	    if (user == null) {
	        return Result.failure("找不到使用者");
	    }
	    
	    saveBo.setUser(user);

	    // 創建訂單項目
	    List<BookingOrderItem> bois = new ArrayList<>();
	    long totalPrice = 0;
	    LocalDateTime now = LocalDateTime.now();

	    for (int i = 0; i < boiDTOs.size(); i++) {
	        BookingOrderItemDTO boiDTO = boiDTOs.get(i);
	        Room assignedRoom = assignedRooms.get(i);
	        
	        BookingOrderItem boi = new BookingOrderItem();
	        BeanUtils.copyProperties(boiDTO, boi);
	        
	        boi.setUpdatedTime(now);
	        boi.setBookingStatus(1);
	        
	        Long boiPrice = calcTotalPrice(boi, roomtype);
	        boi.setPrice(boiPrice);
	        totalPrice += boiPrice;

	        boi.setId(saveBo.getBookingId(), assignedRoom.getRoomId());
	        boi.setRoom(assignedRoom);
	        boi.setBookingOrder(saveBo);
	        boi.setRoomtype(roomtype);
	        bois.add(boi);
	    }

	    saveBo.setTotalPrice(totalPrice);
	    boiRepo.saveAll(bois);

	    return Result.success(saveBo);
	}

	private boolean isRoomAvailable(Room room, LocalDate checkIn, LocalDate checkOut) {
	    // 檢查房間在指定時段是否已被預訂
	    return room.getBookingOrderItems().stream()
	        .filter(boi -> boi.getBookingStatus() == 1)  // 只檢查已確認的預訂
	        .noneMatch(boi -> {
	            // 檢查是否有日期重疊
	            return !(checkOut.isBefore(boi.getCheckInDate()) || 
	                    checkIn.isAfter(boi.getCheckOutDate()));
	        });
	}
	
	// 根據房型計算訂單項目所需要金額
	private Long calcTotalPrice(BookingOrderItem boi, Roomtype roomtype) {
		LocalDate checkInDate = boi.getCheckInDate();
		LocalDate checkOutDate = boi.getCheckOutDate();
		Integer roomtypePrice = roomtype.getRoomtypePrice();
		long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate) + 1;
		return daysBetween * roomtypePrice;
	}

	
	/**
	 * 獲取所有訂單
	 * @param bookingOrderDTO
	 * @return
	 */
	public Result<Page<BookingOrderDTO>> findBookingOrderAll(BookingOrderDTO bookingOrderDTO) {
		
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
			
			Roomtype roomtype = bookingOrder.getRoomtype();
			RoomtypeDTO roomtypeDTO = new RoomtypeDTO();
			BeanUtils.copyProperties(roomtype, roomtypeDTO);
			responseBookingOrderDTO.setRoomtype(roomtypeDTO);
			
			User user = bookingOrder.getUser();
			
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(user, userDTO);
			
			responseBookingOrderDTO.setUser(userDTO);
			
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
			List<BookingOrderItem> bois = bo.getBookingOrderItems();
			User user = bo.getUser();
			Roomtype roomtype = bo.getRoomtype();
			BookingOrderDTO responseBoDTO = new BookingOrderDTO();
			RoomtypeDTO roomtypeDTO = new RoomtypeDTO();
			UserDTO userDTO = new UserDTO();
			BeanUtils.copyProperties(bo, responseBoDTO);
			BeanUtils.copyProperties(roomtype, roomtypeDTO);
			BeanUtils.copyProperties(user, userDTO);
			responseBoDTO.setRoomtype(roomtypeDTO);
			responseBoDTO.setUser(userDTO);
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
		
		User user = bookingOrder.getUser();
		
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		
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
			
			Room room = boi.getRoom();
			RoomDetailDTO roomDetailDTO = new RoomDetailDTO();
			BeanUtils.copyProperties(room, roomDetailDTO);
			boiDTO.setRoom(roomDetailDTO);
			
			boiDTOs.add(boiDTO);
		}
		
		Map<String, Object> bookingOrderInfo = new HashMap<>();
		bookingOrderInfo.put("bookingOrder", bookingOrderDTO);
		bookingOrderInfo.put("bookingOrderItems", boiDTOs);
		bookingOrderInfo.put("roomtype", roomtypeDTO);
		bookingOrderInfo.put("createdDate", localDate);
		bookingOrderInfo.put("user", userDTO);
		
		return Result.success(bookingOrderInfo);
	}

	/**
	 * 入住請求
	 * @param id
	 * @return
	 */
	@Transactional
	public Result<Object> checkIn(BookingOrderItemId id) {
		
		BookingOrderItem boi = boiRepo.findById(id).orElse(null);
		
		if(boi == null) {
			return Result.failure("獲取訂單項目失敗");
		}
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String checkInTime = now.format(formatter);
		
		boi.setCheckInTime(now);
		boi.setBookingStatus(2);
		
		return Result.success("修改訂單項目，入住請求成功").setExtraData("checkInTime", checkInTime);
	}

	/**
	 * 退房請求
	 * @param id
	 * @return
	 */
	@Transactional
	public Result<Integer> checkOut(BookingOrderItemId id) {
		BookingOrderItem boi = boiRepo.findById(id).orElse(null);
		
		if(boi == null) {
			return Result.failure("獲取訂單項目失敗");
		}
	
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String checkOutTime = now.format(formatter);
		
		boi.setCheckOutTime(now);
		boi.setBookingStatus(3);
		
		BookingOrder bookingOrder = boi.getBookingOrder();
		List<BookingOrderItem> bois = bookingOrder.getBookingOrderItems();
		
		for(BookingOrderItem bookingOrderItem : bois) {
			Integer bookingStatus = bookingOrderItem.getBookingStatus();
			if(bookingStatus != 3) {
				return Result.success(1).setExtraData("checkOutTime", checkOutTime);
			}
		}
		
		
		bookingOrder.setOrderStatus(2);
		
		return Result.success(2).setExtraData("checkOutTime", checkOutTime);
	}
	
	/**
	 * 查找距離10天之前的預訂次數
	 * @return
	 */
	public List<Map<String, Object>> countBookingsByCheckInDate() {
		LocalDate today = LocalDate.now();
		LocalDate minusDays = today.minusDays(10);
		
		List<Object[]> countBookings = boiRepo.countBookingsByCheckInDate(minusDays, today);
		
		List<Map<String, Object>> responseData = new ArrayList<>();
		for(Object[] countBooking : countBookings) {
			LocalDate checkInDate = (LocalDate) countBooking[0];
			Long count = (Long) countBooking[1];
			Map<String, Object> map = new HashMap<>();
			map.put("checkInDate", checkInDate);
			map.put("count", count);
			responseData.add(map);
		}
		
		return responseData;
	}
	
	/**
	 * 綠界付款
	 * @param bookingId
	 * @return
	 */
	public String createEcPay(Integer bookingId) {
		BookingOrder bookingOrder = bookingRepo.findById(bookingId).orElse(null);
		Roomtype roomtype = bookingOrder.getRoomtype();
		
		
		String orderNumber = bookingOrder.getOrderNumber();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");
		String createdTime = bookingOrder.getCreatedTime().format(formatter);
		String totalPrice = bookingOrder.getTotalPrice() < 1 ? "1" : bookingOrder.getTotalPrice().toString();
		String ItemName = roomtype.getRoomtypeName();
		
		
		AllInOne ecpay = new AllInOne("");
		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantID("3002599");
		obj.setMerchantTradeNo(orderNumber);
		obj.setMerchantTradeDate(createdTime);
		obj.setTotalAmount(totalPrice);
		obj.setTradeDesc("預定房型");
		obj.setItemName(ItemName);
		obj.setReturnURL("http://localhost:8080/booking/user/order/success");
		obj.setClientBackURL("http://localhost:8080/booking/user/order/success");
//		obj.setOrderResultURL(ngrokUrlConfig.getNgrokURL() + "/booking/user/order/success");
		String form = ecpay.aioCheckOut(obj, null);
		return form;
	}

	@Transactional
	public String setOrderStatus(Integer bookingId) {
		BookingOrder bookingOrder = bookingRepo.findById(bookingId).orElse(null);
		bookingOrder.setOrderStatus(1);
		return "更新訂單狀態成功";
	}
}
