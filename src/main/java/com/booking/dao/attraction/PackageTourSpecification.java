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
	
	    
    //根據價錢進行模糊搜索
    public static Specification<PackageTour> priceBetween(Integer minPrice, Integer maxPrice) {
        return (root, query, cb) -> {
            if (minPrice == null && maxPrice == null) {
                return null;
            }
            if (minPrice == null) {
                return cb.lessThanOrEqualTo(root.get("packageTourPrice"), maxPrice);
            }
            if (maxPrice == null) {
                return cb.greaterThanOrEqualTo(root.get("packageTourPrice"), minPrice);
            }
            return cb.between(root.get("packageTourPrice"), minPrice, maxPrice);
        };
    }
	
	
}
