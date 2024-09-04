package com.booking.dao.shopping;

import com.booking.bean.shopping.Product;
import com.booking.mapper.ProductRowMapper;
import com.booking.utils.DaoResult;
import com.booking.utils.DaoUtils;

public class ProductDao {
	/**
	 * 
	 * @param productId
	 * @return
	 */
	public DaoResult<Product> getProductById(Integer productId) {
		String sql = "SELECT * FROM product WHERE product_id = ?";
		return DaoUtils.commonsQuery(sql, new ProductRowMapper(), productId);
	}
	/**
	 * 傳入排序 升降
	 * @param productName
	 * @param sortBy
	 * @param sortOrder
	 * @return
	 */
	public DaoResult<Product> getProductByName(String productName, String sortBy, String sortOrder){
		 String sql = "SELECT * FROM product WHERE product_name LIKE ? ORDER BY " + sortBy + " " + sortOrder;
	        return DaoUtils.commonsQuery(sql, new ProductRowMapper(), "%" + productName + "%");
	}
	
	public DaoResult<Product> getAllProduct(String sortBy, String sortOrder){
		 String sql = "SELECT * FROM product ORDER BY " + sortBy + " " + sortOrder;
	        return DaoUtils.commonsQuery(sql, new ProductRowMapper());
	}
	
	/**
	 * 
	 * @param product
	 * @return
	 */
	public DaoResult<Integer> addProduct(Product product) {
		String sql = "INSERT INTO product (category_id, product_name, product_image, product_description, product_price, product_sales, product_inventorey,product_state) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
		return DaoUtils.commonsUpdate(sql, product.getCategoryId(), product.getProductName(), product.getProductImage(),
				product.getProductDescription(), product.getProductPrice(), product.getProductSales(),
				product.getProductInventorey(), product.getProductState());
	}
	/**
	 * 
	 * @param productId
	 * @return
	 */
	public DaoResult<Integer> removeProduct(Integer productId) {
		String sql = "DELETE FROM product WHERE product_id = ?";
		return DaoUtils.commonsUpdate(sql, productId);
	}
	/**
	 * 
	 * @param product
	 * @return
	 */
	public DaoResult<Integer> updateProduct(Product product) {
		String sql = "UPDATE product SET product_name = ?,category_id = ?, product_image = ?, "
				+ "product_description = ?, product_price = ?, product_sales = ?,product_inventorey=?,product_state=? "
				+ "WHERE product_id = ?";
		return DaoUtils.commonsUpdate(sql, product.getProductName(), 
										   product.getCategoryId(),
										   product.getProductImage(),
										   product.getProductDescription(),
										   product.getProductPrice(), 
										   product.getProductSales(),
										   product.getProductInventorey(),
										   product.getProductState(),
										   product.getProductId());
	}
}
