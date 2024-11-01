package com.booking.dao.shopping;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.shopping.Product;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ProductSpecification {

	/**
	 * 模糊名稱
	 * 
	 * @param productName
	 * @return
	 */
	public static Specification<Product> productNameContains(String productName) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (productName == null || productName.isEmpty()) {
				return builder.conjunction();
			}
			return builder.like(root.get("productName"), "%" + productName + "%");
		};
	}

	/**
	 * 分類ID
	 * 
	 * @param productCapacityId
	 * @return
	 */
	public static Specification<Product> categoryIdContains(Integer productCategoryId) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (productCategoryId == null) {
				return builder.conjunction();
			}

			return builder.equal(root.get("category").get("categoryId"), productCategoryId);
		};
	}

	/**
	 * 價格區間
	 * @param minPrice
	 * @param maxPrice
	 * @return
	 */
	public static Specification<Product> productPriceInRange(Integer minPrice, Integer maxPrice) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (minPrice != null && maxPrice != null) {
				return builder.between(root.get("productPrice"), minPrice, maxPrice);
			} else if (minPrice != null) {
				return builder.greaterThanOrEqualTo(root.get("productPrice"), minPrice);
			} else if (maxPrice != null) {
				return builder.lessThanOrEqualTo(root.get("productPrice"), maxPrice);
			} else {
				return builder.conjunction();
			}
		};
	}

	/**
	 * 庫存區間
	 * @param minInventory
	 * @param maxInventory
	 * @return
	 */
	public static Specification<Product> productInventoryInRange(Integer minInventory, Integer maxInventory) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (minInventory != null && maxInventory != null) {
				return builder.between(root.get("productInventory"), minInventory, maxInventory);
			} else if (minInventory != null) {
				return builder.greaterThanOrEqualTo(root.get("productInventory"), minInventory);
			} else if (maxInventory != null) {
				return builder.lessThanOrEqualTo(root.get("productInventory"), maxInventory);
			} else {
				return builder.conjunction();
			}
		};
	}

	/**
	 * 模糊說明
	 * 
	 * @param productDescription
	 * @return
	 */
	public static Specification<Product> productDescriptionContains(String productDescription) {
		return (Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (productDescription == null || productDescription.isEmpty()) {
				return builder.conjunction();
			}

			return builder.like(root.get("productDescription"), "%" + productDescription + "%");
		};
	}

}