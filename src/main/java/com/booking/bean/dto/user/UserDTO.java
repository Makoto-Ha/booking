package com.booking.bean.dto.user;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.booking.bean.pojo.user.Resettokens;


public class UserDTO {  
    private Integer userId;   
    private String userName;
    private String userMail;
    private String userPhone;
    private String userAddress;
    private String userAccount;   
    private String userPassword;      
    private LocalDate userBirthday;  
    private String imgsFile;
    private String creditCard;
    private LocalDateTime createdTime; 
    private LocalDateTime updatedTime;         
	private List<Resettokens> resetToken = new LinkedList<Resettokens>();
	private Boolean emailVerified = false;	
	private String verificationToken;		
	private LocalDateTime resetTokenExpiry;
//   private List<PackageTourOrder> packageTourOrder;
//    private List<ShopOrder> shoppingOrder; 
//    private List<BookingOrder> bookingOrder;     		
	private Integer pageNumber = 1;
	private String attrOrderBy = "userId";
	private Boolean selectedSort = true;
	
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
	
	
	public String getImgsFile() {
		return imgsFile;
	}
	public void setImgsFile(String imgsFile) {
		this.imgsFile = imgsFile;
	}
	

	
	

	public UserDTO(String userAccount, String userPassword, String userMail, String userPhone, LocalDate userBirthday,
			String userAddress, String imgsFile, String creditCard, LocalDateTime createdTime,
			LocalDateTime updatedTime, Byte permission, List<Resettokens> resetToken, Boolean emailVerified,
			String verificationToken, LocalDateTime resetTokenExpiry, List<PackageTourOrder> packageTourOrder,
			List<ShopOrder> shoppingOrder, List<BookingOrder> bookingOrder, Integer pageNumber, String attrOrderBy,
			Boolean selectedSort) {
		super();
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.imgsFile = imgsFile;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		
		this.resetToken = resetToken;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetTokenExpiry = resetTokenExpiry;
	
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}
	public UserDTO(String userName, String userAccount, String userPassword, String userMail, String userPhone,
			LocalDate userBirthday, String userAddress, String imgsFile, String creditCard, LocalDateTime createdTime,
			LocalDateTime updatedTime, Byte permission, List<Resettokens> resetToken, Boolean emailVerified,
			String verificationToken, LocalDateTime resetTokenExpiry, List<PackageTourOrder> packageTourOrder,
			List<ShopOrder> shoppingOrder, List<BookingOrder> bookingOrder, Integer pageNumber, String attrOrderBy,
			Boolean selectedSort) {
		super();
		this.userName = userName;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.imgsFile = imgsFile;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	
		this.resetToken = resetToken;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetTokenExpiry = resetTokenExpiry;
	
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}
	public UserDTO(Integer userId, String userName, String userAccount, String userPassword, String userMail,
			String userPhone, LocalDate userBirthday, String userAddress, String imgsFile, String creditCard,
			LocalDateTime createdTime, LocalDateTime updatedTime, Byte permission, List<Resettokens> resetToken,
			Boolean emailVerified, String verificationToken, LocalDateTime resetTokenExpiry,
			List<PackageTourOrder> packageTourOrder, List<ShopOrder> shoppingOrder, List<BookingOrder> bookingOrder,
			Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.imgsFile = imgsFile;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	
		this.resetToken = resetToken;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetTokenExpiry = resetTokenExpiry;
		
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}
	
	
	
	public UserDTO(Integer userId, String userName, String userMail, String userAccount, String userPassword,
			String userPhone, LocalDate userBirthday, String userAddress, String imgsFile, String creditCard,
			LocalDateTime createdTime, LocalDateTime updatedTime, List<Resettokens> resetToken, Boolean emailVerified,
			String verificationToken, LocalDateTime resetTokenExpiry, Integer pageNumber, String attrOrderBy,
			Boolean selectedSort) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userMail = userMail;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.imgsFile = imgsFile;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.resetToken = resetToken;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetTokenExpiry = resetTokenExpiry;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}
	
	
	
	public UserDTO(String userName, String userMail, String userAccount, String userPassword, String userPhone,
			LocalDate userBirthday, String userAddress, String imgsFile, String creditCard, LocalDateTime createdTime,
			LocalDateTime updatedTime, List<Resettokens> resetToken, Boolean emailVerified, String verificationToken,
			LocalDateTime resetTokenExpiry, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.userName = userName;
		this.userMail = userMail;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.imgsFile = imgsFile;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.resetToken = resetToken;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetTokenExpiry = resetTokenExpiry;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}
	
	
	
	
	
	
	
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userName=" + userName + ", userMail=" + userMail + ", userAccount="
				+ userAccount + ", userPassword=" + userPassword + ", userPhone=" + userPhone + ", userBirthday="
				+ userBirthday + ", userAddress=" + userAddress + ", imgsFile=" + imgsFile + ", creditCard="
				+ creditCard + ", createdTime=" + createdTime + ", updatedTime=" + updatedTime + ", resetToken="
				+ resetToken + ", emailVerified=" + emailVerified + ", verificationToken=" + verificationToken
				+ ", resetTokenExpiry=" + resetTokenExpiry + ", pageNumber=" + pageNumber + ", attrOrderBy="
				+ attrOrderBy + ", selectedSort=" + selectedSort + "]";
	}
	public UserDTO(String userMail, String userAccount, String userPassword, String userPhone, LocalDate userBirthday,
			String userAddress, String imgsFile, String creditCard, LocalDateTime createdTime,
			LocalDateTime updatedTime, List<Resettokens> resetToken, Boolean emailVerified, String verificationToken,
			LocalDateTime resetTokenExpiry, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.userMail = userMail;
		this.userAccount = userAccount;
		this.userPassword = userPassword;
		this.userPhone = userPhone;
		this.userBirthday = userBirthday;
		this.userAddress = userAddress;
		this.imgsFile = imgsFile;
		this.creditCard = creditCard;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.resetToken = resetToken;
		this.emailVerified = emailVerified;
		this.verificationToken = verificationToken;
		this.resetTokenExpiry = resetTokenExpiry;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}
	public UserDTO() {
		super();
	}
	
	
	}
   
	
