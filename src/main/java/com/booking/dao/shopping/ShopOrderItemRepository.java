package com.booking.dao.shopping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.bean.dto.shopping.ProductSalesDTO;
import com.booking.bean.dto.shopping.ShopOrderItemDTO;
import com.booking.bean.pojo.shopping.ShopOrderItem;

public interface ShopOrderItemRepository extends JpaRepository<ShopOrderItem, Integer> {

	@Query("SELECT new com.booking.bean.dto.shopping.ShopOrderItemDTO(i.orderItemId, i.product.productId, i.product.productName, i.quantity, i.price, i.subtotal, i.updatedAt, i.createdAt) " +
		       "FROM ShopOrderItem i " +
		       "WHERE i.shopOrder.orderId = :orderId")
		List<ShopOrderItemDTO> findByOrderId(Integer orderId);
	
	@Query("SELECT new com.booking.bean.dto.shopping.ProductSalesDTO(soi.product.productName, SUM(soi.quantity)) " +
	           "FROM ShopOrderItem soi GROUP BY soi.product.productName")
	    List<ProductSalesDTO> findProductSalesData();

}