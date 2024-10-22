package com.booking.service.shopping;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ShopCartItemRepository;
import com.booking.dao.shopping.ShopCartRepository;
import com.booking.dao.shopping.ShopOrderRepository;
import com.booking.utils.Result;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class ShopClientService {

	@Autowired
	private ShopOrderRepository shopOrderRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ShopCartRepository shopCartRepository;
	@Autowired
	private ShopCartItemRepository shopCartItemRepository;
	

	/**
	 * 熱銷商品
	 * 
	 * @param topNumber
	 * @return
	 */

	public Result<List<ProductDTO>> findTopSellingProducts(Integer topNumber) {
		PageRequest pageable = PageRequest.of(0, topNumber);
		List<ProductDTO> result = productRepository.findTopSellingProductDTOs(pageable);
		return Result.success(result);
	}



	// 綠界金流

	public String ecpayCheckout() {

		AllInOne all = new AllInOne("");

		String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(uuid);
		obj.setMerchantTradeDate("2017/01/01 08:05:23");
		obj.setMerchantID("3002607");
		obj.setTotalAmount("50");
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		obj.setReturnURL("http://localhost:8080/booking/shop/cart");
		obj.setNeedExtraPaidInfo("N");
		obj.setClientBackURL("http://localhost:8080/booking/shop");
		String form = all.aioCheckOut(obj, null);

		return form;
	}

}
