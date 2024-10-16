package com.booking.bean.pojo.shopping;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="shopping_order")
public class ShoppingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    private Integer userId;

    private Integer orderPrice;

    private Integer orderState;

    private Integer paymentMethod;

    private Integer paymentState;

    private String merchantTradeNo;

    private String transactionId;

    private LocalDateTime paymentCreatedAt;

    private LocalDateTime paymentUpdatedAt;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "shoppingOrder", cascade = CascadeType.ALL)
    private List<ShoppingOrderItem> orderItems;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

	public ShoppingOrder() {
		super();
	}

	public ShoppingOrder(Integer orderId, Integer userId, Integer orderPrice, Integer orderState, Integer paymentMethod,
			Integer paymentState, String merchantTradeNo, String transactionId, LocalDateTime paymentCreatedAt,
			LocalDateTime paymentUpdatedAt, LocalDateTime createdAt, LocalDateTime updatedAt,
			List<ShoppingOrderItem> orderItems) {
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
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.orderItems = orderItems;
	}
    
    
}
