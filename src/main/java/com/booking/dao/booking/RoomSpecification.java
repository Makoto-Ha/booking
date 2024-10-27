package com.booking.dao.booking;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public class RoomSpecification {
	// 根據號碼進行模糊查詢
	public static Specification<Room> numberContains(String roomNumber) {
		return (Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (roomNumber == null || roomNumber.isEmpty()) {
				return builder.conjunction();
			}

			return builder.like(root.get("roomNumber"), "%" + roomNumber + "%");
		};
	}

	// 根據狀態進行查詢
	public static Specification<Room> statusContains(Integer roomStatus) {
		return (Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (roomStatus == null) {
				return builder.conjunction();
			}

			return builder.equal(root.get("roomStatus"), roomStatus);
		};
	}

	// 根據說明進行模糊查詢
	public static Specification<Room> descriptionContains(String roomDescription) {
		return (Root<Room> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (roomDescription == null || roomDescription.isEmpty()) {
				return builder.conjunction();
			}

			return builder.like(root.get("roomDescription"), "%" + roomDescription + "%");
		};
	}
	

	// 根據roomtypeName進行模糊查詢
	public static Specification<Room> hasRoomtypeName(String roomtypeName) {
		return (root, query, builder) -> {
			if(roomtypeName == null || roomtypeName.isEmpty()) {
				return builder.conjunction();
			}

			Join<Room, Roomtype> roomtypeJoin = root.join("roomtype");
			
			return builder.like(roomtypeJoin.get("roomtypeName"), "%" + roomtypeName + "%");
		};
	}
	
	// 根據bookingStatus進行模糊查詢
	public static Specification<Room> hasBookingStatus(Integer bookingStatus, LocalDate bookingDate) {
		return (root, query, builder) -> {
			// 都沒傳遞直接返回所有狀態的房間
	        if (bookingStatus == null || bookingDate == null) {
	            return builder.conjunction(); 
	        }

	        // 使用 LEFT JOIN 關聯 Room 和 BookingOrderItem
	        Join<Room, BookingOrderItem> boiJoin = root.join("bookingOrderItems", JoinType.LEFT);
	        
	        Predicate predicate = builder.and(
	    	            builder.lessThanOrEqualTo(boiJoin.get("checkInDate"), bookingDate), // checkInDate <= date
	    	            builder.greaterThanOrEqualTo(boiJoin.get("checkOutDate"), bookingDate)); // checkOutDate >= date)
	        
	        if(bookingStatus != 0) {	

	        	return builder.and(
	    	            predicate,
	    	            builder.equal(boiJoin.get("bookingStatus"), bookingStatus)
	    	        );
	        }else {
	        	Predicate predicate2 = builder.or(builder.isNull(boiJoin.get("room").get("roomId")));
	        	return builder.or(builder.not(predicate), predicate2);
	        }
	    };
	}
	
	public static Specification<Room> findAvailableRooms(LocalDate checkDate, Integer availableRooms) {
	    return (root, query, builder) -> {
	        // 如果日期為空，返回所有房間
	        if (availableRooms == null) {
	            return builder.conjunction();
	        }
	        
	        // 創建子查詢
	        Subquery<BookingOrderItem> subquery = query.subquery(BookingOrderItem.class);
	        Root<BookingOrderItem> boiRoot = subquery.from(BookingOrderItem.class);
	        
	        // 構建子查詢條件
	        Predicate roomPredicate = builder.equal(boiRoot.get("id").get("roomId"), root.get("roomId"));
	        Predicate statusPredicate = boiRoot.get("bookingStatus").in(Arrays.asList(1, 2));
	        Predicate datePredicate = builder.and(
	            builder.lessThanOrEqualTo(boiRoot.get("checkInDate"), checkDate),
	            builder.greaterThanOrEqualTo(boiRoot.get("checkOutDate"), checkDate)
	        );
	        
	        // 組合子查詢
	        subquery.select(boiRoot.get("id").get("roomId"))
	               .where(builder.and(roomPredicate, statusPredicate, datePredicate));

	        // 使用 NOT EXISTS
	        return builder.not(builder.exists(subquery));
	    };
	}

}
