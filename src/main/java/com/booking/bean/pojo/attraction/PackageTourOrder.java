package com.booking.bean.pojo.attraction;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.booking.bean.pojo.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "package_tour_order")
public class PackageTourOrder {
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;
    
    @Column(name = "user_id", insertable = false, updatable = false) 
    private Integer userId;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false) 
	private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_tour_id", nullable = false)
    private PackageTour packageTour;
    
    @Column(name = "order_status")
    private Integer orderStatus;
    
    @Column(name = "order_datetime")
    private LocalDateTime orderDateTime;
    
    @Column(name = "order_price")
    private Integer orderPrice;
    
    @Column(name = "travel_date")
    private LocalDate travelDate;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PackageTour getPackageTour() {
		return packageTour;
	}

	public void setPackageTour(PackageTour packageTour) {
		this.packageTour = packageTour;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}

	public Integer getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Integer orderPrice) {
		this.orderPrice = orderPrice;
	}

	public LocalDate getTravelDate() {
		return travelDate;
	}

	public void setTravelDate(LocalDate travelDate) {
		this.travelDate = travelDate;
	}


	@Override
	public String toString() {
		return "PackageTourOrder [orderId=" + orderId + ", user=" + user + ", packageTour=" + packageTour
				+ ", orderStatus=" + orderStatus + ", orderDateTime=" + orderDateTime + ", orderPrice=" + orderPrice
				+ ", travelDate=" + travelDate + "]";
	}

	public PackageTourOrder(Integer orderId, User user, PackageTour packageTour, Integer orderStatus,
			LocalDateTime orderDateTime, Integer orderPrice, LocalDate travelDate) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.packageTour = packageTour;
		this.orderStatus = orderStatus;
		this.orderDateTime = orderDateTime;
		this.orderPrice = orderPrice;
		this.travelDate = travelDate;
	}

	public PackageTourOrder(User user, PackageTour packageTour, Integer orderStatus,
			LocalDateTime orderDateTime, Integer orderPrice, LocalDate travelDate) {
		super();
		this.user = user;
		this.packageTour = packageTour;
		this.orderStatus = orderStatus;
		this.orderDateTime = orderDateTime;
		this.orderPrice = orderPrice;
		this.travelDate = travelDate;
	}

	public PackageTourOrder(PackageTour packageTour, Integer orderStatus, LocalDateTime orderDateTime,
			Integer orderPrice, LocalDate travelDate) {
		super();
		this.packageTour = packageTour;
		this.orderStatus = orderStatus;
		this.orderDateTime = orderDateTime;
		this.orderPrice = orderPrice;
		this.travelDate = travelDate;
	}

	public PackageTourOrder() {
		
	}


}
