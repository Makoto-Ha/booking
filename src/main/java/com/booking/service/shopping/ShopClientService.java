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
	
	
}
