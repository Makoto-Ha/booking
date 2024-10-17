package com.booking.bean.dto.shopping;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProductCategoryDTO {

	private Integer categoryId;

	private String categoryName;

	private String categoryDescription;

	private List<ProductDTO> products;

	// --------------------------------------
	private Integer pageNumber = 1;

	private String attrOrderBy = "categoryId";

	private Boolean selectedSort = true;

	public ProductCategoryDTO(Integer categoryId, String categoryName, String categoryDescription,
			ArrayList<ProductDTO> products, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
		this.products = products;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}

	// JPA用
	public ProductCategoryDTO(Integer categoryId, String categoryName, String categoryDescription) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}

	public ProductCategoryDTO() {
		super();
	}

}
