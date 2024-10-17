package com.booking.bean.dto.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.booking.bean.pojo.booking.Roomtype;

public class BookingOrderItemDTO {

	private Integer roomId;
	
	private Roomtype roomtypeId;
	
	private Long price;
	
	private LocalDate checkInDate;
	
	private LocalDate checkOutDate;
	
	private LocalDateTime checkInTime;
	
	private LocalDateTime checkOutTime;
	
	private LocalDateTime updatedTime;

	public BookingOrderItemDTO() {
	}
	
	public BookingOrderItemDTO(Integer roomId, Roomtype roomtypeId, Long price, LocalDate checkInDate,
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

	public Roomtype getRoomtypeId() {
		return roomtypeId;
	}

	public void setRoomtypeId(Roomtype roomtypeId) {
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
	
}
