package com.booking.dao.booking;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
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
}
