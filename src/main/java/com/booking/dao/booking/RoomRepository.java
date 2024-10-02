package com.booking.dao.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.booking.bean.dto.booking.RoomDTO;
import com.booking.bean.pojo.booking.Room;

public interface RoomRepository extends JpaRepository<Room, Integer>, JpaSpecificationExecutor<Room> {
	// 模糊查詢
	@Query("SELECT (r.roomId, r.roomStatus, r.roomDescription, r.roomNumber) FROM Room r JOIN r.roomtype rt")
	Page<Room> findRoomDTOAll(Specification<Room> spec, Pageable pageable);
	
	// 查全部
	@Query("SELECT new com.booking.bean.dto.booking.RoomDTO(r.roomId, r.roomStatus, r.roomDescription, r.roomNumber, rt.roomtypeName, rt.roomtypeId) FROM Room r JOIN r.roomtype rt")
	Page<RoomDTO> findRoomDTOAll(Pageable pageable);
}
