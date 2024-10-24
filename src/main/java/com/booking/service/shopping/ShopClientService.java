package com.booking.service.shopping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.shopping.PayDetailDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.bean.dto.shopping.ShopOrderItemDTO;
import com.booking.bean.pojo.shopping.ShopCart;
import com.booking.bean.pojo.shopping.ShopCartItem;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.booking.bean.pojo.shopping.ShopOrderItem;
import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ShopCartItemRepository;
import com.booking.dao.shopping.ShopCartRepository;
import com.booking.dao.shopping.ShopOrderItemRepository;
import com.booking.dao.shopping.ShopOrderRepository;
import com.booking.dao.user.UserRepository;
import com.booking.utils.MyModelMapper;
import com.booking.utils.Result;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.transaction.Transactional;

@Service
public class ShopClientService {

	@Autowired
	private ShopOrderRepository shopOrderRepository;
	@Autowired
	private ShopOrderItemRepository shopOrderItemRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ShopCartItemRepository shopCartItemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ShopCartRepository shopCartRepository;

	/**
	 * 綠界支付功能
	 * 
	 * @param orderDTO
	 * @param payDetailDTO
	 * @param userId
	 * @return
	 */

	public String ecpayCheckout(ShopOrderDTO orderDTO, PayDetailDTO payDetailDTO, Integer userId) {
		AllInOne all = new AllInOne("");
		String ngorkUrl = "https://1f42-118-168-74-241.ngrok-free.app" + "/booking/shop/checkout/success";
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
		String tradeDesc = "訂單號:" + orderDTO.getOrderId() + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String itemNames = orderDTO.getOrderItems().stream().map(ShopOrderItemDTO::getProductName)
				.collect(Collectors.joining("#"));

		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(uuid);
		obj.setMerchantTradeDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		obj.setMerchantID("3002599");
		obj.setTotalAmount(orderDTO.getOrderPrice().toString());
		obj.setTradeDesc(tradeDesc);
		obj.setItemName(itemNames); // "商品A#商品B#商品C"
		obj.setReturnURL(ngorkUrl);
		obj.setClientBackURL("http://localhost:8080/booking/shop/orderDetail");
		obj.setCustomField1(userId.toString());
		String form = all.aioCheckOut(obj, null);

		return form;
	}

	/**
	 * 從購物車生成訂單
	 * 
	 * @param userId
	 * @param selectedItemIds
	 * @return
	 */

	@Transactional
	public Result<ShopOrderDTO> createOrderBySelect(Integer userId, List<Integer> selectedItemIds) {

		// 新訂單
		ShopOrder shopOrder = new ShopOrder();
		shopOrder.setUser(userRepository.findById(userId).get());
		shopOrder.setOrderState(1); // 待處理
		shopOrder.setPaymentState(1); // 未付款

		// =============== 從購物車中篩選出選中的項目 ======================

		List<ShopOrderItem> orderItemList = new ArrayList<>();
		Integer totalAmount = 0;

		// 迭代選中的購物車項目
		for (Integer itemId : selectedItemIds) {
			Optional<ShopCartItem> optionalCartItem = shopCartItemRepository.findById(itemId);
			if (!optionalCartItem.isPresent()) {
				return Result.failure("找不到對應的購物車項目: " + itemId);
			}
			ShopCartItem cartItem = optionalCartItem.get();
			// 將購物車項目轉換為訂單項目
			ShopOrderItem orderItem = new ShopOrderItem();
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setProductName(cartItem.getProduct().getProductName());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getPrice());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setShopOrder(shopOrder);
			orderItemList.add(orderItem);

			totalAmount += cartItem.getSubtotal();
		}

		// 設置訂單的總金額
		shopOrder.setOrderPrice(totalAmount);
		shopOrder.setItems(orderItemList);

		shopOrderRepository.save(shopOrder);
		shopOrderItemRepository.saveAll(orderItemList);

		// 將訂單轉換為 DTO 以返回結果
		ShopOrderDTO shopOrderDTO = new ShopOrderDTO();
		BeanUtils.copyProperties(shopOrder, shopOrderDTO);
		shopOrderDTO.setUserId(userId);

		List<ShopOrderItemDTO> orderItemDTOList = orderItemList.stream().map(orderItem -> {

			ShopOrderItemDTO orderItemDTO = new ShopOrderItemDTO();
			BeanUtils.copyProperties(orderItem, orderItemDTO);
			orderItemDTO.setProductId(orderItem.getProduct().getProductId());
			orderItemDTO.setProductName(orderItem.getProduct().getProductName());
			return orderItemDTO;

		}).collect(Collectors.toList());

		shopOrderDTO.setOrderItems(orderItemDTOList);

		return Result.success(shopOrderDTO);
	}

	/**
	 * 獲取當前登入用戶ID
	 * 
	 * @return
	 */

	public Integer getCurrentUserId() {
		String userAccount = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRepository.findByUserAccount(userAccount).get().getUserId();
	}

	/**
	 * 設置最新已付款訂單資訊
	 * 
	 * @param userId
	 * @param orderDTO
	 * @return
	 */

	@Transactional
	public Result<String> setOrderDetail(Integer userId, ShopOrderDTO orderDTO) {
		PageRequest pageable = PageRequest.of(0, 1, Sort.Direction.DESC,"createdAt");
		Page<ShopOrder> page = shopOrderRepository.findByUser_UserIdAndOrderState(userId, 2, pageable);
		ShopOrder shopOrder = page.getContent().get(0);

		System.out.println("=====轉換前的ORDER====="+shopOrder);
		
		if (shopOrder == null) {
			return Result.failure("無法找到符合條件的訂單");
		}
		MyModelMapper.map(orderDTO, shopOrder);
		System.out.println("=====綠界資訊 DTO====="+orderDTO);
		System.out.println("=====轉換完存入 ORDER====="+shopOrder);
		shopOrderRepository.save(shopOrder);
		return Result.success("設置成功付款");
	}

	/**
	 * 設置訂單完成
	 * 
	 * @param userId
	 * @return
	 */

	@Transactional
	public boolean setOrderIsCompleted(Integer userId) {
		PageRequest pageable = PageRequest.of(0, 1, Sort.Direction.DESC,"createdAt");
		Page<ShopOrder> page = shopOrderRepository.findByUser_UserIdAndOrderState(userId, 1, pageable);
		ShopOrder order = page.getContent().get(0);
		if (order == null) {
			return false;
		}
		order.setOrderState(2);

		// 在支付確認後移除對應的購物車項目
		List<Integer> productIds = order.getItems().stream().map(item -> item.getProduct().getProductId())
				.collect(Collectors.toList());

		ShopCart cart = shopCartRepository.findByUser_UserId(userId);
		if (cart != null) {
			List<ShopCartItem> cartItems = cart.getCartItems();
			List<ShopCartItem> itemsToRemove = cartItems.stream()
					.filter(item -> productIds.contains(item.getProduct().getProductId())).collect(Collectors.toList());
			cartItems.removeAll(itemsToRemove);
			cart.setTotalAmount(0);
			shopCartItemRepository.deleteAll(itemsToRemove);
			shopCartRepository.save(cart);
		}
		return true;
	}

	/**
	 * 熱銷商品
	 * 
	 * @param topNumber
	 * @return
	 */

	public Result<List<ProductDTO>> findTopSellingProducts(Integer topNumber) {
		PageRequest pageable = PageRequest.of(0, topNumber);
		List<ProductDTO> result = productRepository.findTopSellingProductDTOs(pageable);
		return Result.success(result);
	}

	/**
	 * 獲取會員的購物車
	 * 
	 * @param userId
	 * @param orderState
	 * @return
	 */

	public Result<ShopOrderDTO> getOrderByUserAndState(Integer userId, Integer orderState) {
		PageRequest pageable = PageRequest.of(0, 1, Sort.Direction.DESC,"createdAt");
		Page<ShopOrderDTO> page = shopOrderRepository.findByUserIdAndOrderStateWithDTO(userId, orderState, pageable);
		ShopOrderDTO shopOrderDTO = page.getContent().get(0);
		if (shopOrderDTO == null) {
			return Result.failure("找不到對應的訂單");
		}
		List<ShopOrderItemDTO> orderItems = shopOrderItemRepository.findByOrderId(shopOrderDTO.getOrderId());
		shopOrderDTO.setOrderItems(orderItems);
		return Result.success(shopOrderDTO);
	}

}
