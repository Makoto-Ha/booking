package com.booking.bean.pojo.user;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name="password_reset_tokens")
public class Resettokens implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id @Column(name="token_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tokenId;
	
	@Column(name="user_id")
	private Integer userId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id",insertable = false,updatable = false)
	private User user;
	
	@Column(name="user_tokenHash")
	private String userTokenHash;
	
	@Column(name="expiration_time", updatable = false, insertable = false)
	private String expirationTime;

	public Object getUserTokenHash() {
		// TODO Auto-generated method stub
		return null;
	}


	
}
