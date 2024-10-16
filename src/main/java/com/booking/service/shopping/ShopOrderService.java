package com.booking.service.shopping;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.booking.bean.pojo.shopping.TestUser;
import com.booking.dao.shopping.ShopOrderRepository;
import com.booking.dao.shopping.TestUserRepository;
import com.booking.utils.Result;

@Service
public class ShopOrderService {

	@Autowired
	private ShopOrderRepository shopOrderRepository;
	@Autowired
	private TestUserRepository testUserRepository;
	@Autowired
	private TestUserService testUserService;

	public Result<String> saveOrder() {

		ShopOrderDTO shopOrderDTO = new ShopOrderDTO();

		shopOrderDTO.setOrderPrice(1000); // 訂單價格
		shopOrderDTO.setOrderState(1); // 訂單狀態: pending
		shopOrderDTO.setPaymentMethod(1); // 支付方式: 綠界
		shopOrderDTO.setPaymentState(1); // 支付狀態: 未付款
		shopOrderDTO.setMerchantTradeNo("T123456789"); // 測試的訂單號碼
		shopOrderDTO.setTransactionId("TX123456789"); // 測試的交易號碼

		ShopOrder shopOrder = new ShopOrder();
		BeanUtils.copyProperties(shopOrderDTO, shopOrder);
		
		Optional<TestUser> result = testUserRepository.findById(1);
		shopOrder.setUsers(result.get());
		
		shopOrderRepository.save(shopOrder);

		return Result.success("訂單新增成功");
	}

}
