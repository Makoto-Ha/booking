package com.booking.bean.dto.shopping;

import lombok.Data;

@Data
public class PayDetailDTO {
	
	private String userName;
	private String userPhone;
	private String userAddress;
	private String merchantTradeNo;
	private String paymentMethod;
	private String paymentState;
	private String paymentCreatedAt;
	private String paymentUpdatedAt;

}
