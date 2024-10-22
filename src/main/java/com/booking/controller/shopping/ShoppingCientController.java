package com.booking.controller.shopping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.bean.dto.shopping.ShopCartItemDTO;
import com.booking.service.shopping.ProductCategoryService;
import com.booking.service.shopping.ProductService;
import com.booking.service.shopping.ShopCartService;
import com.booking.service.shopping.ShopClientService;
import com.booking.utils.Result;

@Controller
@RequestMapping("/shop")
public class ShoppingCientController {

	@Autowired
	private ShopClientService shopClientService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ShopCartService shopCartService;

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

	// 商品加入購物車
	@ResponseBody
    @PostMapping("/cart/add")	
    public ResponseEntity<Result<String>> addCartItem(@RequestBody ProductDTO productDTO) {
		
		System.out.println("======"+productDTO);
		Integer currentUserId = shopCartService.getCurrentUserId();
		Result<ShopCartDTO> userCart = shopCartService.findCartByUserId(currentUserId);
	    
    	ShopCartItemDTO shopCartItemDTO = new ShopCartItemDTO();
    	shopCartItemDTO.setProductId(productDTO.getProductId());
    	shopCartItemDTO.setPrice(productDTO.getProductPrice());
    	shopCartItemDTO.setQuantity(1);
    	
    	Result<String> result = shopCartService.addShopCartItem(shopCartItemDTO,userCart.getData().getShopCartId());
    	return ResponseEntity.ok(result);
    }
	
	// 移除購物車商品
	@GetMapping("/cart/remove/{cartItemId}")
	public String removeCartItem(@PathVariable Integer cartItemId, Model model) {
		System.out.println("cartItemId:"+cartItemId);
		Result<String> result = shopCartService.removeShopCartItem(cartItemId);
		model.addAttribute("shopCartDTO", result.getData());
		return "redirect:/shop/cart";
	}
	
	// 購物車結帳
	@ResponseBody
	@PostMapping("/ecpayCheckout")
	public String ecpayCheckout() {
		String ecpayCheckout = shopClientService.ecpayCheckout();
		return ecpayCheckout;
	}
	
	// 更新購物車商品數量
	@PutMapping("/cart/update/{cartItemId}")
	public String updateCartItemQuantity(@PathVariable Integer cartItemId, Integer quantity,Model model) {
		Result<ShopCartDTO> result = shopCartService.updateCartItemQuantity(cartItemId, quantity);
		model.addAttribute("shopCartDTO", result.getData());
		return "redirect:/shop/cart";
	}
	
	// 商品頁面
	@GetMapping("/detail/{productId}")
	public String sendDetail(@PathVariable Integer productId,Model model) {
		
		Result<ProductDTO> result = productService.findProductDTOById(productId);
	    Result<List<ProductDTO>> topSellingProducts = shopClientService.findTopSellingProducts(4);
	    
	    model.addAttribute("recommendedProducts", topSellingProducts.getData());
		model.addAttribute("productDTO", result.getData());
		return "client/shopping/shop-detail";
	}
	
	// 購物車畫面
	@GetMapping("/cart")
	public String sendCart(Model model) {
		Integer currentUserId = shopCartService.getCurrentUserId();
		Result<ShopCartDTO> userCart = shopCartService.findCartByUserId(currentUserId);
		
		
		model.addAttribute("shopCartDTO", userCart.getData());
		return "client/shopping/shop-cart";
	}
	
	// 訂單畫面
	@GetMapping("/checkout")
	public String sendCheckout(Model model) {
		return "client/shopping/shop-checkout";
	}
	
	
}