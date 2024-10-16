package com.booking.dao.shopping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.bean.pojo.shopping.ShopOrder;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, Integer> {

	
	@Query("SELECT o FROM ShopOrder o JOIN o.items i WHERE i.product.productId IN :productIds")
	List<ShopOrder> findOrdersByProductIds(@Param("productIds") List<Integer> productIds);

	
	
}