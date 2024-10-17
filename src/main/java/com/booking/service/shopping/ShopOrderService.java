package com.booking.service.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.booking.bean.pojo.shopping.TestUser;
import com.booking.dao.shopping.ShopOrderRepository;
import com.booking.dao.shopping.ShopOrderSpecification;
import com.booking.dao.shopping.TestUserRepository;
import com.booking.utils.MyModelMapper;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class ShopOrderService {

	@Autowired
	private ShopOrderRepository shopOrderRepository;
	@Autowired
	private TestUserRepository testUserRepository;

	/**
	 * 多重查詢
	 * 
	 * @param shopOrderDTO
	 * @return
	 */
	@Transactional
	public Result<PageImpl<ShopOrderDTO>> findOrders(ShopOrderDTO shopOrderDTO) {

		Specification<ShopOrder> spec = Specification
				.where(ShopOrderSpecification.orderIdContains(shopOrderDTO.getOrderId())).and(ShopOrderSpecification
                        .userIdContains(shopOrderDTO.getUserId()));

		Pageable pageable = MyPageRequest.of(shopOrderDTO.getPageNumber(), 10, shopOrderDTO.getSelectedSort(),
				shopOrderDTO.getAttrOrderBy());

		Page<ShopOrder> page = shopOrderRepository.findAll(spec, pageable);

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());

		List<ShopOrderDTO> shopOrderDTOs = new ArrayList<>();

		for (ShopOrder shopOrder : page.getContent()) {
			ShopOrderDTO shopOrderDTO2 = new ShopOrderDTO();
			BeanUtils.copyProperties(shopOrder, shopOrderDTO2);
			shopOrderDTO2.setUserId(shopOrder.getUsers().getUserId());
			shopOrderDTOs.add(shopOrderDTO2);
		}
		System.out.println("shopOrderDTOs: " + shopOrderDTOs);
		return Result.success(new PageImpl<>(shopOrderDTOs, newPageable, page.getTotalElements()));
	}

	/**
	 * 更新
	 * 
	 * @param shopOrderDTO
	 * @return
	 */

	@Transactional
	public Result<String> updateOrder(ShopOrderDTO shopOrderDTO) {

		ShopOrder update = shopOrderRepository.findById(shopOrderDTO.getOrderId()).orElse(null);
		MyModelMapper.map(shopOrderDTO, update);
		shopOrderRepository.save(update);

		return Result.success("更新成功");
	}

	/**
	 * 
	 * @param name
	 * @return
	 */

	public Result<List<ShopOrderDTO>> findOrderByMerchantTradeNo(String name) {

		List<ShopOrderDTO> shopOrderDTOs = shopOrderRepository.findOrderDTOByMerchantTradeNo(name);

		return Result.success(shopOrderDTOs);
	}

	/**
	 * 單筆訂單DTO
	 * 
	 * @param shopOrderDTO
	 * @return
	 */

	public Result<ShopOrderDTO> findOrderDTOById(Integer orderId) {
		ShopOrderDTO shopOrderDTO = shopOrderRepository.findOrderDTOById(orderId);
		return Result.success(shopOrderDTO);
	}

	/**
	 * 全部訂單DTO
	 * 
	 * @param shopOrderDTO
	 * @return
	 */

	public Result<Page<ShopOrderDTO>> findOrderAll(ShopOrderDTO shopOrderDTO) {

		Pageable pageable = MyPageRequest.of(shopOrderDTO.getPageNumber(), 10, shopOrderDTO.getSelectedSort(),
				shopOrderDTO.getAttrOrderBy());

		Page<ShopOrderDTO> page = shopOrderRepository.findOrderDTOAll(pageable);

		return Result.success(page);
	}

	/**
	 * 刪除
	 * 
	 * @param orderId
	 * @return
	 */

	public Result<String> deleteOrderById(Integer orderId) {
		shopOrderRepository.deleteById(orderId);
		return Result.success("刪除訂單成功");
	}

	/**
	 * 新增
	 * 
	 * @param shopOrderDTO
	 * @return
	 */

	public Result<String> saveOrder(ShopOrderDTO shopOrderDTO) {

		ShopOrder shopOrder = new ShopOrder();
		BeanUtils.copyProperties(shopOrderDTO, shopOrder);

		Optional<TestUser> result = testUserRepository.findById(shopOrderDTO.getUserId());
		shopOrder.setUsers(result.get());

		shopOrderRepository.save(shopOrder);

		return Result.success("訂單新增成功");
	}

}
