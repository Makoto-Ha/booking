package com.booking.bean.dto.shopping;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ShopOrderItemDTO {

    private Integer orderItemId;
  
    private Integer productId;
    
    private String productName;
    
    private Integer quantity;
    
    private Integer price;
    
    private Integer subtotal;
   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTF+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime updatedAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTF+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

	public ShopOrderItemDTO() {
		super();
	}

	public ShopOrderItemDTO(Integer orderItemId, Integer productId, String productName, Integer quantity,
			Integer price, Integer subtotal, LocalDateTime updatedAt, LocalDateTime createdAt) {
		super();
		this.orderItemId = orderItemId;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.price = price;
		this.subtotal = subtotal;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}
    
    
    
}
