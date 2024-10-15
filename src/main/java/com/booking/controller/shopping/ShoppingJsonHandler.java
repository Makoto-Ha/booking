package com.booking.controller.shopping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.service.shopping.ProductCategoryService;
import com.booking.service.shopping.ProductService;
import com.booking.utils.JsonUtil;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api",  produces = "application/json")
public class ShoppingJsonHandler {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	
	@GetMapping("/product/{id}")
	private String findProductById(@PathVariable Integer id) {
		Result<ProductDTO> result = productService.findProductDTOById(id);
		if (result.isFailure()) {
			return result.getMessage();
		}
		return JsonUtil.toJson(result.getData());
	}
	
	
	@GetMapping("/product")
	private String findProductByName(@RequestParam String name, HttpServletResponse response) {
		Result<List<ProductDTO>> result = productService.findProductsByName(name);
		if (result.isFailure()) {
			return result.getMessage();
		}
		return JsonUtil.toJson(result.getData());
	}
	
	@GetMapping("/productCategory/{id}")
	private String findProductCategoryById(@PathVariable Integer id) {
		Result<ProductCategoryDTO> result = productCategoryService.findCategoryDTOById(id);
		if (result.isFailure()) {
			return result.getMessage();
		}
		return JsonUtil.toJson(result.getData());
	}
	
	@GetMapping("/productCategory")
	private String findProductCategoryByName(@RequestParam String name, HttpServletResponse response) {
		Result<List<ProductCategoryDTO>> result = productCategoryService.findCategoryDTOByName(name);
		if (result.isFailure()) {
			return result.getMessage();
		}
		return JsonUtil.toJson(result.getData());
	}
	
}
