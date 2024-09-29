package com.booking.dao.booking;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.dto.booking.RoomDTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class RoomSpecification {
	// 根據號碼進行模糊查詢
	public static Specification<RoomDTO> numberContains(String roomNumber) {
		return (Root<RoomDTO> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (roomNumber == null || roomNumber.isEmpty()) {
				return builder.conjunction();
			}

			return builder.like(root.get("roomNumber"), "%" + roomNumber + "%");
		};
	}

	// 根據狀態進行查詢
	public static Specification<RoomDTO> statusContains(Integer roomStatus) {
		return (Root<RoomDTO> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (roomStatus == null) {
				return builder.conjunction();
			}

			return builder.equal(root.get("roomStatus"), roomStatus);
		};
	}

	// 根據說明進行模糊查詢
	public static Specification<RoomDTO> descriptionContains(String roomDescription) {
		return (Root<RoomDTO> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (roomDescription == null || roomDescription.isEmpty()) {
				return builder.conjunction();
			}

			return builder.like(root.get("roomDescription"), "%" + roomDescription + "%");
		};
	}
}
