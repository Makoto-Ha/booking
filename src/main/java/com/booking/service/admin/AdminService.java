package com.booking.service.admin;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.booking.bean.admin.Admin;
import com.booking.dao.admin.AdminDao;
import com.booking.dto.admin.AdminDTO;
import com.booking.utils.DaoResult;
import com.booking.utils.Listable;
import com.booking.utils.Result;

public class AdminService {

	private AdminDao adminDao = new AdminDao();

	/**
	 * 獲取所有管理員資料
	 * 
	 * @return
	 */
	public Result<List<Listable>> getAdminAll() {
		DaoResult<Admin> daoResult = adminDao.getAdminAll();
		List<Admin> admins = daoResult.getData();
		List<Listable> lists = new ArrayList<>();
		for (Admin admin : admins) {
			AdminDTO adminDTO = new AdminDTO();
			try {
				BeanUtils.copyProperties(adminDTO, admin);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			lists.add(adminDTO);
		}
		if (admins == null) {
			return Result.failure("查詢所有管理員失敗");
		}
		return Result.success(lists);
	}

	/**
	 * 依id獲取管理員資料
	 * 
	 * @param adminId
	 * @return
	 */
	public Result<Admin> getAdminById(Integer adminId) {
		DaoResult<Admin> daoResult = adminDao.getAdminById(adminId);
		Admin admin = daoResult.getEntity();
		if (admin == null) {
			return Result.failure("找不到該管理員");
		}
		return Result.success(admin);
	}

	/**
	 * 根據模糊查詢得到多筆管理員資料
	 * @param admin
	 * @return
	 */
	public Result<List<Listable>> getAdmins(Admin admin) {
		DaoResult<Admin> daynamicQueryDaoResult = adminDao.dynamicQuery(admin);
		List<Admin> admins = daynamicQueryDaoResult.getData();

		if (admins == null) {
			return Result.failure("查無此員工資料");
		}
		List<Listable> adminsDTO = new ArrayList<>();
		for(Admin adminOne : admins) {
			AdminDTO adminDTO = new AdminDTO();
			try {
				BeanUtils.copyProperties(adminDTO, adminOne);
				adminsDTO.add(adminDTO);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return Result.success(adminsDTO);
	}
	
	public Result<Integer> addAdmin(Admin admin) {
		DaoResult<Integer> daoResult = adminDao.addAdmin(admin);
		Integer addadmin = daoResult.getGeneratedId();
		if (addadmin == null || daoResult.getAffectedRows() == 0) {
			return Result.failure("新增管理員失敗");
		}
		return Result.success(addadmin);
	}

	public Result<Integer> softremoveAdmin(Integer adminId) {
		DaoResult<Integer> daoResult = adminDao.softremoveAdminById(adminId);
		Integer softremoveadmin = daoResult.getAffectedRows();
		if (softremoveadmin == 0) {
			return Result.failure("刪除管理員失敗");
		}
		return Result.success(softremoveadmin);
	}

	public Result<Integer> updateAdmin(Admin admin) {
		Integer oldAdminId = admin.getAdminId();
		Admin oldAdmin = adminDao.getAdminById(oldAdminId).getEntity();
		
		String adminAccount = admin.getAdminAccount();
		String adminPassword = admin.getAdminPassword();
		String adminName = admin.getAdminName();
		String adminMail = admin.getAdminMail();
		LocalDate hiredate = admin.getHiredate();
		Integer adminStatus = admin.getAdminStatus();

		if (adminAccount == null || adminAccount.isEmpty()) {
			admin.setAdminAccount(oldAdmin.getAdminAccount());
		}

		if (adminPassword == null || adminPassword.isEmpty()) {
			admin.setAdminPassword(oldAdmin.getAdminPassword());
		}

		if (adminName == null || adminName.isEmpty()) {
			admin.setAdminName(oldAdmin.getAdminName());
		}

		if (adminMail == null || adminMail.isEmpty()) {
			admin.setAdminMail(oldAdmin.getAdminMail());
		}

		if (hiredate == null) {
			admin.setHiredate(oldAdmin.getHiredate());
		}

		if (adminStatus == null) {
			admin.setAdminStatus(oldAdmin.getAdminStatus());
		}
		admin.setHiredate(LocalDate.now());

		DaoResult<Integer> updateAdminDaoResult = adminDao.updateAdmin(admin);

		if (updateAdminDaoResult.getAffectedRows() == null) {
			return Result.failure("更新管理員資料失敗");
		}
		return Result.success("更新管理員資料成功");
	}
	
	//登入
	public Result<Admin> loginAdmin(String adminAccount, String adminPassword) {
    DaoResult<Admin> daoResult = adminDao.getAdminByAccountAndPassword(adminAccount, adminPassword);
    Admin getadmin = daoResult.getEntity();
        if (getadmin == null) {
            return Result.failure("登入失敗，請先註冊");
        }
        return Result.success(getadmin);
    }
	
	
	// 註冊
	public Result<Admin> registerAdmin(String adminAccount, String adminPassword) {
		DaoResult<Admin> daoResult = adminDao.getAdminByAccountAndPassword(adminAccount, adminPassword);
		Admin admin = daoResult.getEntity();
		if (admin == null) {
			return Result.failure("");
		}
		return Result.success(admin);
	}

	// 檢查帳號重複
	public Result<Admin> checkAccountExists(String adminAccount) {
		 DaoResult<Admin> daoResult = adminDao.existsByAccount(adminAccount);
		 Admin admin = daoResult.getEntity();
		 if (admin == null) {
			 return Result.success(admin);
		}
		 return Result.failure("此帳號已被註冊");
	}

	/**
	 * 註冊後在資料庫建立資料
	 * @param admin
	 * @return
	 */
	public Result<Integer> addNewAdmin(Admin admin) {
		DaoResult<Integer> daoResult = adminDao.addAdmin(admin);
		Integer addnewadmin= daoResult.getGeneratedId();
		if (addnewadmin == null || daoResult.getAffectedRows() == 0) {
			return Result.failure("註冊失敗");
		}
		return Result.success(addnewadmin);
	}
}
