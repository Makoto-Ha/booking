package com.booking.dao.booking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.BookingOrderItemId;

public interface BookingOrderItemRespository extends JpaRepository<BookingOrderItem, BookingOrderItemId> {

	// 根據roomId獲取他的訂單項目在該日期下的狀態
	@Query("SELECT b FROM BookingOrderItem b WHERE b.room.id = :roomId AND :date BETWEEN b.checkInDate AND b.checkOutDate")
	List<BookingOrderItem> findByRoomIdAndDate(@Param("roomId") Integer roomId, @Param("date") LocalDate date);

	@Query("SELECT boi FROM BookingOrderItem boi join boi.roomtype rt WHERE boi.checkInDate <= :endDate AND boi.checkOutDate >= :startDate AND rt.roomtypeId = :roomtypeId AND boi.bookingStatus = 1")
	List<BookingOrderItem> findBookingInRange(Integer roomtypeId, LocalDate startDate, LocalDate endDate);

	@Query("SELECT boi FROM BookingOrderItem boi join boi.roomtype rt WHERE :currentDate >= boi.checkInDate AND boi.checkOutDate >= :currentDate AND rt.roomtypeId = :roomtypeId AND boi.bookingStatus = 1")
	List<BookingOrderItem> findBookingBoi(Integer roomtypeId, LocalDate currentDate);

	@Query(value = """
							       WITH DateSequence AS (
			    SELECT
			        DATEADD(DAY, n - 1, CAST(GETDATE() AS DATE)) AS date
			    FROM (
			        -- 使用 ROW_NUMBER() 生成連續的數字
			        SELECT ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) AS n
			        FROM sys.objects -- 使用系統表生成足夠的數字序列
			    ) AS Numbers
			    WHERE n <= DATEDIFF(DAY, GETDATE(), DATEADD(MONTH, 4, GETDATE()))
			),
			RoomAvailability AS (
			    SELECT
			        rt.roomtype_id,
			        ds.date,
			        (
			            SELECT COUNT(DISTINCT boi.room_id)
			            FROM booking_order_item boi
			            WHERE boi.room_id IN (SELECT room_id FROM Room WHERE roomtype_id = rt.roomtype_id)
			            AND ds.date >= boi.check_in_date
			            AND ds.date <= boi.check_out_date
			        ) AS booked_rooms,
			        (
			            SELECT COUNT(*)
			            FROM Room r
			            WHERE r.roomtype_id = rt.roomtype_id
			        ) AS total_rooms
			    FROM DateSequence ds
			    CROSS JOIN Roomtype rt
			    WHERE rt.roomtype_id = :roomtypeId
			)
			SELECT
			    date AS fullyBookedDate
			FROM RoomAvailability
			WHERE booked_rooms = total_rooms
			AND total_rooms > 0
			ORDER BY roomtype_id, date;
							    """, nativeQuery = true)
	List<?> findFullyBookedDates(Integer roomtypeId);
	
	/**
	 * 查找10天之前的預訂次數
	 * @return
	 */
	@Query("SELECT boi.checkInDate, COUNT(boi.checkInDate) " +
		       "FROM BookingOrderItem boi " +
		       "WHERE boi.checkInDate BETWEEN :startDate " +
		       "AND :endDate " +
		       "GROUP BY boi.checkInDate " +
		       "ORDER BY boi.checkInDate")
	List<Object[]> countBookingsByCheckInDate(LocalDate startDate, LocalDate endDate);
	
}
