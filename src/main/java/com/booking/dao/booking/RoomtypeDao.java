package com.booking.dao.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.booking.bean.booking.Roomtype;
import com.booking.utils.util.DaoResult;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class RoomtypeDao {
	private Session session;

	public RoomtypeDao(Session session) {
		this.session = session;
	}

	/**
	 * 獲取所有房間類型
	 * 
	 * @return
	 */
	public DaoResult<List<Roomtype>> getRoomtypeAll(Integer page) {
		String hql = "FROM Roomtype rt ORDER BY rt.roomtypePrice";
		Query<Roomtype> query = session.createQuery(hql, Roomtype.class);
		
		query.setFirstResult((page - 1) * 10);
		query.setMaxResults(10);
		List<Roomtype> roomtypes = query.getResultList();
		return DaoResult.create(roomtypes).setSuccess(roomtypes != null);
	}

	public DaoResult<Integer> getTotalCounts() {
		String sql = "SELECT COUNT(*) FROM roomtype";
		Query<Integer> query = session.createNativeQuery(sql, Integer.class);
		Integer totalCounts = query.getSingleResult();
		return DaoResult.create(totalCounts).setSuccess(totalCounts != null);
	}

	/**
	 * 模糊查詢
	 * 
	 * @param roomtype
	 * @return
	 */
	public DaoResult<List<Roomtype>> dynamicQuery(Roomtype roomtype, Map<String, Object> extraValues) {
		String roomtypeName = roomtype.getRoomtypeName();
		Integer roomtypeCapacity = roomtype.getRoomtypeCapacity();
		Integer roomtypePrice = roomtype.getRoomtypePrice();
		Integer roomtypeQuantity = roomtype.getRoomtypeQuantity();
		String roomtypeDescription = roomtype.getRoomtypeDescription();
		String roomtypeAddress = roomtype.getRoomtypeAddress();
		String roomtypeCity = roomtype.getRoomtypeCity();
		String roomtypeDistrict = roomtype.getRoomtypeDistrict();
		Integer minMoney = (Integer) extraValues.get("minMoney");
		Integer maxMoney = (Integer) extraValues.get("maxMoney");
		Integer page = (Integer) extraValues.get("switchPage");
		String attrOrderBy = (String) extraValues.get("attrOrderBy");
		String selectedSort = (String) extraValues.get("selectedSort");
		// 透過session創建CriteriaBuilder
		CriteriaBuilder cb = session.getCriteriaBuilder();
		// 創建CriteriaQuery查詢，分兩個Roomtype.class是因為root不能共用
		CriteriaQuery<Roomtype> cq = cb.createQuery(Roomtype.class);
		Root<Roomtype> root = cq.from(Roomtype.class);
		
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Roomtype> countRoot = countQuery.from(Roomtype.class);
		
		// where數據篩選
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> countPredicates = new ArrayList<>();
		
		if (roomtypeName != null) {
			predicates.add(cb.like(root.get("roomtypeName"), "%" + roomtypeName + "%"));
			countPredicates.add(cb.like(countRoot.get("roomtypeName"), "%" + roomtypeName + "%"));
		}

		if (roomtypeCapacity != null) {
			predicates.add(cb.equal(root.get("roomtypeCapacity"), roomtypeCapacity));
			countPredicates.add(cb.equal(countRoot.get("roomtypeCapacity"), roomtypeCapacity));
		}

		if (roomtypePrice != null) {
			predicates.add(cb.equal(root.get("roomtypePrice"), roomtypePrice));
			countPredicates.add(cb.equal(countRoot.get("roomtypePrice"), roomtypePrice));
		}

		if (roomtypeQuantity != null) {
			predicates.add(cb.ge(root.get("roomtypeQuantity"), roomtypeQuantity));
			countPredicates.add(cb.ge(countRoot.get("roomtypeQuantity"), roomtypeQuantity));
		}

		if (roomtypeDescription != null) {
			predicates.add(cb.like(root.get("roomtypeDescription"), "%" + roomtypeDescription + "%"));
			countPredicates.add(cb.like(countRoot.get("roomtypeDescription"), roomtypeDescription));
		}

		if (roomtypeAddress != null) {
			predicates.add(cb.like(root.get("roomtypeAddress"), "%" + roomtypeAddress + "%"));
			countPredicates.add(cb.like(countRoot.get("roomtypeAddress"), "%" + roomtypeAddress + "%"));
		}

		if (roomtypeCity != null) {
			predicates.add(cb.like(root.get("roomtypeCity"), roomtypeCity));
			countPredicates.add(cb.like(countRoot.get("roomtypeCity"), roomtypeCity));
		}

		if (roomtypeDistrict != null) {
			predicates.add(cb.like(root.get("roomtypeDistrict"), roomtypeDistrict));
			countPredicates.add(cb.like(countRoot.get("roomtypeDistrict"), roomtypeDistrict));
		}

		if (minMoney != null) {
			if (maxMoney != null) {
				predicates.add(cb.between(root.get("roomtypePrice"), minMoney, maxMoney));
				countPredicates.add(cb.between(countRoot.get("roomtypePrice"), minMoney, maxMoney));
			} else {
				predicates.add(cb.ge(root.get("roomtypePrice"), minMoney));
				countPredicates.add(cb.ge(countRoot.get("roomtypePrice"), minMoney));
			}
		}else {
			if (maxMoney != null) {
				predicates.add(cb.le(root.get("roomtypePrice"), maxMoney));
				countPredicates.add(cb.le(countRoot.get("roomtypePrice"), maxMoney));
			}
		}
		
		// 查詢條件篩選的總數有多少(不包含分頁)
		countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
		// 創建session查詢獲得結果
		Long count = session.createQuery(countQuery).getSingleResult();
		
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
		Query<Roomtype> query = session.createQuery(cq);
		
		// 分頁
		if(page != null) {
			query.setFirstResult((page - 1) * 10);		
		}else {
			query.setFirstResult(0);
		}
		query.setMaxResults(10);
		
		// 透過session查詢獲得結果
		List<Roomtype> roomtypes = query.getResultList();
		
		return DaoResult.create(roomtypes).setSuccess(roomtypes != null).setExtraData("totalCounts", count);
	}

	/**
	 * 根據id獲取房間類型
	 * 
	 * @param roomtypeId
	 * @return
	 */
	public DaoResult<Roomtype> getRoomtypeById(Integer roomtypeId) {
		String hql = "FROM Roomtype rt WHERE rt.roomtypeId = :roomtypeId";
		Roomtype roomtype = session.createQuery(hql, Roomtype.class).setParameter("roomtypeId", roomtypeId).getSingleResult();
		return DaoResult.create(roomtype).setSuccess(roomtype != null);
	}

	/**
	 * 添加房間類型
	 * 
	 * @param roomtype
	 * @return
	 */
	public DaoResult<?> addRoomtype(Roomtype roomtype) {
		session.persist(roomtype);
		Integer roomtypeId = roomtype.getRoomtypeId();
		return DaoResult.create().setGeneratedId(roomtypeId).setSuccess(roomtypeId != null);
	}

	/**
	 * 根據roomtypeId移除房間類型
	 * 
	 * @param roomtypeId
	 * @return
	 */
	public DaoResult<?> removeRoomtypeById(Integer roomtypeId) {
		Roomtype roomtype = session.get(Roomtype.class, roomtypeId);
		if(roomtype != null) {
			session.remove(roomtype);
			return DaoResult.create().setSuccess(true);
		}
		return DaoResult.create().setSuccess(false);
	}

	/**
	 * 更新房間類型
	 * 
	 * @param roomtype
	 * @return
	 */
	public DaoResult<?> updateRoomtype(Roomtype roomtype) {
		Roomtype updatedRoomtype = session.merge(roomtype);
		return DaoResult.create().setSuccess(updatedRoomtype != null);
	}
}
