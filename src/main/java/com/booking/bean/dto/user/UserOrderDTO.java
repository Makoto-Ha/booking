package com.booking.bean.dto.user;

import java.math.BigDecimal;
import java.util.List;

import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.bean.pojo.booking.BookingOrder;
import com.booking.bean.pojo.shopping.ShopOrder;

import lombok.Data;

@Data
public class UserOrderDTO {
    private Integer userId;
    private String userName;
    private List<BookingOrder> bookingOrders;
    private List<ShopOrder> shopOrders;
    private List<PackageTourOrder> packageTourOrders;
    
    // 訂單統計資訊
    private int totalBookingOrders;
    private int totalShopOrders;
    private int totalPackageTourOrders;
    private BigDecimal totalSpending;
}