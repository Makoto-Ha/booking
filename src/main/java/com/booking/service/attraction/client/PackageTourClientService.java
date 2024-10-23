package com.booking.service.attraction.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.attraction.PackageTourDTO;
import com.booking.bean.pojo.attraction.PackageTour;
import com.booking.bean.pojo.attraction.PackageTourAttraction;
import com.booking.dao.attraction.PackageTourRepository;

@Service
public class PackageTourClientService {
	
    @Autowired
    private PackageTourRepository packageTourRepo;

    
    /**
     * 根據關鍵字搜尋套裝行程
     */
    public List<PackageTourDTO> getPackageTours(String keyword) {
        List<PackageTour> packageTours = packageTourRepo.findByPackageTourNameContaining(keyword);
        List<PackageTourDTO> packageTourDTOs = new ArrayList<>();
        
        for (PackageTour packageTour : packageTours) {
            PackageTourDTO packageTourDTO = new PackageTourDTO();
            BeanUtils.copyProperties(packageTour, packageTourDTO);

            List<String> attractionNames = new ArrayList<>();
            for (PackageTourAttraction attraction : packageTour.getPackageTourAttractions()) {
                attractionNames.add(attraction.getAttraction().getAttractionName());
            }
            packageTourDTO.setAttractionNames(attractionNames);

            packageTourDTOs.add(packageTourDTO);
        }
        
        return packageTourDTOs;
    }

    /**
     * 根據套裝行程id獲取詳細資訊
     */
    public PackageTourDTO getPackageTourDetails(Integer id) {
        PackageTour packageTour = packageTourRepo.findById(id).orElse(null);
        if (packageTour == null) {
            return null;
        }

        PackageTourDTO packageTourDTO = new PackageTourDTO();
        packageTourDTO.setPackageTourId(packageTour.getPackageTourId());
        packageTourDTO.setPackageTourName(packageTour.getPackageTourName());
        packageTourDTO.setPackageTourPrice(packageTour.getPackageTourPrice());
        packageTourDTO.setPackageTourDescription(packageTour.getPackageTourDescription());
        packageTourDTO.setPackageTourImg(packageTour.getPackageTourImg());

        List<String> attractionNames = new ArrayList<>();
        for (PackageTourAttraction attraction : packageTour.getPackageTourAttractions()) {
            attractionNames.add(attraction.getAttraction().getAttractionName());
        }
        packageTourDTO.setAttractionNames(attractionNames);

        return packageTourDTO;
    }
}

