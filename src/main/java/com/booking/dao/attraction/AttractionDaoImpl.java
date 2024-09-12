package com.booking.dao.attraction;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.booking.bean.attraction.Attraction;
import com.booking.utils.util.DaoResult;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AttractionDaoImpl implements AttractionDao {

	private Session session;

	public AttractionDaoImpl(Session session) {
		this.session = session;
	}

	/**
	 * 獲取所有景點
	 * 
	 * @return
	 */
	@Override
	public DaoResult<List<Attraction>> getAttractionAll() {
		String hql = "From Attraction";
		Query<Attraction> query = session.createQuery(hql, Attraction.class);
		List<Attraction> attractions = query.getResultList();
		return DaoResult.create(attractions).setSuccess(attractions != null);
	}

	/**
	 * 模糊查詢
	 * 
	 * @param attraction
	 * @return
	 */
	@Override
	public DaoResult<List<Attraction>> dynamicQuery(Attraction attraction) {
		String attractionName = attraction.getAttractionName();
		String attractionCity = attraction.getAttractionCity();
		String address = attraction.getAddress();
		String openingHours = attraction.getOpeningHour();
		String attractionType = attraction.getAttractionType();
		String attractionDescription = attraction.getAttractionDescription();

		// 透過session創建CriteriaBuilder
		CriteriaBuilder cb = session.getCriteriaBuilder();
		// 創建CriteriaQuery查詢
		CriteriaQuery<Attraction> cq = cb.createQuery(Attraction.class);
		Root<Attraction> root = cq.from(Attraction.class);

		// where數據篩選
		List<Predicate> predicates = new ArrayList<>();

		if (attractionName != null) {
			predicates.add(cb.like(root.get("attractionName"), "%" + attractionName + "%"));
		}

		if (attractionCity != null) {
			predicates.add(cb.like(root.get("attractionCity"), "%" + attractionCity + "%"));
		}

		if (address != null) {
			predicates.add(cb.like(root.get("address"), "%" + address + "%"));
		}

		if (openingHours != null) {
			predicates.add(cb.like(root.get("openingHours"), "%" + openingHours + "%"));
		}

		if (attractionType != null) {
			predicates.add(cb.like(root.get("attractionType"), "%" + attractionType + "%"));
		}

		if (attractionDescription != null) {
			predicates.add(cb.like(root.get("attractionDescription"), "%" + attractionDescription + "%"));
		}

		// 篩選條件
		cq.select(root).where(predicates.toArray(new Predicate[0]));

		// 創建session查詢
		Query<Attraction> query = session.createQuery(cq);

		// 透過session查詢獲得結果
		List<Attraction> attractions = query.getResultList();

		return DaoResult.create(attractions).setSuccess(attractions != null);
	}

	/**
	 * 依id獲取景點
	 * 
	 * @param attractionId
	 * @return
	 */
	@Override
	public DaoResult<Attraction> getAttractionById(Integer attractionId) {
		String hql = "FROM Attraction WHERE attractionId = :attractionId";
		Attraction attraction = session.createQuery(hql, Attraction.class).setParameter("attractionId", attractionId)
				.getSingleResult();
		return DaoResult.create(attraction).setSuccess(attraction != null);
	}

	/**
	 * 新增景點
	 * 
	 * @param attraction
	 * @return
	 */
	@Override
	public DaoResult<?> addAttraction(Attraction attraction) {
		session.persist(attraction);
		Integer attractionId = attraction.getAttractionId();
		return DaoResult.create().setGeneratedId(attractionId).setSuccess(attractionId != null);
	}

	/**
	 * 依id刪除景點
	 * 
	 * @param attractionId
	 */
	@Override
	public DaoResult<?> removeAddractionById(Integer attractionId) {
		Attraction attraction = session.get(Attraction.class, attractionId);
		if (attraction != null) {
			session.remove(attraction);
			return DaoResult.create().setSuccess(true);
		}
		return DaoResult.create().setSuccess(false);
	}

	/**
	 * 更新景點
	 * 
	 * @param attraction
	 */
	@Override
	public DaoResult<?> updateAttraction(Attraction attraction) {
		Attraction updatedAttraction = session.merge(attraction);
		return DaoResult.create().setSuccess(updatedAttraction != null);
	}

}
