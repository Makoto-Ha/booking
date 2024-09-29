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
	
	@Query("SELECT (r.roomId, r.roomStatus, r.roomDescription, r.roomNumber) FROM Room r")
	Page<Room> findAllRoomDTO(Specification<Room> spec, Pageable pageable);
	
	@Query("SELECT new com.booking.bean.dto.booking.RoomDTO(r.roomId, r.roomStatus, r.roomDescription, r.roomNumber, rt.roomtypeName) FROM Room r JOIN r.roomtype rt")
	Page<RoomDTO> findAllRoomDTO(Pageable pageable);
}
