package com.booking.service.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ShopCartItemRepository;
import com.booking.dao.shopping.ShopCartRepository;

@Service
public class ShopCartService {

	@Autowired
	private ShopCartRepository shopCartRepository;

	@Autowired
	private ShopCartItemRepository shopCartItemRepository;

	@Autowired
	private ProductRepository productRepository;
	
	
	// 获取用户的购物车
    public ShopCartDTO findCartByUserId(Integer userId) {
       
    	
        return null;
    }

    // 更新购物车中的商品数量
    public void updateCartItems() {
      
    }

    // 移除购物车中的商品
    public void removeCartItem(Integer cartItemId) {
        shopCartItemRepository.deleteById(cartItemId);
    }

    // 创建订单
    public void createOrder() {
        
    }

    // 清空购物车
    public void clearCart() {
    	
    }
	
	
	

}
