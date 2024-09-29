package com.booking.dao.booking;

import java.util.List;

import com.booking.bean.pojo.booking.Room;
import com.booking.utils.DaoResult;

public interface RoomDao {

	/**
	 * 查詢所有房間
	 * @return
	 */
	DaoResult<List<Object[]>> getRoomAll();

	/**
	 * 根據roomId查找單筆房間
	 * @param roomId
	 * @return
	 */
	DaoResult<Room> getRoomById(Integer roomId);
	
	/**
	 * 根據roomName模糊查詢
	 * @param name
	 * @return
	 */
	DaoResult<List<Room>> getRoomByName(String roomNumber);

	/**
	 * 根據roomtypeId查找多筆房間
	 * @param roomtypeId
	 * @return
	 */
	DaoResult<List<Room>> getRoomsByRoomtypeId(Integer roomtypeId);

	/**
	 * 添加空房間
	 * @param room
	 * @param roomtypeName
	 * @return
	 */
	DaoResult<?> addRoom(Room room, String roomtypeName);
	
	/**
	 * 添加空房間
	 * @param room
	 * @return
	 */
	DaoResult<?> addRoom(Room room);

	/**
	 * 根據roomId刪除房間
	 * @param roomId
	 * @return
	 */
	DaoResult<?> removeRoomById(Integer roomId);

	/**
	 * 根據roomtypeId刪除多筆房間
	 * @param roomtypeId
	 * @return
	 */
	DaoResult<?> removeRoomsByRoomtypeId(Integer roomtypeId);

	/**
	 * 更新房間
	 * @param room
	 * @return
	 */
	DaoResult<?> updateRoom(Room room);

	/**
	 * 刪除房間減一房型數量
	 * @param roomtypeId
	 * @return
	 */
	DaoResult<?> decrementRoomtypeQuantity(Integer roomId);

	/**
	 * 刪除房間加一房型數量
	 * @param roomtypeId
	 * @return
	 */
	DaoResult<?> incrementRoomtypeQuantity(Integer roomtypeId);

}