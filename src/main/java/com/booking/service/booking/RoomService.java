package com.booking.service.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.booking.RoomDTO;
import com.booking.bean.dto.booking.RoomDetailDTO;
import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.booking.BookingOrderItemRespository;
import com.booking.dao.booking.RoomDao;
import com.booking.dao.booking.RoomRepository;
import com.booking.dao.booking.RoomSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class RoomService {
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoomRepository roomRepo;

	@Autowired
	private BookingOrderItemRespository boiRepo;
	
	/**
	 * 獲取單筆房間
	 * 
	 * @return
	 */
	public Result<RoomDetailDTO> findRoomById(Integer roomId, RoomDTO roomDTO) {
		DaoResult<Room> getRoomByIdResult = roomDao.getRoomById(roomId);
	
		if (getRoomByIdResult.isFailure()) {
			return Result.failure("獲取房間失敗");
		}
		
		Room room = getRoomByIdResult.getData();
		
		Roomtype roomtype = (Roomtype) getRoomByIdResult.getExtraData("roomtype");
		
		RoomDetailDTO rdDTO = getRoomDetailDTO(room, roomDTO.getBookingDate());
		
		rdDTO.setRoomtypeName(roomtype.getRoomtypeName());
		
		return Result.success(rdDTO);
	}

	/**
	 * 獲取所有房間
	 * 
	 * @return
	 */
	public Result<Page<RoomDetailDTO>> findRoomAll(RoomDTO roomDTO) {
		// 獲取pageable
		Pageable pageable = MyPageRequest.of(roomDTO.getPageNumber(), 10, roomDTO.getSelectedSort(), roomDTO.getAttrOrderBy());

		// 根據pageable獲取page
		Page<RoomDTO> page = roomRepo.findRoomDTOAll(pageable);
		
		// 因為是單純獲取所有，直接篩選當天的所有房間狀態
		LocalDate currentDate = LocalDate.now();
		
		// RoomDTO的Page轉換成RoomDetailDTO的Page
		Page<RoomDetailDTO> roomDetailDTOPage = MyPageRequest.convert(page, rDTO -> getRoomDetailDTO(rDTO, currentDate));
		
		return Result.success(roomDetailDTOPage);
	}

	/**
	 * 根據房間名稱獲得房間
	 * @param name
	 * @return
	 */
	public Result<List<RoomDetailDTO>> findRoomsByName(String name) {
		DaoResult<List<Room>> getRoomByNameResult = roomDao.getRoomByName(name);
		
		if(getRoomByNameResult.isFailure()) {
			return Result.failure("根據名稱獲取所有房間失敗");
		}
		
		List<Room> rooms = getRoomByNameResult.getData();
		
		List<RoomDetailDTO> rdDTOs = new ArrayList<>();
		
		LocalDate now = LocalDate.now();
		
		for(Room room : rooms) {
			RoomDetailDTO rdDTO = getRoomDetailDTO(room, now);
			rdDTOs.add(rdDTO);
		}
		
		return Result.success(rdDTOs);
	}

	/**
	 * 根據roomtypeId獲取多筆房間
	 * 
	 * @param roomtypeId
	 * @return
	 */
	public Result<Page<RoomDetailDTO>> findRoomsByRoomtypeId(Integer roomtypeId, RoomDTO roomDTO) {
		DaoResult<List<Room>> getRoomsByRoomtypeIdResult = roomDao.getRoomsByRoomtypeId(roomtypeId);
		if (getRoomsByRoomtypeIdResult.isFailure()) {
			return Result.failure("獲取房間失敗");
		}
		List<Room> rooms = getRoomsByRoomtypeIdResult.getData();
		
		// 這邊rooms的roomtype都是一樣的，所以不用放在迴圈裡面
		String roomtypeName = rooms.size() > 0 ? rooms.get(0).getRoomtype().getRoomtypeName() : null;
		List<RoomDetailDTO> rdDTOs = new ArrayList<>();
		
		LocalDate now = LocalDate.now();
		
		for(Room room : rooms) {
			RoomDetailDTO rdDTO = getRoomDetailDTO(room, now);	
			rdDTO.setRoomtypeId(roomtypeId);
			rdDTO.setRoomtypeName(roomtypeName);
			rdDTOs.add(rdDTO);
		}
		
		Pageable pageable = MyPageRequest.of(1, 10, roomDTO.getSelectedSort(), roomDTO.getAttrOrderBy());

		return Result.success(new PageImpl<>(rdDTOs, pageable, rdDTOs.size()));
	}
	
	/**
	 * 模糊查詢
	 * @param room
	 * @param extraValues
	 * @return
	 */
	public Result<Page<RoomDetailDTO>> findRooms(RoomDTO roomDTO, Integer bookingStatus, Integer availableRooms) {
		
		// 選取的日期，查該日期的房間狀態
		LocalDate bookingDate = roomDTO.getBookingDate();
		

		// =========================== 多條件查詢 ===========================
 
		Specification<Room> spec = Specification.where(RoomSpecification.numberContains(roomDTO.getRoomNumber()))
												.and(RoomSpecification.descriptionContains(roomDTO.getRoomDescription()))
												.and(RoomSpecification.hasRoomtypeName(roomDTO.getRoomtypeName()))
												.and(RoomSpecification.hasBookingStatus(bookingStatus, bookingDate))
												.and(RoomSpecification.findAvailableRooms(bookingDate, availableRooms));
	
	    // ================================================================
	
	    // 獲取pageable
		Pageable pageable = MyPageRequest.of(roomDTO.getPageNumber(), 10, roomDTO.getSelectedSort(), roomDTO.getAttrOrderBy());
		
		// 根據spec條件和pageable獲取page
		Page<Room> page = roomRepo.findAll(spec, pageable);
			
		// Room的Page轉換成RoomDTO的Page
		Page<RoomDetailDTO> rdPage = MyPageRequest.convert(page, room -> {
			Roomtype roomtype = room.getRoomtype();
			RoomDetailDTO rdDTO = getRoomDetailDTO(room, bookingDate);
			rdDTO.setRoomtypeName(roomtype.getRoomtypeName());	
			rdDTO.setRoomtypeId(roomtype.getRoomtypeId());
			return rdDTO;
		});
		
		return Result.success(rdPage);
	}

	/**
	 * 根據房型名稱搜尋房間
	 * 還沒用到
	 * @param roomtypeName
	 * @return
	 */
	public Result<Page<RoomDTO>> findRoomsByRoomtypeName(String roomtypeName, RoomDTO roomDTO) {
		Pageable pageable = MyPageRequest.of(roomDTO.getPageNumber(), 10, roomDTO.getSelectedSort(), roomDTO.getAttrOrderBy());
		return Result.success(roomRepo.findRoomsByRoomtypeName(pageable, roomtypeName));
	}
	
	// 根據RoomDTO獲取RoomDetails
	private RoomDetailDTO getRoomDetailDTO(RoomDTO roomDTO, LocalDate date) {
		// 先獲取roomId
		Integer roomId = roomDTO.getRoomId();
		// 根據roomId和當天日期，查找當天的所有訂單項目
		List<BookingOrderItem> bois = boiRepo.findByRoomIdAndDate(roomId, date);
		// 根據所有訂單項目，確定當天是哪個狀態
		int currentDateStatus = getCurrentDateStatus(date, bois);
		// 創建要返回的RoomDetailDTO
		RoomDetailDTO roomDetailDTO = new RoomDetailDTO();
		// 拷貝屬性
		BeanUtils.copyProperties(roomDTO, roomDetailDTO);
		// 設置狀態
		roomDetailDTO.setBookingStatus(currentDateStatus);
		return roomDetailDTO;
	}
	
	// 根據Room獲取RoomDetails
	private RoomDetailDTO getRoomDetailDTO(Room room, LocalDate date) {
		// 先獲取roomId
		Integer roomId = room.getRoomId();
		// 根據roomId和當天日期，查找當天的所有訂單項目
		List<BookingOrderItem> bois = boiRepo.findByRoomIdAndDate(roomId, date);
		// 根據所有訂單項目，確定當天是哪個狀態
		int currentDateStatus = getCurrentDateStatus(date, bois);
		// 創建要返回的RoomDetailDTO
		RoomDetailDTO roomDetailDTO = new RoomDetailDTO();
		// 拷貝屬性
		BeanUtils.copyProperties(room, roomDetailDTO);
		// 設置狀態
		roomDetailDTO.setBookingStatus(currentDateStatus);
		return roomDetailDTO;
	}
	
	// 獲取Room在該日期的狀態
	private int getCurrentDateStatus(LocalDate date, List<BookingOrderItem> bois) {
		for(BookingOrderItem boi : bois) {
			LocalDate checkInDate = boi.getCheckInDate();
	    	LocalDate checkOutDate = boi.getCheckOutDate();
	    	if((checkInDate.isBefore(date) || checkInDate.isEqual(date)) && (checkOutDate.isAfter(date) || checkOutDate.isEqual(date))) {
	    		if(boi.getBookingStatus() == 1) {
	    			return 1;
	    		}else if(boi.getBookingStatus() == 2) {
	    			return 2;
	    		}
	    	}
		}
		
		return 0;
	}
	
	// 檢查請求日期是否在訂單項目的每個日期之間 **還沒用到
	public boolean checkReqDateWithinDate(LocalDate reqDate, List<BookingOrderItem> bois) {
	    for(BookingOrderItem boi : bois) {
	    	LocalDate checkInDate = boi.getCheckInDate();
	    	LocalDate checkOutDate = boi.getCheckOutDate();
	    	if(checkInDate.isAfter(reqDate) && checkOutDate.isBefore(reqDate) ) {
	    		return true;
	    	}
	    }
	    
	    return false;
	}
	
	/**
	 * 更新房間
	 * @param room
	 * @return
	 */
	@Transactional
	public Result<String> updateRoom(Room room) {
		Room oldRoom = roomDao.getRoomById(room.getRoomId()).getData();
		String roomNumber = room.getRoomNumber();
		String roomDescription = room.getRoomDescription();
		Integer roomStatus = room.getRoomStatus();

		if (roomNumber == null || roomNumber.isEmpty()) {
			room.setRoomNumber(oldRoom.getRoomNumber());
		}

		if (roomDescription == null || roomDescription.isEmpty()) {
			room.setRoomDescription(oldRoom.getRoomDescription());
		}

		if (roomStatus == null) {
			room.setRoomStatus(oldRoom.getRoomStatus());
		}

		room.setUpdatedTime(LocalDateTime.now());
		
		room.setRoomtype(oldRoom.getRoomtype());

		DaoResult<?> updateRoomResult = roomDao.updateRoom(room);

		if (updateRoomResult.isFailure()) {
			return Result.failure("更新房間失敗");
		}

		return Result.success("更新房間成功");
	}
	
	/**
	 * 根據roomtype添加房間
	 * 
	 * @param roomtype
	 * @return
	 */
	@Transactional
	public Result<String> saveRoomsByRoomtype(Roomtype roomtype) {
		LocalDateTime updatedTime = roomtype.getUpdatedTime();
		LocalDateTime createdTime = roomtype.getCreatedTime();
		String roomtypeDescription = roomtype.getRoomtypeDescription();
		Integer roomtypeQuanity = roomtype.getRoomtypeQuantity();
		
		for (int i = 0; i < roomtypeQuanity; i++) {
			Room room = new Room("ROOM" + i, 0, roomtypeDescription, updatedTime, createdTime);
			room.setRoomtype(roomtype);
			roomRepo.save(room);
		}
		return Result.success("新增空房成功");
	}
	
	/**
	 * 根據roomId刪除房間
	 * @param roomId
	 * @return
	 */
	@Transactional
	public Result<String> removeRoomById(Integer roomId) {
		DaoResult<?> decrementRoomtypeQuantityResult = roomDao.decrementRoomtypeQuantity(roomId);
		if (decrementRoomtypeQuantityResult.isFailure()) {
			return Result.failure("房間類型數量減一失敗");
		}
		DaoResult<?> removeRoomByIdResult = roomDao.removeRoomById(roomId);

		if (removeRoomByIdResult.isFailure()) {
			return Result.failure("房間不是空房，刪除房間失敗");
		}

		return Result.success("刪除房間類型成功");
	}
	
	/**
	 * 創建房間
	 * 
	 * @param room
	 * @return
	 */
	@Transactional
	public Result<Integer> saveRoomsByRoomtypeName(Room room, String roomtypeName) {
		DaoResult<?> addRoomResult = roomDao.addRoom(room, roomtypeName);
		
		if (addRoomResult.isFailure()) {
			return Result.failure("新增房間失敗");
		}
		
		DaoResult<?> incrementRoomtypeQuantityResult = roomDao.incrementRoomtypeQuantity(room.getRoomtype().getRoomtypeId());

		if (incrementRoomtypeQuantityResult.isFailure()) {
			return Result.failure("房型數量加一失敗");
		}

		return Result.success("新增空房成功");
	}

}
