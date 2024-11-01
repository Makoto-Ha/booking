package com.booking.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // 後台安全配置
    @Bean
    @Order(1) // 優先處理後台請求
    public SecurityFilterChain managementFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(
                "/management/**",
                "/api/**",
                "/uploads/**",
                "/css/**", 
                "/js/**", 
                "/images/**",
                "/frontend/**",
                "/client/**",
                "/management-system/**",
                "/static/**",
                "/META-INF/**",
                "/resources/**",
                "/webjars/**",
                "/templates/**"
            )
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/management/**",
                    "/api/**", // API 端點
                    "/uploads/**",
                    "/css/**", 
                    "/js/**", 
                    "/images/**",
                    "/frontend/**",
                    "/client/**",
                    "/management-system/**",
                    "/static/**",
                    "/META-INF/**",
                    "/resources/**",
                    "/webjars/**",
                    "/templates/**"
                ).permitAll()
                .anyRequest().permitAll()
            )
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            )
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
                .cacheControl(cache -> cache.disable())
            )
            .cors(cors -> cors.disable());
        
        return http.build();
    }

    // 前台安全配置
    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher(new AntPathRequestMatcher("/**"))
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/", 
                    "/auth/**", 
                    "/css/**", 
                    "/js/**", 
                    "/images/**",
                    "/frontend/**",
                    "/client/**",
                    "/uploads/**",
                    "/management-system/**",
                    "/static/**",
                    "/META-INF/**",
                    "/resources/**",
                    "/webjars/**",
                    "/api/**",
                    "/templates/**",
                    "/shop/api/checkout/success/**",
                    "/shop/checkout/linepay/confirm/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .usernameParameter("userAccount")
                .passwordParameter("userPassword")
                .defaultSuccessUrl("/", true)
                .failureUrl("/auth/login?error")
            )
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .oauth2Login(oauth2 -> oauth2
                .loginPage("/auth/login")
                .defaultSuccessUrl("/", true)
                .authorizationEndpoint(authorization -> authorization
                    .baseUri("/oauth2/authorization"))
                .redirectionEndpoint(redirection -> redirection
                    .baseUri("/oauth2/callback/*"))
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/auth/login?invalid")
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
                .expiredUrl("/auth/login?expired")
            )
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
                .cacheControl(cache -> cache.disable())
            )
            .cors(cors -> cors.disable());
     
        return http.build();
    }
}