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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.shopping.PayDetailDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.bean.dto.shopping.ShopOrderItemDTO;
import com.booking.bean.pojo.shopping.ShopCartItem;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.booking.bean.pojo.shopping.ShopOrderItem;
import com.booking.bean.pojo.user.User;
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
import jakarta.persistence.EntityManager;
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
	private ShopCartRepository shopCartRepository;
	@Autowired
	private ShopCartItemRepository shopCartItemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EntityManager entityManager;

	// 從購物車生成選中商品的訂單
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
			// 訂單總金額
			totalAmount += cartItem.getSubtotal();
			// 從購物車中移除選中的商品
			shopCartItemRepository.deleteById(cartItem.getCartItemId());
		}

		// 設置訂單的總金額
		shopOrder.setOrderPrice(totalAmount);
		shopOrder.setItems(orderItemList);

		// 保存訂單
		shopOrderRepository.save(shopOrder);
		// 將訂單項目保存到訂單中
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
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			String username = ((UserDetails) principal).getUsername();
			Optional<User> byUserAccount = userRepository.findByUserName(username);
			if (byUserAccount.isPresent()) {
				User user = byUserAccount.get();
				return user.getUserId();
			} else {
				throw new RuntimeException("找不到對應的使用者");
			}
		} else {
			throw new RuntimeException("使用者尚未登入");
		}
	}

	// 設置最新的那筆已付款訂單
	@Transactional
	public Result<String> setOrderPaid(Integer userId, ShopOrderDTO orderDTO) {
		PageRequest pageable = PageRequest.of(0, 1);
		Page<ShopOrder> page = shopOrderRepository.findByUser_UserIdAndOrderState(userId, 2, pageable);
		System.out.println("========page" + page.getContent());
		ShopOrder shopOrder = page.getContent().get(0);

		if (shopOrder == null) {
			return Result.failure("無法找到符合條件的訂單");
		}
		MyModelMapper.map(orderDTO, shopOrder);
		shopOrderRepository.save(shopOrder);

		return Result.success("設置成功付款");
	}

	// 設置訂單完成
	@Transactional
	public boolean setOrderComplete(Integer userId) {
		PageRequest pageable = PageRequest.of(0, 1);
		Page<ShopOrder> page = shopOrderRepository.findByUser_UserIdAndOrderState(userId, 1, pageable);
		ShopOrder order = page.getContent().get(0);

		if (order == null) {
			return false;
		}
		order.setOrderState(2);
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

	// 獲取會員訂單
	public Result<ShopOrderDTO> getOrderByUserAndState(Integer userId, Integer orderState) {
		PageRequest pageable = PageRequest.of(0, 1);
		Page<ShopOrderDTO> page = shopOrderRepository.findByUserIdAndOrderStateWithDTO(userId, orderState, pageable);
		ShopOrderDTO shopOrderDTO = page.getContent().get(0);
		if (shopOrderDTO == null) {
			return Result.failure("找不到對應的訂單");
		}
		List<ShopOrderItemDTO> orderItems = shopOrderItemRepository.findByOrderId(shopOrderDTO.getOrderId());
		shopOrderDTO.setOrderItems(orderItems);
		return Result.success(shopOrderDTO);
	}

	// 綠界金流

	public String ecpayCheckout(ShopOrderDTO orderDTO, PayDetailDTO payDetailDTO, Integer userId) {

		AllInOne all = new AllInOne("");
		String ngorkUrl = "https://37b6-2402-7500-46b-3963-c519-5745-6b54-9c2b.ngrok-free.app"
				+ "/booking/shop/checkout/success";
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

}
