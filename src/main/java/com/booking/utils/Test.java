package com.booking.utils;

import com.booking.dto.booking.RoomtypeDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Test {
	public static void main(String[] args) throws JsonProcessingException {
		RoomtypeDTO dto = new RoomtypeDTO();
        dto.setRoomtypeName("Deluxe");
        dto.setRoomtypePrice(500);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(dto);
        
        System.out.println(json);  // 應該看不到 static 屬性
	}
}
