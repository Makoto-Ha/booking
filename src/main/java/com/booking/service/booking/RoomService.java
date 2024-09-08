package com.booking.service.booking;

import java.lang.reflect.InvocationTargetException;
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
		
		return null;
	}
	
	/**
	 * 根據roomtype添加房間
	 * @param roomtype
	 * @return
	 */
	public Result<Integer> addRoom(Roomtype roomtype) {

		return null;
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

		return null;
	}
}
