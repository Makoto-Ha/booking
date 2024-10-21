package com.booking.controller.user;

import com.booking.bean.pojo.user.User;
import com.booking.service.user.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        if (principal != null) {
            String username = principal.getName();
            User user = userService.findByUserAccount(username);
            model.addAttribute("user", user);
        }
        return "dashboard"; // 确保这里返回的是 "dashboard"
    }
}