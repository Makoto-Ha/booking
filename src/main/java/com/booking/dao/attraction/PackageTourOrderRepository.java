package com.booking.dao.attraction;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.booking.bean.pojo.attraction.PackageTourOrder;
import com.booking.bean.pojo.user.User;

public interface PackageTourOrderRepository extends JpaRepository<PackageTourOrder, Integer>, JpaSpecificationExecutor<PackageTourOrder>, PackageTourOrderDao {

    List<PackageTourOrder> findByUserId(Integer userId);
    List<PackageTourOrder> findByOrderStatus(Byte orderStatus);
    Page<PackageTourOrder> findByUser(User user, Pageable pageable);
}
