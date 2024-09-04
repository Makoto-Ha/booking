package com.booking.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.booking.bean.admin.Admin;

public class AdminRowMapper implements RowMapper<Admin> {
	
	@Override
	public Admin getRow(ResultSet resultSet) {
		try {
			Integer id = resultSet.getInt("admin_id");
			String account = resultSet.getString("admin_account");
			String password = resultSet.getString("admin_password");
			String name = resultSet.getString("admin_name");
			String mail = resultSet.getString("admin_mail");
			LocalDate hiredate = resultSet.getDate("hiredate").toLocalDate();
			Integer status = resultSet.getInt("admin_status");
			return new Admin(id, account, password, name, mail, hiredate, status);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
}

