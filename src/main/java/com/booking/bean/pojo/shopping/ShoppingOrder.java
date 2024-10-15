package com.booking.bean.pojo.shopping;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "shopping_order")
@DynamicInsert
public class ShoppingOrder {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderId;
	
	private Integer userId;
	
	private Integer orderPrices;
	
	private Integer orderState;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd",timezone = "UTF+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd") // 檢查進來的時間，並做格式化
	private LocalDate createdDate;

	@PrePersist // 當物件要轉換成 Persistent 狀態時，先做這個方法
	public void onCreate() {
		if (createdDate == null) {
			createdDate = LocalDate.now();
		}
	}
	
}
