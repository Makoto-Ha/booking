package com.booking.bean.pojo.attraction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "package_tour")
public class PackageTour {

	@Id @Column(name = "pacakgetour_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer packageTourId;
	
	@Column(name = "packagetour_name")
	private String packageTourName;
	
	@Column(name = "packagetour_price")
	private Integer packageTourPrice;
	
	@Column(name = "packagetour_description")
	private String packageTourDescription;
	
	

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



	@Override
	public String toString() {
		return "PackageTour [packageTourId=" + packageTourId + ", packageTourName=" + packageTourName
				+ ", packageTourPrice=" + packageTourPrice + ", packageTourDescription=" + packageTourDescription + "]";
	}



	
}
