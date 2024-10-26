package com.booking.bean.dto.booking;

import java.time.LocalDateTime;
import java.util.List;

import com.booking.bean.pojo.common.Amenity;

public class RoomtypeDTO {
	private Integer roomtypeId;
	private String roomtypeName;
	private Integer roomtypePrice;
	private Integer roomtypeCapacity;
	private Integer roomtypeQuantity;
	private Double area;
	private Double score;
	private String roomtypeDescription;
	private String roomtypeAddress;
	private String roomtypeCity;
	private String roomtypeDistrict;
	private LocalDateTime updatedTime;
	private LocalDateTime createdTime;
	private String imagePath;
	private List<Amenity> amenities;
	
	private Integer pageNumber = 1;
	private String attrOrderBy = "createdTime";
	private Boolean selectedSort = true;
	private Integer minMoney = 0;
	private Integer maxMoney = 0;

	public RoomtypeDTO() {
	}

	public RoomtypeDTO(Integer roomtypeId, String roomtypeName, Integer roomtypePrice, Integer roomtypeCapacity,
			Integer roomtypeQuantity, Double area, Double score, String roomtypeDescription, String roomtypeAddress,
			String roomtypeCity, String roomtypeDistrict, LocalDateTime updatedTime, LocalDateTime createdTime,
			String imagePath) {
		this.roomtypeId = roomtypeId;
		this.roomtypeName = roomtypeName;
		this.roomtypePrice = roomtypePrice;
		this.roomtypeCapacity = roomtypeCapacity;
		this.roomtypeQuantity = roomtypeQuantity;
		this.area = area;
		this.score = score;
		this.roomtypeDescription = roomtypeDescription;
		this.roomtypeAddress = roomtypeAddress;
		this.roomtypeCity = roomtypeCity;
		this.roomtypeDistrict = roomtypeDistrict;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
		this.imagePath = imagePath;
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

	public Integer getMinMoney() {
		return minMoney;
	}

	public void setMinMoney(Integer minMoney) {
		this.minMoney = minMoney;
	}

	public Integer getMaxMoney() {
		return maxMoney;
	}

	public void setMaxMoney(Integer maxMoney) {
		this.maxMoney = maxMoney;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<Amenity> getAmenities() {
		return amenities;
	}

	public void setAmenities(List<Amenity> amenities) {
		this.amenities = amenities;
	}
	
	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	@Override
	public String toString() {
		return "RoomtypeDTO [roomtypeId=" + roomtypeId + ", roomtypeName=" + roomtypeName + ", roomtypePrice="
				+ roomtypePrice + ", roomtypeCapacity=" + roomtypeCapacity + ", roomtypeQuantity=" + roomtypeQuantity
				+ ", roomtypeDescription=" + roomtypeDescription + ", roomtypeAddress=" + roomtypeAddress
				+ ", roomtypeCity=" + roomtypeCity + ", roomtypeDistrict=" + roomtypeDistrict + ", updatedTime="
				+ updatedTime + ", createdTime=" + createdTime + ", imagePath=" + imagePath + ", amenities=" + amenities
				+ ", pageNumber=" + pageNumber + ", attrOrderBy=" + attrOrderBy + ", selectedSort=" + selectedSort
				+ ", minMoney=" + minMoney + ", maxMoney=" + maxMoney + "]";
	}

}
