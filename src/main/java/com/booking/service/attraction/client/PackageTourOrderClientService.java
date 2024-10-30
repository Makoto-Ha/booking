package com.booking.service.attraction.client;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.attraction.PackageTourOrderDTO;
import com.booking.bean.pojo.attraction.PackageTour;
import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.bean.pojo.user.User;
import com.booking.dao.attraction.PackageTourOrderRepository;
import com.booking.dao.attraction.PackageTourRepository;
import com.booking.dao.user.UserRepository;
import com.booking.service.user.UserService;
import com.booking.utils.Result;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
import jakarta.transaction.Transactional;

@Service
public class PackageTourOrderClientService {

    @Autowired
    private PackageTourOrderRepository packageTourOrderRepo;
    
    @Autowired
    private PackageTourRepository packageTourRepo;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserService userService;

    /**
     * 綠界支付功能
     */
    public String ecpayCheckout(PackageTourOrderDTO packageTourOrderDTO) {
    	Integer userId = userService.getCurrentUserId();
    	Optional<User> optionUser = userRepository.findById(userId);
    	
    	if(!optionUser.isPresent()) {
    		return "找不到使用者";
    	}
    	
    	User user = optionUser.get();
    	
        AllInOne all = new AllInOne("");
        
        // 生成訂單編號
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20);
        
        // 訂單描述
        String tradeDesc = packageTourOrderDTO.getOrderId() + "-" + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        
        
        AioCheckOutALL obj = new AioCheckOutALL();
        obj.setMerchantTradeNo(uuid);
        obj.setMerchantTradeDate(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
        obj.setMerchantID("3002599"); // 測試用商店代號
        obj.setTotalAmount(packageTourOrderDTO.getOrderPrice().toString());
        obj.setTradeDesc(tradeDesc);
        obj.setItemName(packageTourOrderDTO.getPackageTourName());

		obj.setReturnURL("http:/localhost:8080/booking/packageTourOrder/success");
		obj.setClientBackURL("http:/localhost:8080/booking/packageTourOrder/success");

        obj.setCustomField1(user.getUserId().toString());
        obj.setCustomField2(packageTourOrderDTO.getOrderId().toString());

        String form = all.aioCheckOut(obj, null);
        
        // 更新訂單資訊
        packageTourOrderRepo.findById(packageTourOrderDTO.getOrderId()).ifPresent(order -> {
            order.setOrderDateTime(LocalDateTime.now());
            order.setOrderStatus(2); // 未付款狀態
            packageTourOrderRepo.save(order);
        });

        return form;
    }

    /**
     * 創建新訂單
     */
    @Transactional
    public Result<PackageTourOrderDTO> createOrder(PackageTourOrderDTO orderDTO, String account) {
        // 檢查套裝行程是否存在
        Optional<PackageTour> optionalPackageTour = packageTourRepo.findById(orderDTO.getPackageTourId());
        if(!optionalPackageTour.isPresent()) {
            return Result.failure("找不到指定的套裝行程");
        }
        PackageTour packageTour = optionalPackageTour.get();
        
        Optional<User> optionalUser = userRepository.findByUserAccount(account);
        if(!optionalUser.isPresent()) {
            return Result.failure("找不到指定的使用者");
        }
        User user = optionalUser.get();

        PackageTourOrder packageTourOrder = new PackageTourOrder();
        packageTourOrder.setPackageTour(packageTour);
        packageTourOrder.setUser(user);
        packageTourOrder.setOrderPrice(orderDTO.getOrderPrice());
        packageTourOrder.setTravelDate(orderDTO.getTravelDate());
        packageTourOrder.setOrderStatus(1); // 未付款狀態
        packageTourOrder.setOrderDateTime(LocalDateTime.now());

        packageTourOrder = packageTourOrderRepo.save(packageTourOrder);

        packageTourOrder.getOrderId();
        PackageTourOrderDTO packageTourOrderDTO = new PackageTourOrderDTO();
        BeanUtils.copyProperties(packageTourOrder, packageTourOrderDTO);
        packageTourOrderDTO.setPackageTourId(packageTour.getPackageTourId());
        packageTourOrderDTO.setPackageTourName(packageTour.getPackageTourName());
        packageTourOrderDTO.setUserId(user.getUserId());

        return Result.success(packageTourOrderDTO);
    }

    
    
    /**
     * 處理支付成功,更新訂單狀態
     */
    @Transactional  
    public boolean handlePaymentSuccess(Integer orderId) {
        Optional<PackageTourOrder> optional = packageTourOrderRepo.findById(orderId);
        
        if(!optional.isPresent()) {
            return false;
        }
        
        PackageTourOrder order = optional.get();
        order.setOrderStatus(2);
        packageTourOrderRepo.save(order);
        
        return true;
    }
    

    /**
     * 根據訂單ID取得訂單資料
     */
    public Result<PackageTourOrderDTO> getOrderById(Integer orderId) {
        Optional<PackageTourOrder> optional = packageTourOrderRepo.findById(orderId);
        
        if(!optional.isPresent()) {
            return Result.failure("找不到訂單");
        }
        
        PackageTourOrder packageTourOrder = optional.get();
        PackageTourOrderDTO packageTourOrderDTO = new PackageTourOrderDTO();
        BeanUtils.copyProperties(packageTourOrder, packageTourOrderDTO);
        
        packageTourOrderDTO.setPackageTourId(packageTourOrder.getPackageTour().getPackageTourId());
        packageTourOrderDTO.setPackageTourName(packageTourOrder.getPackageTour().getPackageTourName());
        packageTourOrderDTO.setUserId(packageTourOrder.getUser().getUserId());
        
        return Result.success(packageTourOrderDTO);
    }
}
