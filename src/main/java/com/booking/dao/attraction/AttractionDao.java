package com.booking.dao.attraction;

import java.util.List;
import java.util.Map;

import com.booking.bean.pojo.attraction.Attraction;
import com.booking.utils.DaoResult;

public interface AttractionDao {

	/**
	 * 獲取所有景點
	 * 
	 * @return
	 */
	DaoResult<List<Attraction>> getAttractionAll(Integer page);

	/**
	 * 模糊查詢
	 * 
	 * @param attraction
	 * @return
	 */
	DaoResult<List<Attraction>> dynamicQuery(Attraction attraction, Map<String, Object> extraValues);

	/**
	 * 依名稱獲取景點
	 * @param attractionName
	 * @return
	 */
	DaoResult<List<Attraction>> getattractionByName(String attractionName);
	
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
	DaoResult<?> removeAttractionById(Integer attractionId);

	/**
	 * 更新景點
	 * 
	 * @param attraction
	 */
	DaoResult<?> updateAttraction(Attraction attraction);

	/**
	 * 根據景點名稱獲取景點
	 * @param attractionName
	 * @return
	 */
	DaoResult<List<Attraction>> getAttractionsByName(String attractionName);



}