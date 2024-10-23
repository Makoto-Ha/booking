package com.booking.controller.attraction.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.bean.dto.attraction.PackageTourDTO;
import com.booking.service.attraction.client.PackageTourClientService;

@Controller
@RequestMapping("/packageTour")
public class PackageTourClientController {
	
    @Autowired
    private PackageTourClientService packageTourService;

    /**
     * 顯示套裝行程列表頁面
     */
    @GetMapping
    public String showPackageTourPage(Model model) {
        List<PackageTourDTO> packageTours = packageTourService.getPackageTours("");
        model.addAttribute("packageTours", packageTours);
        return "client/attraction/packageTour";
    }

    /**
     * 顯示套裝行程詳細資訊頁面
     */
    @GetMapping("/detail/{id}")
    public String showPackageTourDetailPage(@PathVariable Integer id, Model model) {
        PackageTourDTO packageTour = packageTourService.getPackageTourDetails(id);
        model.addAttribute("packageTour", packageTour);
        return "client/attraction/packageTourDetail";
    }

    /**
     * 處理搜尋請求的API
     */
    @GetMapping("/api/packageTours")
    @ResponseBody
    public List<PackageTourDTO> getPackageTours(@RequestParam(required = false) String keyword) {
        return packageTourService.getPackageTours(keyword);
    }
}
