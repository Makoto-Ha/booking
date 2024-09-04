package com.booking.service.booking;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.booking.bean.booking.Room;
import com.booking.bean.booking.Roomtype;
import com.booking.dao.booking.RoomDao;
import com.booking.dto.booking.RoomDTO;
import com.booking.utils.DaoResult;
import com.booking.utils.Listable;
import com.booking.utils.Result;

public class RoomService {
	
	private RoomDao roomDao = new RoomDao();
	
	/**
	 * 獲取單筆房間
	 * @return
	 */
	public Result<Room> getRoom(Integer roomId) {
		DaoResult<Room> getRoomByIdDaoResult = roomDao.getRoomById(roomId);
		Room room = getRoomByIdDaoResult.getEntity();
		if(room == null) {
			return Result.failure("獲取房間失敗");
		}
		return Result.success(room);
	}
	
	/**
	 * 獲取所有房間
	 * @return
	 */
	public Result<List<Listable>> getRoomAll() {
		DaoResult<Room> getRoomAllDaoResult = roomDao.getRoomAll();
		List<Room> rooms = getRoomAllDaoResult.getData();
		if(rooms == null) {
			return Result.failure("獲取所有房間失敗");
		}
		
		List<Listable> listDTO = new ArrayList<>();
		for(Room room : rooms) {
			RoomDTO roomDTO = new RoomDTO();
			try {
				BeanUtils.copyProperties(roomDTO, room);
				listDTO.add(roomDTO);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}	
		}
		return Result.success(listDTO);
	}
	
	/**
	 * 獲取多筆房間
	 * @param roomtypeId
	 * @return
	 */
	public Result<List<Room>> getRooms(Integer roomtypeId) {
		List<Room> rooms = roomDao.getRoomsByRoomtypeId(roomtypeId).getData();
		if(rooms == null) {
			return Result.failure("獲取房間失敗");
		}
		return Result.success(rooms);
	}
	
	/**
	 * 創建房間
	 * @param room
	 * @return
	 */
	public Result<Integer> addRoom(Room room) {
		
		DaoResult<Integer> inecrementRoomtypeQuantityDaoResult = roomDao.inecrementRoomtypeQuantity(room.getRoomtypeId());
		
		if(inecrementRoomtypeQuantityDaoResult.getAffectedRows() == null) {
			return Result.failure("房型數量加一失敗");
		}
		
		DaoResult<Integer> addRoomDaoResult = roomDao.addRoom(room);
		if(addRoomDaoResult.getAffectedRows() == null) {
			return Result.failure("新增房間失敗");
		}
		
		return Result.success("新增空房成功");
	}
	
	/**
	 * 根據roomtype添加房間
	 * @param roomtype
	 * @return
	 */
	public Result<Integer> addRoom(Roomtype roomtype) {
		LocalDateTime updatedTime = roomtype.getUpdatedTime();
		LocalDateTime createdTime = roomtype.getCreatedTime();
		Integer roomtypeId = roomtype.getRoomtypeId();
		String roomtypeDescription = roomtype.getRoomtypeDescription();
		Integer roomtypeQuanity = roomtype.getRoomtypeQuantity();
		Room room = new Room(roomtypeId, "V1", 0, roomtypeDescription, updatedTime, createdTime);
		for(int i=0; i<roomtypeQuanity; i++) {
			DaoResult<Integer> daoResult = roomDao.addRoom(room);
			if(daoResult.getAffectedRows() == null) {
				return Result.failure("新增空房失敗");
			}
		}
		return Result.success("新增空房成功");
	}
	
	public Result<String> removeRoom(Integer roomId) {
		DaoResult<Integer> decrementRoomtypeQuantityDaoResult = roomDao.decrementRoomtypeQuantity(roomId);
		if(decrementRoomtypeQuantityDaoResult.getAffectedRows() == null) {
			return Result.failure("房間類型數量減一失敗");
		}
		DaoResult<Integer> roomDaoResult = roomDao.removeRoomById(roomId);		
		
		if(roomDaoResult.getAffectedRows() == null) {
			return Result.failure("刪除房間類型失敗");
		}

		return Result.success("刪除房間類型成功");
	}

	/**
	 * 更新房間
	 * @param room
	 * @return
	 */
	public Result<String> updateRoom(Room room) {
		Room oldRoom = roomDao.getRoomById(room.getRoomId()).getEntity();
		
		Integer roomtypeId = room.getRoomtypeId();
		String roomNumber = room.getRoomNumber();
		String roomDescription = room.getRoomDescription();
		Integer roomStatus = room.getRoomStatus();
	
		if(roomtypeId == null) {
			room.setRoomtypeId(oldRoom.getRoomtypeId());
		}
		
		if(roomNumber == null || roomNumber.isEmpty()) {
			room.setRoomNumber(oldRoom.getRoomNumber());
		}
		
		if(roomDescription == null || roomDescription.isEmpty()) {
			room.setRoomDescription(oldRoom.getRoomDescription());
		}
		
		if(roomStatus == null) {
			room.setRoomStatus(oldRoom.getRoomStatus());
		}
		
		room.setUpdatedTime(LocalDateTime.now());
		
		DaoResult<Integer> updateRoomDaoResult = roomDao.updateRoom(room);
		
		if(updateRoomDaoResult.getAffectedRows() == null) {
			return Result.failure("更新房間失敗");
		}
		
		return Result.success("更新房間成功");
	}
}
