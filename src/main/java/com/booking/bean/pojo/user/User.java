package com.booking.bean.pojo.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer userId;

	@Column(name = "user_name", nullable = false, length = 20)
	private String userName;

	@Column(name = "user_account", nullable = false, length = 30, unique = true)
	private String userAccount;

	@Column(name = "user_password", nullable = false, length = 60)
	private String userPassword;

	@Column(name = "user_mail", length = 100, unique = true)
	private String userMail;

	@Column(name = "user_phone", length = 30)
	private String userPhone;

	@Column(name = "user_birthday")
	private LocalDate userBirthday;

	@Column(name = "user_address", length = 100)
	private String userAddress;

	@Column(name = "imgs_file")
	private String imgsFile;

	@Column(name = "credit_card", length = 20)
	private String creditCard;

	@Column(name = "created_time", nullable = false, updatable = false)
	private LocalDateTime createdTime;

	@Column(name = "updated_time")
	private LocalDateTime updatedTime;

	@Column(name = "email_verified")
	private boolean emailVerified;

	@Column(name = "verification_token")
	private String verificationToken;

	@Column(name = "reset_token")
	private String resetToken;

	@Column(name = "provider")
	private String provider;

	@Column(name = "provider_id")
	private String providerId;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<PackageTourOrder> packageTourOrder;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<ShopOrder> shopOrder;

	@JsonIgnore
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<BookingOrder> bookingOrder;

//	@OneToOne(mappedBy = "users")
//	private AdminPermission adminPermission;

	@PreUpdate
	protected void onUpdate() {
		updatedTime = LocalDateTime.now();
	}

	public boolean isResetTokenValid() {
	
		return true;
	}

	public void clearResetToken() {
		this.resetToken = null;
	}

	@PrePersist
	protected void onCreate() {
		createdTime = LocalDateTime.now();
	}

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

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getVerificationToken() {
		return verificationToken;
	}

	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}

	public String getResetToken() {
		return resetToken;
	}

	public void setResetToken(String resetToken) {
		this.resetToken = resetToken;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public List<PackageTourOrder> getPackageTourOrder() {
		return packageTourOrder;
	}

	public void setPackageTourOrder(List<PackageTourOrder> packageTourOrder) {
		this.packageTourOrder = packageTourOrder;
	}

	public List<ShopOrder> getShopOrder() {
		return shopOrder;
	}

	public void setShopOrder(List<ShopOrder> shopOrder) {
		this.shopOrder = shopOrder;
	}

	public List<BookingOrder> getBookingOrder() {
		return bookingOrder;
	}

	public void setBookingOrder(List<BookingOrder> bookingOrder) {
		this.bookingOrder = bookingOrder;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userAccount=" + userAccount + ", userPassword="
				+ userPassword + ", userMail=" + userMail + ", userPhone=" + userPhone + ", userBirthday="
				+ userBirthday + ", userAddress=" + userAddress + ", creditCard=" + creditCard + ", createdTime="
				+ createdTime + ", updatedTime=" + updatedTime + ", emailVerified=" + emailVerified
				+ ", verificationToken=" + verificationToken + ", resetToken=" + resetToken + ", provider=" + provider
				+ ", providerId=" + providerId + ", packageTourOrder=" + packageTourOrder + ", shopOrder=" + shopOrder
				+ ", bookingOrder=" + bookingOrder + "]";
	}

	public User(Integer userId, String userName, String userAccount, String userPassword, String userMail,
			String userPhone, LocalDate userBirthday, String userAddress, String creditCard, LocalDateTime createdTime,
			LocalDateTime updatedTime, boolean emailVerified, String verificationToken, String resetToken,
			String provider, String providerId, List<PackageTourOrder> packageTourOrder, List<ShopOrder> shopOrder,
			List<BookingOrder> bookingOrder) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetToken = resetToken;
		this.provider = provider;
		this.providerId = providerId;
		this.packageTourOrder = packageTourOrder;
		this.shopOrder = shopOrder;
		this.bookingOrder = bookingOrder;
	}

	public User(String userName, String userAccount, String userPassword, String userMail, String userPhone,
			LocalDate userBirthday, String userAddress, String creditCard, LocalDateTime createdTime,
			LocalDateTime updatedTime, boolean emailVerified, String verificationToken, String resetToken,
			String provider, String providerId, List<PackageTourOrder> packageTourOrder, List<ShopOrder> shopOrder,
			List<BookingOrder> bookingOrder) {
		super();
		this.userName = userName;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetToken = resetToken;
		this.provider = provider;
		this.providerId = providerId;
		this.packageTourOrder = packageTourOrder;
		this.shopOrder = shopOrder;
		this.bookingOrder = bookingOrder;
	}

	public User(String userAccount, String userPassword, String userMail, String userPhone, LocalDate userBirthday,
			String userAddress, String creditCard, LocalDateTime createdTime, LocalDateTime updatedTime,
			boolean emailVerified, String verificationToken, String resetToken, String provider, String providerId,
			List<PackageTourOrder> packageTourOrder, List<ShopOrder> shopOrder, List<BookingOrder> bookingOrder) {
		super();
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetToken = resetToken;
		this.provider = provider;
		this.providerId = providerId;
		this.packageTourOrder = packageTourOrder;
		this.shopOrder = shopOrder;
		this.bookingOrder = bookingOrder;
	}

	public User(String userPassword, String userMail, String userPhone, LocalDate userBirthday, String userAddress,
			String creditCard, LocalDateTime createdTime, LocalDateTime updatedTime, boolean emailVerified,
			String verificationToken, String resetToken, String provider, String providerId,
			List<PackageTourOrder> packageTourOrder, List<ShopOrder> shopOrder, List<BookingOrder> bookingOrder) {
		super();
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetToken = resetToken;
		this.provider = provider;
		this.providerId = providerId;
		this.packageTourOrder = packageTourOrder;
		this.shopOrder = shopOrder;
		this.bookingOrder = bookingOrder;
	}

	public User(String userMail, String userPhone, LocalDate userBirthday, String userAddress, String creditCard,
			LocalDateTime createdTime, LocalDateTime updatedTime, boolean emailVerified, String verificationToken,
			String resetToken, String provider, String providerId, List<PackageTourOrder> packageTourOrder,
			List<ShopOrder> shopOrder, List<BookingOrder> bookingOrder) {
		super();
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetToken = resetToken;
		this.provider = provider;
		this.providerId = providerId;
		this.packageTourOrder = packageTourOrder;
		this.shopOrder = shopOrder;
		this.bookingOrder = bookingOrder;
	}

	public User() {
		super();
	}

	public String getImgsFile() {
		return imgsFile;
	}

	public void setImgsFile(String imgsFile) {
		this.imgsFile = imgsFile;
	}

	// getter and setter

}