package com.booking.bean.dto.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingOrderSearchDTO {
	private LocalDate fromCheckInDate;
	private LocalDate toCheckInDate;
	private LocalDate fromCheckOutDate;
	private LocalDate toCheckOutDate;
	
	private LocalDateTime fromCheckInTime;
	private LocalDateTime toCheckInTime;
	private LocalDateTime fromCheckOutTime;
	private LocalDateTime toCheckOutTime;
	
	private String orderNumber;
	private String bookingName;
	private Integer orderStatus;
	private Long totalPrice;
	
	public BookingOrderSearchDTO() {
	}
	
	public LocalDate getFromCheckInDate() {
		return fromCheckInDate;
	}
	public void setFromCheckInDate(LocalDate fromCheckInDate) {
		this.fromCheckInDate = fromCheckInDate;
	}
	public LocalDate getToCheckInDate() {
		return toCheckInDate;
	}
	public void setToCheckInDate(LocalDate toCheckInDate) {
		this.toCheckInDate = toCheckInDate;
	}
	public LocalDate getFromCheckOutDate() {
		return fromCheckOutDate;
	}
	public void setFromCheckOutDate(LocalDate fromCheckOutDate) {
		this.fromCheckOutDate = fromCheckOutDate;
	}
	public LocalDate getToCheckOutDate() {
		return toCheckOutDate;
	}
	public void setToCheckOutDate(LocalDate toCheckOutDate) {
		this.toCheckOutDate = toCheckOutDate;
	}
	public LocalDateTime getFromCheckInTime() {
		return fromCheckInTime;
	}
	public void setFromCheckInTime(LocalDateTime fromCheckInTime) {
		this.fromCheckInTime = fromCheckInTime;
	}
	public LocalDateTime getToCheckInTime() {
		return toCheckInTime;
	}
	public void setToCheckInTime(LocalDateTime toCheckInTime) {
		this.toCheckInTime = toCheckInTime;
	}
	public LocalDateTime getFromCheckOutTime() {
		return fromCheckOutTime;
	}
	public void setFromCheckOutTime(LocalDateTime fromCheckOutTime) {
		this.fromCheckOutTime = fromCheckOutTime;
	}
	public LocalDateTime getToCheckOutTime() {
		return toCheckOutTime;
	}
	public void setToCheckOutTime(LocalDateTime toCheckOutTime) {
		this.toCheckOutTime = toCheckOutTime;
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
	
	public String getBookingName() {
		return bookingName;
	}

	public void setBookingName(String bookingName) {
		this.bookingName = bookingName;
	}

	@Override
	public String toString() {
		return "BookingOrderSearchDTO [fromCheckInDate=" + fromCheckInDate + ", toCheckInDate=" + toCheckInDate
				+ ", fromCheckOutDate=" + fromCheckOutDate + ", toCheckOutDate=" + toCheckOutDate + ", fromCheckInTime="
				+ fromCheckInTime + ", toCheckInTime=" + toCheckInTime + ", fromCheckOutTime=" + fromCheckOutTime
				+ ", toCheckOutTime=" + toCheckOutTime + ", orderNumber=" + orderNumber + ", bookingName=" + bookingName
				+ ", orderStatus=" + orderStatus + ", totalPrice=" + totalPrice + "]";
	}
	
}
