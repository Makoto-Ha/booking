package com.booking.controller.user;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.booking.bean.pojo.user.User;
import com.booking.service.User.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.registerNewUser(user);
        return "redirect:/login?registered";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam String token) {
        userService.verifyEmail(token);
        return "redirect:/login?verified";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email) {
        userService.initiatePasswordReset(email);
        return "redirect:/login?reset";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token, @RequestParam String password) {
        userService.resetPassword(token, password);
        return "redirect:/login?reset-success";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}