package com.booking.dao.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.bean.dto.booking.BookingOrderDTO;
import com.booking.bean.pojo.booking.BookingOrder;

public interface BookingRepository extends JpaRepository<BookingOrder, Integer> {
	// 獲取所有訂單數據
	@Query("SELECT new com.booking.bean.dto.booking.BookingOrderDTO(bo.bookingId, bo.orderStatus, bo.totalPrice, bo.checkInDate, bo.checkOutDate, bo.checkInTime, bo.checkOutTime, bo.updatedTime, bo.createdTime) FROM BookingOrder bo")
	Page<BookingOrderDTO> findAllBookingOrderDTO(Pageable pageable);
}
