package com.booking.dao.shopping;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.bean.pojo.shopping.ShopOrder;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {

	@Query("SELECT o FROM ShopOrder o JOIN o.items i WHERE i.product.productId IN :productIds")
	List<ShopOrder> findOrdersByProductIds(@Param("productIds") List<Integer> productIds);

	@Query("SELECT new com.booking.bean.dto.shopping.ShopOrderDTO(o.orderId,o.users.userId,o.orderPrice,o.orderState,o.paymentMethod, o.paymentState, o.merchantTradeNo, o.transactionId,o.paymentCreatedAt, o.paymentUpdatedAt, o.updatedAt, o.createdAt,new com.booking.bean.dto.shopping.ShopOrderItemDTO(i.orderItemId, i.product.productId, i.productName, i.quantity, i.price, i.subtotal, i.updatedAt, i.createdAt))FROM ShopOrder o LEFT JOIN ShopOrderItem i ON o.orderId = i.shopOrder.orderId WHERE o.orderId = :orderId")
	ShopOrderDTO findOrderDTOById(Integer orderId);

	@Query("SELECT new com.booking.bean.dto.shopping.ShopOrderDTO(o.orderId,o.users.userId,o.orderPrice,o.orderState,o.paymentMethod, o.paymentState, o.merchantTradeNo, o.transactionId,o.paymentCreatedAt, o.paymentUpdatedAt, o.updatedAt, o.createdAt,new com.booking.bean.dto.shopping.ShopOrderItemDTO(i.orderItemId, i.product.productId, i.productName, i.quantity, i.price, i.subtotal, i.updatedAt, i.createdAt))FROM ShopOrder o LEFT JOIN ShopOrderItem i ON o.orderId = i.shopOrder.orderId")
	Page<ShopOrderDTO> findOrderDTOAll(Pageable pageable);

	Page<ShopOrder> findAll(Specification<ShopOrder> spec, Pageable pageable);

	@Query("SELECT new com.booking.bean.dto.shopping.ShopOrderDTO(o.orderId,o.users.userId,o.orderPrice,o.orderState,o.paymentMethod, o.paymentState, o.merchantTradeNo, o.transactionId,o.paymentCreatedAt, o.paymentUpdatedAt, o.updatedAt, o.createdAt,new com.booking.bean.dto.shopping.ShopOrderItemDTO(i.orderItemId, i.product.productId, i.productName, i.quantity, i.price, i.subtotal, i.updatedAt, i.createdAt))FROM ShopOrder o LEFT JOIN ShopOrderItem i ON o.orderId = i.shopOrder.orderId WHERE o.merchantTradeNo = :merchantTradeNo")
	List<ShopOrderDTO> findOrderDTOByMerchantTradeNo(String merchantTradeNo);

}