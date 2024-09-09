package com.booking.bean.attraction;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity @Table(name = "attraction")
public class Attraction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @Column(name = "attraction_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer attractionId;
	
	@Column(name = "attraction_name")
	private String attractionName;
	
	@Column(name = "attraction_city")
	private String attractionCity;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "opening_hours")
	private String openingHour;
	
	@Column(name = "attraction_type")
	private String attractionType;
	
	@Column(name = "attraction_description")
	private String attractionDescription;
	
	public Attraction() {}

	public Attraction(Integer attractionId, String attractionName, String attractionCity, String address,
			String openingHour, String attractionType, String attractionDescription) {
		super();
		this.attractionId = attractionId;
		this.attractionName = attractionName;
		this.attractionCity = attractionCity;
		this.address = address;
		this.openingHour = openingHour;
		this.attractionType = attractionType;
		this.attractionDescription = attractionDescription;
	}

	public Attraction(String attractionName, String attractionCity, String address, String openingHour,
			String attractionType, String attractionDescription) {
		super();
		this.attractionName = attractionName;
		this.attractionCity = attractionCity;
		this.address = address;
		this.openingHour = openingHour;
		this.attractionType = attractionType;
		this.attractionDescription = attractionDescription;
	}

	public Integer getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(Integer attractionId) {
		this.attractionId = attractionId;
	}

	public String getAttractionName() {
		return attractionName;
	}

	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

	public String getAttractionCity() {
		return attractionCity;
	}

	public void setAttractionCity(String attractionCity) {
		this.attractionCity = attractionCity;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOpeningHour() {
		return openingHour;
	}

	public void setOpeningHour(String openingHour) {
		this.openingHour = openingHour;
	}

	public String getAttractionType() {
		return attractionType;
	}

	public void setAttractionType(String attractionType) {
		this.attractionType = attractionType;
	}

	public String getAttractionDescription() {
		return attractionDescription;
	}

	public void setAttractionDescription(String attractionDescription) {
		this.attractionDescription = attractionDescription;
	}

	@Override
	public String toString() {
		return "Attraction [attractionId=" + attractionId + ", attractionName=" + attractionName + ", attractionCity="
				+ attractionCity + ", address=" + address + ", openingHour=" + openingHour + ", attractionType="
				+ attractionType + ", attractionDescription=" + attractionDescription + "]";
	}

}
