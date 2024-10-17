package com.booking.bean.pojo.shopping;

import java.time.LocalDateTime;
import java.util.List;
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
@Data
@Table(name="shop_order")
public class ShopOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer orderId;

    // 與 User 的多對一關係
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)  // 外鍵 user_id
    private TestUser users;

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

    @Override
    public String toString() {
        return "ShopOrder{" +
                "orderId=" + orderId +
                ", orderPrice=" + orderPrice +
                ", orderState=" + orderState +
                ", paymentMethod=" + paymentMethod +
                ", paymentState=" + paymentState +
                ", merchantTradeNo='" + merchantTradeNo + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", paymentCreatedAt=" + paymentCreatedAt +
                ", paymentUpdatedAt=" + paymentUpdatedAt +
                ", updatedAt=" + updatedAt +
                ", createdAt=" + createdAt +
                ", userId=" + (users != null ? users.getUserId() : "null") +  // 避免遞歸，僅打印 userId
                '}';
    }

    
}
