package com.booking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Autowired
    private AdminLoginInterceptor adminLoginInterceptor;
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginInterceptor)
                .addPathPatterns("/management/**")
                .excludePathPatterns(
                    "/management/admin/login",
                    "/management/admin/register",
                    "/management/admin/forgot-password",
                    "/management/admin/reset-password",
                    "/management/**/css/**",
                    "/management/**/js/**",
                    "/management/**/images/**"
                );
    }
}