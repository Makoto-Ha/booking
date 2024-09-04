package com.booking.dao.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.booking.bean.admin.Admin;
import com.booking.mapper.AdminRowMapper;
import com.booking.utils.DaoResult;
import com.booking.utils.DaoUtils;

public class AdminDao {
	
	public DaoResult<Admin> getAdminAll() {
		String sql = "SELECT * FROM admin";
		return DaoUtils.commonsQuery(sql, new AdminRowMapper());
	}
	
	/**
	 * 模糊查詢
	 * @param admin
	 * @return
	 */
	public DaoResult<Admin> dynamicQuery(Admin admin) {
		StringBuilder sql = new StringBuilder("SELECT * FROM admin WHERE 1=1");
		List<Object> list = new ArrayList<>();
		Integer adminId = admin.getAdminId();
		String adminAccount = admin.getAdminAccount();
		String adminName = admin.getAdminName();
		String adminMail = admin.getAdminMail();
		LocalDate hiredate = admin.getHiredate();
		Integer adminStatus = admin.getAdminStatus();
		
		if (adminId != null) {
	        sql.append(" AND admin_id =?");
	        list.add(adminId);
	    }
	    if (adminAccount != null && !adminAccount.isEmpty()) {
	        sql.append(" AND admin_account Like ?");
	        list.add("%"+adminAccount+"%");
	    }
	    if (adminName != null && !adminName.isEmpty()) {
	        sql.append(" AND admin_name Like ?");
	        list.add("%"+adminName+"%");
	    }
	    if (adminMail != null && !adminMail.isEmpty()) {
	        sql.append(" AND admin_mail Like ?");
	        list.add("%"+adminMail+"%");
	    }
	    
	    if(hiredate != null) {
	    	sql.append(" AND hiredate =?");
	    	list.add(hiredate);
	    }
	    
	    if (adminStatus != null) {
	        sql.append(" AND admin_status =?");
	        list.add(adminStatus);
	    }
	    return DaoUtils.commonsQuery(sql.toString(), new AdminRowMapper(), list.toArray());
	}
	
	
	

	public DaoResult<Admin> getAdminByName(String adminName) {
		String sql = "SELECT * FROM admin WHERE admin_name LIKE ?";
		return DaoUtils.commonsQuery(sql, new AdminRowMapper(), "%" + adminName + "%");
	}
	
	/**
	 * 根據id獲取管理員
	 * @param adminId
	 * @return
	 */
	public DaoResult<Admin> getAdminById(Integer adminId) {
		String sql = "SELECT * FROM admin WHERE admin_id = ?";
		return DaoUtils.commonsQuery(sql, new AdminRowMapper(), adminId);
	}
	

	public DaoResult<Integer> addAdmin(Admin admin) {
		String sql = "INSERT INTO admin (admin_account, admin_password, admin_name, admin_mail, hiredate, admin_status) VALUES (?, ?, ?, ?, ?, ?)";
		return DaoUtils.commonsUpdate(sql, admin.getAdminAccount(), admin.getAdminPassword(), admin.getAdminName(),
				admin.getAdminMail(), admin.getHiredate(), admin.getAdminStatus());
	}

	public DaoResult<Integer> softremoveAdminById(Integer adminId) {
		String sql = "UPDATE admin SET admin_status = 0 WHERE admin_id = ?";
		return DaoUtils.commonsUpdate(sql, adminId);
	}

	public DaoResult<Integer> updateAdmin(Admin admin) {
		String sql = "UPDATE admin SET admin_account = ?, admin_password = ?, admin_name = ?, admin_mail = ?, hiredate = ?, admin_status = ? WHERE admin_id = ?";
		return DaoUtils.commonsUpdate(sql, admin.getAdminAccount(), admin.getAdminPassword(), admin.getAdminName(),
				admin.getAdminMail(), admin.getHiredate(), admin.getAdminStatus(), admin.getAdminId());
	}

	/**
	 * 檢查帳號密碼是否重複
	 * @param adminAccount
	 * @param adminPassword
	 * @return
	 */
	public DaoResult<Admin> getAdminByAccountAndPassword(String adminAccount, String adminPassword) {
		String sql = "SELECT * FROM admin WHERE admin_account = ? AND admin_password = ?";
		return DaoUtils.commonsQuery(sql, new AdminRowMapper(), adminAccount, adminPassword);
	}

	/**
	 * 檢查帳號是否重複
	 * @param adminAccount
	 */
	public DaoResult<Admin>  existsByAccount(String adminAccount) {
		String sql = "SELECT * FROM admin WHERE admin_account = ?";
		return DaoUtils.commonsQuery(sql, new AdminRowMapper(), adminAccount);
	}

}
