package com.booking.service.shopping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.pojo.shopping.Product;
import com.booking.bean.pojo.shopping.ProductCategory;
import com.booking.dao.shopping.ProductRepository;
import com.booking.dao.shopping.ProductSpecification;
import com.booking.utils.MyModelMapper;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;
import com.booking.utils.UploadImageFile;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductCategoryService productCategoryService;

	/**
	 * 單筆商品DTO
	 * 
	 * @param productId
	 * @return
	 */

	public Result<ProductDTO> findProductDTOById(Integer productId) {
		ProductDTO productDTO = productRepository.findProductDTOById(productId);
		return Result.success(productDTO);
	}

	/**
	 * 全部商品
	 * 
	 * @param productDTO
	 * @return
	 */

	public Result<Page<ProductDTO>> findProductAll(ProductDTO productDTO) {

		Pageable pageable = MyPageRequest.of(productDTO.getPageNumber(), 10, productDTO.getSelectedSort(),
				productDTO.getAttrOrderBy());

		Page<ProductDTO> page = productRepository.findProductDTOAll(pageable);

		System.out.println(page.getContent());

		return Result.success(page);

	}

	/**
	 * 多重模糊查多筆
	 * 
	 * @param productDTO
	 * @param productCapacityAll
	 * @return
	 */
	public Result<PageImpl<ProductDTO>> findProducts(ProductDTO productDTO) {

		Specification<Product> spec = Specification
				.where(ProductSpecification.productNameContains(productDTO.getProductName()))
				.and(ProductSpecification.categoryIdContains(productDTO.getCategoryId()))
				.and(ProductSpecification.productPriceContains(productDTO.getProductPrice()))
				.and(ProductSpecification.productInventoryContains(productDTO.getProductInventory()))
				.and(ProductSpecification.productDescriptionContains(productDTO.getProductDescription()));

		Pageable pageable = MyPageRequest.of(productDTO.getPageNumber(), 10, productDTO.getSelectedSort(),
				productDTO.getAttrOrderBy());

		Page<Product> page = productRepository.findAll(spec, pageable);
		List<Product> products = page.getContent();
		List<ProductDTO> productDTOs = new ArrayList<>();

		for (Product product : products) {
			ProductDTO productDTO2 = new ProductDTO();
			productDTO2.setCategoryId(product.getCategory().getCategoryId());
			productDTO2.setCategoryName(product.getCategory().getCategoryName());

			BeanUtils.copyProperties(product, productDTO2);
			productDTOs.add(productDTO2);
		}

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());

		return Result.success(new PageImpl<>(productDTOs, newPageable, page.getTotalElements()));
	}

	/**
	 * 名稱模糊搜尋
	 * 
	 * @param name
	 * @return
	 */
	public Result<List<ProductDTO>> findProductsByName(String name) {

		Result<List<Product>> result = productRepository.findProductByNameContaining(name);
		List<ProductDTO> DTOList = new ArrayList<>();

		for (Product product : result.getData()) {
			ProductDTO productDTO = new ProductDTO();
			BeanUtils.copyProperties(product, productDTO);
			DTOList.add(productDTO);
		}
		return Result.success(DTOList);
	}

	/**
	 * 分類ID搜尋
	 * 
	 * @param categoryId
	 * @return
	 */
	public Result<Page<ProductDTO>> findProductsByCategoryId(ProductDTO productDTO) {
		
		List<ProductDTO> DTOList = productRepository.findProductByCategoryId(productDTO.getCategoryId());

		Pageable pageable = MyPageRequest.of(1, 10, DTOList.get(0).getSelectedSort(), DTOList.get(0).getAttrOrderBy());

		return Result.success(new PageImpl<>(DTOList, pageable, DTOList.size()));

	}

	/**
	 * 新增商品 + 圖片
	 * 
	 * @param productDTO
	 * @param imageFile
	 * @return
	 */
	@Transactional
	public Result<String> saveProduct(ProductDTO productDTO, MultipartFile imageFile) {
		
		Result<String> upload = UploadImageFile.upload(imageFile);
		if (upload.isSuccess()) {
			String originalFilename = imageFile.getOriginalFilename();
			productDTO.setProductImage("uploads" + "/" + originalFilename);
		} else {
			productDTO.setProductImage("uploads/default.jpg");
		}

		Product product = new Product();
		BeanUtils.copyProperties(productDTO, product);

		Result<ProductCategory> result = productCategoryService.findProductCategoryById(productDTO.getCategoryId());
		product.setCategory(result.getData());
		productRepository.save(product);

		return Result.success("新增成功");
	}

	/**
	 * ID刪除
	 * 
	 * @param productId
	 * @return
	 */
	@Transactional
	public Result<String> deledeProductById(Integer productId) {
		productRepository.deleteById(productId);
		return Result.success("刪除商品成功");
	}

	/**
	 * 更新
	 * 
	 * @param productDTO
	 * @param imageFile
	 * @return
	 */
	@Transactional
	public Result<String> updateProduct(ProductDTO productDTO, MultipartFile imageFile) {

		Product updateProduct = productRepository.findById(productDTO.getProductId()).orElse(null);

		// 處理圖片邏輯
		if (imageFile != null && !imageFile.isEmpty()) {
			Result<String> upload = UploadImageFile.upload(imageFile);
			if (upload.isSuccess()) {
				String originalFilename = imageFile.getOriginalFilename();
				productDTO.setProductImage("uploads" + "/" + originalFilename);
			} else {
				return Result.failure("圖片上傳失敗");
			}
		} else {
			productDTO.setProductImage(updateProduct.getProductImage());
		}

		ProductCategory newCategory = productCategoryService.findProductCategoryById(productDTO.getCategoryId())
				.getData();
		updateProduct.setCategory(newCategory);

		MyModelMapper.map(productDTO, updateProduct);
		productRepository.save(updateProduct);

		return Result.success("更新成功");
	}

	
	// 圖片 ====================================================================================
	
	/**
	 * ID上傳圖片
	 * 
	 * @param imageFile
	 * @param productId
	 * @return
	 */
	
	public Result<String> uploadImageByProductId(MultipartFile imageFile, Integer productId) {
		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			return Result.failure("無此商品");
		}

		Result<String> uploadImageResult = UploadImageFile.upload(imageFile);

		if (uploadImageResult.isFailure()) {
			return Result.failure(uploadImageResult.getMessage());
		}
		Path path = (Path) uploadImageResult.getExtraData("path");
		product.setProductImage(path.toString());
		return Result.success("上傳圖片成功");
	}

	/**
	 * Id查圖片 預設圖片
	 * 
	 * @param productId
	 * @return
	 */
	
	public Result<UrlResource> findImageByProductId(Integer productId) {
		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			return Result.failure("無此商品");
		}
		String imagesFile = product.getProductImage();
		if (imagesFile == null) {
			imagesFile = "uploads/default.jpg";
		}
		Path path = Paths.get(imagesFile);
		try {
			UrlResource urlResource = new UrlResource(path.toUri());
			if (urlResource.exists() || urlResource.isReadable()) {
				return Result.success(urlResource).setExtraData("path", path);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return Result.failure("獲取圖片失敗");

	}

}
