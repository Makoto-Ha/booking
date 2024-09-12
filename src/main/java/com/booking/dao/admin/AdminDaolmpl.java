package com.booking.dao.admin;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.booking.bean.admin.Admin;
import com.booking.utils.HibernateUtil;

public class AdminDaolmpl implements AdminDao{

    /**
     * 獲取所有管理員資料
     * 
     * @return List<Admin>
     */
	@Override
    public List<Admin> getAdminAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Admin"; // HQL查询
            return session.createQuery(hql, Admin.class).list();
        }
    }

    /**
     * 模糊查詢
     * @param admin
     * @return List<Admin>
     */
	@Override
    public List<Admin> dynamicQuery(Admin admin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
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

            var query = session.createQuery(hql.toString(), Admin.class);

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

            return query.list();
        }
    }

    /**
     * 根據姓名查詢管理員
     * @param adminName
     * @return List<Admin>
     */
	@Override
    public List<Admin> getAdminByName(String adminName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Admin WHERE adminName LIKE :adminName";
            return session.createQuery(hql, Admin.class)
                          .setParameter("adminName", "%" + adminName + "%")
                          .list();
        }
    }

    /**
     * 根據ID獲取管理員
     * @param adminId
     * @return Admin
     */
	@Override
    public Admin getAdminById(Integer adminId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Admin.class, adminId); // 根據ID查找
        }
    }

    /**
     * 添加管理員
     * @param admin
     * @return Integer
     */
	@Override
    public Integer addAdmin(Admin admin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(admin);
            transaction.commit();
            return admin.getAdminId();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 軟刪除管理員
     * @param adminId
     */
	@Override
    public void softremoveAdminById(Integer adminId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Admin admin = session.get(Admin.class, adminId);
            if (admin != null) {
                admin.setAdminStatus(0); // 设置状态为 0 表示软删除
                session.update(admin);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * 更新管理員
     * @param admin
     */
	@Override
    public void updateAdmin(Admin admin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(admin); // 更新实体
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    /**
     * 根據帳號和密碼查詢管理員
     * @param adminAccount
     * @param adminPassword
     * @return Admin
     */
	@Override
    public Admin getAdminByAccountAndPassword(String adminAccount, String adminPassword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Admin WHERE adminAccount = :adminAccount AND adminPassword = :adminPassword";
            return session.createQuery(hql, Admin.class)
                          .setParameter("adminAccount", adminAccount)
                          .setParameter("adminPassword", adminPassword)
                          .uniqueResult();
        }
    }

    /**
     * 檢查帳號是否存在
     * @param adminAccount
     * @return Admin
     */
	@Override
    public Admin existsByAccount(String adminAccount) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Admin WHERE adminAccount = :adminAccount";
            return session.createQuery(hql, Admin.class)
                          .setParameter("adminAccount", adminAccount)
                          .uniqueResult();
        }
    }
}
