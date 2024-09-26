package com.booking.dto.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.booking.utils.Listable;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class RoomtypeDTO implements Listable {
	private Integer roomtypeId;
	private String roomtypeName;
	private Integer roomtypeCapacity;
	private Integer roomtypePrice;
	private Integer roomtypeQuantity;
	private String roomtypeDescription;
	private String roomtypeAddress;
	private String roomtypeCity;
	private String roomtypeDistrict;
	private LocalDateTime updatedTime;
	private LocalDateTime createdTime;
	private Integer totalCounts;

	private static String[] attrs = { "roomtypeName", "roomtypeCapacity", "roomtypePrice", "roomtypeQuantity",
			"roomtypeDescription", "roomtypeAddress", "roomtypeCity", "roomtypeDistrict", "updatedTime",
			"createdTime" };
	private static String[] attrsChinese = { "房間名稱", "房間人數", "房間價錢", "房間數量", "房間介紹", "房間地址", "房間所在城市", "房間所在區域", "更新時間",
			"創建時間" };
	public static List<Map<String, String>> listInfos = new ArrayList<>();
	private static String[] pages = { "房間類型", "房間" };
	private static String[] pageURL = { "/booking/roomtype", "/booking/room" };
	public static String manageListName = "訂房管理列表";
	public static List<Map<String, String>> pageInfos = new ArrayList<>();

	static {
		for (int i = 0; i < attrs.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("attr", attrs[i]);
			map.put("attrChinese", attrsChinese[i]);
			listInfos.add(map);
		}

		for (int i = 0; i < pages.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("page", pages[i]);
			map.put("url", pageURL[i]);
			pageInfos.add(map);
		}
	}

	public RoomtypeDTO(Integer roomtypeId, String roomtypeName, Integer roomtypeCapacity, Integer roomtypePrice,
			Integer roomtypeQuantity, String roomtypeDescription, String roomtypeAddress, String roomtypeCity,
			String roomtypeDistrict, LocalDateTime updatedTime, LocalDateTime createdTime, Integer totalCounts) {
		this.roomtypeId = roomtypeId;
		this.roomtypeName = roomtypeName;
		this.roomtypeCapacity = roomtypeCapacity;
		this.roomtypePrice = roomtypePrice;
		this.roomtypeQuantity = roomtypeQuantity;
		this.roomtypeDescription = roomtypeDescription;
		this.roomtypeAddress = roomtypeAddress;
		this.roomtypeCity = roomtypeCity;
		this.roomtypeDistrict = roomtypeDistrict;
		this.updatedTime = updatedTime;
		this.createdTime = createdTime;
		this.totalCounts = totalCounts;
	}

	public RoomtypeDTO() {
	}

	public Integer getRoomtypeId() {
		return roomtypeId;
	}

	public void setRoomtypeId(Integer roomtypeId) {
		this.roomtypeId = roomtypeId;
	}

	public String getRoomtypeName() {
		return roomtypeName;
	}

	public void setRoomtypeName(String roomtypeName) {
		this.roomtypeName = roomtypeName;
	}

	public Integer getRoomtypeCapacity() {
		return roomtypeCapacity;
	}

	public void setRoomtypeCapacity(Integer roomtypeCapacity) {
		this.roomtypeCapacity = roomtypeCapacity;
	}

	public Integer getRoomtypePrice() {
		return roomtypePrice;
	}

	public void setRoomtypePrice(Integer roomtypePrice) {
		this.roomtypePrice = roomtypePrice;
	}

	public Integer getRoomtypeQuantity() {
		return roomtypeQuantity;
	}

	public void setRoomtypeQuantity(Integer roomtypeQuantity) {
		this.roomtypeQuantity = roomtypeQuantity;
	}

	public String getRoomtypeDescription() {
		return roomtypeDescription;
	}

	public void setRoomtypeDescription(String roomtypeDescription) {
		this.roomtypeDescription = roomtypeDescription;
	}

	@JsonIgnore
	public String[] getAttrs() {
		return attrs;
	}

	@JsonIgnore
	public String[] getAttrsChinese() {
		return attrsChinese;
	}

	public String getRoomtypeAddress() {
		return roomtypeAddress;
	}

	public void setRoomtypeAddress(String roomtypeAddress) {
		this.roomtypeAddress = roomtypeAddress;
	}

	public String getRoomtypeCity() {
		return roomtypeCity;
	}

	public void setRoomtypeCity(String roomtypeCity) {
		this.roomtypeCity = roomtypeCity;
	}

	public String getRoomtypeDistrict() {
		return roomtypeDistrict;
	}

	public void setRoomtypeDistrict(String roomtypeDistrict) {
		this.roomtypeDistrict = roomtypeDistrict;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getTotalCounts() {
		return totalCounts;
	}

	public void setTotalCounts(Integer totalCounts) {
		this.totalCounts = totalCounts;
	}

	@Override
	public String getName() {
		return roomtypeName;
	}

	@Override
	public Integer getId() {
		return roomtypeId;
	}

	@Override
	@JsonIgnore
	public Map<String, Object> getAdditionProperties() {
		Map<String, Object> properties = new LinkedHashMap<>();
		List<String> values = new ArrayList<>();
		int maxLength = 4;
		String comma = "....";

		if (roomtypeDescription != null) {
			values.add(roomtypeDescription);
		}

		if (roomtypeAddress != null) {
			values.add(roomtypeAddress);
		}

		Object[] subLists = values.stream().map(s -> s.length() >= maxLength ? s.substring(0, maxLength) + comma : s)
				.toArray();

		if (subLists.length == 0) {
			return properties;
		}

		properties.put("房間類型價錢", roomtypePrice);
		properties.put("房間類型數量", roomtypeQuantity);
		properties.put("房間類型地址", subLists[1]);
		properties.put("房間類型城市", roomtypeCity);
		properties.put("房間類型地區", roomtypeDistrict);
		properties.put("房間類型說明", subLists[0]);

		return properties;
	}

	@Override
	public String toString() {
		return "RoomtypeDTO [roomtypeId=" + roomtypeId + ", roomtypeName=" + roomtypeName + ", roomtypeCapacity="
				+ roomtypeCapacity + ", roomtypePrice=" + roomtypePrice + ", roomtypeQuantity=" + roomtypeQuantity
				+ ", roomtypeDescription=" + roomtypeDescription + ", roomtypeAddress=" + roomtypeAddress
				+ ", roomtypeCity=" + roomtypeCity + ", roomtypeDistrict=" + roomtypeDistrict + ", updatedTime="
				+ updatedTime + ", createdTime=" + createdTime + ", totalCounts=" + totalCounts + "]";
	}

}
