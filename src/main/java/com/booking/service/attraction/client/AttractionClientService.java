package com.booking.service.attraction.client;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.bean.pojo.attraction.Attraction;
import com.booking.dao.attraction.AttractionRepository;

@Service
public class AttractionClientService {

    @Autowired
    private AttractionRepository attractionRepo;

    public List<AttractionDTO> getAttractions(String name, String type, String city, int offset, int limit) {
        List<Attraction> attractions = attractionRepo.findAttractions(name, type, city, offset, limit);

        return attractions.stream()
            .map(attraction -> {
                AttractionDTO attractionDTO = new AttractionDTO();
                BeanUtils.copyProperties(attraction, attractionDTO);
                return attractionDTO;
            })
            .collect(Collectors.toList());
    }


}
