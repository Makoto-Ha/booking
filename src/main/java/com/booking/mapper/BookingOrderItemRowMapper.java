package com.booking.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.booking.bean.booking.BookingOrderItem;

public class BookingOrderItemRowMapper implements RowMapper<BookingOrderItem> {

	@Override
	public BookingOrderItem getRow(ResultSet resultSet) {
		try {
			Integer bookingId = resultSet.getInt("booking_id");
			Integer roomtypeId = resultSet.getInt("roomtype_id");
			Integer roomId = resultSet.getInt("room_id");
			Integer price = resultSet.getInt("price");
			Integer quantity = resultSet.getInt("quantity");
			LocalDate checkInDate = resultSet.getTimestamp("check_in_date").toLocalDateTime().toLocalDate();
			LocalDate checkOutDate = resultSet.getTimestamp("check_out_date").toLocalDateTime().toLocalDate();
			LocalDateTime checkInTime = resultSet.getTimestamp("check_in_time").toLocalDateTime();
			LocalDateTime checkOutTime = resultSet.getTimestamp("check_out_time").toLocalDateTime();
			
			return new BookingOrderItem(bookingId, roomtypeId, roomId, price, quantity, checkInDate, checkOutDate, checkInTime, checkOutTime);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
