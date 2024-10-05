package com.booking.dao.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.booking.bean.pojo.admin.*;
<<<<<<< HEAD
import com.booking.utils.DaoResult;

=======
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer>, JpaSpecificationExecutor<Admin>,AdminDao {
  
<<<<<<< HEAD
	
	Optional<Admin> findByAdminAccount(String adminAccount);
    Optional<Admin> findByAdminMail(String adminMail);
    Optional<Admin> findByResetPasswordToken(String resetPasswordToken);
    boolean existsByAdminAccount(String adminAccount);
	
=======
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
}