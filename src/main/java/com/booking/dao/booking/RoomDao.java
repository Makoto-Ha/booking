package com.booking.dao.booking;

import com.booking.bean.booking.Room;
import com.booking.mapper.RoomRowMapper;
import com.booking.utils.DaoResult;
import com.booking.utils.DaoUtils;

public class RoomDao {
	/**
	 * 查詢所有房間
	 * @return
	 */
	public DaoResult<Room> getRoomAll() {
		String sql = "SELECT * FROM room";
		return DaoUtils.commonsQuery(sql, new RoomRowMapper());
	}
	
	/**
	 * 根據roomId查找單筆房間
	 * @param roomId
	 * @return
	 */
	public DaoResult<Room> getRoomById(Integer roomId) {
		String sql = "SELECT * FROM room WHERE room_id = ?";
		return DaoUtils.commonsQuery(sql, new RoomRowMapper(), roomId); 
	}
	
	/**
	 * 根據roomtypeId查找多筆房間
	 * @param roomtypeId
	 * @return
	 */
	public DaoResult<Room> getRoomsByRoomtypeId(Integer roomtypeId) {
		String sql = "SELECT * FROM roomtype rt JOIN room r ON rt.roomtype_id = r.roomtype_id WHERE rt.roomtype_id = ?";
		return DaoUtils.commonsQuery(sql, new RoomRowMapper(), roomtypeId);
	}

	/**
	 * 添加空房間
	 * @param room
	 * @return
	 */
	public DaoResult<Integer> addRoom(Room room) {
		String sql = "INSERT INTO room (room_number, room_status, room_description, updated_time, created_time) VALUES (?, ?, ?, ?, ?, ?)";
		return DaoUtils.commonsUpdate(
				sql, 
				room.getRoomNumber(), 
				room.getRoomStatus(), 
				room.getRoomDescription(), 
				room.getUpdatedTime(), 
				room.getCreatedTime()
		);
	}
	
	/**
	 * 根據roomId刪除房間
	 * @param roomId
	 * @return
	 */
	public DaoResult<Integer> removeRoomById(Integer roomId) {
		String sql = "DELETE FROM room WHERE room_id = ?";
		return DaoUtils.commonsUpdate(sql, roomId);
	}
	
	/**
	 * 根據roomtypeId刪除多筆房間
	 * @param roomtypeId
	 * @return
	 */
	public DaoResult<Integer> removeRoomsByRoomtypeId(Integer roomtypeId) {
		String sql = "DELETE FROM room WHERE roomtype_id = ?";
		return DaoUtils.commonsUpdate(sql, roomtypeId);
	}

	/**
	 * 更新房間
	 * @param room
	 * @return
	 */
	public DaoResult<Integer> updateRoom(Room room) {
		String sql = "UPDATE room SET roomtype_id = ?, room_number = ?, room_status = ?, room_description = ?, updated_time = ? WHERE room_id = ?";
		return DaoUtils.commonsUpdate(sql, room.getRoomNumber(), room.getRoomStatus(), room.getRoomDescription(),  room.getUpdatedTime(), room.getRoomId());
	}
	
	/**
	 * 刪除房間減一房型數量
	 * @param roomtypeId
	 * @return
	 */
	public DaoResult<Integer> decrementRoomtypeQuantity(Integer roomId) {
		String sql = "UPDATE roomtype SET roomtype_quantity = roomtype_quantity - 1 WHERE roomtype_id = (SELECT roomtype_id FROM room WHERE room_id = ?)";
		return DaoUtils.commonsUpdate(sql, roomId);
	}
	
	/**
	 * 刪除房間加一房型數量
	 * @param roomtypeId
	 * @return
	 */
	public DaoResult<Integer> inecrementRoomtypeQuantity(Integer roomtypeId) {
		String sql = "UPDATE roomtype SET roomtype_quantity = roomtype_quantity + 1 WHERE roomtype_id = ?";
		return DaoUtils.commonsUpdate(sql, roomtypeId);
	}
}
