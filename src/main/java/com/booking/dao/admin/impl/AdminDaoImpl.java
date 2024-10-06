package com.booking.dao.admin.impl;

<<<<<<< HEAD
<<<<<<< HEAD
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import com.booking.bean.pojo.admin.*;
import com.booking.bean.pojo.attraction.Attraction;
=======
=======
import java.util.ArrayList;
>>>>>>> c80c8df (黃振瑋.修改:properties跟controller (#40))
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import com.booking.bean.pojo.admin.*;
<<<<<<< HEAD
>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
=======
import com.booking.bean.pojo.attraction.Attraction;
>>>>>>> c80c8df (黃振瑋.修改:properties跟controller (#40))
import com.booking.dao.admin.AdminDao;
import com.booking.utils.DaoResult;

@Repository
public class AdminDaoImpl implements AdminDao {

    @PersistenceContext
    private EntityManager entityManager;

<<<<<<< HEAD
<<<<<<< HEAD
    /**
     *獲得所有管理員
     */
    @Override
    public DaoResult<List<Admin>> getAdminAll(Integer page) {
        String jpql = "FROM Admin";
        TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);       
        query.setFirstResult((page - 1) * 10);
		query.setMaxResults(10);
        List<Admin> admins = query.getResultList();
        return DaoResult.create(admins).setSuccess(admins != null);
    }
    	
    
	/**
	 * 根據管理員名稱獲取管理員
	 * @param adminName
	 * @return
	 */
    @Override
	public DaoResult<List<Admin>> getAdminsByName(String adminName) {
		String jpql = "FROM Admin WHERE adminName LIKE :adminName";
		TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);
		query.setParameter("adminName", "%" + adminName + "%");
		List<Admin> admins = query.getResultList();
		return DaoResult.create(admins).setSuccess(admins != null);
	}
	
	/**
	 * 模糊查詢
	 * 
	 * 
	 * @return
	 */
	@Override
	public DaoResult<List<Admin>> dynamicQuery(Admin admin, Map<String, Object> extraValues) {
		String adminAcount = admin.getAdminAccount();
		String adminName = admin.getAdminName();
		String adminMail = admin.getAdminMail();
		Integer adminStatus = admin.getAdminStatus();	
		Integer page = (Integer) extraValues.get("switchPage");
		String attrOrderBy = (String) extraValues.get("attrOrderBy");
		String selectedSort = (String) extraValues.get("selectedSort");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();	
		CriteriaQuery<Admin> cq = cb.createQuery(Admin.class);
		Root<Admin> root = cq.from(Admin.class);
		
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Admin> countRoot = countQuery.from(Admin.class);			
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> countPredicates = new ArrayList<>();
		
		if (adminAcount != null && !adminAcount.isEmpty()) {
			predicates.add(cb.like(root.get("adminAcount"), "%" + adminAcount + "%"));
			countPredicates.add(cb.like(countRoot.get("adminAcount"), "%" + adminAcount + "%"));
		}
		
		if (adminName != null && !adminName.isEmpty()) {
			predicates.add(cb.like(root.get("adminName"), "%" + adminName + "%"));
			countPredicates.add(cb.like(countRoot.get("adminName"), "%" + adminName + "%"));
		}
		
		if (adminMail != null && !adminMail.isEmpty()) {
			predicates.add(cb.like(root.get("adminMail"), "%" + adminMail + "%"));
			countPredicates.add(cb.like(countRoot.get("adminMail"), "%" + adminMail + "%"));
		}
		
		if (adminStatus != null) {
			predicates.add(cb.like(root.get("adminStatus"), "%" + adminStatus + "%"));
			countPredicates.add(cb.like(countRoot.get("adminStatus"), "%" + adminStatus + "%"));
		}
		// 查詢條件篩選的總數有多少(不包含分頁)
		countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
		// 創建session查詢獲得結果
		Long totalCounts = entityManager.createQuery(countQuery).getSingleResult();		
		// 篩選條件
		cq.select(root).where(predicates.toArray(new Predicate[0]));				
		// 排序
		Order order;
		if(selectedSort.equals("asc")) {
			order = cb.asc(root.get(attrOrderBy));
		}else {
			order = cb.desc(root.get(attrOrderBy));
		}
		cq.orderBy(order);
		
		// 創建session查詢
		TypedQuery<Admin> query = entityManager.createQuery(cq);
		
		// 分頁
		if(page != null) {
			query.setFirstResult((page - 1) * 10);		
		}else {
			query.setFirstResult(0);
		}
		query.setMaxResults(10);
		
		// 透過session查詢獲得結果
		List<Admin> admins = query.getResultList();
		
		return DaoResult.create(admins).setSuccess(admins != null).setExtraData("totalCounts", totalCounts);
	}
	
	
	/**
	 *新增管理員
	 *
	 */
	@Override
	    public DaoResult<?> addAdmin(Admin admin) {
	        entityManager.persist(admin);
	        Integer adminId = admin.getAdminId();
			return DaoResult.create().setGeneratedId(adminId).setSuccess(adminId != null);
		}
	
	
	
    /**
     *軟刪除
     *
     */
=======
=======
    /**
     *獲得所有管理員
     */
>>>>>>> c80c8df (黃振瑋.修改:properties跟controller (#40))
    @Override
    public DaoResult<List<Admin>> getAdminAll(Integer page) {
        String jpql = "FROM Admin";
        TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);       
        query.setFirstResult((page - 1) * 10);
		query.setMaxResults(10);
        List<Admin> admins = query.getResultList();
        return DaoResult.create(admins).setSuccess(admins != null);
    }
<<<<<<< HEAD

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

>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
=======
    	
    
	/**
	 * 根據管理員名稱獲取管理員
	 * @param adminName
	 * @return
	 */
	public DaoResult<List<Admin>> getAdminsByName(String adminName) {
		String jpql = "FROM Admin WHERE adminName LIKE :adminName";
		TypedQuery<Admin> query = entityManager.createQuery(jpql, Admin.class);
		query.setParameter("adminName", "%" + adminName + "%");
		List<Admin> admins = query.getResultList();
		return DaoResult.create(admins).setSuccess(admins != null);
	}
	
	/**
	 * 模糊查詢
	 * 
	 * 
	 * @return
	 */
	@Override
	public DaoResult<List<Admin>> dynamicQuery(Admin admin, Map<String, Object> extraValues) {
		String adminAcount = admin.getAdminAccount();
		String adminName = admin.getAdminName();
		String adminMail = admin.getAdminMail();
		Integer adminStatus = admin.getAdminStatus();	
		Integer page = (Integer) extraValues.get("switchPage");
		String attrOrderBy = (String) extraValues.get("attrOrderBy");
		String selectedSort = (String) extraValues.get("selectedSort");
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();	
		CriteriaQuery<Admin> cq = cb.createQuery(Admin.class);
		Root<Admin> root = cq.from(Admin.class);
		
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Admin> countRoot = countQuery.from(Admin.class);			
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> countPredicates = new ArrayList<>();
		
		if (adminAcount != null && !adminAcount.isEmpty()) {
			predicates.add(cb.like(root.get("adminAcount"), "%" + adminAcount + "%"));
			countPredicates.add(cb.like(countRoot.get("adminAcount"), "%" + adminAcount + "%"));
		}
		
		if (adminName != null && !adminName.isEmpty()) {
			predicates.add(cb.like(root.get("adminName"), "%" + adminName + "%"));
			countPredicates.add(cb.like(countRoot.get("adminName"), "%" + adminName + "%"));
		}
		
		if (adminMail != null && !adminMail.isEmpty()) {
			predicates.add(cb.like(root.get("adminMail"), "%" + adminMail + "%"));
			countPredicates.add(cb.like(countRoot.get("adminMail"), "%" + adminMail + "%"));
		}
		
		if (adminStatus != null) {
			predicates.add(cb.like(root.get("adminStatus"), "%" + adminStatus + "%"));
			countPredicates.add(cb.like(countRoot.get("adminStatus"), "%" + adminStatus + "%"));
		}
		// 查詢條件篩選的總數有多少(不包含分頁)
		countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
		// 創建session查詢獲得結果
		Long totalCounts = entityManager.createQuery(countQuery).getSingleResult();		
		// 篩選條件
		cq.select(root).where(predicates.toArray(new Predicate[0]));				
		// 排序
		Order order;
		if(selectedSort.equals("asc")) {
			order = cb.asc(root.get(attrOrderBy));
		}else {
			order = cb.desc(root.get(attrOrderBy));
		}
		cq.orderBy(order);
		
		// 創建session查詢
		TypedQuery<Admin> query = entityManager.createQuery(cq);
		
		// 分頁
		if(page != null) {
			query.setFirstResult((page - 1) * 10);		
		}else {
			query.setFirstResult(0);
		}
		query.setMaxResults(10);
		
		// 透過session查詢獲得結果
		List<Admin> admins = query.getResultList();
		
		return DaoResult.create(admins).setSuccess(admins != null).setExtraData("totalCounts", totalCounts);
	}
	
	
	/**
	 *新增管理員
	 *
	 */
	@Override
	    public DaoResult<?> addAdmin(Admin admin) {
	        entityManager.persist(admin);
	        Integer adminId = admin.getAdminId();
			return DaoResult.create().setGeneratedId(adminId).setSuccess(adminId != null);
		}
	
	
	
    /**
     *軟刪除
     *
     */
>>>>>>> c80c8df (黃振瑋.修改:properties跟controller (#40))
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
<<<<<<< HEAD
<<<<<<< HEAD
    public DaoResult<Admin> getAdminById(Integer adminId) {
        Admin admin = entityManager.find(Admin.class, adminId);
        return DaoResult.create(admin).setSuccess(admin != null);
    }


    /**
     *更新
     *
     */
    @Override
    public DaoResult<?> updateAdmin(Admin admin) {
    	Admin updatedAdmin = entityManager.merge(admin);
		return DaoResult.create().setSuccess(updatedAdmin != null);
	}

    
    //////////////////////////////////////////////////////////////////////////////////
=======
    public DaoResult<?> updateAdmin(Admin admin) {
        entityManager.merge(admin);
        return DaoResult.create(null).setSuccess(true);
    }

>>>>>>> 1bfb762 (黃振瑋.修改:admin-changeto-springboot (#39))
=======
    public DaoResult<Admin> getAdminById(Integer adminId) {
        Admin admin = entityManager.find(Admin.class, adminId);
        return DaoResult.create(admin).setSuccess(admin != null);
    }


    /**
     *更新
     *
     */
    @Override
    public DaoResult<?> updateAdmin(Admin admin) {
    	Admin updatedAdmin = entityManager.merge(admin);
		return DaoResult.create().setSuccess(updatedAdmin != null);
	}

    
    //////////////////////////////////////////////////////////////////////////////////
>>>>>>> c80c8df (黃振瑋.修改:properties跟controller (#40))
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