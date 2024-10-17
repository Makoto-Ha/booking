package com.booking.dao.attraction.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.booking.bean.pojo.attraction.PackageTour;
import com.booking.dao.attraction.PackageTourDao;
import com.booking.utils.DaoResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Repository
public class PackageTourDaoImpl implements PackageTourDao{

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * 獲取所有套裝行程
	 * 
	 * @param page
	 * @return
	 */
	@Override
	public DaoResult<List<PackageTour>> getPackageTourAll(Integer page) {
		String jpql = "SELECT p FROM PackageTour p LEFT JOIN FETCH p.packageTourAttractions";
		TypedQuery<PackageTour> query = entityManager.createQuery(jpql, PackageTour.class);

		query.setFirstResult((page - 1) * 10);
		query.setMaxResults(10);

		List<PackageTour> packageTours = query.getResultList();
		return DaoResult.create(packageTours).setSuccess(packageTours != null);
	}

	/**
	 * 根據ID取得套裝行程
	 * 
	 * @param packageTourId
	 * @return
	 */
	@Override
	public DaoResult<PackageTour> getPackageTourById(Integer packageTourId) {
		PackageTour packageTour = entityManager.find(PackageTour.class, packageTourId);
		return DaoResult.create(packageTour).setSuccess(packageTour != null);
	}

	/**
	 * 新增套裝行程
	 * 
	 * @param packageTour
	 * @return
	 */
	@Override
	public DaoResult<?> addPackageTour(PackageTour packageTour) {
		entityManager.persist(packageTour);
		Integer packageTourId = packageTour.getPackageTourId();
		return DaoResult.create().setGeneratedId(packageTourId).setSuccess(packageTourId != null);
	}

	/**
	 * 根據ID移除套裝行程
	 * 
	 * @param packageTourId
	 * @return
	 */
	@Override
	public DaoResult<?> removePackageTourById(Integer packageTourId) {
		PackageTour packageTour = entityManager.find(PackageTour.class, packageTourId);
		if (packageTour != null) {
			entityManager.remove(packageTour);
			return DaoResult.create().setSuccess(true);
		}
		return DaoResult.create().setSuccess(false);
	}

	/**
	 * 更新套裝行程
	 * 
	 * @param packageTour
	 * @return
	 */
	@Override
	public DaoResult<?> updatePackageTour(PackageTour packageTour) {
		PackageTour updatedPackageTour = entityManager.merge(packageTour);
		return DaoResult.create(updatedPackageTour).setSuccess(updatedPackageTour != null);
	}
}
