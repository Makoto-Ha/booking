package com.booking.bean.pojo.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.shopping.ShoppingOrder;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name", nullable = false, length = 20)
    private String userName;

    @Column(name = "user_account", nullable = false, length = 30)
    private String userAccount;

    @Column(name = "user_password", nullable = false, length = 30)
    private String userPassword;

    @Column(name = "user_mail", length = 100)
    private String userMail;

    @Column(name = "user_phone", length = 30)
    private String userPhone;

    @Column(name = "user_birthday")
    private LocalDate userBirthday;

    @Column(name = "user_address", length = 100)
    private String userAddress;

    @Lob
    @Column(name = "user_img")
    private String userImg;

    @Column(name = "credit_card", length = 20)
    private String creditCard;

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;

    @Column(name = "permission")
    private Byte permission;

    @OneToMany(mappedBy = "users")
    private List<PackageTourOrder> packageTourOrder;

    @OneToMany(mappedBy = "users")
    private List<ShoppingOrder> shoppingOrder;

    @OneToMany(mappedBy = "users")
    private List<BookingOrder> bookingOrder;

    @OneToOne(mappedBy = "users")
    private AdminPermission adminPermission;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public LocalDate getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(LocalDate userBirthday) {
		this.userBirthday = userBirthday;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public Byte getPermission() {
		return permission;
	}

	public void setPermission(Byte permission) {
		this.permission = permission;
	}

	public List<PackageTourOrder> getPackageTourOrder() {
		return packageTourOrder;
	}

	public void setPackageTourOrder(List<PackageTourOrder> packageTourOrder) {
		this.packageTourOrder = packageTourOrder;
	}

	public List<ShoppingOrder> getShoppingOrder() {
		return shoppingOrder;
	}

	public void setShoppingOrder(List<ShoppingOrder> shoppingOrder) {
		this.shoppingOrder = shoppingOrder;
	}

	public List<BookingOrder> getBookingOrder() {
		return bookingOrder;
	}

	public void setBookingOrder(List<BookingOrder> bookingOrder) {
		this.bookingOrder = bookingOrder;
	}

	public AdminPermission getAdminPermission() {
		return adminPermission;
	}

	public void setAdminPermission(AdminPermission adminPermission) {
		this.adminPermission = adminPermission;
	}

	public User(Integer userId, String userName, String userAccount, String userPassword, String userMail,
			String userPhone, LocalDate userBirthday, String userAddress, String userImg, String creditCard,
			LocalDateTime createdTime, LocalDateTime updatedTime, Byte permission,
			List<PackageTourOrder> packageTourOrder, List<ShoppingOrder> shoppingOrder, List<BookingOrder> bookingOrder,
			AdminPermission adminPermission) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.userImg = userImg;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.permission = permission;
		this.packageTourOrder = packageTourOrder;
		this.shoppingOrder = shoppingOrder;
		this.bookingOrder = bookingOrder;
		this.adminPermission = adminPermission;
	}

	public User(String userName, String userAccount, String userPassword, String userMail, String userPhone,
			LocalDate userBirthday, String userAddress, String userImg, String creditCard, LocalDateTime createdTime,
			LocalDateTime updatedTime, Byte permission, List<PackageTourOrder> packageTourOrder,
			List<ShoppingOrder> shoppingOrder, List<BookingOrder> bookingOrder, AdminPermission adminPermission) {
		super();
		this.userName = userName;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.userImg = userImg;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.permission = permission;
		this.packageTourOrder = packageTourOrder;
		this.shoppingOrder = shoppingOrder;
		this.bookingOrder = bookingOrder;
		this.adminPermission = adminPermission;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userAccount=" + userAccount + ", userPassword="
				+ userPassword + ", userMail=" + userMail + ", userPhone=" + userPhone + ", userBirthday="
				+ userBirthday + ", userAddress=" + userAddress + ", userImg=" + userImg + ", creditCard=" + creditCard
				+ ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + ", permission=" + permission
				+ ", shoppingOrder=" + shoppingOrder + ", bookingOrder=" + bookingOrder + "]";
	}

	
    
    
    
    
	}