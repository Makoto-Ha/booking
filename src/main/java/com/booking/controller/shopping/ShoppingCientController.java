package com.booking.controller.shopping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.bean.dto.shopping.PayDetailDTO;
import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.bean.dto.shopping.ShopCartItemDTO;
import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.service.shopping.ProductCategoryService;
import com.booking.service.shopping.ProductService;
import com.booking.service.shopping.ShopCartService;
import com.booking.service.shopping.ShopClientService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpServletRequest;

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
	 * 
	 * @param pageNumber
	 * @param categoryId
	 * @param orderOption
	 * @param model
	 * @return
	 */
	@GetMapping
	public String sendIndex(@RequestParam(required = false, defaultValue = "1") Integer pageNumber,
			@RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String orderOption,
			@RequestParam(required = false) String searchKeyword, Model model) {

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

		// 用於側邊欄分類
		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
		Result<Page<ProductCategoryDTO>> categoryResult = productCategoryService
				.findProductCategoryAll(productCategoryDTO);

		model.addAttribute("productDTO", productDTO);
		model.addAttribute("page", results.getData());
		model.addAttribute("categoryList", categoryResult.getData().getContent());
		model.addAttribute("searchKeyword", searchKeyword);
		return "client/shopping/shop";
	}

	// --------------------- 購物車 -------------------- //

	// 更新購物車商品數量
	@ResponseBody
	@PutMapping("/cart/update/{cartItemId}")
	public Result<String> updateCartItemQuantity(@PathVariable Integer cartItemId,
			@RequestBody Map<String, Integer> payload) {

		Integer quantity = payload.get("quantity");
		Result<ShopCartDTO> result = shopCartService.updateCartItemQuantity(cartItemId, quantity);

		if (result.isSuccess()) {
			return Result.success("更新成功");
		} else {
			return Result.failure(result.getMessage());
		}
	}

	// 商品加入購物車
	@ResponseBody
	@PostMapping("/cart/add")
	public ResponseEntity<Result<String>> addCartItem(@RequestBody ProductDTO productDTO) {

		Integer currentUserId = shopClientService.getCurrentUserId();
		Result<ShopCartDTO> userCart = shopCartService.findCartByUserId(currentUserId);

		ShopCartItemDTO shopCartItemDTO = new ShopCartItemDTO();
		shopCartItemDTO.setProductId(productDTO.getProductId());
		shopCartItemDTO.setPrice(productDTO.getProductPrice());
		shopCartItemDTO.setQuantity(1);

		Result<String> result = shopCartService.addShopCartItem(shopCartItemDTO, userCart.getData().getShopCartId());
		return ResponseEntity.ok(result);
	}

	@ResponseBody
	@DeleteMapping("/cart/remove/{cartItemId}")
	public Result<String> removeCartItem(@PathVariable Integer cartItemId) {
		Result<String> result = shopCartService.removeShopCartItem(cartItemId);
		if (result.isSuccess()) {
			return Result.success("刪除購物車項目成功");
		} else {
			return Result.failure(result.getMessage());
		}
	}

	// --------------------- 綠界 -------------------- //

	// 綠界支付結帳
	@PostMapping("/checkout/confirm")
	public String confirmCheckout(PayDetailDTO payDetailDTO, Model model) {

		Integer userId = shopClientService.getCurrentUserId();
		Result<ShopOrderDTO> order = shopClientService.getOrderByUserAndState(userId, 1);

		String paymentForm = shopClientService.ecpayCheckout(order.getData(), payDetailDTO, userId);

		model.addAttribute("paymentForm", paymentForm);
		return "client/shopping/ecpay-checkout";
	}

	// 綠界回傳資訊
	@ResponseBody
	@PostMapping("/checkout/success")
	public String checkoutSuccess(HttpServletRequest request) {

		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, String> paymentResult = new HashMap<>();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			paymentResult.put(entry.getKey(), entry.getValue()[0]);
		}
		if (!paymentResult.get("RtnCode").equals("1")) {
			return "0|error";
		}

		Integer userId = Integer.parseInt(paymentResult.get("CustomField1"));
		shopClientService.setOrderIsCompleted(userId);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		ShopOrderDTO shopOrderDTO = new ShopOrderDTO();
		shopOrderDTO.setMerchantTradeNo(paymentResult.get("MerchantTradeNo"));
		shopOrderDTO.setTransactionId(paymentResult.get("TradeNo"));
		shopOrderDTO.setPaymentMethod(1);
		shopOrderDTO.setPaymentState(2);
		shopOrderDTO.setPaymentCreatedAt(LocalDateTime.parse(paymentResult.get("TradeDate"), formatter));
		shopOrderDTO.setPaymentUpdatedAt(LocalDateTime.parse(paymentResult.get("PaymentDate"), formatter));

		shopClientService.setOrderDetail(userId, shopOrderDTO);

		return "1|OK";
	}

	// --------------------- 前往各頁面 -------------------- //

	// 商品頁面
	@GetMapping("/detail/{productId}")
	public String sendDetail(@PathVariable Integer productId, Model model) {
		Result<ProductDTO> result = productService.findProductDTOById(productId);
		Result<List<ProductDTO>> topSellingProducts = shopClientService.findTopSellingProducts(4);

		model.addAttribute("recommendedProducts", topSellingProducts.getData());
		model.addAttribute("productDTO", result.getData());
		return "client/shopping/shop-detail";
	}

	// 購物車畫面
	@GetMapping("/cart")
	public String sendCart(Model model) {
		Integer currentUserId = shopClientService.getCurrentUserId();
		Result<ShopCartDTO> userCart = shopCartService.findCartByUserId(currentUserId);
		model.addAttribute("shopCartDTO", userCart.getData());
		return "client/shopping/shop-cart";
	}

	// 訂單畫面
	@PostMapping("/checkout")
	public String sendCheckout(@RequestParam("selectedItems") List<Integer> selectedItemIds, Model model) {
		Integer userId = shopClientService.getCurrentUserId();
		Result<ShopOrderDTO> order = shopClientService.createOrderBySelect(userId, selectedItemIds);
		model.addAttribute("orderDTO", order.getData());
		return "client/shopping/shop-checkout";
	}

	// 結帳成功頁面
	@GetMapping("/orderDetail")
	public String orderDetail(Model model) {
		Integer userId = shopClientService.getCurrentUserId();
		Result<ShopOrderDTO> order = shopClientService.getOrderByUserAndState(userId, 2);
		model.addAttribute("orderDTO", order.getData());
		return "client/shopping/order-detail";
	}
}