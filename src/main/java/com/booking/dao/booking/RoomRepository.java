package com.booking.dao.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.booking.bean.dto.booking.RoomDTO;
import com.booking.bean.pojo.booking.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {
	
	// 查全部
	@Query("SELECT new com.booking.bean.dto.booking.RoomDTO(r.roomId, r.roomStatus, r.roomDescription, r.roomNumber, rt.roomtypeName, rt.roomtypeId) FROM Room r JOIN r.roomtype rt")
	Page<RoomDTO> findRoomDTOAll(Pageable pageable);
	
	// 根據roomtypeName查找房間
	@Query("SELECT new com.booking.bean.dto.booking.RoomDTO(r.roomId, r.roomStatus, r.roomDescription, r.roomNumber, rt.roomtypeName, rt.roomtypeId) FROM Room r JOIN r.roomtype rt WHERE rt.roomtypeName = :roomtypeName")
	Page<RoomDTO> findRoomsByRoomtypeName(Pageable pageable, String roomtypeName);
	
	@Query("SELECT DISTINCT r FROM Room r " +
	           "WHERE (" +
	           "    (0 IN :status AND NOT EXISTS (" +
	           "        SELECT 1 FROM BookingOrderItem boi " +
	           "        WHERE boi.room.id = r.id " +
	           "        AND boi.bookingStatus IN (1, 2) " +  // 排除已預定和已入住的房間
	           "        AND :date BETWEEN boi.checkInDate AND boi.checkOutDate" +
	           "    )) " +
	           "    OR (1 IN :status AND NOT EXISTS (" +
	           "        SELECT 1 FROM BookingOrderItem boi " +
	           "        WHERE boi.room.id = r.id " +
	           "        AND boi.bookingStatus = 2 " +         // 排除已入住的房間，但允許已預定的房間
	           "        AND :date BETWEEN boi.checkInDate AND boi.checkOutDate" +
	           "    ))" +
	           ")")
	    Page<Room> findAvailableRooms(Pageable pageable, List<Integer> status, LocalDate date);
}
