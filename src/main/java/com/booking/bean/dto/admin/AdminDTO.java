package com.booking.bean.dto.admin;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.Column;

public class AdminDTO {
	private Integer adminId;
	private String adminAccount;
	private String adminMail;
	private String adminName;
	private LocalDate hiredate;
	private Integer adminStatus;
	private String adminPassword;
	private String verificationToken;
	private String resetPasswordToken;
	private Integer emailVerified;	
	
	private String imgFile;
	private Integer pageNumber = 1;
	private String attrOrderBy = "adminId";
	private Boolean selectedSort = true;

	public AdminDTO() {
	}
	



	public AdminDTO(Integer adminId, String adminAccount, String adminMail, String adminName, LocalDate hiredate,
			Integer adminStatus, String adminPassword, String verificationToken, String resetPasswordToken,
			Integer emailVerified, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminMail = adminMail;
		this.adminName = adminName;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		this.adminPassword = adminPassword;
		this.verificationToken = verificationToken;
		this.resetPasswordToken = resetPasswordToken;
		this.emailVerified = emailVerified;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}

	public AdminDTO(String adminAccount, String adminMail, String adminName, LocalDate hiredate, Integer adminStatus,
			String adminPassword, String verificationToken, String resetPasswordToken, Integer emailVerified,
			Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.adminAccount = adminAccount;
		this.adminMail = adminMail;
		this.adminName = adminName;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		this.adminPassword = adminPassword;
		this.verificationToken = verificationToken;
		this.resetPasswordToken = resetPasswordToken;
		this.emailVerified = emailVerified;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}





	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
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

	public Integer getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Integer emailVerified) {
		this.emailVerified = emailVerified;
	}




	public String getImgFile() {
		return imgFile;
	}




	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}




	@Override
	public String toString() {
		return "AdminDTO [adminId=" + adminId + ", adminAccount=" + adminAccount + ", adminMail=" + adminMail
				+ ", adminName=" + adminName + ", hiredate=" + hiredate + ", adminStatus=" + adminStatus
				+ ", adminPassword=" + adminPassword + ", verificationToken=" + verificationToken
				+ ", resetPasswordToken=" + resetPasswordToken + ", emailVerified=" + emailVerified + ", imgFile="
				+ imgFile + ", pageNumber=" + pageNumber + ", attrOrderBy=" + attrOrderBy + ", selectedSort="
				+ selectedSort + "]";
	}




	public AdminDTO(Integer adminId, String adminAccount, String adminMail, String adminName, LocalDate hiredate,
			Integer adminStatus, String adminPassword, String verificationToken, String resetPasswordToken,
			Integer emailVerified, String imgFile, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminMail = adminMail;
		this.adminName = adminName;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		this.adminPassword = adminPassword;
		this.verificationToken = verificationToken;
		this.resetPasswordToken = resetPasswordToken;
		this.emailVerified = emailVerified;
		this.imgFile = imgFile;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}




	public AdminDTO(String adminAccount, String adminMail, String adminName, LocalDate hiredate, Integer adminStatus,
			String adminPassword, String verificationToken, String resetPasswordToken, Integer emailVerified,
			String imgFile, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.adminAccount = adminAccount;
		this.adminMail = adminMail;
		this.adminName = adminName;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		this.adminPassword = adminPassword;
		this.verificationToken = verificationToken;
		this.resetPasswordToken = resetPasswordToken;
		this.emailVerified = emailVerified;
		this.imgFile = imgFile;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}


	
}
