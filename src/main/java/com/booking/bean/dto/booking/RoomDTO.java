package com.booking.bean.dto.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RoomDTO {
	private Integer roomId;
	private String roomtypeName;
	private String roomNumber;
	private Integer roomStatus;
	private String roomDescription;
	private LocalDateTime updatedTime;
	private LocalDate bookingDate = LocalDate.now();
	private Integer pageNumber = 1;
	private String attrOrderBy = "updatedTime";
	private Boolean selectedSort = true;
	private Integer roomtypeId;

	public RoomDTO() {}
	
	public RoomDTO(Integer roomId, Integer roomStatus, String roomDescription, String roomNumber, String roomtypeName, Integer roomtypeId) {
		this.roomId = roomId;
		this.roomtypeName = roomtypeName;
		this.roomNumber = roomNumber;
		this.roomStatus = roomStatus;
		this.roomDescription = roomDescription;
		this.roomtypeId = roomtypeId;
	}

	public String getRoomtypeName() {
		return roomtypeName;
	}

	public void setRoomtypeName(String roomtypeName) {
		this.roomtypeName = roomtypeName;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
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

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public Integer getRoomtypeId() {
		return roomtypeId;
	}

	public void setRoomtypeId(Integer roomtypeId) {
		this.roomtypeId = roomtypeId;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
	
	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	@Override
	public String toString() {
		return "RoomDTO [roomId=" + roomId + ", roomtypeName=" + roomtypeName + ", roomNumber=" + roomNumber
				+ ", roomStatus=" + roomStatus + ", roomDescription=" + roomDescription + ", updatedTime=" + updatedTime
				+ ", bookingDate=" + bookingDate + ", pageNumber=" + pageNumber + ", attrOrderBy=" + attrOrderBy
				+ ", selectedSort=" + selectedSort + ", roomtypeId=" + roomtypeId + "]";
	}

}
