package com.booking.bean.dto.user;

import com.booking.bean.pojo.user.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import com.booking.bean.pojo.user.Resettokens;

public class ResettokensDTO {
	private Integer tokenId;		
	private Integer userId;
	private User user;
	private String userTokenHash;
	private String expirationTime;
	public Integer getTokenId() {
		return tokenId;
	}
	public void setTokenId(Integer tokenId) {
		this.tokenId = tokenId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getUserTokenHash() {
		return userTokenHash;
	}
	public void setUserTokenHash(String userTokenHash) {
		this.userTokenHash = userTokenHash;
	}
	public String getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(String expirationTime) {
		this.expirationTime = expirationTime;
	}
	@Override
	public String toString() {
		return "ResettokensDTO [tokenId=" + tokenId + ", userId=" + userId + ", user=" + user + ", userTokenHash="
				+ userTokenHash + ", expirationTime=" + expirationTime + "]";
	}
	public ResettokensDTO() {
		super();
	}
	public ResettokensDTO(Integer tokenId, Integer userId, User user, String userTokenHash, String expirationTime) {
		super();
		this.tokenId = tokenId;
		this.userId = userId;
		this.user = user;
		this.userTokenHash = userTokenHash;
		this.expirationTime = expirationTime;
	}
	public ResettokensDTO(Integer userId, User user, String userTokenHash, String expirationTime) {
		super();
		this.userId = userId;
		this.user = user;
		this.userTokenHash = userTokenHash;
		this.expirationTime = expirationTime;
	}


	
}
