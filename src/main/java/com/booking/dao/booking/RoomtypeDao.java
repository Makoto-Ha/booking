package com.booking.dao.booking;

import java.util.List;
import java.util.Map;

import com.booking.bean.pojo.booking.Roomtype;
import com.booking.utils.DaoResult;

public interface RoomtypeDao {

	/**
	 * 獲取所有房間類型
	 * 
	 * @return
	 */
	DaoResult<List<Roomtype>> getRoomtypeAll(Integer page);

	/**
	 * 獲取房間類型數量
	 * @return
	 */
	DaoResult<Long> getTotalCounts();
	
	/**
	 * 根據房間類型名稱獲取房間類型
	 * @param name
	 * @return
	 */
	DaoResult<List<Roomtype>> getRoomtypesByName(String roomtypeName);

	/**
	 * 模糊查詢
	 * 
	 * @param roomtype
	 * @return
	 */
	DaoResult<List<Roomtype>> dynamicQuery(Roomtype roomtype, Map<String, Object> extraValues);

	/**
	 * 添加房間類型
	 * 
	 * @param roomtype
	 * @return
	 */
	DaoResult<?> addRoomtype(Roomtype roomtype);

	/**
	 * 根據roomtypeId移除房間類型
	 * 
	 * @param roomtypeId
	 * @return
	 */
	DaoResult<?> removeRoomtypeById(Integer roomtypeId);

	/**
	 * 更新房間類型
	 * 
	 * @param roomtype
	 * @return
	 */
	DaoResult<?> updateRoomtype(Roomtype roomtype);

}