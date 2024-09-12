package com.booking.dao.attraction;

import java.util.List;

import com.booking.bean.attraction.Attraction;
import com.booking.utils.util.DaoResult;

public interface AttractionDao {

	/**
	 * 獲取所有景點
	 * 
	 * @return
	 */
	DaoResult<List<Attraction>> getAttractionAll();

	/**
	 * 模糊查詢
	 * 
	 * @param attraction
	 * @return
	 */
	DaoResult<List<Attraction>> dynamicQuery(Attraction attraction);

	/**
	 * 依id獲取景點
	 * 
	 * @param attractionId
	 * @return
	 */
	DaoResult<Attraction> getAttractionById(Integer attractionId);

	/**
	 * 新增景點
	 * 
	 * @param attraction
	 * @return
	 */
	DaoResult<?> addAttraction(Attraction attraction);

	/**
	 * 依id刪除景點
	 * 
	 * @param attractionId
	 */
	DaoResult<?> removeAddractionById(Integer attractionId);

	/**
	 * 更新景點
	 * 
	 * @param attraction
	 */
	DaoResult<?> updateAttraction(Attraction attraction);

}