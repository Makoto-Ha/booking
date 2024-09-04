package com.booking.service.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.booking.bean.booking.BookingOrderItem;
import com.booking.bean.booking.Room;
import com.booking.dao.booking.BookingOrderItemDao;
import com.booking.utils.DaoResult;
import com.booking.utils.Result;

public class BookingOrderItemService {
	
	private BookingOrderItemDao bookingOrderItemDao = new BookingOrderItemDao();
	
	/**
	 * 獲取單筆房間預定紀錄
	 * @param bookingId
	 * @param roomId
	 * @return
	 */
	public Result<BookingOrderItem> getBookingOrderItem(Integer bookingId, Integer roomId) {
		DaoResult<BookingOrderItem> bookingOrderItemDaoResult = bookingOrderItemDao.getBookingOrderItemById(bookingId, roomId);
		if(bookingOrderItemDaoResult.getEntity() == null) {
			return Result.failure("獲取單筆房間預定紀錄失敗");
		}
		return Result.success(bookingOrderItemDaoResult.getEntity());
	}
	
	/**
	 * 根據bookingId獲取多筆房間預定紀錄
	 * @param bookingId
	 * @return
	 */
	public Result<List<BookingOrderItem>> getBookingOrderItems(Integer bookingId) {
		DaoResult<BookingOrderItem> bookingOrderItemsDaoResult = bookingOrderItemDao.getBookingOrderItemsByBookingId(bookingId);
		if(bookingOrderItemsDaoResult.getData() == null) {
			return Result.failure("獲取單筆房間預定紀錄失敗");
		}
		return Result.success(bookingOrderItemsDaoResult.getData());
	}
	
	/**
	 * 添加房間預定紀錄
	 * @param bookingOrderItem
	 * @return
	 */
	public Result<Room> addBookingOrderItem(BookingOrderItem bookingOrderItem) {
		DaoResult<Integer> addBookingDaoResult = bookingOrderItemDao.addBookingOrderItem(bookingOrderItem);
		if(addBookingDaoResult.getAffectedRows() == null) {
			return Result.failure("新增房間紀錄失敗");
		}
		
		return Result.success("新增房間紀錄成功");
	}

	/**
	 * 更新房間預定紀錄
	 * @param bookingOrderItem
	 * @return
	 */
	public Result<String> updateBookingOrderItem(BookingOrderItem bookingOrderItem) {
		
		BookingOrderItem oldBookingOrderItem = bookingOrderItemDao.getBookingOrderItemById(
				bookingOrderItem.getRoomId(), 
				bookingOrderItem.getBookingId()
		).getEntity();
	
		Integer price = bookingOrderItem.getPrice();
		Integer quantity = bookingOrderItem.getQuantity();
		LocalDate checkInDate = bookingOrderItem.getCheckInDate();
		LocalDate checkOutDate = bookingOrderItem.getCheckOutDate();
		LocalDateTime checkInTime = bookingOrderItem.getCheckInTime();
		LocalDateTime checkOutTime = bookingOrderItem.getCheckOutTime();
		
		if(price == null) {
			bookingOrderItem.setPrice(oldBookingOrderItem.getPrice());
		}
		
		if(quantity == null) {
			bookingOrderItem.setQuantity(oldBookingOrderItem.getQuantity());
		}
		
		if(checkInDate == null) {
			bookingOrderItem.setCheckInDate(oldBookingOrderItem.getCheckInDate());
		}
		
		if(checkOutDate == null) {
			bookingOrderItem.setCheckOutDate(oldBookingOrderItem.getCheckOutDate());
		}
		
		if(checkInTime == null) {
			bookingOrderItem.setCheckInTime(oldBookingOrderItem.getCheckInTime());
		}
		
		if(checkOutTime == null) {
			bookingOrderItem.setCheckOutTime(oldBookingOrderItem.getCheckOutTime());
		}
		
		DaoResult<Integer> updateBookingOrderItemDaoResult = bookingOrderItemDao.updateBookingOrderItem(bookingOrderItem);
		
		if(updateBookingOrderItemDaoResult.getAffectedRows() == null) {
			return Result.failure("更新房間預定紀錄失敗");
		}
		
		return Result.success("更新房間預定紀錄成功");
	}

	/**
	 * 刪除單筆房間預定紀錄
	 * @param bookingId
	 * @param roomId
	 */
	public Result<String> removeBookingOrderItem(Integer bookingId, Integer roomId) {
		DaoResult<Integer> removeBookingOrderItemDaoResult = bookingOrderItemDao.removeBookingOrderItemById(bookingId, roomId);
		if(removeBookingOrderItemDaoResult.getAffectedRows() == null) {
			return Result.failure("刪除單筆房間預定紀錄失敗");
		}
		return Result.success("刪除單筆房間預定紀錄成功");
	}
	
	/**
	 * 刪除多筆房間預定紀錄
	 * @param bookingId
	 * @return
	 */
	public Result<String> removeBookingOrderItems(Integer bookingId) {
		DaoResult<Integer> removeBookingOrderItemsDaoResult = bookingOrderItemDao.removeBookingOrderItemsByBookingId(bookingId);
		if(removeBookingOrderItemsDaoResult.getAffectedRows() == null) {
			return Result.failure("刪除多筆房間預定紀錄失敗");
		}
		return Result.success("刪除多筆房間預定紀錄成功");
	}
}
