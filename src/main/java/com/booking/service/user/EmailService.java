package com.booking.service.user;

import com.booking.bean.pojo.user.User;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine; // 注入模板引擎
    

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
    
    public void sendOrderConfirmationEmail(String toEmail, String subject, Map<String, Object> variables) throws MessagingException {
        Context context = new Context();
        context.setVariables(variables);

        String htmlContent = templateEngine.process("client/shopping/shop-email-confirm", context);

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("wei33332222@gmail.com");
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        
        ClassPathResource logoImage = new ClassPathResource("/static/client/common/image/instastay-logo.png");
        helper.addInline("instastayLogo", logoImage);  // Content-ID 為 "instastayLogo"

        mailSender.send(mimeMessage);
    }
}