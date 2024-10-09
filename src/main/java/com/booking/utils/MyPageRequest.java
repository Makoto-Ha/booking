package com.booking.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

public class MyPageRequest {
	// 簡化PageRequest.of
	public static Pageable of(Integer pageNumber, Integer pageSize, Boolean orderBy, String attrOrderBy) {
		if(orderBy) {
			return PageRequest.of(pageNumber-1, pageSize, Direction.DESC, attrOrderBy);
		}
		
		return PageRequest.of(pageNumber-1, pageSize, Direction.ASC, attrOrderBy);
	}
	
	// page轉換，轉換新的類型和新的內容
	public static <T, U> Page<U> convert(Page<T> sourcePage, Function<T, U> converter) {
        List<U> convertedContent = sourcePage.getContent()
                                             .stream()
                                             .map(converter)
                                             .collect(Collectors.toList());
        Pageable pageable = sourcePage.getPageable();
        return new PageImpl<>(convertedContent, pageable, sourcePage.getTotalElements());
    }
}
