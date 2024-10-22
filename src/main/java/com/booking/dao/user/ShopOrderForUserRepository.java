package com.booking.dao.user;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.bean.pojo.shopping.ShopOrder;

@Repository
public interface ShopOrderForUserRepository extends JpaRepository<ShopOrder, Integer> {
    // 查找特定用戶的所有訂單，按創建時間倒序排序
	 List<ShopOrder> findByUser_UserIdOrderByCreatedAtDesc(Integer userId);
    
    // 根據訂單狀態查詢
    List<ShopOrder> findByOrderState(Integer orderState);
    
    // 根據支付狀態查詢
    List<ShopOrder> findByPaymentState(Integer paymentState);
    
    // 根據商家交易編號查詢
    Optional<ShopOrder> findByMerchantTradeNo(String merchantTradeNo);
}