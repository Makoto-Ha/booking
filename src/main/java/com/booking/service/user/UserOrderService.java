package com.booking.service.user;

import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserOrderService {
    
    @PersistenceContext
    private EntityManager entityManager;

    // 獲取套裝行程訂單資訊
    public List<Map> getPackageTourOrdersBasic(Integer userId) {
        String jpql = "SELECT NEW map(" +
                     "p.orderId as orderId, " +
                     "p.orderPrice as price, " +
                     "p.orderStatus as status, " +
                     "p.orderDateTime as orderDateTime, " +
                     "p.travelDate as travelDate) " +
                     "FROM PackageTourOrder p WHERE p.user.userId = :userId " +
                     "ORDER BY p.orderDateTime DESC";
        return entityManager.createQuery(jpql, Map.class)
                          .setParameter("userId", userId)
                          .getResultList();
    }

    // 獲取商城訂單資訊
    public List<Map> getShopOrdersBasic(Integer userId) {
        String jpql = "SELECT NEW map(" +
                     "s.orderId as orderId, " +
                     "s.orderPrice as price, " +
                     "s.orderState as orderStatus, " +
                     "s.paymentState as paymentStatus, " +
                     "s.paymentMethod as paymentMethod, " +
                     "s.createdAt as orderDateTime) " +
                     "FROM ShopOrder s WHERE s.user.userId = :userId " +
                     "ORDER BY s.createdAt DESC";
        return entityManager.createQuery(jpql, Map.class)
                          .setParameter("userId", userId)
                          .getResultList();
    }

    // 獲取住宿訂單資訊
    public List<Map> getBookingOrdersBasic(Integer userId) {
        String jpql = "SELECT NEW map(" +
                     "b.bookingId as orderId, " +
                     "b.totalPrice as price, " +
                     "b.orderStatus as status, " +
                     "b.orderNumber as orderNumber, " +
                     "b.createdTime as orderDateTime) " +
                     "FROM BookingOrder b WHERE b.user.userId = :userId " +
                     "ORDER BY b.createdTime DESC";
        return entityManager.createQuery(jpql, Map.class)
                          .setParameter("userId", userId)
                          .getResultList();
    }

    // 新增：訂單狀態的說明方法
    public String getPackageTourOrderStatusName(Integer status) {
        if (status == null) return "未知狀態";
        return switch (status) {
            case 0 -> "待付款";
            case 1 -> "已付款";
            case 2 -> "已確認";
            case 3 -> "進行中";
            case 4 -> "已完成";
            case 5 -> "已取消";
            case 6 -> "已退款";
            default -> "未知狀態";
        };
    }

    public String getBookingOrderStatusName(Integer status) {
        if (status == null) return "未知狀態";
        return switch (status) {
            case 0 -> "待付款";
            case 1 -> "已付款";
            case 2 -> "已確認";
            case 3 -> "已入住";
            case 4 -> "已退房";
            case 5 -> "已取消";
            case 6 -> "已退款";
            default -> "未知狀態";
        };
    }

    public String getShopOrderStatusName(Integer status) {
        if (status == null) return "未知狀態";
        return switch (status) {
            case 1 -> "待處理";
            case 2 -> "處理中";
            case 3 -> "已出貨";
            case 4 -> "已完成";
            case 5 -> "已取消";
            default -> "未知狀態";
        };
    }

    public String getShopPaymentStatusName(Integer status) {
        if (status == null) return "未知狀態";
        return switch (status) {
            case 1 -> "未付款";
            case 2 -> "已付款";
            case 3 -> "付款失敗";
            case 4 -> "已退款";
            default -> "未知狀態";
        };
    }

    public String getShopPaymentMethodName(Integer method) {
        if (method == null) return "未知方式";
        return switch (method) {
            case 1 -> "綠界支付";
            case 2 -> "Line Pay";
            default -> "未知方式";
        };
    }
    
    public List<Map> getBookingOrderDetailsBasic(Integer userId) {
        String jpql = """
            SELECT NEW map(
                b.bookingId as orderId,
                b.totalPrice as price,
                b.orderStatus as status,
                b.orderNumber as orderNumber,
                b.createdTime as orderDateTime,
                boi.checkInDate as checkInDate,
                boi.checkOutDate as checkOutDate,
                boi.price as itemPrice,
                boi.bookingStatus as bookingStatus
            )
            FROM BookingOrder b
            JOIN b.bookingOrderItems boi
            WHERE b.user.userId = :userId
            ORDER BY b.createdTime DESC
            """;
        
        return entityManager.createQuery(jpql, Map.class)
                           .setParameter("userId", userId)
                           .getResultList();
    }
}