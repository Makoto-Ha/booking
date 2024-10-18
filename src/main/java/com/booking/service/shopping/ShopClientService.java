package com.booking.service.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ShopOrderRepository;
import com.booking.dao.shopping.TestUserRepository;

@Service
public class ShopClientService {

	@Autowired
	private ShopOrderRepository shopOrderRepository;
	@Autowired
	private TestUserRepository testUserRepository;
	@Autowired
	private ProductRepository productRepository;
	
	
	
	
}
