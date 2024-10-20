package com.booking.bean.dto.user;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import com.booking.bean.pojo.user.Resettokens;


public class UserDTO {
   
    private Integer userId;   
    private String userName;
    private String userAccount;
    private String userPassword;
    private String userMail;
    private String userPhone;
    private LocalDate userBirthday;
    private String userAddress;
    private String userImg;
    private String creditCard;
    private LocalDateTime createdTime; 
    private LocalDateTime updatedTime;   
    private Byte permission;    
	private List<Resettokens> resetToken = new LinkedList<Resettokens>();
	private Boolean emailVerified = false;	
	private String verificationToken;		
	private LocalDateTime resetTokenExpiry;
   //private List<PackageTourOrder> packageTourOrder;
    //private List<ShoppingOrder> shoppingOrder; 
    //private List<BookingOrder> bookingOrder;   
   //private AdminPermission adminPermission;
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
//	public List<PackageTourOrder> getPackageTourOrder() {
//		return packageTourOrder;
//	}
//	public void setPackageTourOrder(List<PackageTourOrder> packageTourOrder) {
//		this.packageTourOrder = packageTourOrder;
//	}
//	public List<ShoppingOrder> getShoppingOrder() {
//		return shoppingOrder;
//	}
//	public void setShoppingOrder(List<ShoppingOrder> shoppingOrder) {
//		this.shoppingOrder = shoppingOrder;
//	}
//	public List<BookingOrder> getBookingOrder() {
//		return bookingOrder;
//	}
//	public void setBookingOrder(List<BookingOrder> bookingOrder) {
//		this.bookingOrder = bookingOrder;
//	}
//	public AdminPermission getAdminPermission() {
//		return adminPermission;
//	}
//	public void setAdminPermission(AdminPermission adminPermission) {
//		this.adminPermission = adminPermission;
//	}
	
	
	public UserDTO() {
		super();
	}
	public List<Resettokens> getResetToken() {
		return resetToken;
	}
	public void setResetToken(List<Resettokens> resetToken) {
		this.resetToken = resetToken;
	}
	public Boolean getEmailVerified() {
		return emailVerified;
	}
	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
	public String getVerificationToken() {
		return verificationToken;
	}
	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}
	public LocalDateTime getResetTokenExpiry() {
		return resetTokenExpiry;
	}
	public void setResetTokenExpiry(LocalDateTime resetTokenExpiry) {
		this.resetTokenExpiry = resetTokenExpiry;
	}
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userName=" + userName + ", userAccount=" + userAccount
				+ ", userPassword=" + userPassword + ", userMail=" + userMail + ", userPhone=" + userPhone
				+ ", userBirthday=" + userBirthday + ", userAddress=" + userAddress + ", userImg=" + userImg
				+ ", creditCard=" + creditCard + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime
				+ ", permission=" + permission + ", resetToken=" + resetToken + ", emailVerified=" + emailVerified
				+ ", verificationToken=" + verificationToken + ", resetTokenExpiry=" + resetTokenExpiry + "]";
	}
	public UserDTO(Integer userId, String userName, String userAccount, String userPassword, String userMail,
			String userPhone, LocalDate userBirthday, String userAddress, String userImg, String creditCard,
			LocalDateTime createdTime, LocalDateTime updatedTime, Byte permission, List<Resettokens> resetToken,
			Boolean emailVerified, String verificationToken, LocalDateTime resetTokenExpiry) {
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
		this.resetToken = resetToken;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetTokenExpiry = resetTokenExpiry;
	}
	public UserDTO(String userName, String userAccount, String userPassword, String userMail, String userPhone,
			LocalDate userBirthday, String userAddress, String userImg, String creditCard, LocalDateTime createdTime,
			LocalDateTime updatedTime, Byte permission, List<Resettokens> resetToken, Boolean emailVerified,
			String verificationToken, LocalDateTime resetTokenExpiry) {
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
		this.resetToken = resetToken;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetTokenExpiry = resetTokenExpiry;
	}



    
}