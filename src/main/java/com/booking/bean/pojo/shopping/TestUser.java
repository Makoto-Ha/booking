package com.booking.bean.pojo.shopping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class TestUser {
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
    private List<ShopOrder> shopOrder;

    
    @Override
    public String toString() {
        return "TestUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", userMail='" + userMail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userBirthday=" + userBirthday +
                ", userAddress='" + userAddress + '\'' +
                ", creditCard='" + creditCard + '\'' +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", permission=" + permission +
                ", shopOrderCount=" + (shopOrder != null ? shopOrder.size() : "null") +  // 避免遞歸，僅打印訂單數量
                '}';
    }

}