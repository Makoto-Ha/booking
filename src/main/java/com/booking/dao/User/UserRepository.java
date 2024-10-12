package com.booking.dao.User;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import com.booking.bean.pojo.user.User;


import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{
    Optional<User> findByUserAccount(String userAccount);
    Optional<User> findByUserMail(String userMail);
    Optional<User> findByVerificationToken(String token);
    Optional<User> findByResetToken(String token);
}