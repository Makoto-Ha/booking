package com.booking.bean.dto.shopping;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTF+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime updatedAt;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTF+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createdAt;

	// --------------------------------------
    private Integer minPrice;
    private Integer maxPrice;
    private Integer minInventory;
    private Integer maxInventory;
	// --------------------------------------
	private String productImage;
	private Integer pageNumber = 1;
	private String attrOrderBy = "productId";
	private Boolean selectedSort = true;
	public ProductDTO() {
	}
	public ProductDTO(Integer productId, String productName, String productDescription, Integer productPrice,
			Integer productSales, Integer productInventory, Integer productState, Integer categoryId,
			String categoryName, LocalDateTime updatedAt, LocalDateTime createdAt, String productImage,
			Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
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
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
		this.productImage = productImage;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}
	public ProductDTO(Integer productId, String productName, String productDescription, Integer productPrice,
			Integer productSales, Integer productInventory, Integer productState, Integer categoryId,
			String categoryName, LocalDateTime updatedAt, LocalDateTime createdAt, String productImage) {
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
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
		this.productImage = productImage;
	}

	public ProductDTO(String productName, Integer productSales) {
        this.productName = productName;
        this.productSales = productSales;
    }
	

}


