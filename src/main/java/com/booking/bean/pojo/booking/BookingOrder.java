package com.booking.bean.pojo.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.booking.bean.pojo.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id") // 外鍵 user_id
	private User user;

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
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "roomtype_id", referencedColumnName = "roomtype_id")
	private Roomtype roomtype;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Roomtype getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(Roomtype roomtype) {
		this.roomtype = roomtype;
	}

	public BookingOrder(Integer bookingId, User user, String orderNumber, Integer orderStatus, Long totalPrice,
			LocalDateTime updatedTime, LocalDateTime createdTime, List<BookingOrderItem> bookingOrderItems) {
		this.bookingId = bookingId;
		this.user = user;
		this.orderNumber = orderNumber;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
		this.bookingOrderItems = bookingOrderItems;
	}

	public BookingOrder(User user, String orderNumber, Integer orderStatus, Long totalPrice, LocalDateTime updatedTime,
			LocalDateTime createdTime, List<BookingOrderItem> bookingOrderItems) {
		this.user = user;
		this.orderNumber = orderNumber;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
		this.bookingOrderItems = bookingOrderItems;
	}

	
}