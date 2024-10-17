package com.booking.bean.dto.shopping;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ShopOrderDTO {

	private Integer orderId;

	private Integer userId;

	private Integer orderPrice;

	private Integer orderState;

	private Integer paymentMethod;

	private Integer paymentState;

	private String merchantTradeNo;

	private String transactionId;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTF+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime paymentCreatedAt;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTF+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime paymentUpdatedAt;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTF+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime updatedAt;

	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTF+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDateTime createdAt;

	private List<ShopOrderItemDTO> orderItems;

	// --------------------------------------
	private Integer pageNumber = 1;
	private String attrOrderBy = "orderId";
	private Boolean selectedSort = true;
	// --------------------------------------

	
	public ShopOrderDTO() {
		super();
	}

	public ShopOrderDTO(Integer orderId, Integer userId, Integer orderPrice, Integer orderState, Integer paymentMethod,
			Integer paymentState, String merchantTradeNo, String transactionId, LocalDateTime paymentCreatedAt,
			LocalDateTime paymentUpdatedAt, LocalDateTime updatedAt, LocalDateTime createdAt,
			List<ShopOrderItemDTO> orderItems, Integer pageNumber, String attrOrderBy, Boolean selectedSort) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.orderPrice = orderPrice;
		this.orderState = orderState;
		this.paymentMethod = paymentMethod;
		this.paymentState = paymentState;
		this.merchantTradeNo = merchantTradeNo;
		this.transactionId = transactionId;
		this.paymentCreatedAt = paymentCreatedAt;
		this.paymentUpdatedAt = paymentUpdatedAt;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
		this.orderItems = orderItems;
		this.pageNumber = pageNumber;
		this.attrOrderBy = attrOrderBy;
		this.selectedSort = selectedSort;
	}

	public ShopOrderDTO(Integer orderId, Integer userId, Integer orderPrice, Integer orderState, Integer paymentMethod,
			Integer paymentState, String merchantTradeNo, String transactionId, LocalDateTime paymentCreatedAt,
			LocalDateTime paymentUpdatedAt, LocalDateTime updatedAt, LocalDateTime createdAt,
			List<ShopOrderItemDTO> orderItems) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.orderPrice = orderPrice;
		this.orderState = orderState;
		this.paymentMethod = paymentMethod;
		this.paymentState = paymentState;
		this.merchantTradeNo = merchantTradeNo;
		this.transactionId = transactionId;
		this.paymentCreatedAt = paymentCreatedAt;
		this.paymentUpdatedAt = paymentUpdatedAt;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
		this.orderItems = orderItems;
	}

	public ShopOrderDTO(Integer orderId, Integer userId, Integer orderPrice, Integer orderState, Integer paymentMethod,
			Integer paymentState, String merchantTradeNo, String transactionId, LocalDateTime paymentCreatedAt,
			LocalDateTime paymentUpdatedAt, LocalDateTime updatedAt, LocalDateTime createdAt) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.orderPrice = orderPrice;
		this.orderState = orderState;
		this.paymentMethod = paymentMethod;
		this.paymentState = paymentState;
		this.merchantTradeNo = merchantTradeNo;
		this.transactionId = transactionId;
		this.paymentCreatedAt = paymentCreatedAt;
		this.paymentUpdatedAt = paymentUpdatedAt;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public ShopOrderDTO(Integer orderId, Integer userId, Integer orderPrice, Integer orderState, Integer paymentMethod,
			Integer paymentState, String merchantTradeNo, String transactionId, LocalDateTime paymentCreatedAt,
			LocalDateTime paymentUpdatedAt, LocalDateTime updatedAt, LocalDateTime createdAt,
			ShopOrderItemDTO orderItemDTO) {
		this.orderId = orderId;
		this.userId = userId;
		this.orderPrice = orderPrice;
		this.orderState = orderState;
		this.paymentMethod = paymentMethod;
		this.paymentState = paymentState;
		this.merchantTradeNo = merchantTradeNo;
		this.transactionId = transactionId;
		this.paymentCreatedAt = paymentCreatedAt;
		this.paymentUpdatedAt = paymentUpdatedAt;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
		this.orderItems = List.of(orderItemDTO); // 初始化包含單一ShopOrderItemDTO的集合
	}

}
