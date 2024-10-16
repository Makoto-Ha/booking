package com.booking.service.shopping;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.booking.BookingApplication;

@SpringBootTest(classes = BookingApplication.class) // 確保這裡指定了正確的啟動類
public class ShopOrderServiceTest {

	@Autowired
	private ShopOrderService shopOrderService;

	@Test
	public void testSaveOrder() {
		System.out.println("測試開始");
		var result = shopOrderService.saveOrder();
		System.out.println("測試結束");
		assertEquals("訂單新增成功", result.getMessage());
	}
}
