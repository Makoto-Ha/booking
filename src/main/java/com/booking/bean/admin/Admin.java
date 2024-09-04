package com.booking.bean.admin;

import java.time.LocalDate;

public class Admin {

	private static final long serialVersionUID = 1L;
	private Integer adminId;
	private String adminAccount;
	private String adminName;
	private String adminMail;
	private LocalDate hiredate;
	private Integer adminStatus;
	private String adminPassword;

	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Admin(Integer adminId, String adminAccount, String adminName, String adminMail, LocalDate hiredate,
			Integer adminStatus) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminName = adminName;
		this.adminMail = adminMail;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
	}

	public Admin(Integer adminId, String adminAccount, String adminPassword, String adminName, String adminMail,
			LocalDate hiredate, Integer adminStatus) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminPassword = adminPassword;
		this.adminName = adminName;
		this.adminMail = adminMail;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
	}

	public Admin(String adminAccount, String adminPassword, String adminName, String adminMail, LocalDate hiredate,
			Integer adminStatus) {
		super();
		this.adminAccount = adminAccount;
		this.adminPassword = adminPassword;
		this.adminName = adminName;
		this.adminMail = adminMail;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
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

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminAccount=" + adminAccount + ", adminPassword=" + adminPassword
				+ ", adminName=" + adminName + ", adminMail=" + adminMail + ", hiredate=" + hiredate + ", adminStatus="
				+ adminStatus + "]";
	}

	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
}
