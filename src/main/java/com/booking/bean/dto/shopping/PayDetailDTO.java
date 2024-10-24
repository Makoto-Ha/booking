package com.booking.bean.dto.shopping;

import lombok.Data;

@Data
public class PayDetailDTO {
	
	private String receiverName;
	private String receiverPhone;
	private String receiverAddress;
	private String merchantTradeNo;
	private String paymentMethod;
	private String paymentState;
	private String paymentCreatedAt;
	private String paymentUpdatedAt;
	
	public PayDetailDTO() {
		super();
	}

	public PayDetailDTO(String receiverName, String receiverPhone, String receiverAddress, String merchantTradeNo,
			String paymentMethod, String paymentState, String paymentCreatedAt, String paymentUpdatedAt) {
		super();
		this.receiverName = receiverName;
		this.receiverPhone = receiverPhone;
		this.receiverAddress = receiverAddress;
		this.merchantTradeNo = merchantTradeNo;
		this.paymentMethod = paymentMethod;
		this.paymentState = paymentState;
		this.paymentCreatedAt = paymentCreatedAt;
		this.paymentUpdatedAt = paymentUpdatedAt;
	}

	
	
	
	
}
