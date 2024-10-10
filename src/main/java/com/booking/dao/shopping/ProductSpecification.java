package com.booking.dao.shopping;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.shopping.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ProductSpecification {

	/**
	 * 模糊名稱
	 * @param productName
	 * @return
	 */
	public static Specification<Product> productNameContains(String productName) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(productName == null || productName.isEmpty()) {
				return builder.conjunction();
			}
			return builder.like(root.get("productName"), "%" + productName + "%");
		};
	}

	/**
	 * 分類ID
	 * @param productCapacityId
	 * @return
	 */
	public static Specification<Product> categoryIdContains (Integer productCategoryId) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(productCategoryId == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("category").get("categoryId"), productCategoryId);
		};
	}
	

	/**
	 * 價錢
	 * @param productPrice
	 * @return
	 */
	public static Specification<Product> productPriceContains (Integer productPrice) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(productPrice == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("productPrice"), productPrice);
		};
	}

	
	/**
	 * 庫存量
	 * @param productInventory
	 * @return
	 */
	public static Specification<Product> productInventoryContains (Integer productInventory) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(productInventory == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("productInventory"), productInventory);
		};
	}

	
	/**
	 * 模糊說明
	 * @param productDescription
	 * @return
	 */
	public static Specification<Product> productDescriptionContains (String productDescription) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(productDescription == null || productDescription.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("productDescription"), "%" + productDescription + "%");
		};
	}
	
}