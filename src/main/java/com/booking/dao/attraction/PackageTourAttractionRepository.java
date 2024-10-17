package com.booking.dao.attraction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.booking.bean.pojo.attraction.PackageTour;
import com.booking.bean.pojo.attraction.PackageTourAttraction;
import com.booking.bean.pojo.attraction.PackageTourAttractionId;

public interface PackageTourAttractionRepository extends JpaRepository<PackageTourAttraction, PackageTourAttractionId>, PackageTourAttractionDao{

	List<PackageTourAttraction> findByPackageTour(PackageTour packageTour);
}
