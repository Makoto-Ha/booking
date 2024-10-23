package com.booking.controller.attraction;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.booking.bean.dto.attraction.PackageTourDTO;
import com.booking.bean.dto.attraction.PackageTourOrderDTO;
import com.booking.service.attraction.PackageTourOrderService;
import com.booking.service.attraction.PackageTourService;
import com.booking.utils.Result;


@Controller
@RequestMapping("/management/packageTourOrder")
public class PackageTourOrderController {
	
	
	@Autowired
	private PackageTourService packageTourService;
	
	
	@Autowired
    private PackageTourOrderService packageTourOrderService;
	
    /**
     * 轉到訂單首頁
     */
    @GetMapping
    public String packageTourOrder(Model model) {
        PackageTourOrderDTO packageTourOrderDTO = new PackageTourOrderDTO();
        Result<PageImpl<PackageTourOrderDTO>> findPackageTourOrderAllResult = packageTourOrderService.findAllPackageTourOrder(packageTourOrderDTO);
        
        if(findPackageTourOrderAllResult.isFailure()) {
            return ""; 
        }
        
        Page<PackageTourOrderDTO> page = findPackageTourOrderAllResult.getData();
        
        model.addAttribute("packageTourOrderDTO", packageTourOrderDTO);
        model.addAttribute("page", page);

        return "management-system/attraction/packagetourorder-list";
    }

    
    /**
     * 轉到查詢頁面
     */
    @GetMapping("/select/page")
    public String sendSelectPage() {    
        return "/management-system/attraction/packagetourorder-select";
    }

    
    /**
     * 轉到創建頁面
     */
    @GetMapping("/create/page")
    public String sendCreatePage(Model model) {
        PackageTourOrderDTO packageTourOrderDTO = new PackageTourOrderDTO();
        
        PackageTourDTO packageTourDTO = new PackageTourDTO();
        Result<PageImpl<PackageTourDTO>> result = packageTourService.findAllPackageTour(packageTourDTO);
        
        if (result.isSuccess()) {
            List<PackageTourDTO> packageTours = result.getData().getContent();
            model.addAttribute("packageTours", packageTours);
        } else {
            model.addAttribute("errorMessage", "無法獲取套裝行程列表");
        }
        
        model.addAttribute("packageTourOrderDTO", packageTourOrderDTO);
        return "management-system/attraction/packagetourorder-create";
    }

    
    /**
     * 轉到編輯頁面
     */
    @GetMapping("/edit/page")
    public String sendEditPage(@RequestParam Integer orderId, Model model) {
        Result<PackageTourOrderDTO> packageTourOrderResult = packageTourOrderService.findPackageTourOrderById(orderId);
        
        if (packageTourOrderResult.isFailure()) {
            return "redirect:/management/packageTourOrder";
        }

        PackageTourOrderDTO packageTourOrder = packageTourOrderResult.getData();
        model.addAttribute("packageTourOrder", packageTourOrder);

        return "management-system/attraction/packagetourorder-edit";
    }

    
    /**
     * 處理查詢請求
     */
    @GetMapping("/select")
    public String select(@RequestParam Map<String, String> requestParameters, 
                         @ModelAttribute PackageTourOrderDTO packageTourOrderDTO,
                         @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate searchDate,
                         Model model) {
        
        Result<PageImpl<PackageTourOrderDTO>> packageTourOrderServiceResult = 
            packageTourOrderService.findPackageTourOrders(packageTourOrderDTO, searchDate);
        
        if(packageTourOrderServiceResult.isFailure()) {
            return "error-page"; 
        }
        
        Page<PackageTourOrderDTO> page = packageTourOrderServiceResult.getData();
        
        model.addAttribute("requestParameters", requestParameters);
        model.addAttribute("packageTourOrderDTO", packageTourOrderDTO);
        model.addAttribute("searchDate", searchDate);
        model.addAttribute("page", page);

        return "/management-system/attraction/packagetourorder-list";    
    }

    
    /**
     * 處理創建訂單請求
     */
    @PostMapping("/create")
    public String savePackageTourOrder(@ModelAttribute PackageTourOrderDTO packageTourOrderDTO, Model model) {
        if (packageTourOrderDTO.getUserId() == null) {
            model.addAttribute("errorMessage", "使用者 ID 不能為空");
            return "redirect:/management/packageTourOrder/create/page";
        }
        
        Result<PackageTourOrderDTO> savePackageTourOrderResult = packageTourOrderService.savePackageTourOrder(packageTourOrderDTO);
        if (savePackageTourOrderResult.isSuccess()) {
            return "redirect:/management/packageTourOrder";
        } else {
            model.addAttribute("errorMessage", savePackageTourOrderResult.getMessage());
            return "redirect:/management/packageTourOrder/create/page";
        }
    }
    

    /**
     * 刪除訂單
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deletePackageTourOrder(@RequestParam Integer orderId) {
        Result<?> deletePackageTourOrderResult = packageTourOrderService.deletePackageTourOrderById(orderId);
        String message = deletePackageTourOrderResult.getMessage();
        if (deletePackageTourOrderResult.isFailure()) {
            return ResponseEntity.badRequest().body(message);
        }
        return ResponseEntity.ok(message);
    }

    
    /**
     * 更新訂單
     */
    @PostMapping("/update")
    public String updatePackageTourOrder(@ModelAttribute PackageTourOrderDTO packageTourOrderDTO) {
        if (packageTourOrderDTO.getOrderId() == null) { 
            return "redirect:/management/packageTourOrder";
        }
        Result<String> result = packageTourOrderService.updatePackageTourOrder(packageTourOrderDTO);
        if (result.isSuccess()) {
            return "redirect:/management/packageTourOrder";
        } else {
            return "management-system/attraction/packagetourorder-edit";
        }
    }
}
