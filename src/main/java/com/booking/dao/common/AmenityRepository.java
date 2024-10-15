package com.booking.dao.common;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bean.pojo.common.Amenity;

public interface AmenityRepository extends JpaRepository<Amenity, Integer> {

}