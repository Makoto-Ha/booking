package com.booking.bean.pojo.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roomtype")
public class Roomtype {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "roomtype_id")
	private Integer roomtypeId;

	@Column(name = "roomtype_name")
	private String roomtypeName;

	@Column(name = "roomtype_capacity")
	private Integer roomtypeCapacity;

	@Column(name = "roomtype_price")
	private Integer roomtypePrice;

	@Column(name = "roomtype_quantity")
	private Integer roomtypeQuantity;

	@Column(name = "roomtype_description")
	private String roomtypeDescription;

	@Column(name = "roomtype_address")
	private String roomtypeAddress;

	@Column(name = "roomtype_city")
	private String roomtypeCity;

	@Column(name = "roomtype_district")
	private String roomtypeDistrict;

	@Column(name = "updated_time")
	private LocalDateTime updatedTime;

	@Column(name = "created_time")
	private LocalDateTime createdTime;

	@Column(name = "image_path")
	private String imagePath;
	
	@OneToMany(mappedBy = "roomtype", cascade = CascadeType.ALL)
	private List<Room> rooms = new ArrayList<>();

	public Roomtype() {
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

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "Roomtype [roomtypeId=" + roomtypeId + ", roomtypeName=" + roomtypeName + ", roomtypeCapacity="
				+ roomtypeCapacity + ", roomtypePrice=" + roomtypePrice + ", roomtypeQuantity=" + roomtypeQuantity
				+ ", roomtypeDescription=" + roomtypeDescription + ", roomtypeAddress=" + roomtypeAddress
				+ ", roomtypeCity=" + roomtypeCity + ", roomtypeDistrict=" + roomtypeDistrict + ", updatedTime="
				+ updatedTime + ", createdTime=" + createdTime + ", imagePath=" + imagePath + ", rooms=" + rooms + "]";
	}

}
