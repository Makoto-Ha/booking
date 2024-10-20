package com.booking.service.shopping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ShopCartItemRepository;
import com.booking.dao.shopping.ShopCartRepository;
import com.booking.dao.shopping.ShopOrderRepository;
import com.booking.dao.shopping.TestUserRepository;
import com.booking.utils.Result;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;

@Service
public class ShopClientService {

	@Autowired
	private ShopOrderRepository shopOrderRepository;
	@Autowired
	private TestUserRepository testUserRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
    private ShopCartRepository shopCartRepository;
    @Autowired
    private ShopCartItemRepository shopCartItemRepository;
	
    
    
    
    
    
    /**
     * 熱銷商品
     * @param topNumber
     * @return
     */
    
	public Result<List<ProductDTO>> findTopSellingProducts(Integer topNumber) {
		
		PageRequest pageable = PageRequest.of(0, topNumber);
		List<ProductDTO> result = productRepository.findTopSellingProductDTOs(pageable);
		
		return Result.success(result);
	}
	
	
	
	public String ecpayCheckout() {

		AllInOne all = new AllInOne("");

		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo("kdkfhm21111354545");
		obj.setMerchantTradeDate("2017/01/01 08:05:23");
		obj.setTotalAmount("50");
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		obj.setReturnURL("https://c9a5-2402-7500-46b-3963-442a-d162-9bdb-95ba.ngrok-free.app/booking/");//只接受 https 開頭的網站
		obj.setNeedExtraPaidInfo("N");
		obj.setClientBackURL("https://c9a5-2402-7500-46b-3963-442a-d162-9bdb-95ba.ngrok-free.app/booking/");
		String form = all.aioCheckOut(obj, null);

		return form;
	}
	
	
}
