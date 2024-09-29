package com.booking.controller.booking;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.booking.bean.dto.booking.RoomDTO;
import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.booking.RoomRepository;
import com.booking.dao.booking.RoomtypeRepository;
import com.booking.dao.booking.RoomtypeSpecification;
import com.booking.service.booking.RoomService;
import com.booking.service.booking.RoomtypeService;
import com.booking.utils.JsonUtil;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "/api",  produces = "application/json")
public class JsonHandler {
	@Autowired
	private RoomtypeService roomtypeService;
	
	@Autowired
	private RoomService roomService;
	
	@Autowired
	private RoomtypeRepository roomtypeRepo;
	
	@Autowired
	private RoomRepository roomRepo;
	
	// ===== Roomtype =====
	
	// 測試
	@GetMapping("/page")
	private String getRoomtypeByPage(Roomtype roomtype) {
		Pageable pageable = PageRequest.of(0, 10);
		Specification<Roomtype> spec = Specification.where(RoomtypeSpecification.nameContains(roomtype.getRoomtypeName()))
													.and(RoomtypeSpecification.addressContains(roomtype.getRoomtypeAddress()))
													.and(RoomtypeSpecification.capacityContains(roomtype.getRoomtypeCapacity()))
													.and(RoomtypeSpecification.cityContains(roomtype.getRoomtypeCity()))
													.and(RoomtypeSpecification.descriptionContains(roomtype.getRoomtypeDescription()))
													.and(RoomtypeSpecification.districtContains(roomtype.getRoomtypeDistrict()))
													.and(RoomtypeSpecification.priceContains(roomtype.getRoomtypePrice()))
													.and(RoomtypeSpecification.quantityContains(roomtype.getRoomtypeQuantity()));
		Page<Roomtype> page = roomtypeRepo.findAll(spec, pageable);
		return JsonUtil.toJson(page);
	}
	
	
	/**
	 * 返回所有房間類型
	 * @param roomtypeId
	 * @return
	 */
	@GetMapping("/roomtype/{id}")
	private String getRoomtypeById(@PathVariable Integer id) {
		Result<RoomtypeDTO> roomtypeServiceResult = roomtypeService.getRoomtype(id);
		if (roomtypeServiceResult.isFailure()) {
			return null;
		}
		
		return JsonUtil.toJson(roomtypeServiceResult.getData());
	}
	
	/**
	 * 根據房間類型名稱獲取房間類型
	 * @param name
	 * @return
	 */
	@GetMapping("/roomtype")
	private String getRoomtypesByName(@RequestParam String name, HttpServletResponse response) {
		
		Result<List<RoomtypeDTO>> roomtypeServiceResult = roomtypeService.getRoomtypesByName(name);
		
		if(roomtypeServiceResult.isFailure()) {
			return null;
		}
		
		return JsonUtil.toJson(roomtypeServiceResult.getData());
	}
	
	// ===== Room =====
		
	/**
	 * 根據id返回room的json數據
	 * @param roomId
	 * @return
	 */
	@GetMapping("/room/{id}")
	@ResponseBody
	private String getRoomById(@PathVariable Integer id) {
		Result<RoomDTO> roomServiceResult = roomService.getRoomById(id);
		if(roomServiceResult.isFailure()) {
			return "";
		}
		
		return JsonUtil.toJson(roomServiceResult.getData());	
	}
	
	// 測試2
	@GetMapping("/roomDTO")
	private String getRoomtypeByPage(Integer pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber-1, 10, Direction.DESC, "roomId");
		Sort sort = pageable.getSort();
		Order o = sort.getOrderFor("roomId");
		System.out.println(o.getProperty());
		System.out.println(o.getDirection());
		Page<RoomDTO> page = roomRepo.findAllRoomDTO(pageable);
		JsonUtil.setNonNull();
		return JsonUtil.toJson(page);
	}
	
}
