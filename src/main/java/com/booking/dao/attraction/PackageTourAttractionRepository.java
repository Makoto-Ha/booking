package com.booking.dao.attraction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bean.pojo.attraction.PackageTourAttraction;
import com.booking.bean.pojo.attraction.PackageTourAttractionId;

public interface PackageTourAttractionRepository extends JpaRepository<PackageTourAttraction, PackageTourAttractionId>, PackageTourAttractionDao{

}
