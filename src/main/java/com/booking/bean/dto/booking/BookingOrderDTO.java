package com.booking.bean.dto.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingOrderDTO {
	
	private Integer bookingId;
	
	private String orderNumber;
	
	private Integer orderStatus;
	
	private Long totalPrice;
	
	private LocalDate checkInDate;
	
	private LocalDate checkOutDate;
	
	private LocalDateTime checkInTime;
	
	private LocalDateTime checkOutTime;
	
	private LocalDateTime updatedTime;
	
	private LocalDateTime createdTime;
	
	private Integer pageNumber = 1;
	
	private String attrOrderBy = "createdTime";
	
	private Boolean selectedSort = true;
	
	public BookingOrderDTO() {}
	
	public BookingOrderDTO(Integer bookingId, String orderNumber, Integer orderStatus, Long totalPrice, LocalDate checkInDate, LocalDate checkOutDate, LocalDateTime checkInTime, LocalDateTime checkOutTime, LocalDateTime updatedTime, LocalDateTime createdTime) {
		this.bookingId = bookingId;
		this.orderNumber = orderNumber;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
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

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getAttrOrderBy() {
		return attrOrderBy;
	}

	public void setAttrOrderBy(String attrOrderBy) {
		this.attrOrderBy = attrOrderBy;
	}

	public Boolean getSelectedSort() {
		return selectedSort;
	}

	public void setSelectedSort(Boolean selectedSort) {
		this.selectedSort = selectedSort;
	}

	@Override
	public String toString() {
		return "BookingOrderDTO [bookingId=" + bookingId + ", orderNumber=" + orderNumber + ", orderStatus="
				+ orderStatus + ", totalPrice=" + totalPrice + ", checkInDate=" + checkInDate + ", checkOutDate="
				+ checkOutDate + ", checkInTime=" + checkInTime + ", checkOutTime=" + checkOutTime + ", updatedTime="
				+ updatedTime + ", createdTime=" + createdTime + ", pageNumber=" + pageNumber + ", attrOrderBy="
				+ attrOrderBy + ", selectedSort=" + selectedSort + "]";
	}

}