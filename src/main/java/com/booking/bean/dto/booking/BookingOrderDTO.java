package com.booking.bean.dto.booking;

import java.time.LocalDateTime;
import java.util.List;

import com.booking.bean.dto.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BookingOrderDTO {
	
	private Integer bookingId;
	
	private String orderNumber;
	
	private Integer orderStatus;
	
	private Long totalPrice;
	
	private LocalDateTime updatedTime;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createdTime;

	private Integer roomtypeId;
	
	private RoomtypeDTO roomtype;
	
	private UserDTO user;
	
	private List<BookingOrderItemDTO> bookingOrderItems;
	
	private Integer pageNumber = 1;
	
	private String attrOrderBy = "createdTime";
	
	private Boolean selectedSort = true;
	
	public BookingOrderDTO() {}
	
	public BookingOrderDTO(Integer bookingId, String orderNumber, Integer orderStatus, Long totalPrice, LocalDateTime updatedTime, LocalDateTime createdTime) {
		this.bookingId = bookingId;
		this.orderNumber = orderNumber;
		this.orderStatus = orderStatus;
		this.totalPrice = totalPrice;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
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

	public List<BookingOrderItemDTO> getBookingOrderItems() {
		return bookingOrderItems;
	}

	public void setBookingOrderItems(List<BookingOrderItemDTO> bookingOrderItems) {
		this.bookingOrderItems = bookingOrderItems;
	}
	
	public RoomtypeDTO getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(RoomtypeDTO roomtype) {
		this.roomtype = roomtype;
	}

	public UserDTO getUser() {
		return user;
	}

	public void setUser(UserDTO user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "BookingOrderDTO [roomtypeId=" + roomtypeId + ", bookingOrderItems=" + bookingOrderItems + "user:" + user + "]";
	}


}