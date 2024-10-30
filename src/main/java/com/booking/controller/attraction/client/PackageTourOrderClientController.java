package com.booking.controller.attraction.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.dto.attraction.PackageTourOrderDTO;
import com.booking.service.attraction.client.PackageTourOrderClientService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/packageTourOrder")
public class PackageTourOrderClientController {

    @Autowired
    private PackageTourOrderClientService packageTourOrderClientService;
    

    /**
     * 創建訂單並跳轉到綠界支付
     */
    @PostMapping("/create")
    public String createOrder(@ModelAttribute PackageTourOrderDTO orderDTO, 
                            Authentication authentication, 
                            Model model,
                            HttpSession session) {  
        String account = authentication.getName();
        Result<PackageTourOrderDTO> result = packageTourOrderClientService.createOrder(orderDTO, account);

        if (result.isSuccess()) {
            session.setAttribute("orderId", result.getData().getOrderId());
            
            // 進行綠界支付
            String form = packageTourOrderClientService.ecpayCheckout(result.getData());
            model.addAttribute("ecpayForm", form);

            return "/client/attraction/ecpay-checkout";
        }

        return "redirect:/packageTour";
    }


    /**
     * 訂單新增後的成功頁面
     */
    @GetMapping("/success")
    public String sendOrderSuccess(@SessionAttribute Integer orderId, Model model) {
        Result<PackageTourOrderDTO> result = packageTourOrderClientService.getOrderById(orderId);

        if(result.isFailure()) {
            return "redirect:/packageTour";
        }

        model.addAttribute("order", result.getData());
        
        return "client/attraction/order-success";
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
