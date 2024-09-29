package com.booking.service.shopping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.pojo.shopping.Product;
import com.booking.dao.shopping.ProductDao;
import com.booking.utils.DaoResult;
import com.booking.utils.Result;

@Service
public class ProductService {

	@Autowired
	private ProductDao productDao;

	public Result<Product> getProductById(Integer productId) {
		DaoResult<Product> daoResult = productDao.getProductById(productId);
		if (!daoResult.isSuccess()) {
			return Result.failure("獲取商品失敗");
		}
		return Result.success(daoResult.getData());
	}

	public Result<List<Product>> getProductByName(String productName, String sortBy, String sortOrder) {
		// 檢查、設定默認排序
		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "productId"; // 默認
		}
		if (sortOrder == null || sortOrder.isEmpty()) {
			sortOrder = "DESC"; // 默認
		}
		DaoResult<List<Product>> daoResult = productDao.getProductByName(productName, sortBy, sortOrder);
		if (!daoResult.isSuccess()) {
			return Result.failure("獲取商品失敗");
		}
		return Result.success(daoResult.getData());
	}

	public Result<List<Product>> getAllProduct(String sortBy, String sortOrder) {
		// 檢查、設定默認排序
		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "productId"; // 默認
		}
		if (sortOrder == null || sortOrder.isEmpty()) {
			sortOrder = "DESC"; // 默認
		}
		DaoResult<List<Product>> daoResult = productDao.getAllProduct(sortBy, sortOrder);
		if (!daoResult.isSuccess()) {
			return Result.failure("獲取商品失敗");
		}
		return Result.success(daoResult.getData());
	}
	@Transactional
	public Result<Integer> addProduct(Product product) {
		DaoResult<?> daoResult = productDao.addProduct(product);
		if (!daoResult.isSuccess()) {
			return Result.failure("新增失敗");
		}
		return Result.success(daoResult.getGeneratedId());
	}
	@Transactional
	public Result<String> removeProduct(Integer productId) {
		DaoResult<?> daoResult = productDao.removeProductById(productId);
		if (!daoResult.isSuccess()) {
			return Result.failure("刪除失敗");
		}
		return Result.success("刪除成功");
	}
	@Transactional
	public Result<String> updateProduct(Product product) {

		DaoResult<Product> daoResult = productDao.getProductById(product.getProductId());

		if (!daoResult.isSuccess()) {
			return Result.failure("商品不存在");
		}

		Product oldProduct = daoResult.getData();

		if (product.getCategoryId() == null) {
			product.setCategoryId(oldProduct.getCategoryId());
		}
		if (product.getProductName() == null || product.getProductName().isEmpty()) {
			product.setProductName(oldProduct.getProductName());
		}
		if (product.getProductImage() == null || product.getProductImage().isEmpty()) {
			product.setProductImage(oldProduct.getProductImage());
		}
		if (product.getProductDescription() == null || product.getProductDescription().isEmpty()) {
			product.setProductDescription(oldProduct.getProductDescription());
		}
		if (product.getProductPrice() == null) {
			product.setProductPrice(oldProduct.getProductPrice());
		}
		if (product.getProductSales() == null) {
			product.setProductSales(oldProduct.getProductSales());
		}
		if (product.getProductInventorey() == null) {
			product.setProductInventorey(oldProduct.getProductInventorey());
		}
		if (product.getProductState() == null) {
			product.setProductState(oldProduct.getProductState());
		}

		DaoResult<?> updateProductDaoResult = productDao.updateProduct(product);
		if (!updateProductDaoResult.isSuccess()) {
			return Result.failure("商品更新失敗");
		}
		return Result.success("商品更新成功");
	}

}
