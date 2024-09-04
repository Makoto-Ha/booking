package com.booking.utils;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RequestParamUtils {

    /**
     * 通用方法，用於處理從 HttpServletRequest 中獲取的參數。
     * 當傳入 String 類型時，會返回處理後的 String；當傳入 Integer 類型時，會返回處理後的 Integer；
     * 當傳入 LocalDate 類型時，會返回處理後的 LocalDate。
     * 如果參數為 null、空字符串（對於 String 或 LocalDate 類型），
     * 或無法轉換為 Integer（對於 Integer 類型）或 LocalDate（對於 LocalDate 類型），則返回 null。
     *
     * @param request   HttpServletRequest 對象
     * @param paramName 要獲取的參數名稱
     * @param clazz     返回類型的 Class (String.class, Integer.class 或 LocalDate.class)
     * @param <T>       泛型，表示返回類型
     * @return 處理後的參數值，或在失敗時返回 null
     */
    public static <T> T getParameter(HttpServletRequest request, String paramName, Class<T> clazz) {
        String param = request.getParameter(paramName);

        if (clazz == String.class) {
            if (param != null) {
                param = param.trim();
                if (!param.isEmpty()) {
                    return clazz.cast(param); // 返回處理後的字符串
                }
            }
            return null; // 當 param 為 null 或空字符串時，返回 null
        } else if (clazz == Integer.class) {
            if (param != null) {
                param = param.trim();
                if (!param.isEmpty()) {
                    try {
                        return clazz.cast(Integer.parseInt(param)); // 返回處理後的 Integer
                    } catch (NumberFormatException e) {
                        // 當無法轉換為 Integer 時，返回 null
                        return null;
                    }
                }
            }
            return null; // 當 param 為 null 或空字符串時，返回 null
        } else if (clazz == LocalDate.class) {
            if (param != null) {
                param = param.trim();
                if (!param.isEmpty()) {
                    try {
                        return clazz.cast(LocalDate.parse(param)); // 返回處理後的 LocalDate
                    } catch (DateTimeParseException e) {
                        // 當無法轉換為 LocalDate 時，返回 null
                        return null;
                    }
                }
            }
            return null; // 當 param 為 null 或空字符串時，返回 null
        }

        throw new IllegalArgumentException("Unsupported class type: " + clazz.getName());
    }
    
    public static Object getParameterDate(HttpServletRequest request, String paramName, Class<?> clazz) {
		String paramValue = request.getParameter(paramName);
		if (paramValue == null || paramValue.isEmpty()) {
	        return null;  // 如果參數為空，返回 null 或者一個合理的默認值
	    }
		if (clazz == String.class) {
	        return paramValue;
	    } else if (clazz == Integer.class) {
	        return Integer.parseInt(paramValue);
	    } else if (clazz == LocalDate.class) {
	        return LocalDate.parse(paramValue);  // 這裡只會處理非空的有效日期字符串
	    } else {
	        throw new IllegalArgumentException("Unsupported class type: " + clazz.getName());
	    }
	}
}
