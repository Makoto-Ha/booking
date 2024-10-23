package com.booking.controller.user;

import com.booking.bean.pojo.user.User;
import com.booking.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/user")
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String showProfile(Model model, Authentication authentication) {
        if (authentication == null) {
            return "redirect:/auth/login";
        }

        User user;
        // 處理 OAuth2 登入的情況
        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String email = oauth2User.getAttribute("email");
            user = userService.findByUserMail(email);
        } else {
            // 一般登入的情況
            String userAccount = authentication.getName();
            user = userService.findByUserAccount(userAccount);
        }

        if (user.getUserBirthday() == null) {
            user.setUserBirthday(LocalDate.now());
        }

        model.addAttribute("user", user);
        return "users/profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(
            @ModelAttribute User user,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate userBirthday,
            RedirectAttributes redirectAttributes) {
        try {
            if (userBirthday != null) {
                user.setUserBirthday(userBirthday);
            }
            userService.updateUserProfile(user);
            redirectAttributes.addFlashAttribute("message", "個人資料更新成功！");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "更新失敗：" + e.getMessage());
        }
        return "redirect:/user/profile";
    }
}