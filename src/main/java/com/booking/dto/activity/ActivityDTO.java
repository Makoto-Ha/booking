package com.booking.dto.activity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.utils.Listable;

public class ActivityDTO implements Listable {
	private Integer activityId;
	private String activityName;
	private LocalDate startDate;
	private LocalDate deadline;
	private Integer limitOfTimes;
	private String discountCode;
	private String activityDetail;

	private static String[] attrs = { "activity-name", "start-date", "deadline", "limit-of-times", "discount-code",
			"activity-detail" };
	private static String[] attrsChinese = { "活動名稱", "活動開始日期", "活動截止日期", "使用次數限制", "折扣碼", "活動詳情" };
	public static List<Map<String, String>> listInfos = new ArrayList<>();

	private static String[] pages = { "行銷活動" };
	private static String[] pageURL = { "/booking/activity" };
	public static List<Map<String, String>> pageInfos = new ArrayList<>();
	
	public static String manageListName = "行銷活動列表";

	static {
		for (int i = 0; i < attrs.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("attr", attrs[i]);
			map.put("attrChinese", attrsChinese[i]);
			listInfos.add(map);
		}

		for (int i = 0; i < pages.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("page", pages[i]);
			map.put("url", pageURL[i]);
			pageInfos.add(map);
		}
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

	public static String[] getAttrs() {
		return attrs;
	}

	public static void setAttrs(String[] attrs) {
		ActivityDTO.attrs = attrs;
	}

	public static String[] getAttrsChinese() {
		return attrsChinese;
	}

	public static void setAttrsChinese(String[] attrsChinese) {
		ActivityDTO.attrsChinese = attrsChinese;
	}

	public static List<Map<String, String>> getListInfos() {
		return listInfos;
	}

	public static void setListInfos(List<Map<String, String>> listInfos) {
		ActivityDTO.listInfos = listInfos;
	}

	public static String[] getPages() {
		return pages;
	}

	public static void setPages(String[] pages) {
		ActivityDTO.pages = pages;
	}

	public static String[] getPageURL() {
		return pageURL;
	}

	public static void setPageURL(String[] pageURL) {
		ActivityDTO.pageURL = pageURL;
	}

	public static List<Map<String, String>> getPageInfos() {
		return pageInfos;
	}

	public static void setPageInfos(List<Map<String, String>> pageInfos) {
		ActivityDTO.pageInfos = pageInfos;
	}
	
	
	
	
	

	@Override
	public String toString() {
		return "ActivityDTO [activityId=" + activityId + ", activityName=" + activityName + ", startDate=" + startDate
				+ ", deadline=" + deadline + ", limitOfTimes=" + limitOfTimes + ", discountCode=" + discountCode
				+ ", activityDetail=" + activityDetail + "]";
	}

	public ActivityDTO() {
		super();
	}

	public ActivityDTO(String activityName, LocalDate startDate, LocalDate deadline, Integer limitOfTimes,
			String discountCode, String activityDetail) {
		super();
		this.activityName = activityName;
		this.startDate = startDate;
		this.deadline = deadline;
		this.limitOfTimes = limitOfTimes;
		this.discountCode = discountCode;
		this.activityDetail = activityDetail;
	}

	public ActivityDTO(Integer activityId, String activityName, LocalDate startDate, LocalDate deadline,
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

	@Override
	public String getName() {
		return activityName;
	}

	@Override
	public Integer getId() {
		return activityId;
	}

	@Override
	public Map<String, Object> getAdditionProperties() {
		return new HashMap<>();
	}

	@Override
	public Integer getTotalCounts() {
		return null;
	}
}
