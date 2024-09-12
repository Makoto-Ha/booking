package com.booking.bean.admin;

import java.time.LocalDate;
import java.util.List;

import com.booking.bean.comment.Comment;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin") // 对应数据库中的表名为 "admin"
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略为自增
    @Column(name = "admin_id") // 对应数据库中的列名为 "admin_id"
    private Integer adminId;

    @Column(name = "admin_account", nullable = false, length = 30) // 对应数据库中的列名为 "admin_account"，长度为 30，不能为空
    private String adminAccount;

    @Column(name = "admin_name", nullable = false, length = 50) // 对应数据库中的列名为 "admin_name"，长度为 50，不能为空
    private String adminName;

    @Column(name = "admin_mail", nullable = false, length = 100) // 对应数据库中的列名为 "admin_mail"，长度为 100，不能为空
    private String adminMail;

    @Column(name = "hiredate", nullable = false) // 对应数据库中的列名为 "hiredate"，不能为空
    private LocalDate hiredate;

    @Column(name = "admin_status", nullable = false) // 对应数据库中的列名为 "admin_status"，不能为空
    private Integer adminStatus;

    @Column(name = "admin_password", nullable = false, length = 100) // 对应数据库中的列名为 "admin_password"，长度为 100，不能为空
    private String adminPassword;
    
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<Comment> comments;

    // 默认构造函数
    public Admin() {
        super();
    }

    // 构造函数
    public Admin(Integer adminId, String adminAccount, String adminName, String adminMail, LocalDate hiredate, Integer adminStatus) {
        this.adminId = adminId;
        this.adminAccount = adminAccount;
        this.adminName = adminName;
        this.adminMail = adminMail;
        this.hiredate = hiredate;
        this.adminStatus = adminStatus;
    }

    public Admin(Integer adminId, String adminAccount, String adminPassword, String adminName, String adminMail, LocalDate hiredate, Integer adminStatus) {
        this.adminId = adminId;
        this.adminAccount = adminAccount;
        this.adminPassword = adminPassword;
        this.adminName = adminName;
        this.adminMail = adminMail;
        this.hiredate = hiredate;
        this.adminStatus = adminStatus;
    }

    public Admin(String adminAccount, String adminPassword, String adminName, String adminMail, LocalDate hiredate, Integer adminStatus) {
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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@Override
    public String toString() {
        return "Admin [adminId=" + adminId + ", adminAccount=" + adminAccount + ", adminPassword=" + adminPassword + ", adminName=" + adminName + ", adminMail=" + adminMail + ", hiredate=" + hiredate + ", adminStatus=" + adminStatus + "]";
    }

    public boolean isEmpty() {
        return false;
    }
}
