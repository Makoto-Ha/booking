package com.booking.dao.booking;

import com.booking.bean.booking.BookingOrderItem;
import com.booking.mapper.BookingOrderItemRowMapper;
import com.booking.utils.DaoResult;
import com.booking.utils.DaoUtils;

public class BookingOrderItemDao {
	/**
	 * 根據bookingId和roomId查找單筆房間預定紀錄
	 * @param bookingId
	 * @param roomId
	 * @return
	 */
	public DaoResult<BookingOrderItem> getBookingOrderItemById(Integer bookingId, Integer roomId) {
		String sql = "SELECT * FROM booking_order_item WHERE booking_id = ? AND room_id = ?";
		return DaoUtils.commonsQuery(sql, new BookingOrderItemRowMapper(), bookingId, roomId);
	}
	
	/**
	 * 根據bookingId查找多筆房間預定紀錄
	 * @param bookingId
	 * @return
	 */
	public DaoResult<BookingOrderItem> getBookingOrderItemsByBookingId(Integer bookingId) {
		String sql = "SELECT * FROM booking_order_item WHERE booking_id = ?";
		return DaoUtils.commonsQuery(sql, new BookingOrderItemRowMapper(), bookingId);
	}

	/**
	 * 添加房間預定紀錄
	 * @param bookingOrderItem
	 * @return
	 */
	public DaoResult<Integer> addBookingOrderItem(BookingOrderItem bookingOrderItem) {
		String sql = "INSERT INTO booking_order_item VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		return DaoUtils.commonsUpdate(
				sql, 
				bookingOrderItem.getBookingId(),
				bookingOrderItem.getRoomtypeId(), 
				bookingOrderItem.getRoomId(), 
				bookingOrderItem.getPrice(), 
				bookingOrderItem.getQuantity(), 
				bookingOrderItem.getCheckInDate(), 
				bookingOrderItem.getCheckOutDate(),
				bookingOrderItem.getCheckInTime(),
				bookingOrderItem.getCheckOutTime()
		);
	}

	/**
	 * bookingId和roomId刪除單筆房間預定紀錄
	 * @param bookingId
	 * @param roomId
	 * @return
	 */
	public DaoResult<Integer> removeBookingOrderItemById(Integer bookingId, Integer roomId) {
		String sql = "DELETE FROM booking_order_item WHERE booking_id = ? AND room_id = ?";
		return DaoUtils.commonsUpdate(sql, bookingId, roomId);
	}
	
	/**
	 * 根據bookingId刪除多筆房間預定紀錄
	 * @param bookingId
	 * @return
	 */
	public DaoResult<Integer> removeBookingOrderItemsByBookingId(Integer bookingId) {
		String sql = "DELETE FROM booking_order_item WHERE booking_id = ?";
		return DaoUtils.commonsUpdate(sql, bookingId);
	}

	/**
	 * 更新單筆房間預定紀錄
	 * @param bookingOrderItem
	 * @return
	 */
	public DaoResult<Integer> updateBookingOrderItem(BookingOrderItem bookingOrderItem) {
		String sql = "UPDATE booking_order_item SET price = ?, quantity = ?, check_in_date = ?, check_out_date = ?, check_in_time = ?, check_out_time = ? WHERE booking_id = ? AND room_id = ?";
		return DaoUtils.commonsUpdate(
				sql, 
				bookingOrderItem.getPrice(), 
				bookingOrderItem.getQuantity(), 
				bookingOrderItem.getCheckInDate(), 
				bookingOrderItem.getCheckOutDate(), 
				bookingOrderItem.getCheckInTime(), 
				bookingOrderItem.getCheckOutTime(),
				bookingOrderItem.getBookingId(),
				bookingOrderItem.getRoomId()
		);
	}
}
