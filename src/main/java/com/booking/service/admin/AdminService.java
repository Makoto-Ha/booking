package com.booking.service.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.booking.bean.admin.Admin;
import com.booking.dao.admin.AdminDao;
import com.booking.dto.admin.AdminDTO;
import com.booking.utils.BeanUtil;
import com.booking.utils.Listable;
import com.booking.utils.Result;
import com.booking.utils.util.DaoResult;

@Service
@Transactional
public class AdminService {

	@Autowired
	private AdminDao adminDao;

	/**
	 * 獲取所有管理員資料
	 * 
	 * @return
	 */
	public Result<List<Listable>> getAdminAll() {
		DaoResult<List<Admin>> daoResult = adminDao.getAdminAll();
		if (daoResult.isFailure()) {
			return Result.failure("查詢所有管理員失敗");
		}
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
		return Result.success(lists);
	}

	/**
	 * 模糊查詢
	 * 
	 * @param admin
	 * @return
	 */
	public Result<List<Listable>> getAdmins(Admin admin) {
		DaoResult<List<Admin>> daoResult = adminDao.dynamicQuery(admin);
		if (daoResult.isFailure() || daoResult.getData() == null || daoResult.getData().isEmpty()) {
			return Result.failure("查無此員工資料");
		}

		List<Listable> adminsDTO = new ArrayList<>();
		for (Admin adminOne : daoResult.getData()) {
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

	/**
	 * 依id獲得管理員
	 * 
	 * @param adminId
	 * @return
	 */
	public Result<AdminDTO> getAdminById(Integer adminId) {
		DaoResult<Admin> daoResult = adminDao.getAdminById(adminId);
		Admin admin = daoResult.getData();
		if (daoResult.isFailure()) {
			return Result.failure("找不到該管理員");
		}
		AdminDTO adminDTO = new AdminDTO();
		BeanUtil.copyProperties(adminDTO, admin);
		return Result.success(adminDTO);
	}

	/**
	 * 新增管理員
	 * 
	 * @param admin
	 * @return
	 */
	public Result<Integer> addAdmin(Admin admin) {
		DaoResult<?> daoResult = adminDao.addAdmin(admin);
		if (daoResult.isFailure()) {
			return Result.failure("新增管理員失敗");
		}
		return Result.success(daoResult.getGeneratedId());
	}

	/**
	 * 軟刪除
	 * 
	 * @param adminId
	 * @return
	 */
	public Result<Integer> softRemoveAdmin(Integer adminId) {
		DaoResult<?> daoResult = adminDao.softremoveAdminById(adminId);
		if (daoResult.isFailure()) {
			return Result.failure("軟刪除管理員失敗");
		}
		return Result.success(adminId);
	}

	/**
	 * 更新
	 * 
	 * @param admin
	 * @return
	 */
	public Result<String> updateAdmin(Admin admin) {
		DaoResult<Admin> daoResult = adminDao.getAdminById(admin.getAdminId());

		if (daoResult.isFailure() || daoResult.getData() == null) {
			return Result.failure("更新失敗，找不到該管理員");
		}

		Admin oldAdmin = daoResult.getData();

		if (admin.getAdminAccount() == null || admin.getAdminAccount().isEmpty()) {
			admin.setAdminAccount(oldAdmin.getAdminAccount());
		}

		if (admin.getAdminPassword() == null || admin.getAdminPassword().isEmpty()) {
			admin.setAdminPassword(oldAdmin.getAdminPassword());
		}

		if (admin.getAdminName() == null || admin.getAdminName().isEmpty()) {
			admin.setAdminName(oldAdmin.getAdminName());
		}

		if (admin.getAdminMail() == null || admin.getAdminMail().isEmpty()) {
			admin.setAdminMail(oldAdmin.getAdminMail());
		}

		if (admin.getHiredate() == null) {
			admin.setHiredate(oldAdmin.getHiredate());
		}

		if (admin.getAdminStatus() == null) {
			admin.setAdminStatus(oldAdmin.getAdminStatus());
		}

		DaoResult<?> updateAdminResult = adminDao.updateAdmin(admin);
		if (updateAdminResult.isFailure()) {
			return Result.failure("更新失敗");
		}
		return Result.success("更新成功");
	}

	///////////////////////////////////////////////////////////

	public Result<Admin> loginAdmin(String adminAccount, String adminPassword) {
		DaoResult<Admin> daoResult = adminDao.getAdminByAccountAndPassword(adminAccount, adminPassword);
		if (daoResult.isFailure() || daoResult.getData() == null) {
			return Result.failure("登入失敗，請先註冊");
		}
		return Result.success(daoResult.getData());
	}

	public Result<Admin> registerAdmin(String adminAccount, String adminPassword) {
		DaoResult<Admin> daoResult = adminDao.existsByAccount(adminAccount);
		if (daoResult.isSuccess() && daoResult.getData() != null) {
			return Result.failure("該帳號已存在");
		}
		return Result.success(null);
	}

	public Result<Admin> checkAccountExists(String adminAccount) {
		DaoResult<Admin> daoResult = adminDao.existsByAccount(adminAccount);
		if (daoResult.isSuccess() && daoResult.getData() != null) {
			return Result.failure("此帳號已被註冊");
		}
		return Result.success(null);
	}

	public Result<Integer> addNewAdmin(Admin admin) {
		DaoResult<Integer> daoResult = adminDao.addAdmin(admin);
		if (daoResult.isFailure() || daoResult.getData() == null) {
			return Result.failure("註冊失敗");
		}
		return Result.success(daoResult.getData());
	}
}
