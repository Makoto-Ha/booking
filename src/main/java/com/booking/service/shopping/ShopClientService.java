package com.booking.service.shopping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.booking.bean.dto.shopping.AddCartDTO;
import com.booking.bean.dto.shopping.PayDetailDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.bean.dto.shopping.ShopOrderItemDTO;
import com.booking.bean.pojo.shopping.Product;
import com.booking.bean.pojo.shopping.ShopCart;
import com.booking.bean.pojo.shopping.ShopCartItem;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.booking.bean.pojo.shopping.ShopOrderItem;
import com.booking.bean.pojo.user.User;
import com.booking.config.NgrokUrlConfig;
import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ShopCartItemRepository;
import com.booking.dao.shopping.ShopCartRepository;
import com.booking.dao.shopping.ShopOrderItemRepository;
import com.booking.dao.shopping.ShopOrderRepository;
import com.booking.dao.user.UserRepository;
import com.booking.service.user.EmailService;
import com.booking.utils.MyModelMapper;
import com.booking.utils.Result;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.mail.MessagingException;
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
	@Autowired
	private NgrokUrlConfig ngrokUrlConfig;
	@Autowired
	private EmailService emailService;


	/**
	 * 綠界支付功能
	 * 
	 * @param orderDTO
	 * @param payDetailDTO
	 * @param userId
	 * @return
	 */
	@Transactional
	public String ecpayCheckout(ShopOrderDTO orderDTO, PayDetailDTO payDetailDTO, Integer userId) {

		AllInOne all = new AllInOne("");
		String ngrokUrl = ngrokUrlConfig.getNgrokURL() + "/booking/shop/api/checkout/success";
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
		String tradeDesc = orderDTO.getOrderId() + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String itemNames = orderDTO.getOrderItems().stream().map(ShopOrderItemDTO::getProductName)
				.collect(Collectors.joining("#"));

		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(uuid);
		obj.setMerchantTradeDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		obj.setMerchantID("3002599");
		obj.setTotalAmount(orderDTO.getOrderPrice().toString());
		obj.setTradeDesc(tradeDesc);
		obj.setItemName(itemNames); // "商品A#商品B#商品C"
		obj.setReturnURL(ngrokUrl);
		obj.setClientBackURL("http://localhost:8080/booking/shop/orderDetail/" + orderDTO.getOrderId());
		obj.setCustomField1(userId.toString());
		obj.setCustomField2(orderDTO.getOrderId().toString());

		String form = all.aioCheckOut(obj, null);
		
		// 更新訂單
		shopOrderRepository.findById(orderDTO.getOrderId()).ifPresent(order -> {
			order.setMerchantTradeNo(uuid);
			order.setTransactionId(tradeDesc);
			order.setPaymentMethod(1);
			order.setReceiverName(payDetailDTO.getReceiverName());
			order.setReceiverPhone(Integer.parseInt(payDetailDTO.getReceiverPhone()));
			order.setReceiverAddress(payDetailDTO.getReceiverAddress());

			shopOrderRepository.save(order);
		});

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
	 * 獲取用戶支付資訊
	 * @param userId
	 * @return
	 */
	public Result<PayDetailDTO> getUserPayDetail(Integer userId) {
		User user = userRepository.findById(userId).get();
		PayDetailDTO payDetailDTO = new PayDetailDTO();
		payDetailDTO.setReceiverName(user.getUserName());
		payDetailDTO.setReceiverPhone(user.getUserPhone());
		payDetailDTO.setReceiverAddress(user.getUserAddress());
		return Result.success(payDetailDTO);
	}

	/**
	 * 設置回傳訂單資訊
	 * 
	 * @param userId
	 * @param orderDTO
	 * @return
	 */

	@Transactional
	public Result<String> setOrderDetail(Integer userId, ShopOrderDTO orderDTO) {

		ShopOrder shopOrder = shopOrderRepository.findById(orderDTO.getOrderId()).get();

		if (shopOrder == null) {
			return Result.failure("無法找到符合條件的訂單");
		}
		
		shopOrder.setMerchantTradeNo(orderDTO.getMerchantTradeNo());
		shopOrder.setTransactionId(orderDTO.getTransactionId());
		shopOrder.setOrderState(orderDTO.getOrderState());
		shopOrder.setPaymentState(orderDTO.getPaymentState());
		shopOrder.setPaymentMethod(orderDTO.getPaymentMethod());
		shopOrderRepository.save(shopOrder);
		
		return Result.success("設置成功付款");
	}

	/**
	 * 設置訂單完成
	 * 
	 * @param userId
	 * @return
	 * @throws
	 */

	@Transactional
	public boolean setOrderIsCompleted(Integer userId, Integer orderId) {
		
		// 拿出訂單
		Optional<ShopOrder> option = shopOrderRepository.findById(orderId);
		if (!option.isPresent()) {
			return false;
		}
		
		// 更新訂單
		ShopOrder order = option.get();
		order.setOrderState(2);
		shopOrderRepository.save(order);

		// 更新產品庫存和銷量
		List<ShopOrderItem> orderItems = order.getItems();
		for (ShopOrderItem orderItem : orderItems) {
			Product product = orderItem.getProduct();
			Integer quantity = orderItem.getQuantity();

			product.setProductInventory(product.getProductInventory() - quantity);
			product.setProductSales(product.getProductSales() + quantity);

			productRepository.save(product);
		}

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
		
		// 發送訂單確認郵件
		Map<String, Object> variables = new HashMap<>();

		variables.put("userName", order.getUser().getUserName());
		variables.put("merchantTradeNo", order.getMerchantTradeNo());
		variables.put("orderItems", order.getItems());
		variables.put("orderPrice", order.getOrderPrice());

		String toEmail = order.getUser().getUserMail();
		String subject = "訂單確認 #" + order.getMerchantTradeNo();

		try {
			emailService.sendOrderConfirmationEmail(toEmail, subject, variables);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * 立即購買
	 * @param userId
	 * @param addCartDTO
	 * @return
	 */
	@Transactional
	public Result<String> createOrderByProductAndUser(Integer userId, AddCartDTO addCartDTO) {

		// 查詢商品
		Optional<Product> optionalProduct = productRepository.findById(addCartDTO.getProductId());
		if (!optionalProduct.isPresent()) {
			return Result.failure("商品不存在");
		}
		Product product = optionalProduct.get();

		// 創建新訂單
		ShopOrder shopOrder = new ShopOrder();
		shopOrder.setUser(userRepository.findById(userId).get());
		shopOrder.setOrderState(1); // 待處理
		shopOrder.setPaymentState(1); // 未付款
		System.out.println("======-=-="+addCartDTO.getQuantity());
		// 創建訂單項目
		ShopOrderItem orderItem = new ShopOrderItem();
		orderItem.setProduct(product);
		orderItem.setProductName(product.getProductName());
		orderItem.setQuantity(addCartDTO.getQuantity());
		orderItem.setPrice(product.getProductPrice());
		orderItem.setSubtotal(product.getProductPrice()*addCartDTO.getQuantity());
		orderItem.setShopOrder(shopOrder);

		List<ShopOrderItem> orderItemList = new ArrayList<>();
		orderItemList.add(orderItem);

		// 計算總金額
		shopOrder.setOrderPrice(orderItem.getSubtotal());
		shopOrder.setItems(orderItemList);

		// 保存訂單和訂單項目
		shopOrderRepository.save(shopOrder);
		shopOrderItemRepository.save(orderItem);

		return Result.success("立即購買訂單建立成功");
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
	 * 根據會員ID和訂單狀態獲取訂單
	 * 
	 * @param userId
	 * @param orderState
	 * @return
	 */

	public Result<ShopOrderDTO> getOrderByUserAndState(Integer userId, Integer orderState) {
		PageRequest pageable = PageRequest.of(0, 1, Sort.Direction.DESC, "createdAt");
		Page<ShopOrderDTO> page = shopOrderRepository.findByUserIdAndOrderStateWithDTO(userId, orderState, pageable);
		ShopOrderDTO shopOrderDTO = page.getContent().get(0);
		if (shopOrderDTO == null) {
			return Result.failure("找不到對應的訂單");
		}
		List<ShopOrderItemDTO> orderItems = shopOrderItemRepository.findByOrderId(shopOrderDTO.getOrderId());
		shopOrderDTO.setOrderItems(orderItems);
		return Result.success(shopOrderDTO);
	}

	/**
	 * 根據訂單ID獲取訂單
	 * 
	 * @param orderId
	 * @return
	 */
	@Transactional
	public Result<ShopOrderDTO> getOrderById(Integer orderId) {
		Optional<ShopOrder> byId = shopOrderRepository.findById(orderId);
		ShopOrder shopOrder = byId.get();

		ShopOrderDTO shopOrderDTO = new ShopOrderDTO();
		BeanUtils.copyProperties(shopOrder, shopOrderDTO);

		shopOrderDTO.setUserId(shopOrder.getUser().getUserId());
		shopOrderDTO.setOrderItems(shopOrder.getItems().stream().map(item -> {
			ShopOrderItemDTO orderItemDTO = new ShopOrderItemDTO();
			MyModelMapper.map(item, orderItemDTO);
			orderItemDTO.setProductId(item.getProduct().getProductId());
			orderItemDTO.setProductName(item.getProduct().getProductName());
			return orderItemDTO;
		}).collect(Collectors.toList()));

		return Result.success(shopOrderDTO);
	}

	/**
	 * 根據MerchantTradeNo獲取訂單
	 * @param merchantTradeNo
	 * @return
	 */
	@Transactional
	public Result<ShopOrderDTO> getOrderByMerchantTradeNo(String merchantTradeNo) {
		
		ShopOrder shopOrder = shopOrderRepository.findByMerchantTradeNo(merchantTradeNo);
		ShopOrderDTO shopOrderDTO = new ShopOrderDTO();
		BeanUtils.copyProperties(shopOrder, shopOrderDTO);

		shopOrderDTO.setUserId(shopOrder.getUser().getUserId());
		shopOrderDTO.setOrderItems(shopOrder.getItems().stream().map(item -> {
			ShopOrderItemDTO orderItemDTO = new ShopOrderItemDTO();
			MyModelMapper.map(item, orderItemDTO);
			orderItemDTO.setProductId(item.getProduct().getProductId());
			orderItemDTO.setProductName(item.getProduct().getProductName());
			return orderItemDTO;
		}).collect(Collectors.toList()));

		return Result.success(shopOrderDTO);
	}
}
