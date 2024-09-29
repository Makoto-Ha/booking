package com.booking.bean.dto.booking;

import java.time.LocalDateTime;

public class RoomtypeDTO {
	private Integer roomtypeId;
	private String roomtypeName;
	private Integer roomtypeCapacity;
	private Integer roomtypePrice;
	private Integer roomtypeQuantity;
	private String roomtypeDescription;
	private String roomtypeAddress;
	private String roomtypeCity;
	private String roomtypeDistrict;
	private LocalDateTime updatedTime;
	private LocalDateTime createdTime;
	private Long totalCounts;

	public RoomtypeDTO() {
	}

	public Integer getRoomtypeId() {
		return roomtypeId;
	}

	public void setRoomtypeId(Integer roomtypeId) {
		this.roomtypeId = roomtypeId;
	}

	public String getRoomtypeName() {
		return roomtypeName;
	}

	public void setRoomtypeName(String roomtypeName) {
		this.roomtypeName = roomtypeName;
	}

	public Integer getRoomtypeCapacity() {
		return roomtypeCapacity;
	}

	public void setRoomtypeCapacity(Integer roomtypeCapacity) {
		this.roomtypeCapacity = roomtypeCapacity;
	}

	public Integer getRoomtypePrice() {
		return roomtypePrice;
	}

	public void setRoomtypePrice(Integer roomtypePrice) {
		this.roomtypePrice = roomtypePrice;
	}

	public Integer getRoomtypeQuantity() {
		return roomtypeQuantity;
	}

	public void setRoomtypeQuantity(Integer roomtypeQuantity) {
		this.roomtypeQuantity = roomtypeQuantity;
	}

	public String getRoomtypeDescription() {
		return roomtypeDescription;
	}

	public void setRoomtypeDescription(String roomtypeDescription) {
		this.roomtypeDescription = roomtypeDescription;
	}

	public String getRoomtypeAddress() {
		return roomtypeAddress;
	}

	public void setRoomtypeAddress(String roomtypeAddress) {
		this.roomtypeAddress = roomtypeAddress;
	}

	public String getRoomtypeCity() {
		return roomtypeCity;
	}

	public void setRoomtypeCity(String roomtypeCity) {
		this.roomtypeCity = roomtypeCity;
	}

	public String getRoomtypeDistrict() {
		return roomtypeDistrict;
	}

	public void setRoomtypeDistrict(String roomtypeDistrict) {
		this.roomtypeDistrict = roomtypeDistrict;
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

	public Long getTotalCounts() {
		return totalCounts;
	}

	public void setTotalCounts(Long totalCounts) {
		this.totalCounts = totalCounts;
	}
}
