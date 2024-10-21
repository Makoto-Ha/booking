package com.booking.controller.attraction.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.service.attraction.client.AttractionClientService;

@Controller
@RequestMapping("/attraction")
public class AttractionClientController {

    @Autowired
    private AttractionClientService attractionClientService;

    @Value("${google.maps.api-key}")
    private String googleMapsApiKey;

    /**
     * 顯示景點頁面
     */
    @GetMapping
    public String showAttractionPage(Model model) {
        model.addAttribute("googleMapsApiKey", googleMapsApiKey);
        return "client/attraction/attraction"; 
    }

    /**
     * 獲取景點
     */
    @GetMapping("/api/attractions")
    @ResponseBody
    public List<AttractionDTO> getAttractions(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String city,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "5") int limit) {
        return attractionClientService.getAttractions(name, type, city, offset, limit);
    }


}
