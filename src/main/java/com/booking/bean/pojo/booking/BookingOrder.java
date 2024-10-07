package com.booking.bean.pojo.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_order")
@DynamicInsert
@DynamicUpdate
public class BookingOrder {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private Integer bookingId;
	
	@Column(name = "order_number")
	private String orderNumber;
	
	@Column(name = "order_status")
	private Integer orderStatus;
	
	@Column(name = "total_price")
	private Long totalPrice;
	
	@Column(name = "updated_time")
	private LocalDateTime updatedTime;
	
	@Column(name = "created_time")
	private LocalDateTime createdTime;
	
	@OneToMany(mappedBy = "bookingOrder")
	private List<BookingOrderItem> bookingOrderItems = new ArrayList<>();

	public BookingOrder() {
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public List<BookingOrderItem> getBookingOrderItems() {
		return bookingOrderItems;
	}

	public void setBookingOrderItems(List<BookingOrderItem> bookingOrderItems) {
		this.bookingOrderItems = bookingOrderItems;
	}

	@Override
	public String toString() {
		return "BookingOrder [bookingId=" + bookingId + ", orderNumber=" + orderNumber + ", orderStatus=" + orderStatus
				+ ", totalPrice=" + totalPrice + ", updatedTime=" + updatedTime + ", createdTime=" + createdTime + "]";
	}
	
}