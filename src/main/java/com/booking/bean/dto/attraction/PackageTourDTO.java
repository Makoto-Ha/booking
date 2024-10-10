package com.booking.bean.dto.attraction;

import java.util.List;

import com.booking.bean.pojo.attraction.Attraction;

public class PackageTourDTO {


	private Integer tourId;
	private String tourName;
	private Integer tourPrice;
	private String tourDescription;
	private List<Attraction> attractions;
	private Integer pageNumber = 1;
	private String attrOrderBy = "attractionId";
	private Boolean selectedSort = true;


	public PackageTourDTO() {
		
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
		return "PackageTourDTO [tourId=" + tourId + ", tourName=" + tourName + ", tourPrice=" + tourPrice
				+ ", tourDescription=" + tourDescription + ", attractions=" + attractions + ", pageNumber=" + pageNumber
				+ ", attrOrderBy=" + attrOrderBy + ", selectedSort=" + selectedSort + "]";
	}


	
}
