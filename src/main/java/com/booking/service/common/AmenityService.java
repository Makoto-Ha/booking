package com.booking.service.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.pojo.booking.Roomtype;
import com.booking.bean.pojo.common.Amenity;
import com.booking.dao.booking.RoomtypeRepository;
import com.booking.dao.common.AmenityRepository;
import com.booking.utils.Result;

@Service
public class AmenityService {
	
	@Autowired
	private AmenityRepository amenityRepo;
	
	@Autowired
	private RoomtypeRepository roomtypeRepo;
	
	/**
	 * 查找全部的服務
	 * @return
	 */
	public List<Amenity> findAll() {
		return amenityRepo.findAll();	
	}
	
	/**
	 * 保存單個房型的服務
	 * @param roomtype
	 * @param id
	 * @return
	 */
	@Transactional
	public Result<String> saveRoomtypeAmenity(Roomtype roomtype, Integer id) {
		Amenity amenity = amenityRepo.findById(id).orElse(null);
		
		if(amenity == null) {
			return Result.failure("根據id查找服務失敗");
		}
		
		List<Amenity> amenities = roomtype.getAmenities();
		amenities.add(amenity);
		roomtypeRepo.save(roomtype);
		return Result.success("房型新增服務成功");
	}
	
	/**
	 * 新增房型的多個服務
	 * @param roomtype
	 * @param amenitiesId
	 * @return
	 */
	@Transactional
	public Result<String> saveRoomtypeAmenities(Roomtype roomtype, List<Integer> amenitiesId) {
		List<Amenity> amenities = new ArrayList<>();
		for(Integer id : amenitiesId) {
			Amenity amenity = amenityRepo.findById(id).orElse(null);
			if(amenity != null) {
				amenities.add(amenity);
			}
		}
		
		roomtype.setAmenities(amenities);
		roomtypeRepo.save(roomtype);
		return Result.success("房型新增服務成功");
	}
	
	/**
	 * 更新房型的服務
	 * @param roomtype
	 * @param amenitiesId
	 * @return
	 */
	@Transactional
	public Result<String> updateRoomtypeAmenities(Roomtype roomtype, List<Integer> amenitiesId) {
		Integer roomtypeId = roomtype.getRoomtypeId();
		// 查找房型
		Roomtype foundRoomtype = roomtypeRepo.findById(roomtypeId).orElse(null);
		
		// 獲取查找房型的所有服務
		List<Amenity> foundAmenities = foundRoomtype.getAmenities();
		
		// 創建服務集合，這集合用於之後更新目前所有服務
		List<Amenity> amenities = new ArrayList<>();
		
		// 查找原本所有服務id是否存在於請求的id之中，有的話留著，沒有就排除掉，並且刪除請求的id，用於之後查找要添加新的服務
		for(Amenity amenity : foundAmenities) {
			Integer amenityId = amenity.getAmenityId();
			if(amenitiesId.contains(amenityId)) {
				amenities.add(amenity);
				amenitiesId.remove(amenityId);
			}
		}
		
		// 添加新的服務 
		for(Integer afterAmenitiesId : amenitiesId) {
			Amenity amenity = amenityRepo.findById(afterAmenitiesId).orElse(null);
			
			if(amenity != null) {
				amenities.add(amenity);
			}
		}
		
		foundRoomtype.setAmenities(amenities);
		return Result.success("房型新增服務成功");
	}
	
	public List<Amenity> findAmenitiesByIds(List<Integer> amenitiesId) {
		return amenityRepo.findAllById(amenitiesId);
	}
}
