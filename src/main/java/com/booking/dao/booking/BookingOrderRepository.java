package com.booking.dao.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.user.User;



@Repository
public interface BookingOrderRepository extends JpaRepository<BookingOrder, Integer> {
    Page<BookingOrder> findByUser(User user, Pageable pageable);
}