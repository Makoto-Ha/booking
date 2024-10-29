package com.booking.service.attraction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.attraction.PackageTourOrderDTO;
import com.booking.bean.pojo.attraction.PackageTour;
import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.bean.pojo.user.User;
import com.booking.dao.attraction.PackageTourOrderRepository;
import com.booking.dao.attraction.PackageTourOrderSpecification;
import com.booking.dao.attraction.PackageTourRepository;
import com.booking.dao.user.UserRepository;
import com.booking.utils.DaoResult;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

import jakarta.transaction.Transactional;

@Service
public class PackageTourOrderService {

    @Autowired
    private PackageTourOrderRepository packageTourOrderRepo;

    @Autowired
    private PackageTourRepository packageTourRepo;

    @Autowired
    private UserRepository userRepo;
    
    /**
     * 獲取所有訂單
     * @param packageTourOrderDTO
     * @return
     */
    public Result<PageImpl<PackageTourOrderDTO>> findAllPackageTourOrder(PackageTourOrderDTO packageTourOrderDTO) {
        Integer pageNumber = packageTourOrderDTO.getPageNumber();
        String attrOrderBy = packageTourOrderDTO.getAttrOrderBy();
        Boolean selectedSort = packageTourOrderDTO.getSelectedSort();
        
        Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
        Page<PackageTourOrder> page = packageTourOrderRepo.findAll(pageable);
        List<PackageTourOrderDTO> packageTourOrderDTOs = new ArrayList<>();
        List<PackageTourOrder> packageTourOrders = page.getContent();

        for (PackageTourOrder packageTourOrder : packageTourOrders) {
            PackageTourOrderDTO responsePackageTourOrderDTO = new PackageTourOrderDTO();
            BeanUtils.copyProperties(packageTourOrder, responsePackageTourOrderDTO);
            if (packageTourOrder.getUser() != null) {
                responsePackageTourOrderDTO.setUserId(packageTourOrder.getUser().getUserId());
            }
            if (packageTourOrder.getPackageTour() != null) {
                responsePackageTourOrderDTO.setPackageTourId(packageTourOrder.getPackageTour().getPackageTourId());
                responsePackageTourOrderDTO.setPackageTourName(packageTourOrder.getPackageTour().getPackageTourName());
            }
            packageTourOrderDTOs.add(responsePackageTourOrderDTO);
        }

        PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
        return Result.success(new PageImpl<>(packageTourOrderDTOs, newPageable, page.getTotalElements()));
    }
    
    
    
    /**
     * 模糊查詢
     * @param packageTourOrderDTO
     * @return
     */
    public Result<PageImpl<PackageTourOrderDTO>> findPackageTourOrders(PackageTourOrderDTO packageTourOrderDTO, LocalDate searchDate) {
        Specification<PackageTourOrder> spec = Specification
            .where(PackageTourOrderSpecification.orderIdEquals(packageTourOrderDTO.getOrderId()))
            .and(PackageTourOrderSpecification.packageTourNameContains(packageTourOrderDTO.getPackageTourName()))
            .and(PackageTourOrderSpecification.orderStatusEquals(packageTourOrderDTO.getOrderStatus()));

        if (searchDate != null) {
            LocalDateTime startOfDay = searchDate.atStartOfDay();
            LocalDateTime endOfDay = searchDate.atTime(LocalTime.MAX);
            spec = spec.and(PackageTourOrderSpecification.orderDateTimeBetween(startOfDay, endOfDay));
        }

        Pageable pageable = MyPageRequest.of(packageTourOrderDTO.getPageNumber(), 10, packageTourOrderDTO.getSelectedSort(),
                packageTourOrderDTO.getAttrOrderBy());

        Page<PackageTourOrder> page = packageTourOrderRepo.findAll(spec, pageable);
        List<PackageTourOrderDTO> packageTourOrderDTOs = page.getContent().stream()
                .map(order -> {
                    PackageTourOrderDTO dto = new PackageTourOrderDTO();
                    BeanUtils.copyProperties(order, dto);
                    if (order.getPackageTour() != null) {
                        dto.setPackageTourId(order.getPackageTour().getPackageTourId());
                        dto.setPackageTourName(order.getPackageTour().getPackageTourName());
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        PageImpl<PackageTourOrderDTO> pageResult = new PageImpl<>(packageTourOrderDTOs, pageable, page.getTotalElements());
        return Result.success(pageResult);
    }
    
    
    
    
    /**
     * 根據ID獲取訂單
     * @param orderId
     * @return
     */
    public Result<PackageTourOrderDTO> findPackageTourOrderById(Integer orderId) {
        PackageTourOrder packageTourOrder = packageTourOrderRepo.findById(orderId).orElse(null);
        if (packageTourOrder == null) {
            return Result.failure("訂單不存在");
        }
        
        PackageTourOrderDTO packageTourOrderDTO = new PackageTourOrderDTO();
        BeanUtils.copyProperties(packageTourOrder, packageTourOrderDTO);
        packageTourOrderDTO.setOrderId(packageTourOrder.getOrderId());
        packageTourOrderDTO.setUserId(packageTourOrder.getUser().getUserId());
        packageTourOrderDTO.setPackageTourName(packageTourOrder.getPackageTour().getPackageTourName());
        
        return Result.success(packageTourOrderDTO);
    }
    

    
    /**
     * 新增訂單
     * @param orderDTO
     * @return
     */
    @Transactional
    public Result<PackageTourOrderDTO> savePackageTourOrder(PackageTourOrderDTO orderDTO) {
        PackageTour packageTour = packageTourRepo.findById(orderDTO.getPackageTourId()).orElse(null);
        if (packageTour == null) {
            return Result.failure("套裝行程不存在");
        }
        
        User user = userRepo.findById(orderDTO.getUserId()).orElse(null);
            if (user == null) {
                return Result.failure("使用者不存在");
            }

        PackageTourOrder packageTourOrder = new PackageTourOrder();
        BeanUtils.copyProperties(orderDTO, packageTourOrder);
        packageTourOrder.setUser(user);
        packageTourOrder.setPackageTour(packageTour);
        packageTourOrder.setOrderDateTime(LocalDateTime.now());
        packageTourOrder.setOrderStatus(1); // 未付款狀態

        DaoResult<PackageTourOrder> createResult = packageTourOrderRepo.createOrder(packageTourOrder);
        if (createResult.isSuccess()) {
            PackageTourOrderDTO createdOrderDTO = new PackageTourOrderDTO();
            BeanUtils.copyProperties(createResult.getData(), createdOrderDTO);
            createdOrderDTO.setPackageTourId(packageTour.getPackageTourId());
            createdOrderDTO.setPackageTourName(packageTour.getPackageTourName());
            return Result.success(createdOrderDTO);
        } else {
            return Result.failure("創建訂單失敗");
        }
    }

    
  
    /**
     * 根據使用者ID獲取訂單
     * @param userId
     * @return
     */
    public Result<List<PackageTourOrderDTO>> getOrdersByUserId(Integer userId) {
        DaoResult<List<PackageTourOrder>> getResult = packageTourOrderRepo.getOrdersByUserId(userId);
        List<PackageTourOrderDTO> packageTourOrderDTOs = getResult.getData().stream()
        		.map(order -> { PackageTourOrderDTO packageTourOrderDTO = new PackageTourOrderDTO();
            BeanUtils.copyProperties(order, packageTourOrderDTO);
            packageTourOrderDTO.setPackageTourId(order.getPackageTour().getPackageTourId());
            packageTourOrderDTO.setPackageTourName(order.getPackageTour().getPackageTourName());
            return packageTourOrderDTO;
        }).collect(Collectors.toList());
        return Result.success(packageTourOrderDTOs);
    }

    
    /**
     * 更新訂單狀態
     * @param orderId
     * @param newStatus
     * @return
     */
    @Transactional
    public Result<String> updatePackageTourOrder(PackageTourOrderDTO packageTourOrderDTO) {
        if (packageTourOrderDTO.getOrderId() == null) {
            return Result.failure("訂單ID不能為空");
        }

        Optional<PackageTourOrder> optional = packageTourOrderRepo.findById(packageTourOrderDTO.getOrderId());
        if (optional.isEmpty()) {
            return Result.failure("找不到指定的訂單");
        }

        PackageTourOrder packageTourOrder = optional.get();
        
        // 只更新訂單狀態
        packageTourOrder.setOrderStatus(packageTourOrderDTO.getOrderStatus());

        packageTourOrderRepo.save(packageTourOrder);
        return Result.success("訂單狀態更新成功");
    }

    
    /**
     * 刪除訂單
     * @param orderId
     * @return
     */
    @Transactional
    public Result<?> deletePackageTourOrderById(Integer orderId) {
        DaoResult<Integer> statusResult = packageTourOrderRepo.getOrderStatus(orderId);
        if (statusResult.isFailure()) {
            return Result.failure("獲取訂單狀態失敗");
        }

        Integer orderStatus = statusResult.getData();
        if (orderStatus == null) {
            return Result.failure("訂單不存在");
        }

        if (orderStatus == 2) { // 2 表示已付款狀態
            return Result.failure("已付款的訂單不能被刪除");
        }

        DaoResult<?> deleteResult = packageTourOrderRepo.deleteOrder(orderId);
        if (deleteResult.isSuccess()) {
            return Result.success("訂單刪除成功");
        } else {
            return Result.failure("刪除訂單失敗");
        }
    }
}
