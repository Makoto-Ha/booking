package com.booking.dao.admin.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import com.booking.bean.pojo.admin.*;
import com.booking.dao.admin.AdminDao;
import com.booking.utils.DaoResult;

@Repository
public class AdminDaoImpl implements AdminDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DaoResult<List<Admin>> getAdminAll(Integer page) {
        String jpql = "FROM Admin";
        TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);
        List<Admin> admins = query.getResultList();
        return DaoResult.create(admins).setSuccess(admins != null);
    }

    @Override
    public DaoResult<List<Admin>> dynamicQuery(Admin admin) {
        StringBuilder jpql = new StringBuilder("SELECT a FROM Admin a WHERE 1=1");

        if (admin.getAdminId() != null) {
            jpql.append(" AND a.adminId = :adminId");
        }
        if (admin.getAdminAccount() != null && !admin.getAdminAccount().isEmpty()) {
            jpql.append(" AND a.adminAccount LIKE :adminAccount");
        }
        if (admin.getAdminName() != null && !admin.getAdminName().isEmpty()) {
            jpql.append(" AND a.adminName LIKE :adminName");
        }
        if (admin.getAdminMail() != null && !admin.getAdminMail().isEmpty()) {
            jpql.append(" AND a.adminMail LIKE :adminMail");
        }
        if (admin.getHiredate() != null) {
            jpql.append(" AND a.hiredate = :hiredate");
        }
        if (admin.getAdminStatus() != null) {
            jpql.append(" AND a.adminStatus = :adminStatus");
        }

        TypedQuery<Admin> query = entityManager.createQuery(jpql.toString(), Admin.class);

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

        List<Admin> admins = query.getResultList();
        return DaoResult.create(admins).setSuccess(true);
    }

    @Override
    public DaoResult<List<Admin>> getAdminByName(String adminName) {
        String jpql = "SELECT a FROM Admin a WHERE a.adminName LIKE :adminName";
        TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class)
                .setParameter("adminName", "%" + adminName + "%");
        List<Admin> admins = query.getResultList();
        return DaoResult.create(admins).setSuccess(true);
    }

    @Override
    public DaoResult<Admin> getAdminById(Integer adminId) {
        Admin admin = entityManager.find(Admin.class, adminId);
        return DaoResult.create(admin).setSuccess(admin != null);
    }

    @Override
    public DaoResult<Integer> addAdmin(Admin admin) {
        entityManager.persist(admin);
        return DaoResult.create(admin.getAdminId()).setSuccess(true);
    }

    @Override
    public DaoResult<?> softremoveAdminById(Integer adminId) {
        Admin admin = entityManager.find(Admin.class, adminId);
        if (admin != null) {
            admin.setAdminStatus(0); // 設置狀態為 0 表示軟刪除
            entityManager.merge(admin);
        }
        return DaoResult.create(null).setSuccess(true);
    }

    @Override
    public DaoResult<?> updateAdmin(Admin admin) {
        entityManager.merge(admin);
        return DaoResult.create(null).setSuccess(true);
    }

    @Override
    public DaoResult<Admin> getAdminByAccountAndPassword(String adminAccount, String adminPassword) {
        String jpql = "SELECT a FROM Admin a WHERE a.adminAccount = :adminAccount AND a.adminPassword = :adminPassword";
        TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class)
                .setParameter("adminAccount", adminAccount)
                .setParameter("adminPassword", adminPassword);
        List<Admin> results = query.getResultList();
        Admin admin = results.isEmpty() ? null : results.get(0);
        return DaoResult.create(admin).setSuccess(admin != null);
    }

    @Override
    public DaoResult<Admin> existsByAccount(String adminAccount) {
        String jpql = "SELECT a FROM Admin a WHERE a.adminAccount = :adminAccount";
        TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class)
                .setParameter("adminAccount", adminAccount);
        List<Admin> results = query.getResultList();
        Admin admin = results.isEmpty() ? null : results.get(0);
        return DaoResult.create(admin).setSuccess(admin != null);
    }
}