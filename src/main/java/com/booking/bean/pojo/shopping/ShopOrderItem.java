package com.booking.bean.pojo.shopping;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "shop_order_item")
public class ShopOrderItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer orderItemId;

	// 與 ShoppingOrder 的多對一關係
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", nullable = false) 
	private ShopOrder shopOrder;

	// 與 Product 的多對一關係
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	private String productName;

	private Integer quantity;

	private Integer price;

	private Integer subtotal;

	private LocalDateTime updatedAt;

	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		createdAt = LocalDateTime.now();
		updatedAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		updatedAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "ShopOrderItem [orderItemId=" + orderItemId + ", productName=" + productName + ", quantity=" + quantity
				+ ", price=" + price + ", subtotal=" + subtotal + ", updatedAt=" + updatedAt + ", createdAt="
				+ createdAt + "]";
	}

	
}
