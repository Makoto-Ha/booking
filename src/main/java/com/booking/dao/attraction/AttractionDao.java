package com.booking.dao.attraction;

import java.util.ArrayList;
import java.util.List;

import com.booking.bean.attraction.Attraction;
import com.booking.mapper.AttractionRowMapper;
import com.booking.utils.DaoResult;
import com.booking.utils.DaoUtils;

public class AttractionDao {

	
	/**
	 * 獲取所有景點
	 * @return
	 */
	public DaoResult<Attraction> getAttractionAll() {
		String sql = "SELECT * FROM attraction";
		return DaoUtils.commonsQuery(sql, new AttractionRowMapper());
	}
	
	
	/**
	 * 模糊查詢
	 * @param attraction
	 * @return
	 */
	public DaoResult<Attraction> dynamicQuery(Attraction attraction) {
		StringBuilder sql = new StringBuilder("SELECT * FROM attraction WHERE 1=1");
		List<Object> list = new ArrayList<>();
		String attractionName = attraction.getAttractionName();
		String attractionCity = attraction.getAttractionCity();
		String address = attraction.getAddress();
		String openingHours = attraction.getOpeningHour();
		String attractionType = attraction.getAttractionType();
		String attractionDescription = attraction.getAttractionDescription();
		
		if(attractionName != null) {
			sql.append(" AND attraction_name LIKE ?");
			list.add("%" + attractionName + "%");
		}
		
		if(attractionCity != null) {
			sql.append(" AND attraction_city LIKE ?");
			list.add("%" + attractionCity + "%");
		}
		
		if(address != null) {
			sql.append(" AND address LIKE ?");
			list.add("%" + address + "%");
		}
		
		if(openingHours != null) {
			sql.append(" AND opening_hours LIKE ?");
			list.add("%"+ openingHours + "%");
		}
		
		if(attractionType != null) {
			sql.append(" AND attraction_type LIKE ?");
			list.add("%" + attractionType + "%");
		}
		
		if(attractionDescription != null) {
			sql.append(" AND attraction_description LIKE ?");
			list.add("%" + attractionDescription + "%");
		}
		

		
		return DaoUtils.commonsQuery(sql.toString(), new AttractionRowMapper(), list.toArray());
	}
	
	
	/**
	 * 依id獲取景點
	 * @param attractionId
	 * @return
	 */
	public DaoResult<Attraction> getAttractionById(Integer attractionId) {
		String sql = "SELECT * FROM attraction WHERE attraction_id = ?";
		return DaoUtils.commonsQuery(sql, new AttractionRowMapper(), attractionId);

	}
	
		
	/**
	 * 新增景點
	 * @param attraction
	 * @return
	 */
	public DaoResult<Integer> addAttraction(Attraction attraction) {
		String sql = "INSERT INTO attraction (attraction_name, attraction_city, address, opening_hours,"
				+ " attraction_type, attraction_description) VALUES(?,?,?,?,?,?)";
		return DaoUtils.commonsUpdate(
				sql, 
				attraction.getAttractionName(),attraction.getAttractionCity(),
				attraction.getAddress(),attraction.getOpeningHour(),
				attraction.getAttractionType(),attraction.getAttractionDescription());
	}
	
	
	/**
	 * 依id刪除景點
	 * @param attractionId
	 */
	public DaoResult<Integer> removeAddractionById(Integer attractionId) {
		String sql = "DELETE FROM attraction WHERE attraction_id= ?";
		return DaoUtils.commonsUpdate(sql, attractionId);
	}
	
	
	/**
	 * 更新景點
	 * @param attraction
	 */
	public DaoResult<Integer> updateAttraction(Attraction attraction) {
		String sql = "UPDATE attraction SET attraction_name=?, attraction_city=?,address=?, opening_hours=?,"
				+ " attraction_type=?, attraction_description=? WHERE attraction_id=?";
		return DaoUtils.commonsUpdate(
				sql, attraction.getAttractionName(), attraction.getAttractionCity(),
				attraction.getAddress(), attraction.getOpeningHour(),
				attraction.getAttractionType(),attraction.getAttractionDescription(),
				attraction.getAttractionId());	
	}


	}

