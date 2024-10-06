package com.booking.bean.pojo.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "room")
@DynamicInsert
public class Room {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private Integer roomId;
	
	@Column(name = "room_number")
	private String roomNumber;
	
	@Column(name = "room_status")
	private Integer roomStatus;
	
	@Column(name = "room_description")
	private String roomDescription;
	
	@Column(name = "updated_time")
	private LocalDateTime updatedTime;
	
	@Column(name = "created_time")
	private LocalDateTime createdTime;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roomtype_id")
	private Roomtype roomtype;
	
	@OneToMany(mappedBy = "room")
	private List<BookingOrderItem> bookingOrderItems = new ArrayList<>();

	public Room() {}

	public Room(String roomNumber, Integer roomStatus, String roomDescription,
			LocalDateTime updatedTime, LocalDateTime createdTime) {
		this.roomNumber = roomNumber;
		this.roomStatus = roomStatus;
		this.roomDescription = roomDescription;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
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
	
	public Roomtype getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(Roomtype roomtype) {
		this.roomtype = roomtype;
	}

	public List<BookingOrderItem> getBookingOrderItems() {
		return bookingOrderItems;
	}

	public void setBookingOrderItems(List<BookingOrderItem> bookingOrderItems) {
		this.bookingOrderItems = bookingOrderItems;
	}

	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomNumber=" + roomNumber + ", roomStatus="
				+ roomStatus + ", roomDescription=" + roomDescription + ", updatedTime=" + updatedTime
				+ ", createdTime=" + createdTime + "]";
	}
	
}
