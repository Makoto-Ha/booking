package com.booking.dao.attraction;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.attraction.Attraction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class AttractionSpecification {
	
	// 根據名字進行模糊查詢
	public static Specification<Attraction> nameContains(String attractionName) {
		return (Root<Attraction> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(attractionName == null || attractionName.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("attractionName"), "%" + attractionName + "%");
		};
	}
	
	// 根據城市進行模糊查詢
	public static Specification<Attraction> cityContains (String attractionCity) {
		return (Root<Attraction> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(attractionCity == null || attractionCity.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("attractionCity"), "%" + attractionCity + "%");
		};
	}
	
	// 根據地址進行模糊查詢
	public static Specification<Attraction> addressContains (String address) {
		return (Root<Attraction> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(address == null || address.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("addresss"), "%" + address + "%");
		};
	}

		
	// 根據景點類型進行模糊查詢
	public static Specification<Attraction> typeContains (String attractionType) {
		return (Root<Attraction> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(attractionType == null || attractionType.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("attractionType"), "%" + attractionType + "%");
		};
	}
	
	

	}
	

	
