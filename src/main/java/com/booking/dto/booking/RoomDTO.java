package com.booking.dto.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.booking.utils.Listable;

public class RoomDTO implements Listable {
	private Integer roomId;
	private String roomtypeName;
	private String roomNumber;
	private Integer roomStatus;
	private String roomDescription;
	private Integer totalCounts;
	
	private static String[] attrs = {"roomtypeName", "roomNumber", "roomStatus", "roomDescription" };
	private static String[] attrsChinese = {"所屬房間類型", "房間號碼", "房間狀態(0: 閒置、1: 已預定、2: 已入住)", "房間簡介" };
	public static List<Map<String, String>> listInfos = new ArrayList<>();
	
	private static String[] bookingPages = { "房間類型", "房間" };
	private static String[] pageURL = {"/booking/roomtype", "/booking/room"};
	public static List<Map<String, String>> pageInfos = new ArrayList<>();

	static {
		for (int i = 0; i < attrs.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("attr", attrs[i]);
			map.put("attrChinese", attrsChinese[i]);
			listInfos.add(map);
		}
		
		for(int i=0; i<bookingPages.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("page", bookingPages[i]);
			map.put("url", pageURL[i]);
			pageInfos.add(map);
		}
	}
	
	public String getRoomtypeName() {
		return roomtypeName;
	}

	public void setRoomtypeName(String roomtypeName) {
		this.roomtypeName = roomtypeName;
	}

	public Integer getRoomId() {
		return roomId;
	}

	public void setRoomId(Integer roomId) {
		this.roomId = roomId;
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public Integer getRoomStatus() {
		return roomStatus;
	}

	public void setRoomStatus(Integer roomStatus) {
		this.roomStatus = roomStatus;
	}

	public String getRoomDescription() {
		return roomDescription;
	}

	public void setRoomDescription(String roomDescription) {
		this.roomDescription = roomDescription;
	}
	
	public void setTotalCounts(Integer totalCounts) {
		this.totalCounts = totalCounts;
	}
	
	@Override
	public Integer getTotalCounts() {
		return totalCounts;
	}

	@Override
	public String getName() {
		return roomtypeName + ": " + roomNumber + "號碼";
	}

	@Override
	public Integer getId() {
		return roomId;
	}

	@Override
	public Map<String, Object> getAdditionProperties() {
		Map<String, Object> properties = new LinkedHashMap<>();
		properties.put("所屬房間類型", roomtypeName);
        properties.put("房間號碼", roomNumber);
        properties.put("房間狀態", roomStatus);
        properties.put("房間說明", roomDescription);
        return properties;
	}
	
}
