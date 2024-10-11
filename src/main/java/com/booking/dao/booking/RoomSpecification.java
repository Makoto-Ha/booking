package com.booking.dao.booking;

import java.time.LocalDate;

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

	        // 查找該日期範圍
	        Predicate datePredicate = builder.and(
	            builder.lessThanOrEqualTo(boiJoin.get("checkInDate"), bookingDate), // checkInDate <= date
	            builder.greaterThanOrEqualTo(boiJoin.get("checkOutDate"), bookingDate) // checkOutDate >= date
	        );

	        if (bookingStatus == 1) {
	        	// 查詢已預定的房間，有匹配的BookingOrderItem
	            return datePredicate;
	        } else if (bookingStatus == 0) {
	            // 空房情況：
	            // 1. 沒有關聯的BookingOrderItem（通过 room_id 或 booking_id 检查）
	            // 2. 有關聯的BookingOrderItem但不符合日期范围
	            return builder.or(
	            	// 沒有任何關聯的房間
	                builder.isNull(boiJoin.get("room").get("roomId")),
	                // 有預定項目但沒有在範圍之內
	                builder.not(datePredicate) 
	            );
	        }

	        // 默認返回所有狀態的房間
	        return builder.conjunction(); 
	    };
	}

}
