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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.booking.RoomDTO;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
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

	/**
	 * 獲取單筆房間
	 * 
	 * @return
	 */
	public Result<RoomDTO> getRoomById(Integer roomId) {
		DaoResult<Room> getRoomByIdResult = roomDao.getRoomById(roomId);
	
		if (getRoomByIdResult.isFailure()) {
			return Result.failure("獲取房間失敗");
		}
		
		Room room = getRoomByIdResult.getData();
		
		Roomtype roomtype = (Roomtype) getRoomByIdResult.getExtraData("roomtype");
		
		RoomDTO roomDTO = new RoomDTO();
		
		BeanUtils.copyProperties(room, roomDTO);
		
		roomDTO.setRoomtypeName(roomtype.getRoomtypeName());
		
		return Result.success(roomDTO);
	}

	/**
	 * 獲取所有房間
	 * 
	 * @return
	 */
	public Result<Page<RoomDTO>> findRoomAll(RoomDTO roomDTO) {
		String attrOrderBy = roomDTO.getAttrOrderBy();
		Boolean selectedSort = roomDTO.getSelectedSort();
		Integer pageNumber = roomDTO.getPageNumber();
		
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
		
		Page<RoomDTO> page = roomRepo.findAllRoomDTO(pageable);
		return Result.success(page);
	}
	
	/**
	 * 根據房間名稱獲得房間
	 * @param name
	 * @return
	 */
	public Result<List<Room>> getRoomsByName(String name) {
		DaoResult<List<Room>> getRoomByNameResult = roomDao.getRoomByName(name);
		
		if(getRoomByNameResult.isFailure()) {
			return Result.failure("根據名稱獲取所有房間失敗");
		}
		
		List<Room> rooms = getRoomByNameResult.getData();
		
		RoomDTO roomDTO = new RoomDTO();
		
		List<RoomDTO> list = new ArrayList<>();
		
		for(Room room : rooms) {
			BeanUtils.copyProperties(room, roomDTO);
			list.add(roomDTO);
		}
		
		return Result.success(rooms);
	}

	/**
	 * 根據roomtypeId獲取多筆房間
	 * 
	 * @param roomtypeId
	 * @return
	 */
	public Result<List<Room>> getRooms(Integer roomtypeId) {
		DaoResult<List<Room>> getRoomsByRoomtypeIdResult = roomDao.getRoomsByRoomtypeId(roomtypeId);
		List<Room> rooms = getRoomsByRoomtypeIdResult.getData();
		if (getRoomsByRoomtypeIdResult.isFailure()) {
			return Result.failure("獲取房間失敗");
		}
		return Result.success(rooms);
	}

	/**
	 * 創建房間
	 * 
	 * @param room
	 * @return
	 */
	@Transactional
	public Result<Integer> addRoom(Room room, String roomtypeName) {
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

	/**
	 * 根據roomtype添加房間
	 * 
	 * @param roomtype
	 * @return
	 */
	@Transactional
	public Result<Integer> addRoom(Roomtype roomtype) {
		LocalDateTime updatedTime = roomtype.getUpdatedTime();
		LocalDateTime createdTime = roomtype.getCreatedTime();
		String roomtypeDescription = roomtype.getRoomtypeDescription();
		Integer roomtypeQuanity = roomtype.getRoomtypeQuantity();

		for (int i = 0; i < roomtypeQuanity; i++) {
			Room room = new Room("無", 0, roomtypeDescription, updatedTime, createdTime);
			room.setRoomtype(roomtype);
			DaoResult<?> addRoomResult = roomDao.addRoom(room);
			if (addRoomResult.isFailure()) {
				return Result.failure("新增空房失敗");
			}
		}
		return Result.success("新增空房成功");
	}

	@Transactional
	public Result<String> removeRoom(Integer roomId) {
		DaoResult<?> decrementRoomtypeQuantityResult = roomDao.decrementRoomtypeQuantity(roomId);
		if (decrementRoomtypeQuantityResult.isFailure()) {
			return Result.failure("房間類型數量減一失敗");
		}
		DaoResult<?> removeRoomByIdResult = roomDao.removeRoomById(roomId);

		if (removeRoomByIdResult.isFailure()) {
			return Result.failure("刪除房間類型失敗");
		}

		return Result.success("刪除房間類型成功");
	}

	/**
	 * 更新房間
	 * 
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
	 * 模糊查詢
	 * @param room
	 * @param extraValues
	 * @return
	 */
	public PageImpl<RoomDTO> findRooms(RoomDTO roomDTO) {
		Specification<Room> spec = Specification.where(RoomSpecification.numberContains(roomDTO.getRoomNumber()))
													.and(RoomSpecification.statusContains(roomDTO.getRoomStatus()))
													.and(RoomSpecification.descriptionContains(roomDTO.getRoomDescription()));
			
		String attrOrderBy = roomDTO.getAttrOrderBy();
		Boolean selectedSort = roomDTO.getSelectedSort();
		Integer pageNumber = roomDTO.getPageNumber();
	
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
	
		Page<Room> page = roomRepo.findAll(spec, pageable);
		
		List<Room> rooms = page.getContent();
		
		List<RoomDTO> roomDTOs = new ArrayList<>();
		for(Room r : rooms) {
			Roomtype roomtype = r.getRoomtype();
			RoomDTO responseRoomDTO = new RoomDTO();
			BeanUtils.copyProperties(r, responseRoomDTO);
			responseRoomDTO.setRoomtypeName(roomtype.getRoomtypeName());	
			roomDTOs.add(responseRoomDTO);
		}
		
		Pageable newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		
		return new PageImpl<>(roomDTOs, newPageable, page.getTotalElements());
	}
}
