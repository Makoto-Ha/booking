package com.booking.dao.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.booking.bean.pojo.admin.*;
import com.booking.utils.DaoResult;

import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer>, JpaSpecificationExecutor<Admin>,AdminDao {
  
	
	Optional<Admin> findByAdminAccount(String adminAccount);
    Optional<Admin> findByAdminMail(String adminMail);
    Optional<Admin> findByResetPasswordToken(String resetPasswordToken);
    boolean existsByAdminAccount(String adminAccount);
	
}