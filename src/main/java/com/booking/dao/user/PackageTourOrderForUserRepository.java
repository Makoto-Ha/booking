package com.booking.dao.user;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.booking.bean.pojo.attraction.PackageTourOrder;

@Repository
public interface PackageTourOrderForUserRepository extends JpaRepository<PackageTourOrder, Integer> {
    // 查找特定用戶的所有訂單，按訂單時間倒序排序
	List<PackageTourOrder> findByUser_UserIdOrderByOrderDateTimeDesc(Integer userId);
    
    // 根據訂單狀態查詢
    List<PackageTourOrder> findByOrderStatus(Integer orderStatus);
    
    // 根據旅遊日期查詢
    List<PackageTourOrder> findByTravelDate(LocalDate travelDate);
    
    // 查詢特定日期範圍的訂單
    List<PackageTourOrder> findByTravelDateBetween(LocalDate startDate, LocalDate endDate);
}