package com.booking.dao.shopping;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bean.pojo.shopping.ShopCartItem;

public interface ShopCartItemRepository extends JpaRepository<ShopCartItem, Integer> {


}