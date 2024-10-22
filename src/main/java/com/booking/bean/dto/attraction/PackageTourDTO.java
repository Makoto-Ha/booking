package com.booking.bean.dto.attraction;

import java.util.ArrayList;
import java.util.List;

import com.booking.bean.pojo.attraction.Attraction;

public class PackageTourDTO {


	private Integer packageTourId;
	private String packageTourName;
	private Integer packageTourPrice;
	private List<String> attractionNames;
	private String packageTourDescription;
	
	private List<Attraction> attractions;
	private String packageTourImg;
	private Integer pageNumber = 1;
	private String attrOrderBy = "packageTourId";
	private Boolean selectedSort = true;
    private Integer minPrice;
    private Integer maxPrice;
    
	private List<AttractionDTO> attractionDTOs = new ArrayList<>();
	private List<AttractionDTO> selectedAttractions;
	private List<Integer> selectedAttractionIds;
	
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


	public String getPackageTourImg() {
		return packageTourImg;
	}



	public void setPackageTourImg(String packageTourImg) {
		this.packageTourImg = packageTourImg;
	}



	public List<Attraction> getAttractions() {
		return attractions;
	}

	public void setAttractions(List<Attraction> attractions) {
		this.attractions = attractions;
	}
	
	
	public List<String> getAttractionNames() {
		return attractionNames;
	}


	public void setAttractionNames(List<String> attractionNames) {
		this.attractionNames = attractionNames;
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



	public Integer getMinPrice() {
		return minPrice;
	}



	public void setMinPrice(Integer minPrice) {
		this.minPrice = minPrice;
	}



	public Integer getMaxPrice() {
		return maxPrice;
	}



	public void setMaxPrice(Integer maxPrice) {
		this.maxPrice = maxPrice;
	}


	public List<AttractionDTO> getAttractionDTOs() {
		return attractionDTOs;
	}



	public void setAttractionDTOs(List<AttractionDTO> attractionDTOs) {
		this.attractionDTOs = attractionDTOs;
	}



	public List<AttractionDTO> getSelectedAttractions() {
		return selectedAttractions;
	}



	public void setSelectedAttractions(List<AttractionDTO> selectedAttractions) {
		this.selectedAttractions = selectedAttractions;
	}


    public List<Integer> getSelectedAttractionIds() {
		return selectedAttractionIds;
	}



	public void setSelectedAttractionIds(List<Integer> selectedAttractionIds) {
		this.selectedAttractionIds = selectedAttractionIds;
	}



	@Override
	public String toString() {
		return "PackageTourDTO [packageTourId=" + packageTourId + ", packageTourName=" + packageTourName
				+ ", packageTourPrice=" + packageTourPrice + ", attractionNames=" + attractionNames
				+ ", packageTourDescription=" + packageTourDescription + ", attractions=" + attractions
				+ ", packageTourImg=" + packageTourImg + ", pageNumber=" + pageNumber + ", attrOrderBy=" + attrOrderBy
				+ ", selectedSort=" + selectedSort + ", minPrice=" + minPrice + ", maxPrice=" + maxPrice
				+ ", attractionDTOs=" + attractionDTOs + ", selectedAttractions=" + selectedAttractions
				+ ", selectedAttractionIds=" + selectedAttractionIds + "]";
	}


}
