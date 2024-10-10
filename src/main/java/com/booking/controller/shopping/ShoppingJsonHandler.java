package com.booking.controller.shopping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.service.shopping.ProductService;
import com.booking.utils.JsonUtil;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api",  produces = "application/json")
public class ShoppingJsonHandler {
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 返回所有房間類型
	 * @param roomtypeId
	 * @return
	 */
	@GetMapping("/product/{id}")
	private String findProductById(@PathVariable Integer id) {
		Result<ProductDTO> result = productService.findProductDTOById(id);
		if (result.isFailure()) {
			return result.getMessage();
		}
		return JsonUtil.toJson(result.getData());
	}
	
	/**
	 * 根據房間類型名稱獲取房間類型
	 * @param name
	 * @return
	 */
	@GetMapping("/product")
	private String findProductByName(@RequestParam String name, HttpServletResponse response) {
		
		Result<List<ProductDTO>> result = productService.findProductsByName(name);
		if (result.isFailure()) {
			return result.getMessage();
		}
		return JsonUtil.toJson(result.getData());
	}
	

}
