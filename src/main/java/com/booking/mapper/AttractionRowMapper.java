package com.booking.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.booking.bean.attraction.Attraction;

public class AttractionRowMapper implements RowMapper<Attraction> {

	@Override
	public Attraction getRow(ResultSet resultSet) {
		try {
			Integer id = resultSet.getInt("attraction_id");
			String name = resultSet.getString("attraction_name");
			String city = resultSet.getString("attraction_city");
			String address = resultSet.getString("address");
			String openingHour = resultSet.getString("opening_hours");
			String type = resultSet.getString("attraction_type");
			String description = resultSet.getString("attraction_description");
			return new Attraction(id, name, city, address, openingHour, type, description);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
