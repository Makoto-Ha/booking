package com.booking.dao.attraction.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.booking.bean.pojo.attraction.Attraction;
import com.booking.dao.attraction.AttractionDao;
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
public class AttractionDaoImpl implements AttractionDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 獲取所有景點
	 * 
	 * @return
	 */
	@Override
	public DaoResult<List<Attraction>> getAttractionAll(Integer page) {
		String jpql = "FROM Attraction";
		TypedQuery<Attraction> query = entityManager.createQuery(jpql, Attraction.class);
		
		query.setFirstResult((page - 1) * 10);
		query.setMaxResults(10);
		List<Attraction> attractions = query.getResultList();
		return DaoResult.create(attractions).setSuccess(attractions != null);
	}
	
	
	/**
	 * 根據景點名稱獲取景點
	 * @param attractionName
	 * @return
	 */
	@Override
	public DaoResult<List<Attraction>> getAttractionsByName(String attractionName) {
		String jpql = "FROM Attraction WHERE attractionName LIKE :attractionName";
		TypedQuery<Attraction> query = entityManager.createQuery(jpql, Attraction.class);
		query.setParameter("attractionName", "%" + attractionName + "%");
		List<Attraction> attractions = query.getResultList();
		return DaoResult.create(attractions).setSuccess(attractions != null);
	}

	/**
	 * 模糊查詢
	 * 
	 * @param 
	 * @return
	 */
	@Override
	public DaoResult<List<Attraction>> dynamicQuery(Attraction attraction, Map<String, Object> extraValues) {
		String attractionName = attraction.getAttractionName();
		String attractionCity = attraction.getAttractionCity();
		String address = attraction.getAddress();
		String openingHour = attraction.getOpeningHour();
		String attractionType = attraction.getAttractionType();
		String attractionDescription = attraction.getAttractionDescription();
		Integer page = (Integer) extraValues.get("switchPage");
		String attrOrderBy = (String) extraValues.get("attrOrderBy");
		String selectedSort = (String) extraValues.get("selectedSort");
		// 透過session創建CriteriaBuilder
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		// 創建CriteriaQuery查詢，分兩個Attraction.class是因為root不能共用
		CriteriaQuery<Attraction> cq = cb.createQuery(Attraction.class);
		Root<Attraction> root = cq.from(Attraction.class);
		
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Attraction> countRoot = countQuery.from(Attraction.class);
		
		// where數據篩選
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> countPredicates = new ArrayList<>();
		
		if (attractionName != null && !attractionName.isEmpty()) {
			predicates.add(cb.like(root.get("attractionName"), "%" + attractionName + "%"));
			countPredicates.add(cb.like(countRoot.get("attractionName"), "%" + attractionName + "%"));
		}
		
		if (attractionCity != null && !attractionCity.isEmpty()) {
			predicates.add(cb.like(root.get("attractionCity"), "%" + attractionCity + "%"));
			countPredicates.add(cb.like(countRoot.get("attractionCity"), "%" + attractionCity + "%"));
		}
		
		if (address != null && !address.isEmpty()) {
			predicates.add(cb.like(root.get("address"), "%" + address + "%"));
			countPredicates.add(cb.like(countRoot.get("address"), "%" + address + "%"));
		}
		
		if (openingHour != null && !openingHour.isEmpty()) {
			predicates.add(cb.like(root.get("openingHour"), "%" + openingHour + "%"));
			countPredicates.add(cb.like(countRoot.get("openingHour"), "%" + openingHour + "%"));
		}
		
		if (attractionType != null && !attractionType.isEmpty()) {
			predicates.add(cb.like(root.get("attractionType"), "%" + attractionType + "%"));
			countPredicates.add(cb.like(countRoot.get("attractionType"), "%" + attractionType + "%"));
		}
		
		if (attractionDescription != null && !attractionDescription.isEmpty()) {
			predicates.add(cb.like(root.get("attractionDescription"), "%" + attractionDescription + "%"));
			countPredicates.add(cb.like(countRoot.get("attractionDescription"), "%" + attractionDescription + "%"));
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
		TypedQuery<Attraction> query = entityManager.createQuery(cq);
		
		// 分頁
		if(page != null) {
			query.setFirstResult((page - 1) * 10);		
		}else {
			query.setFirstResult(0);
		}
		query.setMaxResults(10);
		
		// 透過session查詢獲得結果
		List<Attraction> attractions = query.getResultList();
		
		return DaoResult.create(attractions).setSuccess(attractions != null).setExtraData("totalCounts", totalCounts);
	}


	/**
	 * 添加景點
	 * @param attraction
	 * @return
	 */
	@Override
	public DaoResult<?> addAttraction(Attraction attraction) {
		entityManager.persist(attraction);
		Integer attractionId = attraction.getAttractionId();
		return DaoResult.create().setGeneratedId(attractionId).setSuccess(attractionId != null);
	}

	/**
	 * 根據Id移除景點
	 * @param attactionId
	 * @return
	 */
	@Override
	public DaoResult<?> removeAttractionById(Integer attractionId) {
		Attraction attraction = entityManager.find(Attraction.class, attractionId);
		if(attraction != null) {
			entityManager.remove(attraction);
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
	public DaoResult<?> updateAttraction(Attraction attraction) {
		Attraction updatedAttraction = entityManager.merge(attraction);
		return DaoResult.create().setSuccess(updatedAttraction != null);
	}


	@Override
	public DaoResult<List<Attraction>> getattractionByName(String attractionName) {
		
		return null;
	}


	@Override
	public DaoResult<Attraction> getAttractionById(Integer attractionId) {
		
		return null;
	}







}
