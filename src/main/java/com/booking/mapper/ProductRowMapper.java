package com.booking.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.booking.bean.shopping.Product;

public class ProductRowMapper implements RowMapper<Product> {

	@Override
	public Product getRow(ResultSet resultSet) {
		try {
			Integer productId = resultSet.getInt("product_id");
			Integer categoryId = resultSet.getInt("category_id");
			String productName = resultSet.getString("product_name");
			String productImage = resultSet.getString("product_image");
			String productDescription = resultSet.getString("product_description");
			Integer productPrice = resultSet.getInt("product_price");
			Integer productSales = resultSet.getInt("product_sales");
			Integer productInventorey = resultSet.getInt("product_inventorey");
			Integer productState = resultSet.getInt("product_state");
			
			return new Product(productId, categoryId, productName, productImage, productDescription, productPrice, productSales, productInventorey, productState);
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return null;
	}
	
}
