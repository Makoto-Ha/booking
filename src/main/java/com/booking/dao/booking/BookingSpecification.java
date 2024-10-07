package com.booking.dao.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.booking.bean.pojo.booking.BookingOrder;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class BookingSpecification {
	// 根據入住日期進行查詢
	public static Specification<BookingOrder> checkInDateContains(LocalDate fromDate, LocalDate toDate) {
		return (Root<BookingOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (fromDate == null && toDate == null) {
				return builder.conjunction();
			}else if(toDate == null) {
				return builder.greaterThanOrEqualTo(root.get("checkInDate"), fromDate);
			}else if(fromDate == null) {
				return builder.lessThanOrEqualTo(root.get("checkInDate"), toDate);
			}

			return builder.and(
					builder.greaterThanOrEqualTo(root.get("checkInDate"), fromDate),
					builder.lessThanOrEqualTo(root.get("checkInDate"), toDate)
			);
		};
	}

	// 根據退房日期進行查詢
	public static Specification<BookingOrder> checkOutDateContains(LocalDate fromDate, LocalDate toDate) {
		return (Root<BookingOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (fromDate == null && toDate == null) {
				return builder.conjunction();
			}else if(toDate == null) {
				return builder.greaterThanOrEqualTo(root.get("checkOutDate"), fromDate);
			}else if(fromDate == null) {
				return builder.lessThanOrEqualTo(root.get("checkOutDate"), toDate);
			}

			return builder.and(
					builder.greaterThanOrEqualTo(root.get("checkOutDate"), fromDate),
					builder.lessThanOrEqualTo(root.get("checkOutDate"), toDate)
			);
		};
	}
	
	// 根據確切入住時間進行查詢
	public static Specification<BookingOrder> checkInTimeContains(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		return (Root<BookingOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (fromDateTime == null && toDateTime == null) {
				return builder.conjunction();
			}else if(toDateTime == null) {
				return builder.greaterThanOrEqualTo(root.get("checkInTime"), fromDateTime);
			}else if(fromDateTime == null) {
				return builder.lessThanOrEqualTo(root.get("checkInTime"), toDateTime);
			}

			return builder.and(
					builder.greaterThanOrEqualTo(root.get("checkInTime"), fromDateTime),
					builder.lessThanOrEqualTo(root.get("checkInTime"), toDateTime)
			);
		};
	}
	
	// 根據確切入住時間進行查詢
	public static Specification<BookingOrder> checkOutTimeContains(LocalDateTime fromDateTime, LocalDateTime toDateTime) {
		return (Root<BookingOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if (fromDateTime == null && toDateTime == null) {
				return builder.conjunction();
			}else if(toDateTime == null) {
				return builder.greaterThanOrEqualTo(root.get("checkOutTime"), fromDateTime);
			}else if(fromDateTime == null) {
				return builder.lessThanOrEqualTo(root.get("checkOutTime"), toDateTime);
			}

			return builder.and(
					builder.greaterThanOrEqualTo(root.get("checkOutTime"), fromDateTime),
					builder.lessThanOrEqualTo(root.get("checkOutTime"), toDateTime)
			);
		};
	}

	// 根據訂單編號進行模糊查詢
	public static Specification<BookingOrder> orderNumberContains(String orderNumber, String bookingName) {
		return (Root<BookingOrder> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(orderNumber != null && !orderNumber.isEmpty()) {
				return builder.like(root.get("orderNumber"), "%" + orderNumber + "%");
			}else if(bookingName != null && !bookingName.isEmpty()) {
				return builder.like(root.get("orderNumber"), "%" + bookingName + "%");
			}

			return builder.conjunction();
		};
	}
	
	// 根據訂單總金額進行查詢
	public static Specification<BookingOrder> totalPriceContains(Long totalPrice) {
		return (root, query, builder) -> {
			if(totalPrice == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("totalPrice"), totalPrice);
		};
	}
	
	// 根據訂單狀態進行查詢
	public static Specification<BookingOrder> orderStatusContains(Integer orderStatus) {
		return (root, query, builder) -> {
			if(orderStatus == null) {
				return builder.conjunction();
			}
			
			return builder.equal(root.get("orderStatus"), orderStatus);
		};
	}
}
