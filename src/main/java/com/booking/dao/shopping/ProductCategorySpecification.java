package com.booking.dao.shopping;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.shopping.ProductCategory;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ProductCategorySpecification {

	/**
	 * 模糊名稱
	 * @param categoryName
	 * @return
	 */
	public static Specification<ProductCategory> categoryNameContains(String categoryName) {
		return (Root<ProductCategory> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(categoryName == null || categoryName.isEmpty()) {
				return builder.conjunction();
			}
			return builder.like(root.get("categoryName"), "%" + categoryName+ "%");
		};
	}

	/**
	 * 模糊說明
	 * @param categoryDescription
	 * @return
	 */
	public static Specification<ProductCategory> categoryDescriptionContains (String categoryDescription) {
		return (Root<ProductCategory> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(categoryDescription == null || categoryDescription.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("categoryDescription"), "%" + categoryDescription + "%");
		};
	}
	
}