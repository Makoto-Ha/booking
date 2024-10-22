package com.booking.bean.pojo.shopping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;

import com.booking.bean.pojo.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Data
@Entity
@Table(name = "shop_cart")
@DynamicInsert
public class ShopCart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer shopCartId;

	private Integer cartState;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@OneToMany(mappedBy = "shopCart", cascade = CascadeType.ALL)
	private List<ShopCartItem> cartItems = new ArrayList<>();

	private Integer totalAmount; // 新增總金額屬性

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

	/**
	 * 計算並更新總金額
	 */
	public void calculateTotalAmount() {
		if (cartItems != null && !cartItems.isEmpty()) {
			this.totalAmount = cartItems.stream().mapToInt(ShopCartItem::getSubtotal).sum();
		} else {
			this.totalAmount = 0;
		}
	}

	@Override
	public String toString() {
		StringBuilder items = new StringBuilder();
		for (ShopCartItem item : cartItems) {
			items.append(item.toString()).append(", ");
		}
		if (items.length() > 0) {
			items.setLength(items.length() - 2);
		}
		return "ShopCart [shopCartId=" + shopCartId + ",totalAmount=" + totalAmount + ", cartState=" + cartState
				+ ", user=" + user + ", cartItems=[" + items.toString() + "], updatedAt=" + updatedAt + ", createdAt="
				+ createdAt + "]";
	}

}