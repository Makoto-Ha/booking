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

	@Id @Column(name = "tour_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tourId;
	
	@Column(name = "tour_name")
	private String tourName;
	
	@Column(name = "tour_price")
	private Integer tourPrice;
	
	@Column(name = "tour_description")
	private String tourDescription;
	
	

	public PackageTour() {
	}

	public Integer gettourId() {
		return tourId;
	}

	public void settourId(Integer tourId) {
		this.tourId = tourId;
	}

	public String gettourName() {
		return tourName;
	}

	public void settourName(String tourName) {
		this.tourName = tourName;
	}

	public Integer gettourPrice() {
		return tourPrice;
	}

	public void settourPrice(Integer tourPrice) {
		this.tourPrice = tourPrice;
	}

	public String gettourDescription() {
		return tourDescription;
	}

	public void settourDescription(String tourDescription) {
		this.tourDescription = tourDescription;
	}

	@Override
	public String toString() {
		return "PackageTour [tourId=" + tourId + ", tourName=" + tourName + ", tourPrice=" + tourPrice
				+ ", tourDescription=" + tourDescription + "]";
	}


	
}
