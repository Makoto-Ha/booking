package com.booking.bean.pojo.booking;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking_order_item")
@DynamicInsert
@DynamicUpdate
public class BookingOrderItem {
	
	@EmbeddedId
	private BookingOrderItemId id;
	
	@ManyToOne
	@MapsId("bookingId")
	@JoinColumn(name = "booking_id")
	private BookingOrder bookingOrder;
	
	@ManyToOne
	@MapsId("roomId")
	@JoinColumn(name = "room_id")
	private Room room;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roomtype_id")
	private Roomtype roomtype;
	
	@Column(name = "check_in_date")
	private LocalDate checkInDate;
	
	@Column(name = "check_out_date")
	private LocalDate checkOutDate;
	
	@Column(name = "check_in_time")
	private LocalDateTime checkInTime;
	
	@Column(name = "check_out_time")
	private LocalDateTime checkOutTime;
	
	@Column(name = "updated_time")
	private LocalDateTime updatedTime;
	
	@Column(name = "price")
	private Long price;
	
	@Column(name = "booking_status")
	private Integer bookingStatus;
	
	public BookingOrderItem() {
	}
	
	public BookingOrderItem(BookingOrder bookingOrder, Room room) {
		this.id = new BookingOrderItemId(bookingOrder.getBookingId(), room.getRoomId());
		this.bookingOrder = bookingOrder;
		this.room = room;
	}

	public BookingOrderItem(BookingOrder bookingOrder, Room room, Roomtype roomtypeId,
			LocalDate checkInDate, LocalDate checkOutDate, LocalDateTime checkInTime, LocalDateTime checkOutTime,
			LocalDateTime updatedTime, Long price) {
		this.id = new BookingOrderItemId(bookingOrder.getBookingId(), room.getRoomId());
		this.bookingOrder = bookingOrder;
		this.room = room;
		this.checkInDate = checkInDate;
		this.checkOutDate = checkOutDate;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		this.updatedTime = updatedTime;
		this.price = price;
	}

	public BookingOrderItemId getId() {
		return id;
	}

	public void setId(Integer BookingId, Integer RoomId) {
		this.id = new BookingOrderItemId(BookingId, RoomId);
	}
	
	public BookingOrder getBookingOrder() {
		return bookingOrder;
	}

	public void setBookingOrder(BookingOrder bookingOrder) {
		this.bookingOrder = bookingOrder;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public Roomtype getRoomtype() {
		return roomtype;
	}

	public void setRoomtype(Roomtype roomtype) {
		this.roomtype = roomtype;
	}

	public LocalDate getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(LocalDate checkInDate) {
		this.checkInDate = checkInDate;
	}

	public LocalDate getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(LocalDate checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public LocalDateTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalDateTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalDateTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalDateTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
	}

	public Integer getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(Integer bookingStatus) {
		this.bookingStatus = bookingStatus;
	}
	

}
