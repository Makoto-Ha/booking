package com.booking.dao.activity;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.booking.bean.activity.Activity;
import com.booking.mapper.ActivityRowMapper;
import com.booking.utils.DaoResult;
import com.booking.utils.DaoUtils;

public class ActivityDao {

	// 新增活動
	public DaoResult<Integer> insertActivity(Activity activity) {
		String sql = "INSERT INTO marketing_activity (activity_name, start_date, deadline, limit_of_times, discount_code, activity_detail) VALUES (?, ?, ?, ?, ?, ?)";
		return DaoUtils.commonsUpdate(sql, activity.getActivityName(), Date.valueOf(activity.getStartDate()),
				Date.valueOf(activity.getDeadline()), activity.getLimitOfTimes(), activity.getDiscountCode(),
				activity.getActivityDetail()

		);
	}

	// 刪除活動
	public DaoResult<Integer> deleteActivity(Integer activityId) {
		String sql = "DELETE FROM marketing_activity WHERE activity_id = ?";
		return DaoUtils.commonsUpdate(sql, activityId);
	}

	// 查詢單筆活動
	public DaoResult<Activity> getActivityById(Integer activityId) {
		String sql = "SELECT * FROM marketing_activity WHERE activity_id = ?";
		return DaoUtils.commonsQuery(sql, new ActivityRowMapper(), activityId);
	}

	// 查詢所有活動
	public DaoResult<Activity> getAllActivitys() {
		String sql = "SELECT * FROM marketing_activity";
		return DaoUtils.commonsQuery(sql, new ActivityRowMapper());
	}

	// 根據活動詳情進行模糊查詢
	public DaoResult<Activity> getActivityByDetail(String activityDetail)
			throws SQLException, ClassNotFoundException {
		String sql = "SELECT * FROM marketing_activity WHERE activity_detail LIKE ?";
		return DaoUtils.commonsQuery(sql, new ActivityRowMapper(), "%" + activityDetail + "%");
	}

	// 更新活動
	public DaoResult<Integer> updateActivity(Activity activity) {
		String sql = "UPDATE marketing_activity SET activity_name = ?, start_date = ?, deadline = ?, limit_of_times = ?, discount_code = ?, activity_detail = ? WHERE activity_id = ?";
		return DaoUtils.commonsUpdate(sql, activity.getActivityName(), Date.valueOf(activity.getStartDate()),
				Date.valueOf(activity.getDeadline()), activity.getLimitOfTimes(), activity.getDiscountCode(),
				activity.getActivityDetail(), activity.getActivityId());
	}

	// 更新日期 根據id
	public DaoResult<Integer> updateActivityDeadline(Integer activityId, LocalDate newDeadline)
			throws SQLException, ClassNotFoundException {
		String sql = "UPDATE marketing_activity SET deadline = ? WHERE activity_id = ?";
		return DaoUtils.commonsUpdate(sql, newDeadline, activityId);
	}

	// 更新使用次數限制 根據id
	public DaoResult<Integer> updateLimitOfTimes(Integer activityId, Integer newLimit)
			throws SQLException, ClassNotFoundException {
		String sql = "UPDATE marketing_activity SET limit_of_times = ? WHERE activity_id = ?";
		return DaoUtils.commonsUpdate(sql, newLimit, activityId);
	}

	// 多重模糊查詢
	public DaoResult<Activity> dynamicQuery(Activity activity) {
		StringBuilder sql = new StringBuilder("SELECT * FROM marketing_activity WHERE 1=1");
		List<Object> list = new ArrayList<>();

		Integer activityId = activity.getActivityId();
		String activityName = activity.getActivityName();
		LocalDate startDate = activity.getStartDate();
		LocalDate deadline = activity.getDeadline();
		Integer limitOfTimes = activity.getLimitOfTimes();
		String discountCode = activity.getDiscountCode();
		String activityDetail = activity.getActivityDetail();

		if (activityId != null) {
			sql.append(" AND activity_id = ?");
			list.add(activityId);
		}

		if (activityName != null) {
			sql.append(" AND activity_name LIKE ?");
			list.add("%" + activityName + "%");
		}

		if (startDate != null) {
			sql.append(" AND start_date = ?");
			list.add(startDate);
		}

		if (deadline != null) {
			sql.append(" AND deadline = ?");
			list.add(deadline);
		}

		if (limitOfTimes != null) {
			sql.append(" AND limit_of_times = ?");
			list.add(limitOfTimes);
		}

		if (discountCode != null) {
			sql.append(" AND discount_code LIKE ?");
			list.add("%" + discountCode + "%");
		}

		if (activityDetail != null) {
			sql.append(" AND activity_detail LIKE ?");
			list.add("%" + activityDetail + "%");
		}

		return DaoUtils.commonsQuery(sql.toString(), new ActivityRowMapper(), list.toArray());
	}

}
