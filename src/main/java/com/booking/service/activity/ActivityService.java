package com.booking.service.activity;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.booking.bean.activity.Activity;
import com.booking.dao.activity.ActivityDao;
import com.booking.dto.activity.ActivityDTO;
import com.booking.utils.DaoResult;
import com.booking.utils.Listable;
import com.booking.utils.Result;

public class ActivityService {

	private ActivityDao activityDao = new ActivityDao();

	/**
	 * 刪除
	 * 
	 * @param activityId
	 * @return
	 */
	public Result<String> deleteActivity(Integer activityId) {
		DaoResult<Integer> daoResult = activityDao.deleteActivity(activityId);
		if (daoResult.getAffectedRows() > 0) {
			return Result.success("刪除成功");
		} else {
			return Result.failure("刪除失敗，未找到該活動");
		}
	}

	
	/**
	 * 新增活動
	 * 
	 * @param activity
	 * @return
	 */
	public Result<Integer> insertActivity(Activity activity) {		
		if (activity.getStartDate().isAfter(activity.getDeadline())) {
			return Result.failure("活動開始日期不能晚於結束日期");

		}
		DaoResult<Integer> daoResult = activityDao.insertActivity(activity);
		if (daoResult.getAffectedRows() > 0) {
			return Result.success(daoResult.getGeneratedId());
		} else {
			return Result.failure("新增失敗");
		}
	}

	
	/**
	 * 查詢單筆活動
	 * 
	 * @param activityId
	 * @return
	 */
	public Result<Activity> getActivity(Integer activityId) {
		DaoResult<Activity> daoResult = activityDao.getActivityById(activityId);
		Activity activity = daoResult.getEntity();
		if (activity == null) {
			return Result.failure("未找到");
		}
		return Result.success(activity);
	}

	
	/**
	 * 查詢所有活動
	 * 
	 * @return
	 */
	public Result<List<Listable>> getAllActivitys() {
		DaoResult<Activity> daoResult = activityDao.getAllActivitys();

		List<Activity> activitys = daoResult.getData();
		List<Listable> lists = new ArrayList<>();
		for (Activity activity : activitys) {
			ActivityDTO activityDTO = new ActivityDTO();
			try {
				BeanUtils.copyProperties(activityDTO, activity);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			lists.add(activityDTO);
		}
		if (activitys == null) {
			return Result.failure("查詢所有房間類型失敗");
		}
		return Result.success(lists);
	}

	
	/**
	 * 更新資料
	 * 
	 * @param activityBean
	 * @return
	 */
	public Result<String> updateActivity(Activity activity) {
		Integer oldActivityId = activity.getActivityId();
		Activity oldActivityBean = activityDao.getActivityById(oldActivityId).getEntity();
		String detail = activity.getActivityDetail();
		Integer limitOfTime = activity.getLimitOfTimes();
		String activityName = activity.getActivityName();
		LocalDate deadLine = activity.getDeadline();
		LocalDate startDate = activity.getStartDate();
		String discountCode = activity.getDiscountCode();

		if (activityName == null || activityName.isEmpty()) {
			activity.setActivityName(oldActivityBean.getActivityName());
		}
		if (limitOfTime == null) {
			activity.setLimitOfTimes(oldActivityBean.getLimitOfTimes());
		}

		if (detail == null || detail.isEmpty()) {
			activity.setActivityDetail(oldActivityBean.getActivityDetail());
		}
		// 檢查並更新 deadLine
		if (deadLine == null) {
			activity.setDeadline(oldActivityBean.getDeadline());
		} else {
			activity.setDeadline(deadLine); // 使用傳入的 deadLine 值
		}
		// 檢查並更新 startDate
		if (startDate == null) {
			activity.setStartDate(oldActivityBean.getStartDate());
		} else {
			activity.setStartDate(startDate); // 使用傳入的 startDate 值
		}
		// 檢查並更新 discountCode
		if (discountCode == null || discountCode.isEmpty()) {
			activity.setDiscountCode(oldActivityBean.getDiscountCode());
		}
		DaoResult<Integer> updateActivityDaoResult = activityDao.updateActivity(activity);
		if (updateActivityDaoResult.getAffectedRows() == null) {
			return Result.failure("更新活動失敗");
		}
		return Result.success("更新活動成功");
	}
	
	
	/**
	 * 多重模糊查詢
	 * 
	 * @param activity
	 * @return
	 */
	public Result<List<Listable>> getActivitylike(Activity activity) {
		DaoResult<Activity> daynamicQueryDaoResult = activityDao.dynamicQuery(activity);
		List<Activity> activitylike = daynamicQueryDaoResult.getData();

		if (activitylike == null) {
			return Result.failure("查詢活動失敗");
		}
		List<Listable> ActivitysDTO = new ArrayList<>();
		for (Activity activityOne : activitylike) {
			ActivityDTO activityDTO = new ActivityDTO();
			try {
				BeanUtils.copyProperties(activityDTO, activityOne);
				ActivitysDTO.add(activityDTO);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return Result.success(ActivitysDTO);
	}

}
