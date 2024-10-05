package com.booking.dao.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.booking.bean.pojo.admin.*;
import java.util.List;
import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Integer>, JpaSpecificationExecutor<Admin>,AdminDao {
  
}