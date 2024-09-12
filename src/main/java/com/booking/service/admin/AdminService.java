package com.booking.service.admin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.booking.bean.admin.Admin;
import com.booking.dao.admin.AdminDaolmpl;
import com.booking.dto.admin.AdminDTO;
import com.booking.utils.DaoResult;
import com.booking.utils.Listable;
import com.booking.utils.Result;

public class AdminService {

    private AdminDaolmpl adminDao = new AdminDaolmpl();

    /**
     * 獲取所有管理員資料
     * 
     * @return
     */
    public Result<List<Listable>> getAdminAll() {
        List<Admin> admins = adminDao.getAdminAll();
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
        if (admins == null || admins.isEmpty()) {
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
        Admin admin = adminDao.getAdminById(adminId);
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
        List<Admin> admins = adminDao.dynamicQuery(admin);
        if (admins == null || admins.isEmpty()) {
            return Result.failure("查無此員工資料");
        }

        List<Listable> adminsDTO = new ArrayList<>();
        for (Admin adminOne : admins) {
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
     * 新增管理員
     */
    public Result<Integer> addAdmin(Admin admin) {
        Integer addAdminId = adminDao.addAdmin(admin);
        if (addAdminId == null) {
            return Result.failure("新增管理員失敗");
        }
        return Result.success(addAdminId);
    }

    /**
     * 軟刪除管理員
     */
    public Result<Integer> softRemoveAdmin(Integer adminId) {
        adminDao.softremoveAdminById(adminId);  // 软删除的逻辑放在 DAO 层
        return Result.success(adminId);
    }

    /**
     * 更新管理員
     */
    public Result<Integer> updateAdmin(Admin admin) {
        Admin oldAdmin = adminDao.getAdminById(admin.getAdminId());

        if (oldAdmin == null) {
            return Result.failure("更新失敗，找不到該管理員");
        }

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

        //admin.setHiredate(LocalDate.now()); // 更新 hiredate 为当前日期

        adminDao.updateAdmin(admin);
        return Result.success(admin.getAdminId());
    }

    /**
     * 登入管理員
     */
    public Result<Admin> loginAdmin(String adminAccount, String adminPassword) {
        Admin admin = adminDao.getAdminByAccountAndPassword(adminAccount, adminPassword);
        if (admin == null) {
            return Result.failure("登入失敗，請先註冊");
        }
        return Result.success(admin);
    }

    /**
     * 註冊
     */
    public Result<Admin> registerAdmin(String adminAccount, String adminPassword) {
        Admin admin = adminDao.getAdminByAccountAndPassword(adminAccount, adminPassword);
        if (admin != null) {
            return Result.failure("該帳號已存在");
        }
        return Result.success(admin);
    }

    /**
     * 檢查帳號是否重複
     */
    public Result<Admin> checkAccountExists(String adminAccount) {
        Admin admin = adminDao.existsByAccount(adminAccount);
        if (admin != null) {
            return Result.failure("此帳號已被註冊");
        }
        return Result.success(null);
    }

    /**
     * 新增註冊用戶
     */
    public Result<Integer> addNewAdmin(Admin admin) {
        Integer addNewAdminId = adminDao.addAdmin(admin);
        if (addNewAdminId == null) {
            return Result.failure("註冊失敗");
        }
        return Result.success(addNewAdminId);
    }
}
