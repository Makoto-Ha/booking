package com.booking.controller.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.pojo.shopping.ProductCategory;
import com.booking.service.shopping.ProductCategoryService;
import com.booking.service.shopping.ProductService;
import com.booking.service.shopping.ShopClientService;
import com.booking.utils.Result;

@Controller
public class ShoppingCientController {

	@Autowired
	private ShopClientService shopclientService;
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductCategoryService productCategoryService;
	
//	@GetMapping("/shop")
//	public String sendIndex(@RequestParam(required = false,defaultValue = "1") Integer pageNumber, @RequestParam(required = false) Integer categoryId,Model model) {
//		ProductDTO productDTO = new ProductDTO();
//		productDTO.setPageNumber(pageNumber);
//		Result<Page<ProductDTO>> results = productService.findProductAll(productDTO);
//		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
//		Result<Page<ProductCategoryDTO>> categoryResult = productCategoryService.findProductCategoryAll(productCategoryDTO);
//		
//		model.addAttribute("categoryList",categoryResult.getData().getContent());
//		model.addAttribute("productDTO", productDTO);
//		model.addAttribute("page", results.getData());
//		return "client/shopping/shop";
//	}

	@GetMapping("/shop")
	public String sendIndex(
	    @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
	    @RequestParam(required = false) Integer categoryId,
	    Model model) {
		ProductDTO productDTO = new ProductDTO();
	    if (categoryId != null) {
	        // 分页获取指定分类下的产品
	    	productDTO.setCategoryId(categoryId);
	    	productDTO.setPageNumber(pageNumber);
	        Result<Page<ProductDTO>> results = productService.findProductsByCategoryId(productDTO);
	        model.addAttribute("page", results.getData());
	        model.addAttribute("categoryId", categoryId);
	    } else {
	        // 获取所有产品
	        productDTO.setPageNumber(pageNumber);
	        Result<Page<ProductDTO>> results = productService.findProductAll(productDTO);
	        model.addAttribute("page", results.getData());
	    }

	    // 获取所有分类，用于侧边栏
	    ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
	    Result<Page<ProductCategoryDTO>> categoryResult = productCategoryService.findProductCategoryAll(productCategoryDTO);
	    model.addAttribute("productDTO", productDTO);
	    model.addAttribute("categoryList", categoryResult.getData().getContent());
	    return "client/shopping/shop";
	}
	
}