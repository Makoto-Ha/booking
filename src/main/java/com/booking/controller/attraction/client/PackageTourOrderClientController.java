package com.booking.controller.attraction.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.bean.dto.attraction.PackageTourOrderDTO;
import com.booking.service.attraction.client.PackageTourOrderClientService;
import com.booking.utils.Result;

@Controller
@RequestMapping("/packageTourOrder")
public class PackageTourOrderClientController {

    @Autowired
    private PackageTourOrderClientService packageTourOrderClientService;
    

    /**
     * 創建訂單並跳轉到綠界支付
     */
    @PostMapping("/create")
    public String createOrder(@ModelAttribute PackageTourOrderDTO orderDTO, Authentication authentication, Model model) {
    	String account = authentication.getName();
        Result<PackageTourOrderDTO> result = packageTourOrderClientService.createOrder(orderDTO, account);
        
        if (result.isSuccess()) {
            // 進行綠界支付
            String form = packageTourOrderClientService.ecpayCheckout(result.getData());
            
            model.addAttribute("ecpayForm", form);
            
            return "/client/attraction/ecpay-checkout";
        }
        
        return "redirect:/packageTour";
    }


    /**
     * 處理綠界支付回調
     */
    @PostMapping("/api/checkout/success")
    @ResponseBody
    public String handleEcpayCallback(@RequestParam Map<String, String> params) {
        String customField2 = params.get("CustomField2"); // 訂單ID
        Integer orderId = Integer.parseInt(customField2);
        
        if (packageTourOrderClientService.handlePaymentSuccess(orderId)) {
            return "redirect:/packageTourOrder/attraction/order-success?orderId=" + orderId;
        }
        return "0|Error";
    }

    /**
     * 顯示付款成功頁面
     */
    @GetMapping("/attraction/order-success")
    public String showPaymentSuccess(@RequestParam Integer orderId, Model model) {
        Result<PackageTourOrderDTO> result = packageTourOrderClientService.getOrderById(orderId);
        if (result.isSuccess()) {
            model.addAttribute("order", result.getData());
            return "client/attraction/order-success";
        }
        return "redirect:/packageTour";
    }

    /**
     * 顯示訂單詳情
     */
    @GetMapping("/detail/{orderId}")
    public String showOrderDetail(@PathVariable Integer orderId, Model model) {
        Result<PackageTourOrderDTO> result = packageTourOrderClientService.getOrderById(orderId);
        if (result.isSuccess()) {
            model.addAttribute("order", result.getData());
            return "client/packageTourOrder/detail";
        }
        return "redirect:/packageTour";
    }
}
