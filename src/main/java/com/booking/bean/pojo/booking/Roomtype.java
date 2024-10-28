package com.booking.bean.pojo.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

import com.booking.bean.pojo.common.Amenity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roomtype")
@DynamicInsert
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
	
	@Column(name = "score")
	private Double score;
	
	@Column(name = "area")
	private Double area;
	
	@OneToMany(mappedBy = "roomtype", cascade = CascadeType.ALL)
	private List<Room> rooms = new ArrayList<>();
	
	@OneToMany(mappedBy = "roomtype", cascade = CascadeType.ALL)
	private List<BookingOrderItem> bookingOrderItems = new ArrayList<>();
	
	@ManyToMany
    @JoinTable(
        name = "roomtype_amenity", // 中間表名稱
        joinColumns = @JoinColumn(name = "roomtype_id"), 
        inverseJoinColumns = @JoinColumn(name = "amenity_id") 
    )
    private List<Amenity> amenities = new ArrayList<>();
	
	@OneToMany(mappedBy = "roomtype")
	private List<BookingOrder> bookingOrder;

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

	public List<BookingOrderItem> getBookingOrderItems() {
		return bookingOrderItems;
	}

	public void setBookingOrderItems(List<BookingOrderItem> bookingOrderItems) {
		this.bookingOrderItems = bookingOrderItems;
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

	public List<BookingOrder> getBookingOrder() {
		return bookingOrder;
	}

	public void setBookingOrder(List<BookingOrder> bookingOrder) {
		this.bookingOrder = bookingOrder;
	}	

}
