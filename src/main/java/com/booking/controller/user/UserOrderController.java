package com.booking.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.booking.bean.pojo.user.User;
import com.booking.service.user.UserOrderService;
import com.booking.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserOrderController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserOrderService userOrderService;

    @GetMapping("/orders")
    public String showUserOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null) {
            return "redirect:/auth/login";
        }

        try {
            // 獲取用戶信息
            User user;
            if (auth.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) auth.getPrincipal();
                String email = oauth2User.getAttribute("email");
                user = userService.findByUserMail(email);
            } else {
                String username = auth.getName();
                user = userService.findByUserAccount(username);
            }
            
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // 獲取訂單數據並處理狀態名稱
            List<Map> packageTourOrders = userOrderService.getPackageTourOrdersBasic(user.getUserId());
            List<Map> shopOrders = userOrderService.getShopOrdersBasic(user.getUserId());
            List<Map> bookingOrders = userOrderService.getBookingOrdersBasic(user.getUserId());

            // 處理每個訂單的狀態名稱
            packageTourOrders.forEach(order -> {
                Integer status = (Integer) order.get("status");
                order.put("statusName", userOrderService.getPackageTourOrderStatusName(status));
            });

            shopOrders.forEach(order -> {
                Integer orderState = (Integer) order.get("orderStatus");
                Integer paymentState = (Integer) order.get("paymentStatus");
                order.put("orderStatusName", userOrderService.getShopOrderStatusName(orderState));
                order.put("paymentStatusName", userOrderService.getShopPaymentStatusName(paymentState));
            });

            bookingOrders.forEach(order -> {
                Integer status = (Integer) order.get("status");
                order.put("statusName", userOrderService.getBookingOrderStatusName(status));
            });

            model.addAttribute("packageTourOrders", packageTourOrders);
            model.addAttribute("shopOrders", shopOrders);
            model.addAttribute("bookingOrders", bookingOrders);

            return "users/orders";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/auth/login?error=用戶資訊獲取失敗";
        }
    }
}