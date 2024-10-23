package com.booking.service.booking.client;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.bean.pojo.booking.BookingOrderItem;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.booking.BookingOrderItemRespository;
import com.booking.dao.booking.RoomtypeRepository;

@Service
public class RoomClientService {
	
	@Autowired
	private RoomtypeRepository roomtypeRepo;
	
	@Autowired
	private BookingOrderItemRespository boiRepo;
	
	/**
	 * 根據roomtypeId獲取那一天的空房數量
	 * @param date
	 * @param roomtypeId
	 * @return
	 */
	public int findAvailableCount(LocalDate date, Integer roomtypeId) {
		Roomtype roomtype = roomtypeRepo.findById(roomtypeId).orElse(null);
		
		if(roomtype == null) {
			return 0;
		}
		
		List<Room> rooms = roomtype.getRooms();
		
		int roomCount = rooms.size();
		
		List<BookingOrderItem> bois = boiRepo.findBookingBoi(roomtype.getRoomtypeId(), date);
		
		return roomCount - bois.size();
	}
	
	/**
	 * 查找日期區間，可以預訂幾間房間
	 * @return
	 */
	public int findAvailableRoomCountInRange(LocalDate startDate, LocalDate endDate, Integer roomtypeId) {
	    Roomtype roomtype = roomtypeRepo.findById(roomtypeId).orElse(null);
	    
	    if (roomtype == null) {
	        return 0;  // 如果找不到房型，返回0
	    }
	    
	    List<Room> rooms = roomtype.getRooms();
	    
	    // 查找覆蓋這個日期範圍的預訂項目
	    List<BookingOrderItem> overlappingBookings = boiRepo.findBookingInRange(roomtypeId, startDate, endDate);
	    
	    // 過濾出所有在該日期範圍內被預訂的房間
	    Set<Integer> bookedRoomIds = overlappingBookings.stream()
	        .filter(boi -> !(boi.getCheckOutDate().isBefore(startDate) || boi.getCheckInDate().isAfter(endDate)))  // 過濾出日期範圍內的預訂
	        .map(boi -> boi.getRoom().getRoomId())  // 提取被預訂的房間ID
	        .collect(Collectors.toSet());
	    
	    // 計算可用房間數量（總房間數 - 被預訂的房間數）
	    int availableRoomCount = (int) rooms.stream()
	        .filter(room -> !bookedRoomIds.contains(room.getRoomId()))  // 只選擇未被預訂的房間
	        .count();
	    
	    return availableRoomCount;  // 返回可以預訂的房間數量
	}	
	
}
