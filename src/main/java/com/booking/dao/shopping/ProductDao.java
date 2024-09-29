package com.booking.dao.shopping;

import java.util.List;

import com.booking.bean.pojo.shopping.Product;
import com.booking.utils.DaoResult;

public interface ProductDao {

	DaoResult<Product> getProductById(Integer productId);

	DaoResult<List<Product>> getProductByName(String productName, String sortBy, String sortOrder);

	DaoResult<List<Product>> getAllProduct(String sortBy, String sortOrder);

	DaoResult<?> addProduct(Product product);

	DaoResult<?> removeProductById(Integer productId);

	DaoResult<?> updateProduct(Product product);

}