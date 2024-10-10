package com.booking.bean.dto.shopping;

import lombok.Data;

@Data
public class ProductDTO {

	private Integer productId;

	private String productName;

	private String productDescription;

	private Integer productPrice;

	private Integer productSales;

	private Integer productInventory;

	private Integer productState;

	private Integer categoryId;

	private String categoryName;
	//--------------------------------------
	private String productImage;
	private Integer pageNumber = 1;
	private String attrOrderBy = "productId";
	private Boolean selectedSort = true;

	public ProductDTO() {

	}

	public ProductDTO(Integer productId, String productName, String productDescription, Integer productPrice,
			Integer productSales, Integer productInventory, Integer productState, Integer categoryId,
			String categoryName,String productImage, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.productSales = productSales;
		this.productInventory = productInventory;
		this.productState = productState;
		this.categoryId = categoryId;
		this.productImage = productImage;
		this.categoryName = categoryName;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}

	public ProductDTO(Integer productId, String productName, String productDescription, Integer productPrice,
			Integer productSales, Integer productInventory, Integer productState, Integer categoryId,
			String categoryName,String productImage) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPrice = productPrice;
		this.productSales = productSales;
		this.productInventory = productInventory;
		this.productState = productState;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.productImage = productImage;
	}

}
