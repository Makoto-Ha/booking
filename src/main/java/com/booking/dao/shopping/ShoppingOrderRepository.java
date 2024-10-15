package com.booking.dao.shopping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.bean.pojo.shopping.ShoppingOrder;

public interface ShoppingOrderRepository extends JpaRepository<ShoppingOrder, Integer> {

//	
//	@Query()
//	List<ShoppingOrder> findOrdersByProductIds(List<Integer> productId);
	
	
}
