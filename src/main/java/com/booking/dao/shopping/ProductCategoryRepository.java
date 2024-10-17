package com.booking.dao.shopping;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.pojo.shopping.ProductCategory;
import com.booking.utils.Result;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {


	// 不含products
	@Query("SELECT new com.booking.bean.dto.shopping.ProductCategoryDTO(pc.categoryId, pc.categoryName,pc.categoryDescription) FROM ProductCategory pc")
	Page<ProductCategoryDTO> findProductCategoryDTOALL(Pageable pageable);

	Page<ProductCategory> findAll(Specification<ProductCategory> spec, Pageable pageable);
	
	@Query("SELECT new com.booking.bean.dto.shopping.ProductCategoryDTO(pc.categoryId, pc.categoryName,pc.categoryDescription) FROM ProductCategory pc WHERE pc.categoryId = :categoryId")
	ProductCategoryDTO findProductCategoryDTOById(Integer categoryId);
	
	@Query("SELECT new com.booking.bean.dto.shopping.ProductCategoryDTO(pc.categoryId, pc.categoryName,pc.categoryDescription) FROM ProductCategory pc WHERE pc.categoryName LIKE %:categoryName%")
	Result<List<ProductCategory>> findProductCategoryByNameContaining(String categoryName);
}
