package com.booking.dao.user;
import java.util.List;
import java.util.Map;


import com.booking.bean.pojo.user.User;
import com.booking.utils.DaoResult;
public interface UserDao {

		
		DaoResult<List<User>> getUserAll(Integer page);

		
		DaoResult<List<User>> dynamicQuery(User user, Map<String, Object> extraValues);

		
		DaoResult<List<User>> getuserByName(String userName);
		
		
		DaoResult<User> getUserById(Integer userId);

		
		DaoResult<?> addUser(User user);

		
		DaoResult<?> removeUserById(Integer userId);

	
		DaoResult<?> updateUser(User user);

		
		DaoResult<List<User>> getUsersByName(String userName);



	}
