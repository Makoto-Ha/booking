package com.booking.service.user;

import com.booking.bean.pojo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getUserMail());
        message.setSubject("驗證您的帳戶");
        message.setText("請點擊以下連結驗證您的帳戶: " +
                "http://localhost:8080/booking/verify?token=" + user.getVerificationToken());
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getUserMail());
        message.setSubject("重設密碼");
        message.setText("請點擊以下連結重設您的密碼: " +
                "http://localhost:8080/booking/auth/reset-password?token=" + user.getResetToken());
        mailSender.send(message);
    }
}