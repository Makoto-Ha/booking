package com.booking.bean.pojo.common;

import java.util.HashSet;
import java.util.Set;

import com.booking.bean.pojo.booking.Roomtype;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "amenity")
public class Amenity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private Integer amenityId;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "amenities")
    @JsonBackReference
    private Set<Roomtype> roomtypes = new HashSet<>();

    public Amenity() {}

	public Integer getAmenityId() {
		return amenityId;
	}

	public void setAmenityId(Integer amenityId) {
		this.amenityId = amenityId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Roomtype> getRoomtypes() {
		return roomtypes;
	}

	public void setRoomtypes(Set<Roomtype> roomtypes) {
		this.roomtypes = roomtypes;
	}

	@Override
	public String toString() {
		return "Amenity [amenityId=" + amenityId + ", name=" + name + "]";
	}
	
}

