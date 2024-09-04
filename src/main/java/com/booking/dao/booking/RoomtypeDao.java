package com.booking.dao.booking;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.booking.bean.booking.Roomtype;
import com.booking.dto.booking.RoomtypeDTO;
import com.booking.mapper.RoomtypeDTORowMapper;
import com.booking.mapper.RoomtypeRowMapper;
import com.booking.mapper.RowMapper;
import com.booking.utils.DaoResult;
import com.booking.utils.DaoUtils;

public class RoomtypeDao {
	/**
	 * 獲取所有房間類型
	 * 
	 * @return
	 */
	public DaoResult<Roomtype> getRoomtypeAll(Integer page) {
		String sql = "SELECT * FROM roomtype ORDER BY roomtype_price OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY";
		return DaoUtils.commonsQuery(sql, new RoomtypeRowMapper(), (page - 1) * 10); // 當前頁數-1再承10，跳過的數據
	}
	
	public DaoResult<Integer> getTotalCounts() {
		String sql = "SELECT COUNT(*) AS total_count FROM roomtype";
		return DaoUtils.commonsQuery(sql, new RowMapper<Integer>() {

			@Override
			public Integer getRow(ResultSet resultSet) {
				try {
					return resultSet.getInt("total_count");
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

	/**
	 * 模糊查詢
	 * 
	 * @param roomtype
	 * @return
	 */
	public DaoResult<RoomtypeDTO> dynamicQuery(Roomtype roomtype, Map<String, Object> extraValues) {
		StringBuilder sql = new StringBuilder("SELECT *, COUNT(*) OVER() as total_counts FROM roomtype WHERE 1=1");
		List<Object> list = new ArrayList<>();
		String roomtypeName = roomtype.getRoomtypeName();
		Integer roomtypeCapacity = roomtype.getRoomtypeCapacity();
		Integer roomtypePrice = roomtype.getRoomtypePrice();
		Integer roomtypeQuantity = roomtype.getRoomtypeQuantity();
		String roomtypeDescription = roomtype.getRoomtypeDescription();
		String roomtypeAddress = roomtype.getRoomtypeAddress();
		String roomtypeCity = roomtype.getRoomtypeCity();
		String roomtypeDistrict = roomtype.getRoomtypeDistrict();
		Integer minMoney = (Integer) extraValues.get("minMoney");
		Integer maxMoney = (Integer) extraValues.get("maxMoney");
		Integer page = (Integer) extraValues.get("switchPage");
		String attrOrderBy = (String) extraValues.get("attrOrderBy");
		String selectedSort = (String) extraValues.get("selectedSort");
		
		if (roomtypeName != null) {
			sql.append(" AND roomtype_name LIKE ?");
			list.add("%" + roomtypeName + "%");
		}

		if (roomtypeCapacity != null) {
			sql.append(" AND roomtype_capacity LIKE ?");
			list.add(roomtypeCapacity);
		}

		if (roomtypePrice != null) {
			sql.append(" AND roomtype_price LIKE ?");
			list.add(roomtypePrice);
		}

		if (roomtypeQuantity != null) {
			sql.append(" AND roomtype_quantity >= ?");
			list.add(roomtypeQuantity);
		}

		if (roomtypeDescription != null) {
			sql.append(" AND roomtype_description LIKE ?");
			list.add("%" + roomtypeDescription + "%");
		}

		if (roomtypeAddress != null) {
			sql.append(" AND roomtype_description LIKE ?");
			list.add("%" + roomtypeAddress + "%");
		}

		if (roomtypeCity != null) {
			sql.append(" AND roomtype_city LIKE ?");
			list.add("%" + roomtypeCity + "%");
		}

		if (roomtypeDistrict != null) {
			sql.append(" AND roomtype_district LIKE ?");
			list.add("%" + roomtypeDistrict + "%");
		}

		if (minMoney != null) {
			if (maxMoney != null) {
				sql.append(" AND roomtype_price >= ? AND roomtype_price <= ?");
				list.add(minMoney);
				list.add(maxMoney);
			} else {
				sql.append(" AND roomtype_price >= ?");
				list.add(minMoney);
			}
		}

		if (maxMoney != null && minMoney == null) {
			sql.append(" AND roomtype_price <= ?");
			list.add(maxMoney);
		}
		
		sql.append(" ORDER BY " + attrOrderBy + " " + selectedSort + " OFFSET ? ROWS FETCH NEXT 10 ROWS ONLY");
	
		if(page != null) {
			list.add((page-1)*10);
		}else {
			list.add(0);
		}
		
		return DaoUtils.commonsQuery(sql.toString(), new RoomtypeDTORowMapper(), list.toArray());
	}

	/**
	 * 根據id獲取房間類型
	 * 
	 * @param roomtypeId
	 * @return
	 */
	public DaoResult<Roomtype> getRoomtypeById(Integer roomtypeId) {
		String sql = "SELECT * FROM roomtype WHERE roomtype_id = ?";
		return DaoUtils.commonsQuery(sql, new RoomtypeRowMapper(), roomtypeId);
	}

	/**
	 * 添加房間類型
	 * 
	 * @param roomtype
	 * @return
	 */
	public DaoResult<Integer> addRoomtype(Roomtype roomtype) {
		String sql = "INSERT INTO roomtype (roomtype_name, roomtype_capacity, roomtype_price, roomtype_quantity, roomtype_description, roomtype_address, roomtype_city, roomtype_district, updated_time, created_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		return DaoUtils.commonsUpdate(sql, roomtype.getRoomtypeName(), roomtype.getRoomtypeCapacity(),
				roomtype.getRoomtypePrice(), roomtype.getRoomtypeQuantity(), roomtype.getRoomtypeDescription(),
				roomtype.getRoomtypeAddress(), roomtype.getRoomtypeCity(), roomtype.getRoomtypeDistrict(),
				Timestamp.valueOf(roomtype.getUpdatedTime()), Timestamp.valueOf(roomtype.getCreatedTime()));
	}

	/**
	 * 根據roomtypeId移除房間類型
	 * 
	 * @param roomtypeId
	 * @return
	 */
	public DaoResult<Integer> removeRoomtypeById(Integer roomtypeId) {
		String sql = "DELETE FROM roomtype WHERE roomtype_id = ?";
		return DaoUtils.commonsUpdate(sql, roomtypeId);
	}

	/**
	 * 更新房間類型
	 * 
	 * @param roomtype
	 * @return
	 */
	public DaoResult<Integer> updateRoomtype(Roomtype roomtype) {
		String sql = "UPDATE roomtype SET roomtype_name = ?, roomtype_capacity = ?, roomtype_price = ?, roomtype_quantity = ?, roomtype_description = ?, roomtype_address = ?, roomtype_city = ?, roomtype_district = ?, updated_time = ? WHERE roomtype_id = ?";

		return DaoUtils.commonsUpdate(sql, roomtype.getRoomtypeName(), roomtype.getRoomtypeCapacity(),
				roomtype.getRoomtypePrice(), roomtype.getRoomtypeQuantity(), roomtype.getRoomtypeDescription(),
				roomtype.getRoomtypeAddress(), roomtype.getRoomtypeCity(), roomtype.getRoomtypeDistrict(),
				Timestamp.valueOf(roomtype.getUpdatedTime()), roomtype.getRoomtypeId());
	}
}
