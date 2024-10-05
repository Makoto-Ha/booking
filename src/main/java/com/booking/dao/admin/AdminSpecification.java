package com.booking.dao.admin;

import org.springframework.data.jpa.domain.Specification;
import com.booking.bean.pojo.admin.Admin;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import java.time.LocalDate;

public class AdminSpecification {
	
//依帳號模糊查詢
    public static Specification<Admin> accountContains(String adminAccount) {
        return (Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (adminAccount == null || adminAccount.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(root.get("adminAccount"), "%" + adminAccount + "%");
        };
    }
    
    
//依名字模糊查詢
    public static Specification<Admin> nameContains(String adminName) {
        return (Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (adminName == null || adminName.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(root.get("adminName"), "%" + adminName + "%");
        };
    }

    
    //依信箱模糊查詢
    public static Specification<Admin> mailContains(String adminMail) {
        return (Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (adminMail == null || adminMail.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(root.get("adminMail"), "%" + adminMail + "%");
        };
    }
//依受雇日期模糊查詢
    public static Specification<Admin> hiredateEquals(LocalDate hiredate) {
        return (Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (hiredate == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("hiredate"), hiredate);
        };
    }
//依狀態模糊查詢
    public static Specification<Admin> statusEquals(Integer adminStatus) {
        return (Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (adminStatus == null) {
                return builder.conjunction();
            }
            return builder.equal(root.get("adminStatus"), adminStatus);
        };
    }
}