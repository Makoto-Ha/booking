package com.booking.dao.admin;

import java.util.List;
import java.util.Map;

import com.booking.bean.pojo.admin.Admin;
import com.booking.utils.DaoResult;

public interface AdminDao {

    /**
     * 獲取所有管理員資料
     * 
     * @return DaoResult<List<Admin>>
     */
  DaoResult<List<Admin>> getAdminAll(Integer page); 

    /**
     * 模糊查詢
     * @param admin
     * @return DaoResult<List<Admin>>
     */
<<<<<<< HEAD
  DaoResult<List<Admin>> dynamicQuery(Admin admin, Map<String, Object> extraValues);
=======
  DaoResult<List<Admin>> dynamicQuery(Admin admin);
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))

    /**
     * 根據姓名查詢管理員
     * @param adminName
     * @return DaoResult<List<Admin>>
     */
<<<<<<< HEAD
  DaoResult<List<Admin>> getAdminsByName(String adminName);
  
  
=======
  DaoResult<List<Admin>> getAdminByName(String adminName);
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))

    /**
     * 根據ID獲取管理員
     * @param adminId
     * @return DaoResult<Admin>
     */
     DaoResult<Admin> getAdminById(Integer adminId);

    /**
     * 添加管理員
     * @param admin
     * @return DaoResult<Integer>
     */
<<<<<<< HEAD
 DaoResult<?> addAdmin(Admin admin);
=======
 DaoResult<Integer> addAdmin(Admin admin);
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))

    /**
     * 軟刪除管理員
     * @param adminId
     * @return DaoResult<Void>
     */
  DaoResult<?> softremoveAdminById(Integer adminId);

    /**
     * 更新管理員
     * @param admin
     * @return DaoResult<Void>
     */
   DaoResult<?> updateAdmin(Admin admin);

    /**
     * 根據帳號和密碼查詢管理員
     * @param adminAccount
     * @param adminPassword
     * @return DaoResult<Admin>
     */
 DaoResult<Admin> getAdminByAccountAndPassword(String adminAccount, String adminPassword);
    
    /**
     * 檢查帳號是否存在
     * @param adminAccount
     * @return DaoResult<Admin>
     */
  DaoResult<Admin> existsByAccount(String adminAccount);
}
