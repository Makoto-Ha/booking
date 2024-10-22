package com.booking.bean.pojo.shopping;

import java.time.LocalDateTime;
import java.util.List;

import com.booking.bean.pojo.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity

@Table(name="shop_order")
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    // 與 User 的多對一關係
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false) // 避免重复
    private TestUser users;

    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false) // 外鍵 user_id
	private User user;

    private Integer orderPrice;

    private Integer orderState;  // 訂單狀態：1: pending, 2: processing, 3: shipped, 4: completed, 5: cancelled

    private Integer paymentMethod;  // 支付方式：1: 綠界, 2: LinePay

    private Integer paymentState;   // 支付狀態：1: unpaid, 2: paid, 3: failed, 4: refunded

    private String merchantTradeNo;

    private String transactionId;

    private LocalDateTime paymentCreatedAt;

    private LocalDateTime paymentUpdatedAt;

    private LocalDateTime updatedAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 與 ShoppingOrderItem 的一對多關係
    @OneToMany(mappedBy = "shopOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShopOrderItem> items;
    
    // ----------------------------------
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public TestUser getUsers() {
		return users;
	}

	public void setUsers(TestUser users) {
		this.users = users;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Integer orderPrice) {
		this.orderPrice = orderPrice;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Integer getPaymentState() {
		return paymentState;
	}

	public void setPaymentState(Integer paymentState) {
		this.paymentState = paymentState;
	}

	public String getMerchantTradeNo() {
		return merchantTradeNo;
	}

	public void setMerchantTradeNo(String merchantTradeNo) {
		this.merchantTradeNo = merchantTradeNo;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public LocalDateTime getPaymentCreatedAt() {
		return paymentCreatedAt;
	}

	public void setPaymentCreatedAt(LocalDateTime paymentCreatedAt) {
		this.paymentCreatedAt = paymentCreatedAt;
	}

	public LocalDateTime getPaymentUpdatedAt() {
		return paymentUpdatedAt;
	}

	public void setPaymentUpdatedAt(LocalDateTime paymentUpdatedAt) {
		this.paymentUpdatedAt = paymentUpdatedAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<ShopOrderItem> getItems() {
		return items;
	}

	public void setItems(List<ShopOrderItem> items) {
		this.items = items;
	}

	public ShopOrder(Integer orderId, TestUser users, User user, Integer orderPrice, Integer orderState,
			Integer paymentMethod, Integer paymentState, String merchantTradeNo, String transactionId,
			LocalDateTime paymentCreatedAt, LocalDateTime paymentUpdatedAt, LocalDateTime updatedAt,
			LocalDateTime createdAt, List<ShopOrderItem> items) {
		super();
		this.orderId = orderId;
		this.users = users;
		this.user = user;
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
		this.items = items;
	}

	public ShopOrder(TestUser users, User user, Integer orderPrice, Integer orderState, Integer paymentMethod,
			Integer paymentState, String merchantTradeNo, String transactionId, LocalDateTime paymentCreatedAt,
			LocalDateTime paymentUpdatedAt, LocalDateTime updatedAt, LocalDateTime createdAt,
			List<ShopOrderItem> items) {
		super();
		this.users = users;
		this.user = user;
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
		this.items = items;
	}

	public ShopOrder(User user, Integer orderPrice, Integer orderState, Integer paymentMethod, Integer paymentState,
			String merchantTradeNo, String transactionId, LocalDateTime paymentCreatedAt,
			LocalDateTime paymentUpdatedAt, LocalDateTime updatedAt, LocalDateTime createdAt,
			List<ShopOrderItem> items) {
		super();
		this.user = user;
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
		this.items = items;
	}

	@Override
	public String toString() {
		return "ShopOrder [orderId=" + orderId + ", users=" + users + ", user=" + user + ", orderPrice=" + orderPrice
				+ ", orderState=" + orderState + ", paymentMethod=" + paymentMethod + ", paymentState=" + paymentState
				+ ", merchantTradeNo=" + merchantTradeNo + ", transactionId=" + transactionId + ", paymentCreatedAt="
				+ paymentCreatedAt + ", paymentUpdatedAt=" + paymentUpdatedAt + ", updatedAt=" + updatedAt
				+ ", createdAt=" + createdAt + ", items=" + items + "]";
	}

	public ShopOrder() {
		super();
	}

 

    
}
