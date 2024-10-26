package com.booking.config;

import com.booking.bean.pojo.admin.Admin;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminLoginInterceptor implements HandlerInterceptor {
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler) throws Exception {
        // 獲取當前請求路徑
        String requestUri = request.getRequestURI();
        
        // 允許通過的路徑
        String[] allowedPaths = {
            "/management/admin/login",
            "/management/admin/register",
            "/management/admin/forgot-password",
            "/management/admin/reset-password"
        };
        
        // 檢查是否是允許通過的路徑
        for (String path : allowedPaths) {
            if (requestUri.contains(path)) {
                return true;
            }
        }
        
        // 檢查是否已登入
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        
        if (admin == null) {
            // 未登入則重定向到登入頁面
            response.sendRedirect("/booking/management/admin/login");
            return false;
        }
        
        return true;
    }
}