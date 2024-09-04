package com.booking.bean.booking;

import java.time.LocalDateTime;

public class Roomtype {
	
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

	public Roomtype() {}

	public Roomtype(Integer roomtypeId, String roomtypeName, Integer roomtypeCapacity, Integer roomtypePrice, Integer roomtypeQuantity,
			String roomtypeDescription, String roomtypeAddress, String roomtypeCity, String roomtypeDistrict) {
		this.roomtypeId = roomtypeId;
		this.roomtypeName = roomtypeName;
		this.roomtypeCapacity = roomtypeCapacity;
		this.roomtypePrice = roomtypePrice;
		this.roomtypeQuantity = roomtypeQuantity;
		this.roomtypeDescription = roomtypeDescription;
		this.roomtypeAddress = roomtypeAddress;
		this.roomtypeCity = roomtypeCity;
		this.roomtypeDistrict = roomtypeDistrict;
	}
	
	public Roomtype(String roomtypeName, Integer roomtypeCapacity, Integer roomtypePrice, Integer roomtypeQuantity,
			String roomtypeDescription, String roomtypeAddress, String roomtypeCity, String roomtypeDistrict, LocalDateTime updatedTime, LocalDateTime createdTime) {
		this.roomtypeName = roomtypeName;
		this.roomtypeCapacity = roomtypeCapacity;
		this.roomtypePrice = roomtypePrice;
		this.roomtypeQuantity = roomtypeQuantity;
		this.roomtypeDescription = roomtypeDescription;
		this.roomtypeAddress = roomtypeAddress;
		this.roomtypeCity = roomtypeCity;
		this.roomtypeDistrict = roomtypeDistrict;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
	}

	public Roomtype(Integer roomtypeId, String roomtypeName, Integer roomtypeCapacity, Integer roomtypePrice,
			Integer roomtypeQuantity, String roomtypeDescription, String roomtypeAddress, String roomtypeCity, String roomtypeDistrict, LocalDateTime createdTime, LocalDateTime updatedTime) {
		this.roomtypeId = roomtypeId;
		this.roomtypeName = roomtypeName;
		this.roomtypeCapacity = roomtypeCapacity;
		this.roomtypePrice = roomtypePrice;
		this.roomtypeQuantity = roomtypeQuantity;
		this.roomtypeDescription = roomtypeDescription;
		this.roomtypeAddress = roomtypeAddress;
		this.roomtypeCity = roomtypeCity;
		this.roomtypeDistrict = roomtypeDistrict;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
	}

	public Roomtype(String roomtypeName, Integer roomtypeCapacity, Integer roomtypePrice, Integer roomtypeQuantity,
			String roomtypeDescription, String roomtypeAddress, String roomtypeCity, String roomtypeDistrict) {
		this.roomtypeName = roomtypeName;
		this.roomtypeCapacity = roomtypeCapacity;
		this.roomtypePrice = roomtypePrice;
		this.roomtypeQuantity = roomtypeQuantity;
		this.roomtypeDescription = roomtypeDescription;
		this.roomtypeAddress = roomtypeAddress;
		this.roomtypeCity = roomtypeCity;
		this.roomtypeDistrict = roomtypeDistrict;
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

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	
	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	@Override
	public String toString() {
		return "Roomtype [roomtypeId=" + roomtypeId + ", roomtypeName=" + roomtypeName + ", roomtypeCapacity="
				+ roomtypeCapacity + ", roomtypePrice=" + roomtypePrice + ", roomtypeQuantity=" + roomtypeQuantity
				+ ", roomtypeDescription=" + roomtypeDescription + ", createdTime=" + createdTime + ", updatedTime="
				+ updatedTime + "]";
	}
}
