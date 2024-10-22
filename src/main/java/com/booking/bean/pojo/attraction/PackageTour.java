package com.booking.bean.pojo.attraction;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "package_tour")
public class PackageTour {

	@Id @Column(name = "package_tour_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer packageTourId;
	
	@Column(name = "package_tour_name")
	private String packageTourName;
	
	@Column(name = "package_tour_price")
	private Integer packageTourPrice;
	
	@Column(name = "package_tour_description")	
	private String packageTourDescription;
	
	@Column(name = "package_tour_img")
	private String packageTourImg;
	
	@OneToMany(mappedBy = "packageTour", fetch = FetchType.LAZY)
	private List<PackageTourAttraction> packageTourAttractions = new ArrayList<>();

	public PackageTour() {
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



	public Integer getPackageTourPrice() {
		return packageTourPrice;
	}



	public void setPackageTourPrice(Integer packageTourPrice) {
		this.packageTourPrice = packageTourPrice;
	}



	public String getPackageTourDescription() {
		return packageTourDescription;
	}



	public void setPackageTourDescription(String packageTourDescription) {
		this.packageTourDescription = packageTourDescription;
	}



	public String getPackageTourImg() {
		return packageTourImg;
	}



	public void setPackageTourImg(String packageTourImg) {
		this.packageTourImg = packageTourImg;
	}



	public List<PackageTourAttraction> getPackageTourAttractions() {
		return packageTourAttractions;
	}



	public void setPackageTourAttractions(List<PackageTourAttraction> packageTourAttractions) {
		this.packageTourAttractions = packageTourAttractions;
	}



	@Override
	public String toString() {
		return "PackageTour [packageTourId=" + packageTourId + ", packageTourName=" + packageTourName
				+ ", packageTourPrice=" + packageTourPrice + ", packageTourDescription=" + packageTourDescription
				+ ", packageTourImg=" + packageTourImg + ", packageTourAttractions=" + packageTourAttractions + "]";
	}


	
}
