package com.booking.service.booking.client;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.dto.booking.client.RoomtypeKeywordSearchDTO;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.booking.RoomtypeRepository;
import com.booking.dao.booking.RoomtypeSpecification;
import com.booking.utils.MyPageRequest;

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
	
public Page<RoomtypeDTO> userSearchRoomtypes(RoomtypeKeywordSearchDTO roomtypeSearchDTO) {
		// 根據房型、城市、區域的名稱來做or條件合併
		Specification<Roomtype> spec = Specification.where(RoomtypeSpecification.availableRoomTypes(roomtypeSearchDTO.getSearchStartDate(), roomtypeSearchDTO.getSearchEndDate()))
										.and(RoomtypeSpecification.hasNameContains(roomtypeSearchDTO.getRoomtypeName()));								
//										.or(RoomtypeSpecification.cityContains(roomtypeSearchDTO.getRoomtypeCity()));
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

}