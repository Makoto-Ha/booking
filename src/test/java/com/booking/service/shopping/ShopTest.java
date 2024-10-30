package com.booking.service.shopping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.bean.dto.shopping.ShopCartItemDTO;
import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.service.shopping.management.ShopOrderService;
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
		Result<ShopOrderDTO> orderDTO = shopClientService.getOrderById(1);
		System.out.println("測試結束");
	}
}
