package com.booking.bean.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingOrderItem {
	private Integer bookingId;
	private Integer roomtypeId;
	private Integer roomId;
	private Integer price;
	private Integer quantity;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	private LocalDateTime checkInTime;
	private LocalDateTime checkOutTime;

	public BookingOrderItem() {}
	
	public BookingOrderItem(Integer bookingId, Integer roomtypeId, Integer roomId, Integer price, Integer quantity,
			LocalDate checkInDate, LocalDate checkOutDate, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
		this.bookingId = bookingId;
		this.roomtypeId = roomtypeId;
		this.roomId = roomId;
		this.price = price;
		this.quantity = quantity;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getRoomtypeId() {
		return roomtypeId;
	}

	public void setRoomtypeId(Integer roomtypeId) {
		this.roomtypeId = roomtypeId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDateTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	@Override
	public String toString() {
		return "RoomOrderItem [bookingId=" + bookingId + ", roomtypeId=" + roomtypeId + ", roomId=" + roomId
				+ ", price=" + price + ", quantity=" + quantity + ", checkInDate=" + checkInDate + ", checkOutDate="
				+ checkOutDate + ", checkInTime=" + checkInTime + ", checkOutTime=" + checkOutTime + "]";
	}
}
