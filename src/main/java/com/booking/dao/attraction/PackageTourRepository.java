package com.booking.dao.attraction;


import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.bean.pojo.attraction.PackageTour;

public interface PackageTourRepository extends JpaRepository<PackageTour, Integer>, JpaSpecificationExecutor<PackageTour>, PackageTourDao {

    @Query("SELECT p FROM PackageTour p LEFT JOIN FETCH p.packageTourAttractions WHERE p.packageTourId = :id")
    PackageTour findByIdWithAttractions(@Param("id") Integer id);
    
    @Query(value = "SELECT p FROM PackageTour p",
           countQuery = "SELECT COUNT(p) FROM PackageTour p")
    Page<PackageTour> findAllPaged(Pageable pageable);
    
    default Page<PackageTour> findAllWithAttractions(Pageable pageable) {
        Page<PackageTour> page = findAllPaged(pageable);
        List<PackageTour> results = new ArrayList<>();
        
        for (PackageTour packageTour : page.getContent()) {
            PackageTour fullPackageTour = findByIdWithAttractions(packageTour.getPackageTourId());
            if (fullPackageTour != null) {
                results.add(fullPackageTour);
            }
        }
        
        return new PageImpl<>(results, pageable, page.getTotalElements());
    }
    
    List<PackageTour> findByPackageTourNameContaining(String keyword);

}
