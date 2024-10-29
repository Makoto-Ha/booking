package com.booking.service.booking.client;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.dto.booking.client.RoomtypeKeywordSearchDTO;
import com.booking.bean.dto.booking.client.RoomtypeSearchDTO;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.booking.RoomtypeRepository;
import com.booking.dao.booking.RoomtypeSpecification;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class RoomtypeClientService {
	
	@Autowired
	private RoomtypeRepository roomtypeRepo; 
	
	/**
	 * 根據房型、城市、區域的名稱來做模糊查詢
	 * @param roomtypeDTO
	 * @return
	 */
	public Page<RoomtypeDTO> searchRoomtypesByKeyword(RoomtypeKeywordSearchDTO roomtpyeSearchDTO) {
		
		// 根據房型、城市、區域的名稱來做or條件合併
		Specification<Roomtype> spec = Specification.where(RoomtypeSpecification.nameContains(roomtpyeSearchDTO.getRoomtypeName()))
													.or(RoomtypeSpecification.likeCityContains(roomtpyeSearchDTO.getRoomtypeCity()))
													.or(RoomtypeSpecification.likeDistrictContains(roomtpyeSearchDTO.getRoomtypeDistrict()));
													
		// 獲取pageable
		PageRequest pageable = PageRequest.of(roomtpyeSearchDTO.getPageNumber() - 1, 10);
		
		// 獲取page
		Page<Roomtype> page = roomtypeRepo.findAll(spec, pageable);
		
		// Page<Roomtype>轉換類型成Page<RoomtypeDTO>
		return MyPageRequest.convert(page, roomtype -> {
			RoomtypeDTO responseDTO = new RoomtypeDTO();
			BeanUtils.copyProperties(roomtype, responseDTO);
			return responseDTO;
		});
	}
	
	/**
	 * 使用者做名字、城市、區域、日期做模糊查詢房型
	 * @param roomtypeSearchDTO
	 * @return
	 */
	public Page<RoomtypeDTO> userSearchRoomtypes(RoomtypeKeywordSearchDTO roomtypeSearchDTO) {

		// 根據房型、城市、區域的名稱來做and條件合併
		Specification<Roomtype> spec = Specification.where(RoomtypeSpecification.nameContains(roomtypeSearchDTO.getRoomtypeName()))			
										.and(RoomtypeSpecification.likeCityContains(roomtypeSearchDTO.getRoomtypeCity()))								
										.and(RoomtypeSpecification.likeDistrictContains(roomtypeSearchDTO.getRoomtypeDistrict()))
										.and(RoomtypeSpecification.hasPeopleNumber(roomtypeSearchDTO.getPeopleNumber()))
										.and(RoomtypeSpecification.availableRoomTypes(roomtypeSearchDTO.getSearchStartDate(), roomtypeSearchDTO.getSearchEndDate()));
		// 獲取pageable
		PageRequest pageable = PageRequest.of(roomtypeSearchDTO.getPageNumber() - 1, 10);
		
		// 獲取page	
		Page<Roomtype> page = roomtypeRepo.findAll(spec, pageable);
		
		// Page<Roomtype>轉換類型成Page<RoomtypeDTO>
		return MyPageRequest.convert(page, roomtype -> {
			RoomtypeDTO responseDTO = new RoomtypeDTO();
			BeanUtils.copyProperties(roomtype, responseDTO);
			return responseDTO;
		});
	}
	
	/**
	 * 使用者在房型列表做模糊查詢
	 * @param roomtypeSearchDTO
	 * @return
	 */
	public Page<RoomtypeDTO> searchRoomtypes(RoomtypeSearchDTO roomtypeSearchDTO) {

		// 根據房型、城市、區域、服務的名稱來做and條件合併
		Specification<Roomtype> spec = Specification.where(RoomtypeSpecification.nameContains(roomtypeSearchDTO.getRoomtypeName()))			
										.and(RoomtypeSpecification.likeCityContains(roomtypeSearchDTO.getRoomtypeCity()))								
										.and(RoomtypeSpecification.likeDistrictContains(roomtypeSearchDTO.getRoomtypeDistrict()))
										.and(RoomtypeSpecification.hasScores(roomtypeSearchDTO.getScores()))
										.and(RoomtypeSpecification.hasAmenities(roomtypeSearchDTO.getAmenities()))
										.and(RoomtypeSpecification.moneyContains(roomtypeSearchDTO.getMinMoney(), roomtypeSearchDTO.getMaxMoney()))			
										.and(RoomtypeSpecification.availableRoomTypes(roomtypeSearchDTO.getSearchStartDate(), roomtypeSearchDTO.getSearchEndDate()))
										.and(RoomtypeSpecification.orderBy(roomtypeSearchDTO.getAttrOrderBy(), roomtypeSearchDTO.getSelectedSort()));
		// 獲取pageable					
		Pageable pageable = PageRequest.of(roomtypeSearchDTO.getPageNumber()-1, 10);

		// 獲取page	
		Page<Roomtype> page = roomtypeRepo.findAll(spec, pageable);
		
		// Page<Roomtype>轉換類型成Page<RoomtypeDTO>
		return MyPageRequest.convert(page, roomtype -> {
			RoomtypeDTO responseDTO = new RoomtypeDTO();
			BeanUtils.copyProperties(roomtype, responseDTO);
			return responseDTO;
		});
	}
	
	/**
	 * 根據roomtypeId查找房型
	 * @param roomtypeId
	 * @return
	 */
	public Result<RoomtypeDTO> findById(Integer roomtypeId) {
		Roomtype roomtype = roomtypeRepo.findById(roomtypeId).orElse(null);
		
		if(roomtype == null) {
			return Result.failure("查找房間失敗");
		}
		
		RoomtypeDTO roomtypeDTO = new RoomtypeDTO();
		BeanUtils.copyProperties(roomtype, roomtypeDTO);
		
		return Result.success(roomtypeDTO);
	}

}