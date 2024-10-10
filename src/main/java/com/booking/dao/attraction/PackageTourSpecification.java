package com.booking.dao.attraction;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.attraction.PackageTour;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PackageTourSpecification {

	
	// 根據名字進行模糊查詢
	public static Specification<PackageTour> nameContains(String packageTourName) {
		return (Root<PackageTour> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(packageTourName == null || packageTourName.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("packageTourName"), "%" + packageTourName + "%");
		};
	}
	
	
	//根據價錢進行模糊查詢
	public static Specification<PackageTour> priceContains (Integer packageTourPrice) {
		return (Root<PackageTour> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(packageTourPrice == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("packageTourPrice"), packageTourPrice);
		};
	}
	
	
	// 根據描述進行模糊查詢
	public static Specification<PackageTour> descriptionContains (String packageTourDescription) {
		return (Root<PackageTour> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(packageTourDescription == null || packageTourDescription.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("packageTourDescription"), "%" + packageTourDescription + "%");
		};
	}
	
	
}
