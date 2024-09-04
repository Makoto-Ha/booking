package com.booking.dto.attraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.booking.utils.Listable;

public class AttractionDTO implements Listable {
	private Integer attractionId;
	private String attractionName;
	private String attractionCity;

	private String address;
	private String openingHour;
	private String attractionType;
	private String attractionDescription;
	private Integer totalCounts;
	
	private static String[] attrs = { "attraction-name","attraction-city", "address", "opening-hour", "attraction-type", "attraction-description" };
	private static String[] attrsChinese = { "景點名稱", "城市", "地址", "開放時間", "景點類型", "景點介紹" };
	public static List<Map<String, String>> listInfos = new ArrayList<>();
	
	private static String[] pages = { "景點" };
	private static String[] pageURL = {"/booking/attraction"};
	public static List<Map<String, String>> pageInfos = new ArrayList<>();
	
	public static String manageListName = "景點列表";

	static {
		for (int i = 0; i < attrs.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("attr", attrs[i]);
			map.put("attrChinese", attrsChinese[i]);
			listInfos.add(map);
		}
		
		for(int i=0; i<pages.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("page", pages[i]);
			map.put("url", pageURL[i]);
			pageInfos.add(map);
		}
	}

	public AttractionDTO() {}



	public Integer getAttractionId() {
		return attractionId;
	}



	public void setAttractionId(Integer attractionId) {
		this.attractionId = attractionId;
	}



	public String getAttractionName() {
		return attractionName;
	}



	public void setAttractionName(String attractionName) {
		this.attractionName = attractionName;
	}

	public String getAttractionCity() {
		return attractionCity;
	}
	
	
	
	public void setAttractionCity(String attractionCity) {
		this.attractionCity = attractionCity;
	}


	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getOpeningHour() {
		return openingHour;
	}



	public void setOpeningHour(String openingHour) {
		this.openingHour = openingHour;
	}



	public String getAttractionType() {
		return attractionType;
	}



	public void setAttractionType(String attractionType) {
		this.attractionType = attractionType;
	}



	public String getAttractionDescription() {
		return attractionDescription;
	}



	public void setAttractionDescription(String attractionDescription) {
		this.attractionDescription = attractionDescription;
	}



	public String[] getAttrs() {
		return attrs;
	}

	public String[] getAttrsChinese() {
		return attrsChinese;
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
		return attractionName;
	}

	@Override
	public Integer getId() {
		return attractionId;
	}

	@Override
	public String toString() {
		return "AttractionDTO [attractionId=" + attractionId + ", attractionName=" + attractionName
				+ ", attractionCity=" + attractionCity + ", address=" + address + ", openingHour=" + openingHour
				+ ", attractionType=" + attractionType + ", attractionDescription=" + attractionDescription + ", attrs=" + Arrays.toString(attrs)
				+ ", attrsChinese=" + Arrays.toString(attrsChinese) + "]";
	}

	@Override
	public Map<String, Object> getAdditionProperties(){
		Map<String, Object> properties = new HashMap<>();
		List<String> strs = new ArrayList<>();
		int maxLength = 5;
		String comma = "...";
		strs.add(attractionName);
		strs.add(address);
		strs.add(openingHour);
		strs.add(attractionDescription);
		Object[] subLists = strs.stream().map(s -> s.length() >= maxLength ? s.substring(0, maxLength) + comma : s).toArray(); 
		
		properties.put("景點名稱", subLists[0]);
		properties.put("城市", attractionCity);
		properties.put("地址", subLists[1]);
		properties.put("開放時間", subLists[2]);
		properties.put("景點類型", attractionType);
		properties.put("景點描述", subLists[3]);
		return properties;
	}
}
