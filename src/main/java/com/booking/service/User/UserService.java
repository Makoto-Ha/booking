package com.booking.service.User;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.booking.bean.pojo.user.User;
import com.booking.dao.User.UserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    public User registerNewUser(User user) {
        if (userRepository.findByUserAccount(user.getUserAccount()).isPresent()) {
            throw new RuntimeException("用戶帳號已存在");
        }
        if (userRepository.findByUserMail(user.getUserMail()).isPresent()) {
            throw new RuntimeException("電子郵件已被使用");
        }

        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setCreatedTime(LocalDateTime.now());
        user.setEmailVerified(false);
        user.setVerificationToken(generateToken());

        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(user.getUserMail(), user.getVerificationToken());

        return savedUser;
    }

    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("無效的驗證令牌"));

        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    public void initiatePasswordReset(String email) {
        User user = userRepository.findByUserMail(email)
                .orElseThrow(() -> new RuntimeException("找不到使用此電子郵件的用戶"));

        user.setResetToken(generateToken());
        user.setResetTokenExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

        emailService.sendPasswordResetEmail(user.getUserMail(), user.getResetToken());
    }

    public void resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("無效的重置令牌"));

        if (user.getResetTokenExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("重置令牌已過期");
        }

        user.setUserPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}