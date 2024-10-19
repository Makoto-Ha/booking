package com.booking.bean.dto.shopping;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ShopCartDTO {
	
    private Integer shopCartId;
    private Integer cartState;
    private Integer userId;
    private List<ShopCartItemDTO> cartItems;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
	
}
