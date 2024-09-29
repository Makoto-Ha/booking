package com.booking.dao.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.booking.bean.pojo.booking.Roomtype;

public interface RoomtypeRepository extends JpaRepository<Roomtype, Integer>, JpaSpecificationExecutor<Roomtype>, RoomtypeDao {

}