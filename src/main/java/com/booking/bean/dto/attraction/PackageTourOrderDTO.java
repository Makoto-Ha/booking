package com.booking.bean.dto.attraction;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PackageTourOrderDTO {

    private Integer orderId;
    private Integer userId;
    private Integer packageTourId;
    private String packageTourName;
    private Integer orderStatus;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime orderDateTime;
    private Integer orderPrice;
    private LocalDate travelDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate searchDate;
    
    private Integer pageNumber = 1;
    private String attrOrderBy = "orderId";
    private Boolean selectedSort = true;
    
	public PackageTourOrderDTO() {
		
	}


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

	public Integer getPackageTourId() {
		return packageTourId;
	}


	public void setPackageTourId(Integer packageTourId) {
		this.packageTourId = packageTourId;
	}


	public String getPackageTourName() {
		return packageTourName;
	}


	public void setPackageTourName(String packageTourName) {
		this.packageTourName = packageTourName;
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
	
	
	public LocalDate getSearchDate() {
		return searchDate;
	}


	public void setSearchDate(LocalDate searchDate) {
		this.searchDate = searchDate;
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


	@Override
	public String toString() {
		return "PackageTourOrderDTO [orderId=" + orderId + ", userId=" + userId + ", packageTourId=" + packageTourId
				+ ", packageTourName=" + packageTourName + ", orderStatus=" + orderStatus + ", orderDateTime="
				+ orderDateTime + ", orderPrice=" + orderPrice + ", travelDate=" + travelDate + ", searchDate="
				+ searchDate + ", pageNumber=" + pageNumber + ", attrOrderBy=" + attrOrderBy + ", selectedSort="
				+ selectedSort + "]";
	}


    
}
