package com.booking.dao.attraction;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.bean.attraction.Attraction;
import com.booking.utils.util.DaoResult;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Repository
public class AttractionDaoImpl implements AttractionDao {

	@Autowired
	private SessionFactory sessionFactory;



	/**
	 * 獲取所有景點
	 * 
	 * @return
	 */
	@Override
	public DaoResult<List<Attraction>> getAttractionAll() {
		String hql = "From Attraction";
		Query<Attraction> query = sessionFactory.getCurrentSession().createQuery(hql, Attraction.class);
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
		String openingHour = attraction.getOpeningHour();
		String attractionType = attraction.getAttractionType();
		String attractionDescription = attraction.getAttractionDescription();

		// 透過session創建CriteriaBuilder
		CriteriaBuilder cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
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

		if (openingHour != null) {
			predicates.add(cb.like(root.get("openingHour"), "%" + openingHour + "%"));
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
		Query<Attraction> query = sessionFactory.getCurrentSession().createQuery(cq);

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
		Attraction attraction = sessionFactory.getCurrentSession().createQuery(hql, Attraction.class).setParameter("attractionId", attractionId)
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
		sessionFactory.getCurrentSession().persist(attraction);
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
		Attraction attraction = sessionFactory.getCurrentSession().get(Attraction.class, attractionId);
		if (attraction != null) {
			sessionFactory.getCurrentSession().remove(attraction);
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
		Attraction updatedAttraction = sessionFactory.getCurrentSession().merge(attraction);
		return DaoResult.create().setSuccess(updatedAttraction != null);
	}

}
