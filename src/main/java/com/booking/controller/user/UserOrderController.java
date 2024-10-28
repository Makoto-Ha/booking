package com.booking.controller.user;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.booking.bean.dto.booking.BookingOrderDTO;
import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.bean.pojo.user.User;
import com.booking.service.booking.BookingService;
import com.booking.service.shopping.ShopClientService;
import com.booking.service.user.UserOrderService;
import com.booking.service.user.UserService;
import com.booking.utils.Result;

@Controller
@RequestMapping("/user")
public class UserOrderController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserOrderService userOrderService;
    
    @Autowired
    private BookingService bookingService;
    
    @Autowired
    private ShopClientService shopClientService;

    @GetMapping("/orders")
    public String showUserOrders(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null) {
            return "redirect:/auth/login";
        }

        try {
            // 獲取用戶信息
            User user;
            if (auth.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) auth.getPrincipal();
                String email = oauth2User.getAttribute("email");
                user = userService.findByUserMail(email);
            } else {
                String username = auth.getName();
                user = userService.findByUserAccount(username);
            }
            
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // 獲取訂單數據並處理狀態名稱
            List<Map> packageTourOrders = userOrderService.getPackageTourOrdersBasic(user.getUserId());
            List<Map> shopOrders = userOrderService.getShopOrdersBasic(user.getUserId());
            List<Map> bookingOrders = userOrderService.getBookingOrdersBasic(user.getUserId());

            // 處理每個訂單的狀態名稱
            packageTourOrders.forEach(order -> {
                Integer status = (Integer) order.get("status");
                order.put("statusName", userOrderService.getPackageTourOrderStatusName(status));
            });

            shopOrders.forEach(order -> {
                Integer orderState = (Integer) order.get("orderStatus");
                Integer paymentState = (Integer) order.get("paymentStatus");
                order.put("orderStatusName", userOrderService.getShopOrderStatusName(orderState));
                order.put("paymentStatusName", userOrderService.getShopPaymentStatusName(paymentState));
            });

            bookingOrders.forEach(order -> {
                Integer status = (Integer) order.get("status");
                order.put("statusName", userOrderService.getBookingOrderStatusName(status));
            });

            model.addAttribute("packageTourOrders", packageTourOrders);
            model.addAttribute("shopOrders", shopOrders);
            model.addAttribute("bookingOrders", bookingOrders);

            return "users/orders";
            
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/auth/login?error=用戶資訊獲取失敗";
        }
    }
    
 // 查詢住宿訂單詳細
//    @GetMapping("/orders/{bookingId}")
//    public String getBookingOrderDetail(@PathVariable Integer bookingId, Model model) {
//        try {
//            Result<BookingOrderDTO> result = bookingService.findBookingOrderById(bookingId);
//            if (result.isSuccess()) {
//                model.addAttribute("orderDetail", result.getData());
//                return "users/orderDetail"; // 新建一個orderDetail.html模板
//            } else {
//                return "redirect:/user/orders?error=" + result.getMessage();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "redirect:/user/orders?error=訂單詳情獲取失敗";
//        }
//    }

    // 查詢商城訂單詳細
    @GetMapping("/shop/detail/{orderId}")
    public String getShopOrderDetail(@PathVariable Integer orderId, Model model) {
        Result<ShopOrderDTO> result = shopClientService.getOrderById(orderId);
        if (result.isSuccess()) {
            model.addAttribute("shopOrder", result.getData());
            return "users/shop-order-detail";  // 返回商城訂單詳細的視圖
        }
        return "redirect:/user/orders?error=訂單查詢失敗";
    }
    
    
    
    @GetMapping("/orders/{orderId}")
    public String showBookingOrderDetail(@PathVariable Integer orderId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth == null) {
            return "redirect:/auth/login";
        }

        try {
            // 獲取用戶信息
            User user;
            if (auth.getPrincipal() instanceof OAuth2User) {
                OAuth2User oauth2User = (OAuth2User) auth.getPrincipal();
                String email = oauth2User.getAttribute("email");
                user = userService.findByUserMail(email);
            } else {
                String username = auth.getName();
                user = userService.findByUserAccount(username);
            }
            
            if (user == null) {
                throw new RuntimeException("User not found");
            }

            // 獲取訂單詳細資訊
            List<Map> orderDetails = userOrderService.getBookingOrderDetailsBasic(user.getUserId());
            
            // 處理訂單狀態名稱
            orderDetails.forEach(order -> {
                Integer status = (Integer) order.get("bookingStatus");
                order.put("statusName", userOrderService.getBookingOrderStatusName(status));
            });

            model.addAttribute("orderDetails", orderDetails);
            return "users/order-detail";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/auth/login?error=訂單資訊獲取失敗";
        }
    }
}
