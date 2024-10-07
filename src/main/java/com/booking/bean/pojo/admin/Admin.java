package com.booking.bean.pojo.admin;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "admin_id")
	private Integer adminId;

	@Column(name = "admin_account", length = 30)
	private String adminAccount;

	@Column(name = "admin_name", length = 50)
	private String adminName;

	@Column(name = "admin_mail", length = 100)
	private String adminMail;

	@Column(name = "hiredate")
	private LocalDate hiredate;

	@Column(name = "admin_status")
	private Integer adminStatus;

	@Column(name = "admin_password", length = 100)
	private String adminPassword;

	@Column(name = "verification_token", length = 100)
	private String verificationToken;

	@Column(name = "reset_password_token", length = 100)
	private String resetPasswordToken;

	@Column(name = "email_verified", length = 100)
	private Integer emailVerified;
	@Column(name = "img_file")
	private String imgFile;

	public Admin() {
		super();
	}

	public Admin(Integer adminId, String adminAccount, String adminName, String adminMail, LocalDate hiredate,
			Integer adminStatus) {
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminName = adminName;
		this.adminMail = adminMail;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
	}

	public Admin(Integer adminId, String adminAccount, String adminPassword, String adminName, String adminMail,
			LocalDate hiredate, Integer adminStatus) {
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
		this.adminAccount = adminAccount;
		this.adminPassword = adminPassword;
		this.adminName = adminName;
		this.adminMail = adminMail;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
	}

	// Getters 和 Setters

	public Integer getAdminId() {
		return adminId;
	}

	public Admin(Integer adminId, String adminAccount, String adminName, String adminMail, LocalDate hiredate,
			Integer adminStatus, String adminPassword, String verificationToken, String resetPasswordToken) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminName = adminName;
		this.adminMail = adminMail;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		this.adminPassword = adminPassword;
		this.verificationToken = verificationToken;
		this.resetPasswordToken = resetPasswordToken;
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

	public Integer getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Integer emailVerified) {
		this.emailVerified = emailVerified;
	}

	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", adminAccount=" + adminAccount + ", adminName=" + adminName
				+ ", adminMail=" + adminMail + ", hiredate=" + hiredate + ", adminStatus=" + adminStatus
				+ ", adminPassword=" + adminPassword + ", verificationToken=" + verificationToken
				+ ", resetPasswordToken=" + resetPasswordToken + ", emailVerified=" + emailVerified + ", imgFile="
				+ imgFile + "]";
	}

	public Admin(String adminAccount, String adminName, String adminMail, LocalDate hiredate, Integer adminStatus,
			String adminPassword, String verificationToken, String resetPasswordToken, Integer emailVerified,
			String imgFile) {
		super();
		this.adminAccount = adminAccount;
		this.adminName = adminName;
		this.adminMail = adminMail;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		this.adminPassword = adminPassword;
		this.verificationToken = verificationToken;
		this.resetPasswordToken = resetPasswordToken;
		this.emailVerified = emailVerified;
		this.imgFile = imgFile;
	}

	public Admin(Integer adminId, String adminAccount, String adminName, String adminMail, LocalDate hiredate,
			Integer adminStatus, String adminPassword, String verificationToken, String resetPasswordToken,
			Integer emailVerified, String imgFile) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminName = adminName;
		this.adminMail = adminMail;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		this.adminPassword = adminPassword;
		this.verificationToken = verificationToken;
		this.resetPasswordToken = resetPasswordToken;
		this.emailVerified = emailVerified;
		this.imgFile = imgFile;
	}

	public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Admin(Integer adminId, String adminAccount, String adminName, String adminMail, LocalDate hiredate,
			Integer adminStatus, String adminPassword, String verificationToken, String resetPasswordToken,
			Integer emailVerified) {
		super();
		this.adminId = adminId;
		this.adminAccount = adminAccount;
		this.adminName = adminName;
		this.adminMail = adminMail;
		this.hiredate = hiredate;
		this.adminStatus = adminStatus;
		this.adminPassword = adminPassword;
		this.verificationToken = verificationToken;
		this.resetPasswordToken = resetPasswordToken;
		this.emailVerified = emailVerified;
	}

}
