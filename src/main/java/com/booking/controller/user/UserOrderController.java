package com.booking.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.user.UserOrderDTO;
import com.booking.service.user.UserOrderService;

@RestController
@RequestMapping("/api/user")
public class UserOrderController {
    @Autowired
    private UserOrderService userOrderService;

    // 獲取會員所有訂單
    @GetMapping("/{userId}/orders")
    public ResponseEntity<UserOrderDTO> getUserOrders(@PathVariable Integer userId) {
        UserOrderDTO userOrders = userOrderService.getUserOrders(userId);
        return ResponseEntity.ok(userOrders);
    }

    // 獲取特定類型的訂單
    @GetMapping("/{userId}/orders/{orderType}")
    public ResponseEntity<?> getUserOrdersByType(
            @PathVariable Integer userId,
            @PathVariable String orderType) {
        
        switch (orderType.toLowerCase()) {
            case "booking":
                return ResponseEntity.ok(userOrderService.getUserBookingOrders(userId));
            case "shop":
                return ResponseEntity.ok(userOrderService.getUserShopOrders(userId));
            case "package":
                return ResponseEntity.ok(userOrderService.getUserPackageTourOrders(userId));
            default:
                return ResponseEntity.badRequest().body("Invalid order type");
        }
    }

    // 根據狀態查詢訂單
    @GetMapping("/{userId}/orders/{orderType}/status/{status}")
    public ResponseEntity<?> getUserOrdersByStatus(
            @PathVariable Integer userId,
            @PathVariable String orderType,
            @PathVariable Integer status) {
        
        switch (orderType.toLowerCase()) {
            case "booking":
                return ResponseEntity.ok(userOrderService.getBookingOrdersByStatus(userId, status));
            case "shop":
                return ResponseEntity.ok(userOrderService.getShopOrdersByStatus(userId, status));
            case "package":
                return ResponseEntity.ok(userOrderService.getPackageOrdersByStatus(userId, status));
            default:
                return ResponseEntity.badRequest().body("Invalid order type");
        }
    }
}