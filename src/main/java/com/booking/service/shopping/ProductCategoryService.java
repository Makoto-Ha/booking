package com.booking.service.shopping;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.pojo.shopping.ProductCategory;
import com.booking.dao.shopping.ProductCategoryRepository;
import com.booking.utils.Result;

@Service
public class ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	
	public Result<ProductCategory> findProductCategoryById(Integer productCategoryId){
		Optional<ProductCategory> optional = productCategoryRepository.findById(productCategoryId);
		if (optional.isPresent()) {
			return Result.success(optional.get());
		}
		return Result.failure("Category Is Not Found");
	}
	
	
}
