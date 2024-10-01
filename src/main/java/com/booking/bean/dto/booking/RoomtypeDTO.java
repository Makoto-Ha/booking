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
	private String imageFile;
	
	private Integer pageNumber = 1;
	private String attrOrderBy = "createdTime";
	private Boolean selectedSort = true;
	private Integer minMoney = 0;
	private Integer maxMoney = 0;

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

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

	@Override
	public String toString() {
		return "RoomtypeDTO [roomtypeId=" + roomtypeId + ", roomtypeName=" + roomtypeName + ", roomtypeCapacity="
				+ roomtypeCapacity + ", roomtypePrice=" + roomtypePrice + ", roomtypeQuantity=" + roomtypeQuantity
				+ ", roomtypeDescription=" + roomtypeDescription + ", roomtypeAddress=" + roomtypeAddress
				+ ", roomtypeCity=" + roomtypeCity + ", roomtypeDistrict=" + roomtypeDistrict + ", updatedTime="
				+ updatedTime + ", createdTime=" + createdTime + ", imageFile=" + imageFile + ", pageNumber="
				+ pageNumber + ", attrOrderBy=" + attrOrderBy + ", selectedSort=" + selectedSort + ", minMoney="
				+ minMoney + ", maxMoney=" + maxMoney + "]";
	}
	
}
