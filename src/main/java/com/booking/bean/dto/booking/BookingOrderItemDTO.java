package com.booking.bean.dto.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.booking.bean.pojo.booking.BookingOrderItemId;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BookingOrderItemDTO {

	private BookingOrderItemId id;
	
	private Integer roomId;
	
	private RoomDetailDTO room;
	
	private Integer roomtypeId;
	
	private Long price;
	
	private Integer bookingStatus;
	
	private LocalDate checkInDate;
	
	private LocalDate checkOutDate;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime checkInTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime checkOutTime;
	
	private LocalDateTime updatedTime;

	public BookingOrderItemDTO() {
	}
	
	public BookingOrderItemDTO(Integer roomId, Integer roomtypeId, Long price, LocalDate checkInDate,
			LocalDate checkOutDate, LocalDateTime checkInTime, LocalDateTime checkOutTime, LocalDateTime updatedTime) {
		this.roomId = roomId;
		this.roomtypeId = roomtypeId;
		this.price = price;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.updatedTime = updatedTime;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public Integer getRoomtypeId() {
		return roomtypeId;
	}

	public void setRoomtypeId(Integer roomtypeId) {
		this.roomtypeId = roomtypeId;
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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public BookingOrderItemId getId() {
		return id;
	}

	public void setId(BookingOrderItemId id) {
		this.id = id;
	}

	public Integer getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(Integer bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public RoomDetailDTO getRoom() {
		return room;
	}

	public void setRoom(RoomDetailDTO room) {
		this.room = room;
	}
	
}