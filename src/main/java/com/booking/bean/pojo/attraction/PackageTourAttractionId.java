package com.booking.bean.pojo.attraction;

import java.io.Serializable;
import java.util.Objects;

public class PackageTourAttractionId implements Serializable{
		private static final long serialVersionUID = 1L;
		
	private Integer attractionId;
	
	private Integer packageTourId;

	public PackageTourAttractionId() {
		
	}

	
	public PackageTourAttractionId(Integer attractionId, Integer packageTourId) {
		this.attractionId = attractionId;
		this.packageTourId = packageTourId;
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(attractionId, packageTourId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PackageTourAttractionId other = (PackageTourAttractionId) obj;
		return Objects.equals(attractionId, other.attractionId) && Objects.equals(packageTourId, other.packageTourId);
	}


	public Integer getAttractionId() {
		return attractionId;
	}

	public void setAttractionId(Integer attractionId) {
		this.attractionId = attractionId;
	}

	public Integer getPackageTourId() {
		return packageTourId;
	}

	public void setPackageTourId(Integer packageTourId) {
		this.packageTourId = packageTourId;
	}
	
	
}
