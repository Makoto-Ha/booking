package com.booking.bean.dto.attraction;


public class AttractionDTO{
	private Integer attractionId;
	private String attractionName;
	private String attractionCity;
	private String address;
	private String openingHour;
	private String attractionType;
	private String attractionDescription;
	
	private Integer pageNumber = 1;
	private String attrOrderBy = "attractionId";
	private Boolean selectedSort = true;

	public AttractionDTO() {
		
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
		return "AttractionDTO [attractionId=" + attractionId + ", attractionName=" + attractionName
				+ ", attractionCity=" + attractionCity + ", address=" + address + ", openingHour=" + openingHour
				+ ", attractionType=" + attractionType + ", attractionDescription=" + attractionDescription
				+ ", pageNumber=" + pageNumber + ", attrOrderBy=" + attrOrderBy + ", selectedSort=" + selectedSort
				+ "]";
	}






}
