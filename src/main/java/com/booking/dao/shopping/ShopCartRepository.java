package com.booking.dao.shopping;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bean.pojo.shopping.ShopCart;

public interface ShopCartRepository extends JpaRepository<ShopCart, Integer> {


}