package com.booking.dao.admin;

import java.util.List;

import com.booking.bean.admin.Admin;
import com.booking.utils.util.DaoResult;

public interface AdminDao {

    /**
     * 獲取所有管理員資料
     * 
     * @return List<Admin>
     */
    public List<Admin> getAdminAll(); 
    
    /**
     * 模糊查詢
     * @param admin
     * @return List<Admin>
     */
    public abstract List<Admin> dynamicQuery(Admin admin);
    

    /**
     * 根據姓名查詢管理員
     * @param adminName
     * @return List<Admin>
     */
    public List<Admin> getAdminByName(String adminName);

    /**
     * 根據ID獲取管理員
     * @param adminId
     * @return Admin
     */
    public Admin getAdminById(Integer adminId);

    /**
     * 添加管理員
     * @param admin
     * @return Integer
     */
    public Integer addAdmin(Admin admin);

    /**
     * 軟刪除管理員
     * @param adminId
     */
    public void softremoveAdminById(Integer adminId);

    /**
     * 更新管理員
     * @param admin
     */
    public void updateAdmin(Admin admin);

    /**
     * 根據帳號和密碼查詢管理員
     * @param adminAccount
     * @param adminPassword
     * @return Admin
     */
    public Admin getAdminByAccountAndPassword(String adminAccount, String adminPassword);
    
    /**
     * 檢查帳號是否存在
     * @param adminAccount
     * @return Admin
     */
    public Admin existsByAccount(String adminAccount);
}
