package com.booking.dao.shopping;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.shopping.ShopOrder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class ShopOrderSpecification {

	/**
	 * 
	 * @param orderId
	 * @return
	 */

	public static Specification<ShopOrder> orderIdContains(Integer orderId) {
		return (Root<ShopOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (orderId == null) {
				return builder.conjunction();
			}
			return builder.equal(root.get("orderId"), orderId);
		};
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */

	public static Specification<ShopOrder> userIdContains(Integer userId) {
		return (Root<ShopOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (userId == null) {
				return builder.conjunction();
			}
			 root.fetch("users", JoinType.INNER);
			return builder.equal(root.get("users").get("userId"), userId);
		};
	}
}