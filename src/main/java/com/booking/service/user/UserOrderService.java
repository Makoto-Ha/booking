package com.booking.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.pojo.user.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Map;

@Service
public class UserOrderService {
    
    @PersistenceContext
    private EntityManager entityManager;

    // 獲取套裝行程訂單的ID和價格
    public List<Map> getPackageTourOrdersBasic(Integer userId) {
        String jpql = "SELECT NEW map(p.orderId as orderId, p.orderPrice as price) " +
                     "FROM PackageTourOrder p WHERE p.user.userId = :userId";
        return entityManager.createQuery(jpql, Map.class)
                          .setParameter("userId", userId)
                          .getResultList();
    }

    // 獲取商城訂單的ID和價格
    public List<Map> getShopOrdersBasic(Integer userId) {
        String jpql = "SELECT NEW map(s.orderId as orderId, s.orderPrice as price) " +
                     "FROM ShopOrder s WHERE s.user.userId = :userId";
        return entityManager.createQuery(jpql, Map.class)
                          .setParameter("userId", userId)
                          .getResultList();
    }

    // 獲取住宿訂單的ID和價格
    public List<Map> getBookingOrdersBasic(Integer userId) {
        String jpql = "SELECT NEW map(b.bookingId as orderId, b.totalPrice as price) " +
                     "FROM BookingOrder b WHERE b.user.userId = :userId";
        return entityManager.createQuery(jpql, Map.class)
                          .setParameter("userId", userId)
                          .getResultList();
    }
}