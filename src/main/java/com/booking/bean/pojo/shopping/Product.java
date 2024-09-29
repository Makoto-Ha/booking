package com.booking.bean.pojo.shopping;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	
	@Column(name = "category_id")
	private Integer categoryId;
	
	@Column(name = "product_name")
	private String productName;
	
	@Column(name = "product_image")
	private String productImage;
	
	@Column(name = "product_description")
	private String productDescription;
	
	@Column(name = "product_price")
	private Integer productPrice;
	
	@Column(name = "product_sales")
	private Integer productSales;
	
	@Column(name = "product_inventorey")
	private Integer productInventorey;
	
	@Column(name = "product_state")
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