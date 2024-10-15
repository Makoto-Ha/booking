package com.booking.service.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.pojo.common.Amenity;
import com.booking.dao.common.AmenityRepository;

@Service
public class AmenityService {
	
	@Autowired
	private AmenityRepository amenityRepo;
	
	public List<Amenity> findAll() {
		return amenityRepo.findAll();	
	}
}
