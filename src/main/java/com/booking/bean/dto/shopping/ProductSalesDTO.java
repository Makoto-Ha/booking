package com.booking.bean.dto.shopping;

import lombok.Data;

@Data
public class ProductSalesDTO {

	private String productName;
	private Long salesQuantity;

	public ProductSalesDTO(String productName, Long salesQuantity) {
		super();
		this.productName = productName;
		this.salesQuantity = salesQuantity;
	}

	public ProductSalesDTO() {
		super();
	}

}
