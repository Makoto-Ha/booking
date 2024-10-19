package com.booking.bean.dto.booking.client;

import java.time.LocalDate;
import java.util.List;

import com.booking.bean.pojo.common.Amenity;

public class RoomtypeSearchDTO {
	private LocalDate searchStartDate;
	private LocalDate searchEndDate;
	private String roomtypeName;
	private String roomtypeDistrict;
	private String roomtypeCity;
	private String peopleNumber;
	private Integer pageNumber = 1;
	private List<Amenity> amenities;

	public RoomtypeSearchDTO() {
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

	public String getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(String peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}

	@Override
	public String toString() {
		return "RoomtypeSearchDTO [searchStartDate=" + searchStartDate + ", searchEndDate=" + searchEndDate
				+ ", roomtypeName=" + roomtypeName + ", roomtypeDistrict=" + roomtypeDistrict + ", roomtypeCity="
				+ roomtypeCity + ", peopleNumber=" + peopleNumber + ", pageNumber=" + pageNumber + ", amenities="
				+ amenities + "]";
	}

}
