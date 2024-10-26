package com.booking.dao.user;


import com.booking.bean.pojo.user.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> , JpaSpecificationExecutor<User>, UserDao{
	Optional<User> findByUserName(String userName);
    Optional<User> findByUserAccount(String userAccount);
    Optional<User> findByUserMail(String userMail);
    Optional<User> findByVerificationToken(String token);
    Optional<User> findByResetToken(String token);
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
    boolean existsByUserAccount(String userAccount);
    boolean existsByUserMail(String userMail);
}