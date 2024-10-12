package com.booking.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("請驗證您的電子郵件");
        message.setText("請點擊以下鏈接驗證您的電子郵件：http://yourapp.com/verify?token=" + token);
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(String to, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("密碼重置請求");
        message.setText("請點擊以下鏈接重置您的密碼：http://yourapp.com/reset-password?token=" + token);
        mailSender.send(message);
    }
}