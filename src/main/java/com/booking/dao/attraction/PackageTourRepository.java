package com.booking.dao.attraction;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.booking.bean.pojo.attraction.PackageTour;

public interface PackageTourRepository extends JpaRepository<PackageTour, Integer>, JpaSpecificationExecutor<PackageTour>, PackageTourDao {



}
