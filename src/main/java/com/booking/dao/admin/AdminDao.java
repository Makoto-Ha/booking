package com.booking.dao.admin;

import java.util.List;
import com.booking.bean.admin.Admin;
import com.booking.utils.util.DaoResult;

public interface AdminDao {

    /**
     * 獲取所有管理員資料
     * 
     * @return DaoResult<List<Admin>>
     */
    public DaoResult<List<Admin>> getAdminAll(); 

    /**
     * 模糊查詢
     * @param admin
     * @return DaoResult<List<Admin>>
     */
    public DaoResult<List<Admin>> dynamicQuery(Admin admin);

    /**
     * 根據姓名查詢管理員
     * @param adminName
     * @return DaoResult<List<Admin>>
     */
    public DaoResult<List<Admin>> getAdminByName(String adminName);

    /**
     * 根據ID獲取管理員
     * @param adminId
     * @return DaoResult<Admin>
     */
    public DaoResult<Admin> getAdminById(Integer adminId);

    /**
     * 添加管理員
     * @param admin
     * @return DaoResult<Integer>
     */
    public DaoResult<Integer> addAdmin(Admin admin);

    /**
     * 軟刪除管理員
     * @param adminId
     * @return DaoResult<Void>
     */
    public DaoResult<?> softremoveAdminById(Integer adminId);

    /**
     * 更新管理員
     * @param admin
     * @return DaoResult<Void>
     */
    public DaoResult<?> updateAdmin(Admin admin);

    /**
     * 根據帳號和密碼查詢管理員
     * @param adminAccount
     * @param adminPassword
     * @return DaoResult<Admin>
     */
    public DaoResult<Admin> getAdminByAccountAndPassword(String adminAccount, String adminPassword);
    
    /**
     * 檢查帳號是否存在
     * @param adminAccount
     * @return DaoResult<Admin>
     */
    public DaoResult<Admin> existsByAccount(String adminAccount);
}
