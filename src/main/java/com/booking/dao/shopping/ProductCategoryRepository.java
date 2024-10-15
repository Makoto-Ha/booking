package com.booking.dao.shopping;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.pojo.shopping.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
	
	// 不含products
	@Query("SELECT new com.booking.bean.dto.shopping.ProductCategoryDTO(pc.categoryId, pc.categoryName,pc.categoryDescription) FROM ProductCategory pc")
	Page<ProductCategoryDTO> findProductCategoryDTOALL(Pageable pageable);

}
