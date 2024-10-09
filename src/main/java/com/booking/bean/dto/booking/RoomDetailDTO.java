package com.booking.bean.dto.booking;

import java.time.LocalDateTime;

public class RoomDetailDTO {
	private Integer roomId;
	private String roomtypeName;
	private String roomNumber;
	private Integer bookingStatus;
	private String roomDescription;
	private LocalDateTime updatedTime;
	private Integer roomStatus;
	private Integer roomtypeId;

	public RoomDetailDTO() {
	}
	
	public RoomDetailDTO(String roomtypeName, String roomNumber, Integer roomStatus, String roomDescription,
			LocalDateTime updatedTime) {
		this.roomtypeName = roomtypeName;
		this.roomNumber = roomNumber;
		this.roomStatus = roomStatus;
		this.roomDescription = roomDescription;
		this.updatedTime = updatedTime;
	}

	public String getRoomtypeName() {
		return roomtypeName;
	}

	public void setRoomtypeName(String roomtypeName) {
		this.roomtypeName = roomtypeName;
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

	public Integer getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(Integer bookingStatus) {
		this.bookingStatus = bookingStatus;
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

	@Override
	public String toString() {
		return "RoomDetailDTO [roomtypeName=" + roomtypeName + ", roomNumber=" + roomNumber + ", roomStatus="
				+ roomStatus + ", roomDescription=" + roomDescription + ", updatedTime=" + updatedTime + ", roomId="
				+ roomId + ", bookingStatus=" + bookingStatus + ", roomtypeId=" + roomtypeId + "]";
	}
	
}
