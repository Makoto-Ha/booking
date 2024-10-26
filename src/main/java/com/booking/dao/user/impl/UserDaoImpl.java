package com.booking.dao.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.booking.bean.pojo.user.User;

import com.booking.dao.user.UserDao;
import com.booking.utils.DaoResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class UserDaoImpl implements UserDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 獲取所有景點
	 * 
	 * @return
	 */
	@Override
	public DaoResult<List<User>> getUserAll(Integer page) {
		String jpql = "FROM Users";
		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
		
		query.setFirstResult((page - 1) * 10);
		query.setMaxResults(10);
		List<User> users = query.getResultList();
		return DaoResult.create(users).setSuccess(users != null);
	}
	
	
	/**
	 * 根據景點名稱獲取景點
	 * @param attractionName
	 * @return
	 */
	@Override
	public DaoResult<List<User>> getUsersByName(String userName) {
		String jpql = "FROM User WHERE userName LIKE :userName";
		TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
		query.setParameter("userName", "%" + userName + "%");
		List<User> users = query.getResultList();
		return DaoResult.create(users).setSuccess(users != null);
	}

	/**
	 * 模糊查詢
	 * 
	 * @param 
	 * @return
	 */
	@Override
	public DaoResult<List<User>> dynamicQuery(User user, Map<String, Object> extraValues) {
		String userName = user.getUserName();
		String userAccount = user.getUserAccount();
		String userMail = user.getUserMail();
		String userAddress = user.getUserAddress();
		Integer page = (Integer) extraValues.get("switchPage");
		String attrOrderBy = (String) extraValues.get("attrOrderBy");
		String selectedSort = (String) extraValues.get("selectedSort");
		// 透過session創建CriteriaBuilder
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		// 創建CriteriaQuery查詢，分兩個Attraction.class是因為root不能共用
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<User> countRoot = countQuery.from(User.class);
		
		// where數據篩選
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> countPredicates = new ArrayList<>();
		
		if (userName != null && !userName.isEmpty()) {
			predicates.add(cb.like(root.get("userName"), "%" + userName + "%"));
			countPredicates.add(cb.like(countRoot.get("userName"), "%" + userName + "%"));
		}
		
		if (userAccount != null && !userAccount.isEmpty()) {
			predicates.add(cb.like(root.get("userAccount"), "%" + userAccount + "%"));
			countPredicates.add(cb.like(countRoot.get("userAccount"), "%" + userAccount + "%"));
		}
		
		if (userMail != null && !userMail.isEmpty()) {
			predicates.add(cb.like(root.get("userMail"), "%" + userMail + "%"));
			countPredicates.add(cb.like(countRoot.get("userMail"), "%" + userMail + "%"));
		}
		
		if (userAddress != null && !userAddress.isEmpty()) {
			predicates.add(cb.like(root.get("userAddress"), "%" + userAddress + "%"));
			countPredicates.add(cb.like(countRoot.get("userAddress"), "%" + userAddress + "%"));
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
		TypedQuery<User> query = entityManager.createQuery(cq);
		
		// 分頁
		if(page != null) {
			query.setFirstResult((page - 1) * 10);		
		}else {
			query.setFirstResult(0);
		}
		query.setMaxResults(10);
		
		// 透過session查詢獲得結果
		List<User> users = query.getResultList();
		
		return DaoResult.create(users).setSuccess(users != null).setExtraData("totalCounts", totalCounts);
	}


	/**
	 * 添加景點
	 * @param attraction
	 * @return
	 */
	@Override
	public DaoResult<?> addUser(User user) {
		entityManager.persist(user);
		Integer userId = user.getUserId();
		return DaoResult.create().setGeneratedId(userId).setSuccess(userId != null);
	}

	/**
	 * 根據Id移除景點
	 * @param attactionId
	 * @return
	 */
	@Override
	public DaoResult<?> removeUserById(Integer userId) {
		User user = entityManager.find(User.class, userId);
		if(user != null) {
			entityManager.remove(user);
			return DaoResult.create().setSuccess(true);
		}
		return DaoResult.create().setSuccess(false);
	}

	/**
	 * 更新景點
	 * 
	 * @param roomtype
	 * @return
	 */
	@Override
	public DaoResult<?> updateUser(User user) {
		User updatedUser = entityManager.merge(user);
		return DaoResult.create().setSuccess(updatedUser != null);
	}


	


	@Override
	public DaoResult<List<User>> getuserByName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public DaoResult<User> getUserById(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
