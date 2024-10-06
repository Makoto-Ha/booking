package com.booking.dao.booking;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.BookingOrderItemId;

public interface BookingOrderItemRespository extends JpaRepository<BookingOrderItem, BookingOrderItemId> {

}
