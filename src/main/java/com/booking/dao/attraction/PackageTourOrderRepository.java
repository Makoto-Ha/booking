package com.booking.dao.attraction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.booking.bean.pojo.attraction.PackageTourOrder;

public interface PackageTourOrderRepository extends JpaRepository<PackageTourOrder, Integer>, JpaSpecificationExecutor<PackageTourOrder>, PackageTourOrderDao {

    List<PackageTourOrder> findByUserId(Integer userId);
    List<PackageTourOrder> findByOrderStatus(Byte orderStatus);
}
