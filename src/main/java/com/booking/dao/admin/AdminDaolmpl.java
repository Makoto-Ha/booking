package com.booking.dao.admin;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.booking.bean.admin.Admin;
import com.booking.utils.util.DaoResult;

@Repository // 表明這是一個 DAO 類別
public class AdminDaolmpl implements AdminDao {

	@Autowired
	private SessionFactory sessionFactory;

	// 取得當前的 Hibernate Session
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	/**
	 * 獲取所有管理員資料
	 * 
	 * @return DaoResult<List<Admin>>
	 */

	@Override
	public DaoResult<List<Admin>> getAdminAll() {
		String hql = "FROM Admin";
		Query<Admin> query = getCurrentSession().createQuery(hql, Admin.class);
		List<Admin> admins = query.getResultList();
		return DaoResult.create(admins).setSuccess(admins != null);
	}

	/**
	 * 模糊查詢
	 * 
	 * @param admin
	 * @return DaoResult<List<Admin>>
	 */
	@Override
	public DaoResult<List<Admin>> dynamicQuery(Admin admin) {
		try {
			StringBuilder hql = new StringBuilder("FROM Admin WHERE 1=1");

			if (admin.getAdminId() != null) {
				hql.append(" AND adminId = :adminId");
			}
			if (admin.getAdminAccount() != null && !admin.getAdminAccount().isEmpty()) {
				hql.append(" AND adminAccount LIKE :adminAccount");
			}
			if (admin.getAdminName() != null && !admin.getAdminName().isEmpty()) {
				hql.append(" AND adminName LIKE :adminName");
			}
			if (admin.getAdminMail() != null && !admin.getAdminMail().isEmpty()) {
				hql.append(" AND adminMail LIKE :adminMail");
			}
			if (admin.getHiredate() != null) {
				hql.append(" AND hiredate = :hiredate");
			}
			if (admin.getAdminStatus() != null) {
				hql.append(" AND adminStatus = :adminStatus");
			}

			var query = getCurrentSession().createQuery(hql.toString(), Admin.class);

			if (admin.getAdminId() != null) {
				query.setParameter("adminId", admin.getAdminId());
			}
			if (admin.getAdminAccount() != null && !admin.getAdminAccount().isEmpty()) {
				query.setParameter("adminAccount", "%" + admin.getAdminAccount() + "%");
			}
			if (admin.getAdminName() != null && !admin.getAdminName().isEmpty()) {
				query.setParameter("adminName", "%" + admin.getAdminName() + "%");
			}
			if (admin.getAdminMail() != null && !admin.getAdminMail().isEmpty()) {
				query.setParameter("adminMail", "%" + admin.getAdminMail() + "%");
			}
			if (admin.getHiredate() != null) {
				query.setParameter("hiredate", admin.getHiredate());
			}
			if (admin.getAdminStatus() != null) {
				query.setParameter("adminStatus", admin.getAdminStatus());
			}

			List<Admin> admins = query.list();
			return DaoResult.create(admins).setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			return DaoResult.<List<Admin>>create(null).setSuccess(false);
		}
	}

	/**
	 * 根據姓名查詢管理員
	 * 
	 * @param adminName
	 * @return DaoResult<List<Admin>>
	 */
	@Override
	public DaoResult<List<Admin>> getAdminByName(String adminName) {
		try {
			String hql = "FROM Admin WHERE adminName LIKE :adminName";
			List<Admin> admins = getCurrentSession().createQuery(hql, Admin.class)
					.setParameter("adminName", "%" + adminName + "%").list();
			return DaoResult.create(admins).setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			return DaoResult.<List<Admin>>create(null).setSuccess(false);
		}
	}

	/**
	 * 根據ID獲取管理員
	 * 
	 * @param adminId
	 * @return DaoResult<Admin>
	 */
	@Override
	public DaoResult<Admin> getAdminById(Integer adminId) {
		try {
			Admin admin = getCurrentSession().get(Admin.class, adminId);
			return DaoResult.create(admin).setSuccess(admin != null);
		} catch (Exception e) {
			e.printStackTrace();
			return DaoResult.<Admin>create(null).setSuccess(false);
		}
	}

	/**
	 * 添加管理員
	 * 
	 * @param admin
	 * @return DaoResult<Integer>
	 */
	@Override
	public DaoResult<Integer> addAdmin(Admin admin) {
		try {
			getCurrentSession().persist(admin);
			return DaoResult.create(admin.getAdminId()).setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			return DaoResult.<Integer>create(null).setSuccess(false);
		}
	}

	/**
	 * 軟刪除管理員
	 * 
	 * @param adminId
	 * @return DaoResult<Void>
	 */
	@Override
	public DaoResult<?> softremoveAdminById(Integer adminId) {
		try {
			Admin admin = getCurrentSession().get(Admin.class, adminId);
			if (admin != null) {
				admin.setAdminStatus(0); // 設置狀態為 0 表示軟刪除
				getCurrentSession().merge(admin);
			}
			return DaoResult.create(null).setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			return DaoResult.create(null).setSuccess(false);
		}
	}

	/**
	 * 更新管理員
	 * 
	 * @param admin
	 * @return DaoResult<Void>
	 */
	@Override
	public DaoResult<?> updateAdmin(Admin admin) {
		try {
			getCurrentSession().merge(admin);
			return DaoResult.create(null).setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			return DaoResult.create(null).setSuccess(false);
		}
	}

	/**
	 * 根據帳號和密碼查詢管理員
	 * 
	 * @param adminAccount
	 * @param adminPassword
	 * @return DaoResult<Admin>
	 */
	@Override
	public DaoResult<Admin> getAdminByAccountAndPassword(String adminAccount, String adminPassword) {
		try {
			String hql = "FROM Admin WHERE adminAccount = :adminAccount AND adminPassword = :adminPassword";
			Admin admin = getCurrentSession().createQuery(hql, Admin.class).setParameter("adminAccount", adminAccount)
					.setParameter("adminPassword", adminPassword).uniqueResult();
			return DaoResult.create(admin).setSuccess(admin != null);
		} catch (Exception e) {
			e.printStackTrace();
			return DaoResult.<Admin>create(null).setSuccess(false);
		}
	}

	/**
	 * 檢查帳號是否存在
	 * 
	 * @param adminAccount
	 * @return DaoResult<Admin>
	 */
	@Override
	public DaoResult<Admin> existsByAccount(String adminAccount) {
		try {
			String hql = "FROM Admin WHERE adminAccount = :adminAccount";
			Admin admin = getCurrentSession().createQuery(hql, Admin.class).setParameter("adminAccount", adminAccount)
					.uniqueResult();
			return DaoResult.create(admin).setSuccess(admin != null);
		} catch (Exception e) {
			e.printStackTrace();
			return DaoResult.<Admin>create(null).setSuccess(false);
		}
	}
}
