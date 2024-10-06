package com.booking.bean.pojo.booking;

import java.io.Serializable;
import java.util.Objects;

public class BookingOrderItemId implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer bookingId;
	private Integer roomId;
	
	public BookingOrderItemId() {
	}
	
	public BookingOrderItemId(Integer bookingId, Integer roomId) {
		this.bookingId = bookingId;
		this.roomId = roomId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookingId, roomId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingOrderItemId other = (BookingOrderItemId) obj;
		return Objects.equals(bookingId, other.bookingId) && Objects.equals(roomId, other.roomId);
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}