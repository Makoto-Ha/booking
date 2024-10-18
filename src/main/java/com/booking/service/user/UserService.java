package com.booking.service.user;

import com.booking.bean.pojo.user.User;
import com.booking.dao.user.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public User registerUser(User user) {
        if (userRepository.existsByUserAccount(user.getUserAccount())) {
            throw new RuntimeException("Error: Username is already taken!");
        }
        if (userRepository.existsByUserMail(user.getUserMail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }
        
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setEmailVerified(false);
        user.setVerificationToken(UUID.randomUUID().toString());
        User savedUser = userRepository.save(user);
        emailService.sendVerificationEmail(savedUser);
        return savedUser;
    }

    public User findByUserAccount(String userAccount) {
        return userRepository.findByUserAccount(userAccount)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByUserMail(String userMail) {
        return userRepository.findByUserMail(userMail)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public void verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid verification token"));
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);
    }

    @Transactional
    public void initiatePasswordReset(String email) {
        User user = findByUserMail(email);
        user.setResetToken(UUID.randomUUID().toString());
        userRepository.save(user);
        emailService.sendPasswordResetEmail(user);
    }

    @Transactional
    public boolean resetPassword(String token, String newPassword) {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));
        if (user.isResetTokenValid()) {
            user.setUserPassword(passwordEncoder.encode(newPassword));
            user.clearResetToken();
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    public User processOAuthPostLogin(String email, String provider, String providerId) {
        User existUser = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElse(null);
        if (existUser == null) {
            User newUser = new User();
            newUser.setUserMail(email);
            newUser.setProvider(provider);
            newUser.setProviderId(providerId);
            newUser.setEmailVerified(true);
            
            // 設置 userAccount
            String userAccount = email.split("@")[0]; // 使用郵箱的用戶名部分作為 userAccount
            newUser.setUserAccount(userAccount);
            
            // 設置一個隨機的密碼（因為OAuth2用戶不需要密碼登錄）
            newUser.setUserPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            
            // 設置其他必要的字段
            newUser.setUserName(email.split("@")[0]); // 使用郵箱的用戶名部分作為 userName
            newUser.setCreatedTime(LocalDateTime.now());
            
            return userRepository.save(newUser);
        }
        return existUser;
    }
    
    @Transactional
    public User updateUser(User updatedUser) {
        User existingUser = userRepository.findById(updatedUser.getUserId())
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Update fields
        existingUser.setUserName(updatedUser.getUserName());
        existingUser.setUserMail(updatedUser.getUserMail());
        existingUser.setUserPhone(updatedUser.getUserPhone());
        existingUser.setUserBirthday(updatedUser.getUserBirthday());
        existingUser.setUserAddress(updatedUser.getUserAddress());

        // Save the updated user
        return userRepository.save(existingUser);
    }
}