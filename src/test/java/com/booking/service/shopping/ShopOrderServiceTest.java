package com.booking.service.shopping;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.bean.dto.shopping.ShopCartItemDTO;
import com.booking.utils.Result;

@SpringBootTest
public class ShopOrderServiceTest {

	@Autowired
	private ShopOrderService shopOrderService;
	@Autowired
	private ShopClientService shopClientService;
	@Autowired
	private ShopCartService shopCartService;
	

	@Test
	public void testSaveOrder() {
		System.out.println("測試開始");

		
		System.out.println("測試結束");
	}
}
