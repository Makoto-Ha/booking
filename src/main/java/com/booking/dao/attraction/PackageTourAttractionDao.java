package com.booking.dao.attraction;

import java.util.List;

import com.booking.bean.pojo.attraction.Attraction;
import com.booking.bean.pojo.attraction.PackageTourAttraction;
import com.booking.utils.DaoResult;

public interface PackageTourAttractionDao {
	
    /**
     * 新增景點到套裝行程 
     * @param packageTourAttraction
     * @return
     */
    DaoResult<PackageTourAttraction> addAttractionToPackage(PackageTourAttraction packageTourAttraction);

    /**
     * 根據套裝行程取得相關的景點 
     * @param tourId
     * @return
     */
    DaoResult<List<Attraction>> getAttractionsByPackageTourId(Integer tourId);

    /**
     * 從套裝行程中刪除景點 
     * @param tourId
     * @param attractionId
     * @return
     */
    DaoResult<?> removeAttractionFromPackage(Integer tourId, Integer attractionId);

    /**
     * 更新套裝行程中的景點 
     * @param packageTourAttraction
     * @return
     */
    DaoResult<PackageTourAttraction> updateAttractionInPackage(PackageTourAttraction packageTourAttraction);
}

