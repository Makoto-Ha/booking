package com.booking.service.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.booking.Room;
import com.booking.bean.booking.Roomtype;
import com.booking.dao.booking.RoomDao;
import com.booking.dto.booking.RoomDTO;
import com.booking.utils.BeanUtil;
import com.booking.utils.Listable;
import com.booking.utils.Result;
import com.booking.utils.util.DaoResult;

@Service
@Transactional
public class RoomService {
	@Autowired
	private RoomDao roomDao;

	/**
	 * 獲取單筆房間
	 * 
	 * @return
	 */
	public Result<RoomDTO> getRoom(Integer roomId) {
		DaoResult<Room> getRoomByIdResult = roomDao.getRoomById(roomId);
	
		if (getRoomByIdResult.isFailure()) {
			return Result.failure("獲取房間失敗");
		}
		
		Room room = getRoomByIdResult.getData();
		
		RoomDTO roomDTO = new RoomDTO();
		
		BeanUtil.copyProperties(roomDTO, room);
		
		return Result.success(roomDTO);
	}

	/**
	 * 獲取所有房間
	 * 
	 * @return
	 */
	public Result<List<Listable>> getRoomAll() {
		DaoResult<List<Room>> getRoomAllResult = roomDao.getRoomAll();
		List<Room> rooms = getRoomAllResult.getData();
		if (getRoomAllResult.isFailure()) {
			return Result.failure("獲取所有房間失敗");
		}

		List<Listable> listDTO = new ArrayList<>();
		for (Room room : rooms) {
			RoomDTO roomDTO = new RoomDTO();

			BeanUtil.copyProperties(roomDTO, room);
			listDTO.add(roomDTO);
		}
		return Result.success(listDTO);
	}

	/**
	 * 獲取多筆房間
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
	public Result<Integer> addRoom(Room room) {
		DaoResult<?> inecrementRoomtypeQuantityResult = roomDao
				.inecrementRoomtypeQuantity(room.getRoomtype().getRoomtypeId());

		if (inecrementRoomtypeQuantityResult.isFailure()) {
			return Result.failure("房型數量加一失敗");
		}

		DaoResult<?> addRoomResult = roomDao.addRoom(room);
		if (addRoomResult.isFailure()) {
			return Result.failure("新增房間失敗");
		}

		return Result.success("新增空房成功");
	}

	/**
	 * 根據roomtype添加房間
	 * 
	 * @param roomtype
	 * @return
	 */
	public Result<Integer> addRoom(Roomtype roomtype) {
		LocalDateTime updatedTime = roomtype.getUpdatedTime();
		LocalDateTime createdTime = roomtype.getCreatedTime();
		String roomtypeDescription = roomtype.getRoomtypeDescription();
		Integer roomtypeQuanity = roomtype.getRoomtypeQuantity();

		for (int i = 0; i < roomtypeQuanity; i++) {
			Room room = new Room("C1", 0, roomtypeDescription, updatedTime, createdTime);
			room.setRoomtype(roomtype);
			DaoResult<?> addRoomResult = roomDao.addRoom(room);
			if (addRoomResult.isFailure()) {
				return Result.failure("新增空房失敗");
			}
		}
		return Result.success("新增空房成功");
	}

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

		DaoResult<?> updateRoomResult = roomDao.updateRoom(room);

		if (updateRoomResult.isFailure()) {
			return Result.failure("更新房間失敗");
		}

		return Result.success("更新房間成功");
	}
}
