package com.booking.controller.user;

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

        User user;
        try {
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

            // 獲取訂單數據
            model.addAttribute("packageTourOrders", 
                userOrderService.getPackageTourOrdersBasic(user.getUserId()));
            model.addAttribute("shopOrders", 
                userOrderService.getShopOrdersBasic(user.getUserId()));
            model.addAttribute("bookingOrders", 
                userOrderService.getBookingOrdersBasic(user.getUserId()));

            return "users/orders";
            
        } catch (Exception e) {
            // 記錄錯誤
            e.printStackTrace();
            // 重定向到錯誤頁面或登入頁面
            return "redirect:/auth/login?error=用戶資訊獲取失敗";
        }
    }
}