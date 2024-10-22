package com.booking.bean.dto.shopping;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ShopCartDTO {
	
    private Integer shopCartId;
    private Integer cartState;
    private Integer userId;
    private Integer totalAmount;
    private List<ShopCartItemDTO> cartItems;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
	
    
    public ShopCartDTO() {
    	super();
    }


	public ShopCartDTO(Integer shopCartId, Integer cartState, Integer userId, Integer totalAmount,
			List<ShopCartItemDTO> cartItems, LocalDateTime updatedAt, LocalDateTime createdAt) {
		super();
		this.shopCartId = shopCartId;
		this.cartState = cartState;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.cartItems = cartItems;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}


	public ShopCartDTO(Integer shopCartId, Integer cartState, Integer userId, Integer totalAmount,
			LocalDateTime updatedAt, LocalDateTime createdAt) {
		super();
		this.shopCartId = shopCartId;
		this.cartState = cartState;
		this.userId = userId;
		this.totalAmount = totalAmount;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}
    
   
    
    
}
