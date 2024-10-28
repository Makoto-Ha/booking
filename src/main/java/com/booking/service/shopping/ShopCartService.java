package com.booking.service.shopping;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.shopping.ShopCartDTO;
import com.booking.bean.dto.shopping.ShopCartItemDTO;
import com.booking.bean.pojo.shopping.Product;
import com.booking.bean.pojo.shopping.ShopCart;
import com.booking.bean.pojo.shopping.ShopCartItem;
import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ShopCartItemRepository;
import com.booking.dao.shopping.ShopCartRepository;
import com.booking.dao.user.UserRepository;
import com.booking.utils.Result;

import jakarta.transaction.Transactional;

@Service
public class ShopCartService {

	@Autowired
	private ShopCartRepository shopCartRepository;
	@Autowired
	private ShopCartItemRepository shopCartItemRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	
	// 計算購物車筆數
	public Integer getCartItemCount(Integer userId) {
        ShopCart shopCart = shopCartRepository.findByUser_UserId(userId);
        if (shopCart == null) {
            return 0;
        }
        Integer count = shopCartItemRepository.countByShopCart_ShopCartId(shopCart.getShopCartId());
        return count ;
	}
	/**
	 * 會員ID獲取購物車
	 * @param cartId
	 * @return
	 */
	
	public Result<ShopCartDTO> findCartByUserId(Integer userId) {
		
		ShopCart shopCart = shopCartRepository.findByUser_UserId(userId);
		
	    if (shopCart == null) {
	        shopCart = new ShopCart();
	        shopCart.setCartState(1);
	        userRepository.findById(userId).ifPresent(shopCart::setUser);
	        shopCartRepository.save(shopCart);
	    }
	  
	    ShopCartDTO shopCartDTO = new ShopCartDTO();
	    BeanUtils.copyProperties(shopCart, shopCartDTO);
	    
	    Integer cartId = shopCart.getShopCartId();
	    List<ShopCartItemDTO> itemDTOList = shopCartItemRepository.findAllByCartId(cartId);
	    shopCartDTO.setCartItems(itemDTOList);
	    shopCartDTO.setUserId(shopCart.getUser().getUserId());
	    
	    return Result.success(shopCartDTO);
	}

	/**
	 * 更新購物車的商品數量
	 * @param cartItemId
	 * @param newQuantity
	 * @return
	 */
	
	@Transactional
	public Result<ShopCartDTO> updateCartItemQuantity(Integer cartItemId, Integer newQuantity) {

		Optional<ShopCartItem> option = shopCartItemRepository.findById(cartItemId);
		if (!option.isPresent()) {
			return Result.failure("購物車項目不存在");
		}
		if (newQuantity == null || newQuantity < 0) {
			return Result.failure("數量不合法");
		}

		ShopCartItem cartItem = option.get();
		ShopCart shopCart = cartItem.getShopCart();

		if (newQuantity == 0) {
			shopCartItemRepository.delete(cartItem);
		} else {
			cartItem.setQuantity(newQuantity);
			cartItem.calculateSubtotal();
			shopCartItemRepository.save(cartItem);
		}
		shopCart.calculateTotalAmount();

		ShopCartDTO shopCartDTO = new ShopCartDTO();
		BeanUtils.copyProperties(shopCart, shopCartDTO);
		shopCartDTO.setUserId(shopCart.getUser().getUserId());
		shopCartDTO.setCartItems(shopCartItemRepository.findAllByCartId(shopCart.getShopCartId()));

		return Result.success(shopCartDTO);
	}

	/**
	 * 添加購物車商品
	 * @param shopCartItemDTO
	 * @param CartId
	 * @return
	 */
	
	@Transactional
	public Result<String> addShopCartItem(Integer productId, Integer CartId, Integer addQuantity) {
		// 確認購物車和商品
		Optional<ShopCart> findCart = shopCartRepository.findById(CartId);
		if (!findCart.isPresent()) {
			return Result.failure("購物車不存在");
		}
		Optional<Product> findProduct = productRepository.findById(productId);
		if (!findProduct.isPresent()) {
			return Result.failure("商品不存在");
		}
		ShopCart shopCart = findCart.get();
		Product product = findProduct.get();
		
		// 確認購物車中是否已有項目
		ShopCartItem cartItem = shopCartItemRepository.findByShopCartAndProduct(shopCart, product);
		// 如果存在，數量+1
		if (cartItem != null) {
			cartItem.setQuantity(cartItem.getQuantity()+addQuantity);
		} else { 
			cartItem = new ShopCartItem();  // 如果不存在，新增商品項目
			cartItem.setShopCart(shopCart);
			cartItem.setProduct(product);
			cartItem.setQuantity(addQuantity);
			cartItem.setPrice(product.getProductPrice());
		}
		// 更新小記後 將購物車項目存入
		cartItem.calculateSubtotal();  
		shopCartItemRepository.save(cartItem);
		// 更新總金額後 將購物車存入
		shopCart.calculateTotalAmount();
		shopCartRepository.save(shopCart);
		
		// 將購物車轉換為DTO
		ShopCartDTO shopCartDTO = new ShopCartDTO();
		BeanUtils.copyProperties(shopCart, shopCartDTO);
		shopCartDTO.setUserId(shopCart.getUser().getUserId());
		shopCartDTO.setCartItems(shopCartItemRepository.findAllByCartId(shopCart.getShopCartId()));

		shopCartDTO.setTotalAmount(shopCart.getTotalAmount());

		return Result.success("添加成功");
	}

	/**
	 *  移除購物車商品
	 * @param cartItemId
	 * @return
	 */
	
	@Transactional
	public Result<String> removeShopCartItem(Integer cartItemId) {

		Optional<ShopCartItem> option = shopCartItemRepository.findById(cartItemId);
		if (!option.isPresent()) {
			return Result.failure("購物車項目不存在");
		}

		ShopCartItem shopCartItem = option.get();
		ShopCart shopCart = shopCartItem.getShopCart();

	    // 從購物車中移除該項目
	    shopCart.getCartItems().remove(shopCartItem);
		shopCartItemRepository.deleteById(cartItemId);

		shopCart.calculateTotalAmount();
		shopCartRepository.save(shopCart);

		return Result.success("移除成功");
	}

	/**
	 *  建立購物車
	 * @param userId
	 * @return
	 */
	
	public Result<ShopCartDTO> getShopCart(Integer userId) {

		Optional<ShopCart> option = shopCartRepository.findById(userId);
		ShopCartDTO shopCartDTO = new ShopCartDTO();

		if (!option.isPresent()) {
			ShopCart shopCart = new ShopCart();
			userRepository.findById(userId).ifPresent(shopCart::setUser);
			shopCartRepository.save(shopCart);
			BeanUtils.copyProperties(shopCart, shopCartDTO);
			return Result.success(shopCartDTO);
		}
		BeanUtils.copyProperties(option.get(), shopCartDTO);
		return Result.success(shopCartDTO);
	}

	/**
	 *  清空購物車
	 * @param userId
	 * @return
	 */
	
	@Transactional
	public Result<String> deleteShopCart(Integer userId) {

		ShopCart cart = shopCartRepository.findByUser_UserId(userId);
		if (cart == null) {
			return Result.failure("購物車不存在");
		}
		shopCartRepository.delete(cart);

		return Result.success("刪除成功");
	}

}
