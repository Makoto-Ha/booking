package com.booking.bean.dto.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminDTO {
	private Integer adminId;
	private String adminAccount;
	private String adminMail;
	private String adminName;
	private LocalDate hiredate;
	private Integer adminStatus;
	private String adminPassword;

	private Integer pageNumber = 1;
	private String attrOrderBy = "adminId";
	private Boolean selectedSort = true;

	public AdminDTO() {
	}

	
	public String getAdminPassword() {
		return adminPassword;
	}


	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}


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

	

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getAttrOrderBy() {
		return attrOrderBy;
	}

	public void setAttrOrderBy(String attrOrderBy) {
		this.attrOrderBy = attrOrderBy;
	}

	public Boolean getSelectedSort() {
		return selectedSort;
	}

	public void setSelectedSort(Boolean selectedSort) {
		this.selectedSort = selectedSort;
	}

	public AdminDTO(Integer adminId, String adminAccount, String adminMail, String adminName, LocalDate hiredate,
			Integer adminStatus,  Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminMail = adminMail;
		this.adminName = adminName;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
	
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}

	public AdminDTO(String adminAccount, String adminMail, String adminName, LocalDate hiredate, Integer adminStatus
			, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.adminAccount = adminAccount;
		this.adminMail = adminMail;
		this.adminName = adminName;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}


	public AdminDTO(Integer adminId, String adminAccount, String adminMail, String adminName, LocalDate hiredate,
			Integer adminStatus, String adminPassword, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminMail = adminMail;
		this.adminName = adminName;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		this.adminPassword = adminPassword;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}

}
