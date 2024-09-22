package com.booking.utils;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtil {
	public static void copyProperties(Object toObj, Object fromObj) {
		try {
			BeanUtils.copyProperties(toObj, fromObj);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
