package com.booking.dto.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.utils.Listable;

public class AdminDTO implements Listable {
	private Integer adminId;
	private String adminAccount;
	private String adminMail;
	private String adminName;
	private LocalDate hiredate;
	private Integer adminStatus;

	private static String[] attrs = { "admin-account", "admin-name", "admin-mail", "hiredate", "admin-status" };
	private static String[] attrsChinese = { "管理員帳號", "管理員姓名", "管理員信箱", "到職日期", "管理員狀態" };
	public static List<Map<String, String>> listInfos = new ArrayList<>();
	
	private static String[] pages = { "管理員"};
	private static String[] pageURL = {"/booking/admin"};
	public static List<Map<String, String>> pageInfos = new ArrayList<>();
	
	public static String manageListName = "管理員列表"; 

	static {
		for (int i = 0; i < attrs.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("attr", attrs[i]);
			map.put("attrChinese", attrsChinese[i]);
			listInfos.add(map);
		}
		
		for(int i = 0; i < pages.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("page", pages[i]);
			map.put("url", pageURL[i]);
			pageInfos.add(map);
		}
	}

	public AdminDTO() {}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminAccount() {
		return adminAccount;
	}

	public void setAdminAccount(String adminAccount) {
		this.adminAccount = adminAccount;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminMail() {
		return adminMail;
	}

	public void setAdminMail(String adminMail) {
		this.adminMail = adminMail;
	}

	public LocalDate getHiredate() {
		return hiredate;
	}

	public void setHiredate(LocalDate hiredate) {
		this.hiredate = hiredate;
	}

	public Integer getAdminStatus() {
		return adminStatus;
	}

	public void setAdminStatus(Integer adminStatus) {
		this.adminStatus = adminStatus;
	}

	public String[] getAttrs() {
		return attrs;
	}

	public String[] getAttrsChinese() {
		return attrsChinese;
	}

	@Override
	public String getName() {
		return adminName;
	}

	@Override
	public Integer getId() {
		return adminId;
	}

	@Override
	public String toString() {
		return "AdminDTO [adminId=" + adminId + ", adminAccount=" + adminAccount + ", adminName=" 
		       + adminName + ", adminMail=" + adminMail + ", hiredate=" + hiredate + ", adminStatus=" 
		       + adminStatus + ", attrs=" + Arrays.toString(attrs) + ", attrsChinese=" 
		       + Arrays.toString(attrsChinese) + "]";
	}

	@Override
	public Integer getTotalCounts() {
		return null;
	}

	@Override
	public Map<String, Object> getAdditionProperties() {
		return new HashMap<>();
	}
}


