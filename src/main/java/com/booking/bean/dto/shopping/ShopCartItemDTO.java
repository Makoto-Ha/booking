package com.booking.bean.dto.shopping;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ShopCartItemDTO {

    private Integer cartItemId;
    private Integer shopCartId;
    private Integer productId;
    private String productName;
    private String productImage;
    private Integer quantity;
    private Integer price;
    private Integer subtotal;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
	
    
    public ShopCartItemDTO() {
		super();
	}


	public ShopCartItemDTO(Integer cartItemId, Integer shopCartId, Integer productId, String productName,
			String productImage, Integer quantity, Integer price, Integer subtotal, LocalDateTime updatedAt,
			LocalDateTime createdAt) {
		super();
		this.cartItemId = cartItemId;
		this.shopCartId = shopCartId;
		this.productId = productId;
		this.productName = productName;
		this.productImage = productImage;
		this.quantity = quantity;
		this.price = price;
		this.subtotal = subtotal;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}
	
    
    
}
