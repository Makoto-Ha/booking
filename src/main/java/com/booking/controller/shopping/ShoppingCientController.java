package com.booking.controller.shopping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.service.shopping.ProductCategoryService;
import com.booking.service.shopping.ProductService;
import com.booking.service.shopping.ShopClientService;
import com.booking.utils.Result;

@Controller
@RequestMapping("/shop")
public class ShoppingCientController {

	@Autowired
	private ShopClientService shopclientService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 商城首页
	 * @param pageNumber
	 * @param categoryId
	 * @param orderOption
	 * @param model
	 * @return
	 */
	@GetMapping
	public String sendIndex(@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false) Integer categoryId,
			@RequestParam(required = false) String orderOption,
			@RequestParam(required = false) String searchKeyword,
			Model model) {

		ProductDTO productDTO = new ProductDTO();
		productDTO.setPageNumber(pageNumber);
		productDTO.setProductName(searchKeyword);
		// 拆排序
		if (orderOption != null && !orderOption.isEmpty()) { 
			String[] parts = orderOption.split("_");
			if (parts.length == 2) {
				productDTO.setAttrOrderBy(parts[0]);
				productDTO.setSelectedSort("desc".equals(parts[1]));
			}
		}
		
	    Result<Page<ProductDTO>> results;
	    
	    if (searchKeyword != null && !searchKeyword.isEmpty()) {
	        results = productService.findProducts(productDTO);
	    } else if (categoryId != null) {
	        // 分页获取指定分类下的产品
	        productDTO.setCategoryId(categoryId);
	        results = productService.findProductsByCategoryId(productDTO);
	        model.addAttribute("categoryId", categoryId);
	    } else {
	        results = productService.findProductAll(productDTO);
	    }

	    model.addAttribute("productDTO", productDTO);
	    model.addAttribute("page", results.getData());

		// 获取所有分类，用于侧边栏
		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
		Result<Page<ProductCategoryDTO>> categoryResult = productCategoryService
				.findProductCategoryAll(productCategoryDTO);
		
		model.addAttribute("productDTO", productDTO);
		model.addAttribute("categoryList", categoryResult.getData().getContent());
		model.addAttribute("searchKeyword", searchKeyword);
		return "client/shopping/shop";
	}

	//---前往----
	// 商品頁面
	// 購物車畫面
	// 訂單畫面

	
	
	//---功能---
	// 加入購物車
	// 移除購物車
	// 訂單結帳
	
	@ResponseBody
	@PostMapping("/ecpayCheckout")
	public String ecpayCheckout() {
		String ecpayCheckout = shopclientService.ecpayCheckout();
		return ecpayCheckout;
	}
	
	
	
	
	@GetMapping("/detail/{productId}")
	public String sendDetail(@PathVariable Integer productId,Model model) {
		
		Result<ProductDTO> result = productService.findProductDTOById(productId);

	    Result<List<ProductDTO>> topSellingProducts = shopclientService.findTopSellingProducts(4);
	    
	    model.addAttribute("recommendedProducts", topSellingProducts.getData());
		model.addAttribute("productDTO", result.getData());
		return "client/shopping/shop-detail";
	}
	
	@GetMapping("/cart")
	public String sendCart(Model model) {
		return "client/shopping/shop-cart";
	}
	
	@GetMapping("/checkout")
	public String sendCheckout(Model model) {
		
		return "client/shopping/shop-checkout";
	}
	
	
}