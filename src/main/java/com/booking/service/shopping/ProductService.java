package com.booking.service.shopping;

import java.util.List;

import com.booking.bean.shopping.Product;
import com.booking.dao.shopping.ProductDao;
import com.booking.utils.DaoResult;
import com.booking.utils.Result;

public class ProductService {

	private ProductDao productDao = new ProductDao();

	public Result<Product> getProductById(Integer productId) {
		DaoResult<Product> daoResult = productDao.getProductById(productId);
		Product product = daoResult.getEntity();
		if (product == null) {
			return Result.failure("獲取商品失敗");
		}
		return Result.success(product);
	}

	public Result<List<Product>> getProductByName(String productName, String sortBy, String sortOrder) {
		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "product_id"; // 默認排序字段
		}
		if (sortOrder == null || sortOrder.isEmpty()) {
			sortOrder = "DESC"; // 默認排序方式
		}
		DaoResult<Product> daoResult = productDao.getProductByName(productName, sortBy,sortOrder);
		List<Product> productList = daoResult.getData();
		if (productList == null) {
			return Result.failure("獲取商品失敗");
		}
		return Result.success(productList);
	}
	
	public Result<List<Product>> getAllProduct(String sortBy, String sortOrder) {
		DaoResult<Product> daoResult = productDao.getAllProduct(sortBy,sortOrder);
		List<Product> productList = daoResult.getData();
		if (productList == null) {
			return Result.failure("獲取商品失敗");
		}
		return Result.success(productList);
	}

	public Result<Integer> addProduct(Product product) {
		DaoResult<Integer> daoResult = productDao.addProduct(product);
		if (daoResult.getGeneratedId() == null) {
			return Result.failure("新增失敗");
		}
		return Result.success(daoResult.getGeneratedId());
	}

	public Result<String> removeProduct(Integer productId) {
		DaoResult<Integer> productDaoResult = productDao.removeProduct(productId);

		if (productDaoResult.getAffectedRows() == 0) {
			return Result.failure("刪除失敗");
		}

		return Result.success("刪除成功");
	}

	public Result<String> updateProduct(Product product) {
		Integer oldProductId = product.getProductId();
		Product oldProduct = productDao.getProductById(oldProductId).getEntity();

		Integer categoryId = product.getCategoryId();
		String productName = product.getProductName();
		String productImage = product.getProductImage();
		String productDescription = product.getProductDescription();
		Integer productPrice = product.getProductPrice();
		Integer productSales = product.getProductSales();
		Integer productInventorey = product.getProductInventorey();
		Integer productState = product.getProductState();

		if (categoryId == null) {
			product.setCategoryId(oldProduct.getCategoryId());
		}
		if (productName == null || productName.isEmpty()) {
			product.setProductName(oldProduct.getProductName());
		}
		if (productImage == null || productImage.isEmpty()) {
			product.setProductImage(oldProduct.getProductImage());
		}
		if (productDescription == null || productDescription.isEmpty()) {
			product.setProductDescription(oldProduct.getProductDescription());
		}
		if (productPrice == null) {
			product.setProductPrice(oldProduct.getProductPrice());
		}
		if (productSales == null) {
			product.setProductSales(oldProduct.getProductSales());
		}
		if (productInventorey == null) {
			product.setProductInventorey(oldProduct.getProductInventorey());
		}
		if (productState == null) {
			product.setProductState(oldProduct.getProductState());
		}
		DaoResult<Integer> updateProductDaoResult = productDao.updateProduct(product);

		if (updateProductDaoResult.getAffectedRows() == null) {
			return Result.failure("商品更新失敗");
		}

		return Result.success("商品更新成功");
	}

}
