package com.booking.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

public class MyPageRequest {
	public static Pageable of(Integer pageNumber, Integer pageSize, Boolean orderBy, String attrOrderBy) {
		if(orderBy) {
			return PageRequest.of(pageNumber-1, pageSize, Direction.DESC, attrOrderBy);
		}
		
		return PageRequest.of(pageNumber-1, pageSize, Direction.ASC, attrOrderBy);
	}
}
