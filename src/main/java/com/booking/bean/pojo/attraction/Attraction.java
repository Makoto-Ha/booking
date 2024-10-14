package com.booking.bean.pojo.attraction;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity @Table(name = "attraction")
public class Attraction{

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
	
	@Column(name = "images_file")
	private String imagesFile;
	
	@OneToMany(mappedBy = "attraction")
	private List<PackageTourAttraction> packageTourAttractions = new ArrayList<>();
	
	public Attraction() {
		
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


	public String getImagesFile() {
		return imagesFile;
	}


	public void setImagesFile(String imagesFile) {
		this.imagesFile = imagesFile;
	}


	public List<PackageTourAttraction> getPackageTourAttractions() {
		return packageTourAttractions;
	}


	public void setPackageTourAttractions(List<PackageTourAttraction> packageTourAttractions) {
		this.packageTourAttractions = packageTourAttractions;
	}


	@Override
	public String toString() {
		return "Attraction [attractionId=" + attractionId + ", attractionName=" + attractionName + ", attractionCity="
				+ attractionCity + ", address=" + address + ", openingHour=" + openingHour + ", attractionType="
				+ attractionType + ", attractionDescription=" + attractionDescription + ", imagesFile=" + imagesFile
				+ ", packageTourAttractions=" + packageTourAttractions + "]";
	}



}
