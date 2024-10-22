package com.booking.controller.user;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.booking.service.user.UserService;
import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.booking.bean.pojo.user.User;
import com.booking.service.user.UserOrderService;

@Controller
@RequestMapping("/user")
public class UserOrderViewController {
    
    @Autowired
    private UserOrderService userOrderService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/orders")
    public String showUserOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }
        
        // 獲取當前登入用戶
        String username = principal.getName();
        User user = userService.findByUserAccount(username);
        
        // 獲取各類訂單
        List<BookingOrder> bookingOrders = userOrderService.getUserBookingOrders(user.getUserId());
        List<ShopOrder> shopOrders = userOrderService.getUserShopOrders(user.getUserId());
        List<PackageTourOrder> packageOrders = userOrderService.getUserPackageTourOrders(user.getUserId());
        
        // 添加到模型
        model.addAttribute("bookingOrders", bookingOrders);
        model.addAttribute("shopOrders", shopOrders);
        model.addAttribute("packageOrders", packageOrders);
        
        return "user-orders";
    }
}