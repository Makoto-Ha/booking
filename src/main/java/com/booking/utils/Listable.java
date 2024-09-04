package com.booking.utils;

import java.util.Map;

// 用於展示列表清單的方法
public interface Listable {
	String getName();
	Integer getId();
	Integer getTotalCounts();
	Map<String, Object> getAdditionProperties();
}
