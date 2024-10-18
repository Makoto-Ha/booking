package com.booking.controller.user;

import com.booking.bean.pojo.user.User;
import com.booking.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        try {
            userService.registerUser(user);
            model.addAttribute("message", "User registered successfully! Please check your email to verify your account.");
            return "register-success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String authenticateUser(@RequestParam String userAccount, @RequestParam String userPassword, Model model) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userAccount, userPassword)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/"; // 登入成功後重定向到首頁
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/auth/login";
        }
        String username = principal.getName();
        User user = userService.findByUserAccount(username);
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logoutUser() {
        SecurityContextHolder.clearContext();
        return "redirect:/?logout"; // 登出後重定向到首頁，並帶上logout參數
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String forgotPassword(@RequestParam String email, Model model) {
        try {
            userService.initiatePasswordReset(email);
            model.addAttribute("message", "If an account exists with the provided email, a password reset link has been sent.");
            return "forgot-password-success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "forgot-password";
        }
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "密码不匹配");
            return "reset-password";
        }
        boolean result = userService.resetPassword(token, newPassword);
        if (result) {
            model.addAttribute("message", "密码已成功重置。");
            return "reset-password-success";
        } else {
            model.addAttribute("error", "无效或已过期的令牌。");
            return "reset-password";
        }
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token, Model model) {
        try {
            userService.verifyEmail(token);
            model.addAttribute("message", "Email verified successfully!");
            return "verify-success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "verify-error";
        }
    }
    
    
    @GetMapping("/oauth2/callback/google")
    public String handleGoogleCallback() {
        return "redirect:/auth/oauth2-success";
    }
    
    @GetMapping("/oauth2-success")
    public String handleOAuth2Success(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
            String email = oauth2User.getAttribute("email");
            String name = oauth2User.getAttribute("name");
            
            // 處理 OAuth2 用戶登錄
            User user = userService.processOAuthPostLogin(email, "GOOGLE", oauth2User.getName());
            
            model.addAttribute("user", user);
            return "oauth2-success";  // 創建一個新的視圖來顯示 OAuth2 登錄成功信息
        }
        return "redirect:/";  // 如果不是 OAuth2 用戶，重定向到首頁
    }

    @GetMapping("/profile")
    public String showUserProfile(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserAccount(username);
        model.addAttribute("user", user);
        return "user-profile";
    }

    @GetMapping("/edit-profile")
    public String showEditProfileForm(Model model, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserAccount(username);
        model.addAttribute("user", user);
        return "edit-profile";
    }

    @PostMapping("/edit-profile")
    public String updateProfile(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "個人資料更新成功！");
            return "redirect:/auth/profile";
        } catch (Exception e) {
            model.addAttribute("error", "更新失敗：" + e.getMessage());
            return "edit-profile";
        }
    }
}
