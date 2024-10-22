package com.booking.dao.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.bean.pojo.booking.BookingOrder;

@Repository
public interface BookingOrderForUserRepository extends JpaRepository<BookingOrder, Integer> {
    // 查找特定用戶的所有訂單，按創建時間倒序排序
	   List<BookingOrder> findByUser_UserIdOrderByCreatedTimeDesc(Integer userId);
    
    // 根據訂單狀態查詢
    List<BookingOrder> findByOrderStatus(Integer status);
    
    // 根據訂單號查詢
    Optional<BookingOrder> findByOrderNumber(String orderNumber);
}