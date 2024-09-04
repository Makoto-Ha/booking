package com.booking.dto.booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO還沒寫完，要思考每個人的pages
 */
public class CommonDTO {
	private static String[] bookingPages = { "房間類型", "房間" };
	private static String[] pageURL = {"/booking/roomtype", "/booking/room"};
	public static List<Map<String, String>> pageInfos = new ArrayList<>();
	
	static {
		for(int i=0; i<bookingPages.length; i++) {
			Map<String, String> map = new HashMap<>();
			map.put("page", bookingPages[i]);
			map.put("url", pageURL[i]);
			pageInfos.add(map);
		}
	}
}
