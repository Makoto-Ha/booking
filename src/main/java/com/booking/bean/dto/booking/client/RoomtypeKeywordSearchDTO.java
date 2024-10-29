package com.booking.bean.dto.booking.client;

import java.time.LocalDate;

public class RoomtypeKeywordSearchDTO {
	private LocalDate searchStartDate;
	private LocalDate searchEndDate;
	private String roomtypeName;
	private String roomtypeDistrict;
	private String roomtypeCity;
	private Integer peopleNumber;
	private Integer pageNumber = 1;

	public RoomtypeKeywordSearchDTO() {
	}

	public LocalDate getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(LocalDate searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public LocalDate getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(LocalDate searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getRoomtypeName() {
		return roomtypeName;
	}

	public void setRoomtypeName(String roomtypeName) {
		this.roomtypeName = roomtypeName;
	}

	public String getRoomtypeDistrict() {
		return roomtypeDistrict;
	}

	public void setRoomtypeDistrict(String roomtypeDistrict) {
		this.roomtypeDistrict = roomtypeDistrict;
	}

	public String getRoomtypeCity() {
		return roomtypeCity;
	}

	public void setRoomtypeCity(String roomtypeCity) {
		this.roomtypeCity = roomtypeCity;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	@Override
	public String toString() {
		return "RoomtypeKeywordSearchDTO [searchStartDate=" + searchStartDate + ", searchEndDate=" + searchEndDate
				+ ", roomtypeName=" + roomtypeName + ", roomtypeDistrict=" + roomtypeDistrict + ", roomtypeCity="
				+ roomtypeCity + ", peopleNumber=" + peopleNumber + ", pageNumber=" + pageNumber + "]";
	}
	
}