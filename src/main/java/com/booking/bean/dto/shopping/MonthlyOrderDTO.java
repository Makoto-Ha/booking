package com.booking.bean.dto.shopping;

import lombok.Data;

@Data
public class MonthlyOrderDTO {

	private String month;
	private Long orderCount;

	public MonthlyOrderDTO(String month, Long orderCount) {
		super();
		this.month = month;
		this.orderCount = orderCount;
	}

	public MonthlyOrderDTO() {
		super();
	}

}
