package com.booking.bean.pojo.shopping;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;

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

@Data
@Entity
@Table(name = "shop_cart_item")
@DynamicInsert
public class ShopCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    private ShopCart shopCart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

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
    
    /**
     * 計算小計
     */
    public void calculateSubtotal() {
        if (price != null && quantity != null) {
            this.subtotal = price * quantity;
        } else {
            this.subtotal = 0;
        }
    }
    
    
    @Override
    public String toString() {
        return "ShopCartItem [cartItemId=" + cartItemId + ", productId=" + product.getProductId()
                + ", productName=" + product.getProductName() + ", quantity=" + quantity
                + ", price=" + price + ", subtotal=" + subtotal + ", updatedAt=" + updatedAt
                + ", createdAt=" + createdAt + "]";
    }

    
}
