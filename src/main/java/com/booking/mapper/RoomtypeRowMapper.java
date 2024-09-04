package com.booking.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.booking.bean.booking.Roomtype;

public class RoomtypeRowMapper implements RowMapper<Roomtype> {

	@Override
	public Roomtype getRow(ResultSet resultSet) {
		try {
			Integer id = resultSet.getInt("roomtype_id");
			String name = resultSet.getString("roomtype_name");
			Integer capacity = resultSet.getInt("roomtype_capacity");
			Integer price = resultSet.getInt("roomtype_price");
			Integer quantity = resultSet.getInt("roomtype_quantity");
			String description = resultSet.getString("roomtype_description");
			String address = resultSet.getString("roomtype_address");
			String city = resultSet.getString("roomtype_city");
			String district = resultSet.getString("roomtype_district");
			LocalDateTime updatedTime = resultSet.getTimestamp("updated_time").toLocalDateTime();
			LocalDateTime createdTime = resultSet.getTimestamp("created_time").toLocalDateTime();
			return new Roomtype(id, name, capacity, price, quantity, description, address, city, district, updatedTime, createdTime);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
