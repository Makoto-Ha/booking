package com.booking.dao.attraction;

import java.util.List;

import com.booking.bean.pojo.attraction.PackageTour;
import com.booking.utils.DaoResult;

public interface PackageTourDao {

	/**
	 * 獲取所有套裝行程
	 * @param page
	 * @return
	 */
	DaoResult<List<PackageTour>> getPackageTourAll(Integer page);
	
	
	/**
	 * 根據ID獲取套裝行程
	 * @param tourId
	 * @return
	 */
	DaoResult<PackageTour> getPackageTourById(Integer tourId);
	
	
	/**
	 * 新增套裝行程
	 * @param packageTour
	 * @return
	 */
	DaoResult<?> addPackageTour(PackageTour packageTour);
	
	
	/**
	 * 依據ID移除套裝行程
	 * @param tourId
	 * @return
	 */
	DaoResult<?> removePackageTourById(Integer tourId);
	
	
	/**
	 * 更新套裝行程
	 * @param packageTour
	 * @return
	 */
	DaoResult<?> updatePackageTour(PackageTour packageTour);
	
	
}
