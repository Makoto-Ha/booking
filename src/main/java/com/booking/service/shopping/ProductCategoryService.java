package com.booking.service.shopping;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.pojo.shopping.ProductCategory;
import com.booking.dao.shopping.ProductCategoryRepository;
import com.booking.dao.shopping.ProductRepository;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class ProductCategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;
	@Autowired
	private ProductRepository productRepository;

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
		for (ProductDTO productDTO : products) {
			productRepository.deleteById(productDTO.getProductId());
		}
		productCategoryRepository.deleteById(productCategoryId);
		return Result.success("刪除分類與其中產品成功");
	}

	/**
	 * 更新
	 * 
	 * @param productCategoryDTO
	 * @return
	 */

	@Transactional
	public Result<String> updateProductCategory(ProductCategoryDTO productCategoryDTO) {

		
		return Result.success("更新分類成功");
	}
}
