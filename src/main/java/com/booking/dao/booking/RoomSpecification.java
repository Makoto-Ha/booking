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
	public static Specification<Room> hasBookingStatus(Integer bookingStatus, LocalDate date) {
		return (root, query, builder) -> {
	        if (bookingStatus == null || date == null) {
	            return builder.conjunction(); // 没有传递参数时，返回所有房间
	        }

	        // 使用 LEFT JOIN 连接 Room 和 BookingOrderItem
	        Join<Room, BookingOrderItem> boiJoin = root.join("bookingOrderItems", JoinType.LEFT);

	        // 日期范围条件
	        Predicate datePredicate = builder.and(
	            builder.lessThanOrEqualTo(boiJoin.get("checkInDate"), date), // checkInDate <= date
	            builder.greaterThanOrEqualTo(boiJoin.get("checkOutDate"), date) // checkOutDate >= date
	        );

	        if (bookingStatus == 1) {
	            // 查询已预定的房间：有匹配的 BookingOrderItem
	            return datePredicate;
	        } else if (bookingStatus == 0) {
	            // 查询空房：
	            // 1. 没有关联的 BookingOrderItem（通过 room_id 或 booking_id 检查）
	            // 2. 关联的 BookingOrderItem 不符合日期范围
	            return builder.or(
	                builder.isNull(boiJoin.get("room").get("roomId")), // 没有任何关联的房间
	                builder.not(datePredicate) // 有订单项，但不在指定日期范围内
	            );
	        }

	        return builder.conjunction(); // 默认返回所有房间
	    };
	}

}
