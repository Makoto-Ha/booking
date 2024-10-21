package com.booking.service.shopping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.bean.dto.shopping.ShopCartItemDTO;
import com.booking.bean.pojo.shopping.Product;
import com.booking.bean.pojo.shopping.ShopCart;
import com.booking.bean.pojo.shopping.ShopCartItem;
import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ShopCartItemRepository;
import com.booking.dao.shopping.ShopCartRepository;
import com.booking.dao.shopping.ShopOrderRepository;
import com.booking.dao.shopping.TestUserRepository;
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
	private TestUserRepository testUserRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ShopCartRepository shopCartRepository;
	@Autowired
	private ShopCartItemRepository shopCartItemRepository;

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

	// 更新購物車
	public Result<ShopCartDTO> updateShopCart(Integer userId) {

		return Result.success(null);
	}

	// 添加購物車商品
	@Transactional
	public Result<ShopCartDTO> addShopCartItem(ShopCartItemDTO shopCartItemDTO, Integer CartId) {

		Optional<ShopCart> findCart = shopCartRepository.findById(CartId);
		if (!findCart.isPresent()) {
			return Result.failure("購物車不存在");
		}

		Optional<Product> findProduct = productRepository.findById(shopCartItemDTO.getProductId());
		if (!findProduct.isPresent()) {
			return Result.failure("商品不存在");
		}

		ShopCart shopCart = findCart.get();
		Product product = findProduct.get();

		ShopCartItem cartItem = shopCartItemRepository.findByShopCartAndProduct(shopCart, product);

		if (cartItem != null) {
			cartItem.setQuantity(cartItem.getQuantity() + shopCartItemDTO.getQuantity());
		} else {
		cartItem = new ShopCartItem();
		cartItem.setShopCart(shopCart);
		cartItem.setProduct(product);
		cartItem.setQuantity(shopCartItemDTO.getQuantity());
		cartItem.setPrice(product.getProductPrice());
		cartItem.setSubtotal(product.getProductPrice() * shopCartItemDTO.getQuantity());
		}

		cartItem.setSubtotal(cartItem.getPrice() * cartItem.getQuantity());
		shopCartItemRepository.save(cartItem);

		ShopCartDTO shopCartDTO = new ShopCartDTO();
		MyModelMapper.map(shopCart, shopCartDTO);
		
		return Result.success(shopCartDTO);
	}

	// 移除購物車商品
	public Result<String> removeShopCartItem(Integer cartItemId) {

		return Result.success("移除成功");
	}

	// 建立購物車
	public Result<ShopCartDTO> getShopCart(Integer userId) {

		Optional<ShopCart> option = shopCartRepository.findById(userId);
		ShopCartDTO shopCartDTO = new ShopCartDTO();

		if (!option.isPresent()) {
			ShopCart shopCart = new ShopCart();
			testUserRepository.findById(userId).ifPresent(shopCart::setUser);
			shopCartRepository.save(shopCart);
			BeanUtils.copyProperties(shopCart, shopCartDTO);
			return Result.success(shopCartDTO);
		}
		BeanUtils.copyProperties(option.get(), shopCartDTO);
		return Result.success(shopCartDTO);
	}

	// 清空購物車

	public Result<String> deleteShopCart(Integer userId) {
		shopCartRepository.deleteById(userId);
		return Result.success("刪除成功");
	}

	// 綠界金流

	public String ecpayCheckout() {

		AllInOne all = new AllInOne("");

		String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);

		AioCheckOutALL obj = new AioCheckOutALL();
		obj.setMerchantTradeNo(uuid);
		obj.setMerchantTradeDate("2017/01/01 08:05:23");
		obj.setMerchantID("3002607");
		obj.setTotalAmount("50");
		obj.setTradeDesc("test Description");
		obj.setItemName("TestItem");
		obj.setReturnURL("http://localhost:8080/booking/shop/cart");
		obj.setNeedExtraPaidInfo("N");
		obj.setClientBackURL("http://localhost:8080/booking/shop");
		String form = all.aioCheckOut(obj, null);

		return form;
	}

}
