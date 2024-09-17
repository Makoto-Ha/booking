package com.booking.dao.admin;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.booking.bean.admin.Admin;
import com.booking.utils.HibernateUtil;
import com.booking.utils.util.DaoResult;

public class AdminDaolmpl implements AdminDao {

    /**
     * 獲取所有管理員資料
     * 
     * @return DaoResult<List<Admin>>
     */
    @Override
    public DaoResult<List<Admin>> getAdminAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Admin";
            List<Admin> admins = session.createQuery(hql, Admin.class).getResultList();
            return DaoResult.create(admins).setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            return DaoResult.<List<Admin>>create(null).setSuccess(false);
        }
    }

    /**
     * 模糊查詢
     * @param admin
     * @return DaoResult<List<Admin>>
     */
    @Override
    public DaoResult<List<Admin>> dynamicQuery(Admin admin) {
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

            List<Admin> admins = query.list();
            return DaoResult.create(admins).setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            return DaoResult.<List<Admin>>create(null).setSuccess(false);
        }
    }

    /**
     * 根據姓名查詢管理員
     * @param adminName
     * @return DaoResult<List<Admin>>
     */
    @Override
    public DaoResult<List<Admin>> getAdminByName(String adminName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Admin WHERE adminName LIKE :adminName";
            List<Admin> admins = session.createQuery(hql, Admin.class)
                                        .setParameter("adminName", "%" + adminName + "%")
                                        .list();
            return DaoResult.create(admins).setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            return DaoResult.<List<Admin>>create(null).setSuccess(false);
        }
    }

    /**
     * 根據ID獲取管理員
     * @param adminId
     * @return DaoResult<Admin>
     */
    @Override
    public DaoResult<Admin> getAdminById(Integer adminId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Admin admin = session.get(Admin.class, adminId);
            return DaoResult.create(admin).setSuccess(admin != null);
        } catch (Exception e) {
            e.printStackTrace();
            return DaoResult.<Admin>create(null).setSuccess(false);
        }
    }

    /**
     * 添加管理員
     * @param admin
     * @return DaoResult<Integer>
     */
    @Override
    public DaoResult<Integer> addAdmin(Admin admin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(admin);
            transaction.commit();
            return DaoResult.create(admin.getAdminId()).setSuccess(true);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return DaoResult.<Integer>create(null).setSuccess(false);
        }
    }

    /**
     * 軟刪除管理員
     * @param adminId
     * @return DaoResult<Void>
     */
    @Override
    public DaoResult<?> softremoveAdminById(Integer adminId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Admin admin = session.get(Admin.class, adminId);
            if (admin != null) {
                admin.setAdminStatus(0); // 设置状态为 0 表示软删除
                session.merge(admin);
            }
            transaction.commit();
            return DaoResult.<Void>create(null).setSuccess(true);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return DaoResult.<Void>create(null).setSuccess(false);
        }
    }

    /**
     * 更新管理員
     * @param admin
     * @return DaoResult<Void>
     */
    @Override
    public DaoResult<?> updateAdmin(Admin admin) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(admin);
            transaction.commit();
            return DaoResult.<Void>create(null).setSuccess(true);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return DaoResult.<Void>create(null).setSuccess(false);
        }
    }

    /**
     * 根據帳號和密碼查詢管理員
     * @param adminAccount
     * @param adminPassword
     * @return DaoResult<Admin>
     */
    @Override
    public DaoResult<Admin> getAdminByAccountAndPassword(String adminAccount, String adminPassword) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Admin WHERE adminAccount = :adminAccount AND adminPassword = :adminPassword";
            Admin admin = session.createQuery(hql, Admin.class)
                                 .setParameter("adminAccount", adminAccount)
                                 .setParameter("adminPassword", adminPassword)
                                 .uniqueResult();
            return DaoResult.create(admin).setSuccess(admin != null);
        } catch (Exception e) {
            e.printStackTrace();
            return DaoResult.<Admin>create(null).setSuccess(false);
        }
    }

    /**
     * 檢查帳號是否存在
     * @param adminAccount
     * @return DaoResult<Admin>
     */
    @Override
    public DaoResult<Admin> existsByAccount(String adminAccount) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Admin WHERE adminAccount = :adminAccount";
            Admin admin = session.createQuery(hql, Admin.class)
                                 .setParameter("adminAccount", adminAccount)
                                 .uniqueResult();
            return DaoResult.create(admin).setSuccess(admin != null);
        } catch (Exception e) {
            e.printStackTrace();
            return DaoResult.<Admin>create(null).setSuccess(false);
        }
    }
}
