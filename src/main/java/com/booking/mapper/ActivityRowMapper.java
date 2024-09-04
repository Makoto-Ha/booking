package com.booking.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.booking.bean.activity.Activity;

public class ActivityRowMapper implements RowMapper<Activity> {

	@Override
	public Activity getRow(ResultSet resultSet) {
		try {
		    Integer activityId = resultSet.getInt("activity_id");
		    String activityName = resultSet.getString("activity_name");
		    LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
		    LocalDate deadline = resultSet.getDate("deadline").toLocalDate();
		    Integer limitOfTimes = resultSet.getInt("limit_of_times");
		    String discountCode = resultSet.getString("discount_code");
		    String activityDetail = resultSet.getString("activity_detail");
		    
		    return new Activity(activityId, activityName, startDate, deadline,limitOfTimes, discountCode, activityDetail);
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return null;

	}
	
}
