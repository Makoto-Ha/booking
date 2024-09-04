package com.booking.bean.booking;

import java.time.LocalDateTime;

public class Room {
	
	private Integer roomId;
	private Integer roomtypeId;
	private String roomNumber;
	private Integer roomStatus;
	private String roomDescription;
	private LocalDateTime updatedTime;
	private LocalDateTime createdTime;
	

	public Room() {}

	public Room(Integer roomtypeId, String roomNumber, Integer roomStatus, String roomDescription,
			LocalDateTime updatedTime, LocalDateTime createdTime) {
		this.roomtypeId = roomtypeId;
		this.roomNumber = roomNumber;
		this.roomStatus = roomStatus;
		this.roomDescription = roomDescription;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
	}
	
	public Room(Integer roomId, Integer roomtypeId, String roomNumber, Integer roomStatus, String roomDescription,
			LocalDateTime updatedTime, LocalDateTime createdTime) {
		this.roomId = roomId;
		this.roomtypeId = roomtypeId;
		this.roomNumber = roomNumber;
		this.roomStatus = roomStatus;
		this.roomDescription = roomDescription;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
	}
	
	public Room(Integer roomId, Integer roomtypeId, String roomNumber, Integer roomStatus, String roomDescription) {
		this.roomId = roomId;
		this.roomtypeId = roomtypeId;
		this.roomNumber = roomNumber;
		this.roomStatus = roomStatus;
		this.roomDescription = roomDescription;
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

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public Integer getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
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

	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomtypeId=" + roomtypeId + ", roomNumber=" + roomNumber + ", roomStatus="
				+ roomStatus + ", roomDescription=" + roomDescription + ", updatedTime=" + updatedTime
				+ ", createdTime=" + createdTime + "]";
	}
}
