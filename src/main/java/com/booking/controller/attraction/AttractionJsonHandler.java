package com.booking.controller.attraction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.service.attraction.AttractionService;
import com.booking.utils.JsonUtil;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api",  produces = "application/json")
public class AttractionJsonHandler {
	
	@Autowired
	private AttractionService attractionService;
	

	
	/**
	 * 返回景點類型
	 * @param roomtypeId
	 * @return
	 */
	@GetMapping("/attraction/{id}")
	private String findAttractionById(@PathVariable Integer id) {
		Result<AttractionDTO> attractionServiceResult = attractionService.findAttractionById(id);
		if (attractionServiceResult.isFailure()) {
			return null;
		}
		
		return JsonUtil.toJson(attractionServiceResult.getData());
	}
	
	/**
	 * 根據景點名稱獲取景點
	 * @param name
	 * @return
	 */
	@GetMapping("/attraction")
	private String findAttractionByName(@RequestParam String name, HttpServletResponse response) {
		
		Result<List<AttractionDTO>> attractionServiceResult = attractionService.findAttractionByName(name);
		
		if(attractionServiceResult.isFailure()) {
			return null;
		}
		
		return JsonUtil.toJson(attractionServiceResult.getData());
	}
	


}
