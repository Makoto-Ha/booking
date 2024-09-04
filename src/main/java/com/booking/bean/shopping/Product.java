package com.booking.bean.shopping;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer productId;
	private Integer categoryId;
	private String productName;
	private String productImage;
	private String productDescription;
	private Integer productPrice;
	private Integer productSales;
	private Integer productInventorey;
	private Integer productState;

	public Product() {
	}

	public Product(Integer categoryId, String productName, String productImage, String productDescription,
			Integer productPrice, Integer productSales, Integer productInventorey, Integer productState) {
		super();
		this.categoryId = categoryId;
		this.productName = productName;
		this.productImage = productImage;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.productSales = productSales;
		this.productInventorey = productInventorey;
		this.productState = productState;
	}

	public Product(Integer productId, Integer categoryId, String productName, String productImage,
			String productDescription, Integer productPrice, Integer productSales, Integer productInventorey,
			Integer productState) {
		super();
		this.productId = productId;
		this.categoryId = categoryId;
		this.productName = productName;
		this.productImage = productImage;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.productSales = productSales;
		this.productInventorey = productInventorey;
		this.productState = productState;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getProductSales() {
		return productSales;
	}

	public void setProductSales(Integer productSales) {
		this.productSales = productSales;
	}

	public Integer getProductInventorey() {
		return productInventorey;
	}

	public void setProductInventorey(Integer productInventorey) {
		this.productInventorey = productInventorey;
	}

	public Integer getProductState() {
		return productState;
	}

	public void setProductState(Integer productState) {
		this.productState = productState;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", categoryId=" + categoryId + ", productName=" + productName
				+ ", productImage=" + productImage + ", productDescription=" + productDescription + ", productPrice="
				+ productPrice + ", productSales=" + productSales + ", productInventorey=" + productInventorey
				+ ", productState=" + productState + "]";
	}

}