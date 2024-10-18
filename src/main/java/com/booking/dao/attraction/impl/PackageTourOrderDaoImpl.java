package com.booking.dao.attraction.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.dao.attraction.PackageTourOrderDao;
import com.booking.utils.DaoResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class PackageTourOrderDaoImpl implements PackageTourOrderDao{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DaoResult<PackageTourOrder> createOrder(PackageTourOrder packageTourOrder) {
        entityManager.persist(packageTourOrder);
        return DaoResult.create(packageTourOrder).setSuccess(true);
    }

    @Override
    public DaoResult<PackageTourOrder> getOrderById(Integer orderId) {
        PackageTourOrder packageTourOrder = entityManager.find(PackageTourOrder.class, orderId);
        return DaoResult.create(packageTourOrder).setSuccess(packageTourOrder != null);
    }

    @Override
    public DaoResult<List<PackageTourOrder>> getOrdersByUserId(Integer userId) {
        TypedQuery<PackageTourOrder> query = entityManager.createQuery(
            "SELECT o FROM PackageTourOrder o WHERE o.userId = :userId", PackageTourOrder.class);
        query.setParameter("userId", userId);
        List<PackageTourOrder> orders = query.getResultList();
        return DaoResult.create(orders).setSuccess(true);
    }

    @Override
    public DaoResult<List<PackageTourOrder>> getOrdersByStatus(Integer orderStatus) {
        TypedQuery<PackageTourOrder> query = entityManager.createQuery(
            "SELECT o FROM PackageTourOrder o WHERE o.orderStatus = :status", PackageTourOrder.class);
        query.setParameter("status", orderStatus);
        List<PackageTourOrder> orders = query.getResultList();
        return DaoResult.create(orders).setSuccess(true);
    }

    @Override
    public DaoResult<?> updateOrderStatus(Integer orderId, Integer newStatus) {
        PackageTourOrder packageTourOrder = entityManager.find(PackageTourOrder.class, orderId);
        if (packageTourOrder != null) {
        	packageTourOrder.setOrderStatus(newStatus);  
            entityManager.merge(packageTourOrder);
            return DaoResult.create().setSuccess(true);
        }
        return DaoResult.create().setSuccess(false);
    }
    
    @Override
    public DaoResult<Integer> getOrderStatus(Integer orderId) {
        PackageTourOrder packageTourOrder = entityManager.find(PackageTourOrder.class, orderId);
        if (packageTourOrder != null) {
            return DaoResult.<Integer>create(packageTourOrder.getOrderStatus()).setSuccess(true);
        }
        return DaoResult.<Integer>create().setSuccess(false);
    }

    @Override
    public DaoResult<?> deleteOrder(Integer orderId) {
        PackageTourOrder packageTourOrder = entityManager.find(PackageTourOrder.class, orderId);
        if (packageTourOrder != null) {
            entityManager.remove(packageTourOrder);
            return DaoResult.create().setSuccess(true);
        }
        return DaoResult.create().setSuccess(false);
    }
}
