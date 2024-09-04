package com.booking.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.booking.bean.booking.Room;

public class RoomRowMapper implements RowMapper<Room> {

	@Override
	public Room getRow(ResultSet resultSet) {
		
		try {
			Integer roomId = resultSet.getInt("room_id");
			Integer roomtypeId = resultSet.getInt("roomtype_id");
			String roomNumber = resultSet.getString("room_number");
			Integer roomStatus = resultSet.getInt("room_status");
			String roomDescription = resultSet.getString("room_description");
			LocalDateTime updatedTime = resultSet.getTimestamp("updated_time").toLocalDateTime();
			LocalDateTime createdTime = resultSet.getTimestamp("created_time").toLocalDateTime();
			
			return new Room(roomId, roomtypeId, roomNumber, roomStatus, roomDescription, updatedTime, createdTime);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
