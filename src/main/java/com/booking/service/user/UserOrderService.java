package com.booking.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.user.UserOrderDTO;
import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.booking.bean.pojo.user.User;
import com.booking.dao.user.BookingOrderForUserRepository;
import com.booking.dao.user.PackageTourOrderForUserRepository;
import com.booking.dao.user.ShopOrderForUserRepository;
import com.booking.dao.user.UserRepository;

@Service
public class UserOrderService {
    @Autowired
    private BookingOrderForUserRepository bookingOrderRepository;
    
    @Autowired
    private ShopOrderForUserRepository shopOrderRepository;
    
    @Autowired
    private PackageTourOrderForUserRepository packageTourOrderRepository;
    
    @Autowired
    private UserRepository userRepository;

    // 獲取會員所有訂單資訊
    public UserOrderDTO getUserOrders(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserOrderDTO userOrderDTO = new UserOrderDTO();
        userOrderDTO.setUserId(userId);
        userOrderDTO.setUserName(user.getUserName());

        // 獲取各類訂單
        List<BookingOrder> bookingOrders = bookingOrderRepository.findByUser_UserIdOrderByCreatedTimeDesc(userId);
        List<ShopOrder> shopOrders = shopOrderRepository.findByUser_UserIdOrderByCreatedAtDesc(userId);
        List<PackageTourOrder> packageTourOrders = packageTourOrderRepository.findByUser_UserIdOrderByOrderDateTimeDesc(userId);

        // 設置訂單列表
        userOrderDTO.setBookingOrders(bookingOrders);
        userOrderDTO.setShopOrders(shopOrders);
        userOrderDTO.setPackageTourOrders(packageTourOrders);

        // 設置統計資訊
        userOrderDTO.setTotalBookingOrders(bookingOrders.size());
        userOrderDTO.setTotalShopOrders(shopOrders.size());
        userOrderDTO.setTotalPackageTourOrders(packageTourOrders.size());

        return userOrderDTO;
    }

    // 獲取住宿訂單
    public List<BookingOrder> getUserBookingOrders(Integer userId) {
        return bookingOrderRepository.findByUser_UserIdOrderByCreatedTimeDesc(userId);
    }

    // 獲取商城訂單
    public List<ShopOrder> getUserShopOrders(Integer userId) {
        return shopOrderRepository.findByUser_UserIdOrderByCreatedAtDesc(userId);
    }

    // 獲取套裝行程訂單
    public List<PackageTourOrder> getUserPackageTourOrders(Integer userId) {
        return packageTourOrderRepository.findByUser_UserIdOrderByOrderDateTimeDesc(userId);
    }

    // 根據訂單狀態查詢住宿訂單
    public List<BookingOrder> getBookingOrdersByStatus(Integer userId, Integer status) {
        return bookingOrderRepository.findByOrderStatus(status);
    }

    // 根據訂單狀態查詢商城訂單
    public List<ShopOrder> getShopOrdersByStatus(Integer userId, Integer status) {
        return shopOrderRepository.findByOrderState(status);
    }

    // 根據訂單狀態查詢套裝行程訂單
    public List<PackageTourOrder> getPackageOrdersByStatus(Integer userId, Integer status) {
        return packageTourOrderRepository.findByOrderStatus(status);
    }
}