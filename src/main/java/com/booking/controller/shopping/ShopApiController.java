package com.booking.controller.shopping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.shopping.AddCartDTO;
import com.booking.bean.dto.shopping.PayDetailDTO;
import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.service.shopping.LinePayService;
import com.booking.service.shopping.ShopCartService;
import com.booking.service.shopping.ShopClientService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/shop/api")
public class ShopApiController {

	@Autowired
	private ShopClientService shopClientService;
	@Autowired
	private ShopCartService shopCartService;
	@Autowired
	private LinePayService linePayService;

	// 立刻購買
	@PostMapping("/buyNow")
	public Result<String> buyNow(@RequestBody AddCartDTO addCartDTO) {
		Integer userId = shopClientService.getCurrentUserId();
		Result<String> result = shopClientService.createOrderByProductAndUser(userId, addCartDTO);
		if (result.isSuccess()) {
			return Result.success("立即購買成功");
		} else {
			return Result.failure(result.getMessage());
		}
	}

	// LINEPAY
	@PostMapping("/checkout/linepay")
	public ResponseEntity<Void> linePayCheckout(@ModelAttribute PayDetailDTO payDetailDTO) {
		Integer userId = shopClientService.getCurrentUserId();
		Result<ShopOrderDTO> orderDTO = shopClientService.getOrderByUserAndState(userId, 1);

		try {
			String paymentUrl = linePayService.requestPayment(orderDTO.getData(), userId.toString(), payDetailDTO);
			// 重定向用戶到 LinePay 支付頁面
			return ResponseEntity.status(HttpStatus.FOUND).header(HttpHeaders.LOCATION, paymentUrl).build();

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	// 綠界回傳資訊
	@PostMapping("/checkout/success")
	public String checkoutSuccess(HttpServletRequest request) {

		// 取得回傳參數
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, String> paymentResult = new HashMap<>();

		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			paymentResult.put(entry.getKey(), entry.getValue()[0]);
		}
		if (!paymentResult.get("RtnCode").equals("1")) {
			return "0|error";
		}
		System.out.println(paymentResult);
		// 更新訂單狀態
		Integer userId = Integer.parseInt(paymentResult.get("CustomField1"));
		int orderId = Integer.parseInt(paymentResult.get("CustomField2"));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

		shopClientService.setOrderIsCompleted(userId, orderId);

		ShopOrderDTO shopOrderDTO = new ShopOrderDTO();
		shopOrderDTO.setOrderId(orderId);
		shopOrderDTO.setMerchantTradeNo(paymentResult.get("MerchantTradeNo"));
		shopOrderDTO.setTransactionId(paymentResult.get("TradeNo"));
		shopOrderDTO.setPaymentMethod(1);
		shopOrderDTO.setPaymentState(2);
		shopOrderDTO.setOrderState(2);
		shopOrderDTO.setPaymentCreatedAt(LocalDateTime.parse(paymentResult.get("TradeDate"), formatter));
		shopOrderDTO.setPaymentUpdatedAt(LocalDateTime.parse(paymentResult.get("PaymentDate"), formatter));

		shopClientService.setOrderDetail(userId, shopOrderDTO);

		return "1|OK";
	}

	// --------------------- 購物車 -------------------- //

	// 購物車計數
	@GetMapping("/cart/itemCount")
	public Result<Integer> getCartItemCount() {
		Integer currentUserId = shopClientService.getCurrentUserId();
		int cartItemCount = shopCartService.getCartItemCount(currentUserId);
		return Result.success(cartItemCount);
	}

	// 更新購物車商品數量
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
	@PostMapping("/cart/add")
	public ResponseEntity<Result<String>> addCartItem(@RequestBody AddCartDTO addCartDTO) {
		Integer currentUserId = shopClientService.getCurrentUserId();
		Result<ShopCartDTO> userCart = shopCartService.findCartByUserId(currentUserId);
		Result<String> result = shopCartService.addShopCartItem(addCartDTO.getProductId(),
				userCart.getData().getShopCartId(), addCartDTO.getQuantity());
		return ResponseEntity.ok(result);
	}

	// 刪除購物車商品
	@DeleteMapping("/cart/remove/{cartItemId}")
	public Result<String> removeCartItem(@PathVariable Integer cartItemId) {
		Result<String> result = shopCartService.removeShopCartItem(cartItemId);
		if (result.isSuccess()) {
			return Result.success("刪除購物車項目成功");
		} else {
			return Result.failure(result.getMessage());
		}
	}

}
