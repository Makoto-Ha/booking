package com.booking.service.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.booking.bean.pojo.user.User;
import com.booking.dao.User.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserAccount(username)
                .orElseThrow(() -> new UsernameNotFoundException("用戶不存在"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserAccount())
                .password(user.getUserPassword())
                .authorities("USER")
                .accountExpired(!user.getEmailVerified())
                .build();
    }
}
