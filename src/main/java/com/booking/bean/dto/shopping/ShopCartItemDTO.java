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
	
}
