package com.booking.service.shopping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.bean.dto.shopping.ShopCartItemDTO;
import com.booking.utils.Result;

@SpringBootTest
public class ShopTest {

	@Autowired
	private ShopOrderService shopOrderService;
	@Autowired
	private ShopClientService shopClientService;
	@Autowired
	private ShopCartService shopCartService;
	

	@Test
	public void testShop() {
		System.out.println("測試開始");

//		ShopCartItemDTO shopCartItemDTO = new ShopCartItemDTO();
//		shopCartItemDTO.setProductId(1);
//		shopCartItemDTO.setQuantity(2);
//		shopCartItemDTO.setPrice(1000);
//		shopClientService.addShopCartItem(shopCartItemDTO, 3 );
//		Result<ShopCartDTO> shopCart = shopClientService.getShopCart(1);
//		System.out.println(shopCart.getData());

		
		
//		shopClientService.deleteShopCart(1);
		
		System.out.println("測試結束");
	}
}
