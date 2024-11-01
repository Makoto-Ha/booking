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

import com.booking.bean.dto.shopping.PayDetailDTO;
import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.service.shopping.ShopCartService;
import com.booking.service.shopping.ShopClientService;
import com.booking.service.shopping.management.ProductCategoryService;
import com.booking.service.shopping.management.ProductService;
import com.booking.utils.Result;

@Controller
@RequestMapping("/shop")
public class ShopCientController {

	@Autowired
	private ShopClientService shopClientService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategoryService;
	@Autowired
	private ShopCartService shopCartService;

	//商城首頁
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

	// --------------------- 金流 ------------------------ //

	// LINEPAY 回傳資料
	@GetMapping("/checkout/linepay/confirm")
	public String linePayConfirm(@RequestParam("transactionId") String transactionId,
			@RequestParam("orderId") String merchantTradeNo) {
		
		Result<ShopOrderDTO> result = shopClientService.getOrderByMerchantTradeNo(merchantTradeNo);
		ShopOrderDTO shopOrderDTO = result.getData();
		Integer orderId = shopOrderDTO.getOrderId();
		// 設置查詢到的訂單的交易資訊
		shopOrderDTO.setTransactionId(transactionId);
		shopOrderDTO.setPaymentMethod(2);
		shopOrderDTO.setPaymentState(2);
		shopOrderDTO.setOrderState(2);

		// 更新訂單狀態為已完成
		shopClientService.setOrderIsCompleted(shopOrderDTO.getUserId(), orderId);
		// 更新訂單詳細資訊
		shopClientService.setOrderDetail(shopOrderDTO.getUserId(), shopOrderDTO);

		// 返回訂單詳細頁面
		return "redirect:http://localhost:8080/booking/shop/orderDetail/" + orderId;
	}

	// 綠界支付結帳
	@PostMapping("/checkout/confirm")
	public String confirmCheckout(PayDetailDTO payDetailDTO, Model model) {

		Integer userId = shopClientService.getCurrentUserId();
		Result<ShopOrderDTO> order = shopClientService.getOrderByUserAndState(userId, 1);
		String paymentForm = shopClientService.ecpayCheckout(order.getData(), payDetailDTO, userId);

		model.addAttribute("paymentForm", paymentForm);
		return "client/shopping/ecpay-checkout";
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
		Result<PayDetailDTO> userPayDetail = shopClientService.getUserPayDetail(userId);
		model.addAttribute("userPayDetail", userPayDetail.getData());
		model.addAttribute("orderDTO", order.getData());
		return "client/shopping/shop-checkout";
	}

	// 立即購買的訂單畫面
	@GetMapping("/checkout/buyNow")
	public String sendCheckoutForBuyNow(Model model) {
		Integer userId = shopClientService.getCurrentUserId();
		Result<ShopOrderDTO> order = shopClientService.getOrderByUserAndState(userId, 1);
		Result<PayDetailDTO> userPayDetail = shopClientService.getUserPayDetail(userId);
		model.addAttribute("userPayDetail", userPayDetail.getData());
		model.addAttribute("orderDTO", order.getData());
		return "client/shopping/shop-checkout";
	}

	// 結帳成功頁面
	@GetMapping("/orderDetail/{orderId}")
	public String orderDetail(@PathVariable Integer orderId, Model model) {
		Result<ShopOrderDTO> result = shopClientService.getOrderById(orderId);
		ShopOrderDTO orderDTO = result.getData();
		model.addAttribute("orderDTO", orderDTO);
		return "client/shopping/order-detail";
	}
}