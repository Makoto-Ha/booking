package com.booking.dao.shopping;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bean.pojo.shopping.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	
	

}
