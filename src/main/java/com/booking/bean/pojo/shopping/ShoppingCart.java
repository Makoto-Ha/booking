//package com.booking.bean.pojo.shopping;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import org.hibernate.annotations.DynamicInsert;
//
//import com.booking.bean.pojo.admin.User;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "shopping_cart")
//@DynamicInsert
//public class ShoppingCart {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer shoppingCartId;
//
//    private Integer cartState;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL)
//    private List<ShoppingCartItem> cartItems = new ArrayList<>();
//    
//    private LocalDateTime updatedAt;
//    
//    @Column(nullable = false)
//    private LocalDateTime createdAt;
//}
