package com.booking.bean.dto.attraction;

import java.util.List;

import com.booking.bean.pojo.attraction.Attraction;

public class PackageTourDTO {


	private Integer packageTourId;
	private String packageTourName;
	private Integer packageTourPrice;
	private String packageTourDescription;
	private List<Attraction> attractions;
	private Integer pageNumber = 1;
	private String attrOrderBy = "attractionId";
	private Boolean selectedSort = true;


	public PackageTourDTO() {
		
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



	public List<Attraction> getAttractions() {
		return attractions;
	}

	public void setAttractions(List<Attraction> attractions) {
		this.attractions = attractions;
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
		return "PackageTourDTO [packageTourId=" + packageTourId + ", packageTourName=" + packageTourName
				+ ", packageTourPrice=" + packageTourPrice + ", packageTourDescription=" + packageTourDescription
				+ ", attractions=" + attractions + ", pageNumber=" + pageNumber + ", attrOrderBy=" + attrOrderBy
				+ ", selectedSort=" + selectedSort + "]";
	}



	
}
