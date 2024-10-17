package com.booking.dao.attraction.impl;

import java.util.List;

import com.booking.bean.pojo.attraction.Attraction;
import com.booking.bean.pojo.attraction.PackageTourAttraction;
import com.booking.dao.attraction.PackageTourAttractionDao;
import com.booking.utils.DaoResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class PackageTourAttractionDaoImpl implements PackageTourAttractionDao{

    @PersistenceContext
    private EntityManager entityManager;
    
    
    /**
     * 新增景點到套裝行程
     * @param packageTourAttraction
     * @return
     */
    @Override
    public DaoResult<PackageTourAttraction> addAttractionToPackage(PackageTourAttraction packageTourAttraction) {
        entityManager.persist(packageTourAttraction);
        return DaoResult.create(packageTourAttraction).setSuccess(true);
    }
    
    
    /**
     * 根據套裝行程取得相關的景點 
     * @param tourId
     * @return
     */
    @Override
    public DaoResult<List<Attraction>> getAttractionsByPackageTourId(Integer tourId) {
        String jpql = "SELECT attraction FROM PackageTourAttraction  WHERE packageTour.packageTourId = :pacakgeTourId";
        TypedQuery<Attraction> query = entityManager.createQuery(jpql, Attraction.class);
        query.setParameter("pacakgeTourId", tourId);
        List<Attraction> attractions = query.getResultList();
        return DaoResult.create(attractions).setSuccess(attractions != null);
    }
    
    
    /**
     * 從套裝行程中刪除景點
     * @param tourId
     * @param attractionId
     * @return
     */
    @Override
    public DaoResult<?> removeAttractionFromPackage(Integer tourId, Integer attractionId) {
        String jpql = "DELETE FROM PackageTourAttraction WHERE packageTour.pacakgeTourId = :pacakgeTourId AND attraction.attractionId = :attractionId";
        Query query = entityManager.createQuery(jpql);
        query.setParameter("pacakgeTourId", tourId);
        query.setParameter("attractionId", attractionId);
        int rowsDeleted = query.executeUpdate();
        return DaoResult.create().setAffectedRows(rowsDeleted).setSuccess(rowsDeleted > 0);
    }
    
    
    /**
     * 更新套裝行程中的景點
     * @param packageTourAttraction
     * @return
     */
    @Override
    public DaoResult<PackageTourAttraction> updateAttractionInPackage(PackageTourAttraction packageTourAttraction) {
        PackageTourAttraction updatedAttraction = entityManager.merge(packageTourAttraction);
        return DaoResult.create(updatedAttraction).setSuccess(updatedAttraction != null);
    }
}
