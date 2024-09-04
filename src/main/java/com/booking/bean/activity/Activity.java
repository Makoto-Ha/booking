package com.booking.bean.activity;

import java.time.LocalDate;

public class Activity {

	private Integer activityId;
	private String activityName;
	private LocalDate startDate;
	private LocalDate deadline;
	private Integer limitOfTimes;
	private String discountCode;
	private String activityDetail;

	public Activity(Integer activityId, String activityName, LocalDate startDate, LocalDate deadline,
			Integer limitOfTimes, String discountCode, String activityDetail) {
		super();
		this.activityId = activityId;
		this.activityName = activityName;
		this.startDate = startDate;
		this.deadline = deadline;
		this.limitOfTimes = limitOfTimes;
		this.discountCode = discountCode;
		this.activityDetail = activityDetail;
	}

	public Activity() {

	}

	public Activity(String activityName, LocalDate startDate, LocalDate deadline, Integer limitOfTimes,
			String discountCode, String activityDetail) {
		super();
		this.activityName = activityName;
		this.startDate = startDate;
		this.deadline = deadline;
		this.limitOfTimes = limitOfTimes;
		this.discountCode = discountCode;
		this.activityDetail = activityDetail;
	}

	public Activity(Integer activityId, LocalDate deadline) {
		super();
		this.activityId = activityId;
		this.deadline = deadline;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public Integer getLimitOfTimes() {
		return limitOfTimes;
	}

	public void setLimitOfTimes(Integer limitOfTimes) {
		this.limitOfTimes = limitOfTimes;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}

	public String getActivityDetail() {
		return activityDetail;
	}

	public void setActivityDetail(String activityDetail) {
		this.activityDetail = activityDetail;
	}

	@Override
	public String toString() {
		return "ActivityBean [activityId=" + activityId + ", activityName=" + activityName + ", startDate=" + startDate
				+ ", deadline=" + deadline + ", limitOfTimes=" + limitOfTimes + ", discountCode=" + discountCode
				+ ", activityDetail=" + activityDetail + "]";
	}

}
