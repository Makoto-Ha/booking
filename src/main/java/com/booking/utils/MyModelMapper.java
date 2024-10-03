package com.booking.utils;

import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;

// 拷貝屬性，複製的時候能排除空字串和null欄位
public class MyModelMapper {
	public static void map(Object source, Object target) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(skipEmptyStrings()).setSkipNullEnabled(true);
		modelMapper.map(source, target);
	}
	
	private static Condition<Object, Object> skipEmptyStrings() {
		 return new Condition<Object, Object>() {
	            @Override
            public boolean applies(MappingContext<Object, Object> context) {
                if (context.getSource() instanceof String) {
                    // 檢查是否是空字串
                    return context.getSource() != null && !((String) context.getSource()).isEmpty();
                }
                // 對於非 String 類型，只跳過 null
                return context.getSource() != null;
            }
        };
	}
	
}
