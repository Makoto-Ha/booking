package com.booking.dao.shopping;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bean.pojo.shopping.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
