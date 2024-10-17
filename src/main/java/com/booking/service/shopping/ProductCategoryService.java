package com.booking.service.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.pojo.shopping.Product;
import com.booking.bean.pojo.shopping.ProductCategory;
import com.booking.bean.pojo.shopping.ShopOrder;
import com.booking.dao.shopping.ProductCategoryRepository;
import com.booking.dao.shopping.ProductCategorySpecification;
import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ShopOrderRepository;
import com.booking.utils.MyModelMapper;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ShopOrderRepository shopOrderRepository;

	/**
	 * ID單筆DTO
	 * 
	 * @param categoryId
	 * @return
	 */

	public Result<ProductCategoryDTO> findCategoryDTOById(Integer categoryId) {
		ProductCategoryDTO productCategoryDTO = productCategoryRepository.findProductCategoryDTOById(categoryId);
		return Result.success(productCategoryDTO);
	}

	/**
	 * 名稱模糊查多筆
	 * @param name
	 * @return
	 */
	
	public Result<List<ProductCategoryDTO>> findCategoryDTOByName(String name) {

		Result<List<ProductCategory>> result = productCategoryRepository.findProductCategoryByNameContaining(name);

		List<ProductCategoryDTO> DTOList = new ArrayList<>();

		for (ProductCategory productCategory : result.getData()) {
			ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
			BeanUtils.copyProperties(productCategory, productCategoryDTO);
			DTOList.add(productCategoryDTO);
		}
		return Result.success(DTOList);
	}

	/**
	 * 分類ID找分類
	 * 
	 * @param productCategoryId
	 * @return
	 */

	public Result<ProductCategory> findProductCategoryById(Integer productCategoryId) {
		Optional<ProductCategory> optional = productCategoryRepository.findById(productCategoryId);
		if (optional.isPresent()) {
			return Result.success(optional.get());
		}
		return Result.failure("無此分類");
	}

	/**
	 * 全部分類
	 * 
	 * @param productCategoryDTO
	 * @return
	 */

	public Result<Page<ProductCategoryDTO>> findProductCategoryAll(ProductCategoryDTO productCategoryDTO) {

		Pageable pageable = MyPageRequest.of(productCategoryDTO.getPageNumber(), 10,
				productCategoryDTO.getSelectedSort(), productCategoryDTO.getAttrOrderBy());

		Page<ProductCategoryDTO> page = productCategoryRepository.findProductCategoryDTOALL(pageable);

		// 手動設productDTO陣列
		for (ProductCategoryDTO matchCategoryDTO : page.getContent()) {
			List<ProductDTO> productDTOs = productRepository.findProductByCategoryId(matchCategoryDTO.getCategoryId());
			matchCategoryDTO.setProducts(productDTOs);
		}
		return Result.success(page);
	}

	/**
	 * 多重模糊查多筆
	 * 
	 * @param productCategoryDTO
	 * @return
	 */

	public Result<PageImpl<ProductCategoryDTO>> findCategorys(ProductCategoryDTO productCategoryDTO) {

		Specification<ProductCategory> spec = Specification
				.where(ProductCategorySpecification.categoryNameContains(productCategoryDTO.getCategoryName()))
				.and(ProductCategorySpecification
						.categoryDescriptionContains(productCategoryDTO.getCategoryDescription()));

		Pageable pageable = MyPageRequest.of(productCategoryDTO.getPageNumber(), 10,
				productCategoryDTO.getSelectedSort(), productCategoryDTO.getAttrOrderBy());

		Page<ProductCategory> page = productCategoryRepository.findAll(spec, pageable);

		List<ProductCategoryDTO> productCategoryDTOs = new ArrayList<>();

		for (ProductCategory productCategory : page.getContent()) {

			ProductCategoryDTO productCategoryDTO2 = new ProductCategoryDTO();
			BeanUtils.copyProperties(productCategory, productCategoryDTO2);

			List<ProductDTO> productDTOs = new ArrayList<>();

			for (Product product : productCategory.getProducts()) {
				ProductDTO productDTO = new ProductDTO();
				BeanUtils.copyProperties(product, productDTO);

				productDTO.setCategoryId(productCategory.getCategoryId());
				productDTO.setCategoryName(productCategory.getCategoryName());

				productDTOs.add(productDTO);
			}
			productCategoryDTO2.setProducts(productDTOs);
			productCategoryDTOs.add(productCategoryDTO2);
		}
		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());

		return Result.success(new PageImpl<>(productCategoryDTOs, newPageable, page.getTotalElements()));
	}

	/**
	 * 新增
	 * 
	 * @param productCategoryDTO
	 * @return
	 */

	@Transactional
	public Result<String> saveProductCategory(ProductCategoryDTO productCategoryDTO) {
		ProductCategory productCategory = new ProductCategory();
		BeanUtils.copyProperties(productCategoryDTO, productCategory);
		productCategoryRepository.save(productCategory);
		return Result.success("新增分類成功");
	}

	/**
	 * ID刪除
	 * 
	 * @param productCategoryId
	 * @return
	 */

	@Transactional
	public Result<String> deleteProductCategoryById(Integer productCategoryId) {

		List<ProductDTO> products = productRepository.findProductByCategoryId(productCategoryId);

		// 檢查是否有訂單包含這些產品
		if (hasOrdersWithProducts(products)) {
			return Result.failure("有訂單包含此分類下的產品，無法刪除");
		}

		for (ProductDTO productDTO : products) {
			productRepository.deleteById(productDTO.getProductId());
		}
		productCategoryRepository.deleteById(productCategoryId);
		return Result.success("刪除分類與其中產品成功");

	}

	
	/**
	 * 檢查訂單是否包含商品
	 * @param products
	 * @return
	 */
	
	private boolean hasOrdersWithProducts(List<ProductDTO> products) {
		List<Integer> productIds = products.stream().map(ProductDTO::getProductId).collect(Collectors.toList());

		// 查詢訂單，檢查是否存在與產品 ID 相關的訂單
		List<ShopOrder> orders = shopOrderRepository.findOrdersByProductIds(productIds);
		return !orders.isEmpty(); // 如果訂單列表不為空，則表示有依賴的訂單
	}

	
	/**
	 * 更新 目前不處理products
	 * 
	 * @param productCategoryDTO
	 * @return
	 */

	@Transactional
	public Result<String> updateProductCategory(ProductCategoryDTO productCategoryDTO) {

		ProductCategory update = productCategoryRepository.findById(productCategoryDTO.getCategoryId()).orElse(null);
		System.out.println("傳來的categoryDTO " + productCategoryDTO);
		System.out.println("原本的category " + update);
		MyModelMapper.map(productCategoryDTO, update);
		productCategoryRepository.save(update);

		return Result.success("更新分類成功");
	}
}
