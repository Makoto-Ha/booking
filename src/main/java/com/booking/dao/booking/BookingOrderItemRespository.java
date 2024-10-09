package com.booking.dao.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.BookingOrderItemId;

public interface BookingOrderItemRespository extends JpaRepository<BookingOrderItem, BookingOrderItemId> {
	
	// 根據roomId獲取他的訂單項目在該日期下的狀態
	@Query("SELECT b FROM BookingOrderItem b WHERE b.room.id = :roomId AND :date BETWEEN b.checkInDate AND b.checkOutDate")
    List<BookingOrderItem> findByRoomIdAndDate(@Param("roomId") Integer roomId, @Param("date") LocalDate date);
}
