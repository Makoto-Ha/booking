//package com.booking.service.shopping;
//
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.booking.bean.pojo.shopping.TestUser;
//import com.booking.dao.shopping.TestUserRepository;
//import com.booking.utils.Result;
//
//@Service
//public class TestUserService {
//
//	@Autowired
//	private TestUserRepository testUserRepository;
//	
//	public Result<TestUser> findUserById(Integer userId){
//		Optional<TestUser> result = testUserRepository.findById(userId);
//		return Result.success(result.get());
//	}
//	
//	
//	
//}
