package com.booking.dao.booking;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.booking.mapping.RoomtypeBookingStats;

public interface RoomtypeRepository extends JpaRepository<Roomtype, Integer>, JpaSpecificationExecutor<Roomtype>, RoomtypeDao {
	
	/**
	 * 查找最多預定得前幾房型
	 * @param pageable
	 * @return
	 */
	@Query("SELECT COUNT(rt.roomtypeId) AS count, rt.roomtypeId AS id " +
		       "FROM BookingOrderItem boi " +
		       "JOIN boi.roomtype rt " +
		       "GROUP BY rt.roomtypeId " +
		       "ORDER BY COUNT(rt.roomtypeId) DESC")
	List<RoomtypeBookingStats> findMostBookedRoomtypes(Pageable pageable);

	/**
	 * 查找房型人數的每個預訂情況
	 * @return
	 */
	@Query("SELECT rt.roomtypeCapacity as capacity, COUNT(rt.roomtypeCapacity) as count FROM Roomtype rt JOIN rt.bookingOrderItems group by rt.roomtypeCapacity")
	List<RoomtypeBookingStats> findMostBookedCapacity();
}