package com.booking.dao.user;
import org.springframework.data.jpa.domain.Specification;


import com.booking.bean.pojo.user.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


public class UserSpecification {
	
	// 根據名字進行模糊查詢
	public static Specification<User> nameContains(String userName) {
		return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
			if(userName == null || userName.isEmpty()) {
				return builder.conjunction();
			}
			
			return builder.like(root.get("userName"), "%" + userName + "%");
		};
	}
	
	//依帳號模糊查詢
    public static Specification<User> accountContains(String userAccount) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
            if (userAccount == null || userAccount.isEmpty()) {
                return builder.conjunction();
            }
            return builder.like(root.get("userAccount"), "%" + userAccount + "%");
        };
    }
	
	


	}
	

	
