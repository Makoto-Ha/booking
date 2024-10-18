package com.booking.dao.attraction;

import java.util.List;

import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.utils.DaoResult;

public interface PackageTourOrderDao {

	/**
	 * 新增訂單
	 * @param order
	 * @return
	 */
    DaoResult<PackageTourOrder> createOrder(PackageTourOrder order);
    
    
    /**
     * 根據ID獲取訂單
     * @param orderId
     * @return
     */
    DaoResult<PackageTourOrder> getOrderById(Integer orderId);
    
    
    /**
     * 根據使用者ID獲取訂單
     * @param userId
     * @return
     */
    DaoResult<List<PackageTourOrder>> getOrdersByUserId(Integer userId);
    
    
    /**
     * 根據訂單狀態獲取訂單
     * @param orderStatus
     * @return
     */
    DaoResult<List<PackageTourOrder>> getOrdersByStatus(Integer orderStatus);
    
    
    /**
     * 更新訂單
     * @param orderId
     * @param newStatus
     * @return
     */
    DaoResult<?> updateOrderStatus(Integer orderId, Integer newStatus);
    
    
    DaoResult<Integer> getOrderStatus(Integer orderId);
    
    /**
     * 刪除訂單
     * @param orderId
     * @return
     */
    DaoResult<?> deleteOrder(Integer orderId);
}
