package com.booking.dao.shopping;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.booking.bean.dto.shopping.ShopCartItemDTO;
import com.booking.bean.pojo.shopping.Product;
import com.booking.bean.pojo.shopping.ShopCart;
import com.booking.bean.pojo.shopping.ShopCartItem;

public interface ShopCartItemRepository extends JpaRepository<ShopCartItem, Integer> {

	ShopCartItem findByShopCartAndProduct(ShopCart shopCart, Product product);
	
	@Query("SELECT new com.booking.bean.dto.shopping.ShopCartItemDTO("
            + "sci.cartItemId, sci.shopCart.shopCartId, sci.product.productId, sci.product.productName, sci.product.productImage, sci.quantity, sci.product.productPrice, sci.subtotal, sci.updatedAt, sci.createdAt) "
            + "FROM ShopCartItem sci WHERE sci.shopCart.shopCartId = :cartId")
	List<ShopCartItemDTO> findAllByCartId(Integer cartId);

	Integer countByShopCart_ShopCartId(Integer shopCartId);
	
}