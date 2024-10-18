package com.booking.bean.pojo.attraction;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "package_tour_attraction")
public class PackageTourAttraction{
	
	@EmbeddedId
	private PackageTourAttractionId id;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("attractionId")
	@JoinColumn(name = "attraction_id")
	private Attraction attraction;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("packageTourId")
	@JoinColumn(name = "package_tour_id")
	private PackageTour packageTour;

	public PackageTourAttraction() {
		
	}

	public PackageTourAttraction(PackageTourAttractionId id, Attraction attraction, PackageTour packageTour) {
		super();
		this.id = id;
		this.attraction = attraction;
		this.packageTour = packageTour;
	}

	public PackageTourAttractionId getId() {
		return id;
	}

	public void setId(PackageTourAttractionId id) {
		this.id = id;
	}

	public Attraction getAttraction() {
		return attraction;
	}

	public void setAttraction(Attraction attraction) {
		this.attraction = attraction;
	}

	public PackageTour getPackageTour() {
		return packageTour;
	}

	public void setPackageTour(PackageTour packageTour) {
		this.packageTour = packageTour;
	}	
	
}
