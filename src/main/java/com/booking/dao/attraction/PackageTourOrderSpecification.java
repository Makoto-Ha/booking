package com.booking.dao.attraction;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.attraction.PackageTourOrder;

public class PackageTourOrderSpecification {

	
	//根據訂單編號進行模糊查詢
	public static Specification<PackageTourOrder> orderIdEquals(Integer orderId) {
	    return (root, query, criteriaBuilder) -> {
	        if (orderId == null) {
	            return criteriaBuilder.conjunction();
	        }
	        return criteriaBuilder.equal(root.get("orderId"), orderId);
	    };
	}
	
	
	//根據套裝行程名稱進行模糊查詢
    public static Specification<PackageTourOrder> packageTourNameContains(String packageTourName) {
        return (root, query, criteriaBuilder) -> {
            if (packageTourName == null || packageTourName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                criteriaBuilder.lower(root.get("packageTour").get("packageTourName")),
                "%" + packageTourName.toLowerCase() + "%"
            );
        };
    }
    
    
    //根據訂單狀態進行模糊查詢
    public static Specification<PackageTourOrder> orderStatusEquals(Integer orderStatus) {
        return (root, query, criteriaBuilder) -> {
            if (orderStatus == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("orderStatus"), orderStatus);
        };
    }
    
    
    //根據訂單建立日期進行模糊查詢
    public static Specification<PackageTourOrder> orderDateTimeBetween(LocalDateTime start, LocalDateTime end) {
        return (root, query, criteriaBuilder) -> {
            if (start == null || end == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get("orderDateTime"), start, end);
        };
    }
    
    

    
}
