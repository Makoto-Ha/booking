package com.booking.service.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.booking.RoomDao;
import com.booking.dao.booking.RoomtypeRepository;
import com.booking.dao.booking.RoomtypeSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class RoomtypeService {
	@Autowired
	private RoomtypeRepository roomtypeRepo;
	@Autowired
	private RoomDao roomDao;
	@Autowired
	private RoomService roomService;
	
	/**
	 * 獲取所有房間類型
	 * @param roomtypeDTO
	 * @return
	 */
	public Result<PageImpl<RoomtypeDTO>> findRoomtypeAll(RoomtypeDTO roomtypeDTO) {
		Integer pageNumber = roomtypeDTO.getPageNumber();
		String attrOrderBy = roomtypeDTO.getAttrOrderBy();
		Pageable pageable = MyPageRequest.of(pageNumber, 10, false, attrOrderBy);
		Page<Roomtype> page = roomtypeRepo.findAll(pageable);
		
		List<RoomtypeDTO> roomtypeDTOs = new ArrayList<>();
		List<Roomtype> roomtypes = page.getContent();
		
		for(Roomtype roomtype : roomtypes) {
			RoomtypeDTO responseRoomtypeDTO = new RoomtypeDTO();
			BeanUtils.copyProperties(roomtype, responseRoomtypeDTO);
			roomtypeDTOs.add(responseRoomtypeDTO);
		}
	
		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		
		return Result.success(new PageImpl<>(roomtypeDTOs, newPageable, page.getTotalElements()));
	}
	
	/**
	 * 根據多條選項模糊查詢得到多筆房間類型
	 * @param roomtypeDTO
	 * @return
	 */
	public Result<PageImpl<RoomtypeDTO>> findRoomtypes(RoomtypeDTO roomtypeDTO) {
		
		Specification<Roomtype> spec = Specification
					 .where(RoomtypeSpecification.nameContains(roomtypeDTO.getRoomtypeName()))
					 .and(RoomtypeSpecification.priceContains(roomtypeDTO.getRoomtypePrice()))
					 .and(RoomtypeSpecification.quantityContains(roomtypeDTO.getRoomtypeQuantity()))
					 .and(RoomtypeSpecification.capacityContains(roomtypeDTO.getRoomtypeCapacity()))
					 .and(RoomtypeSpecification.cityContains(roomtypeDTO.getRoomtypeCity()))
					 .and(RoomtypeSpecification.districtContains(roomtypeDTO.getRoomtypeDistrict()))
					 .and(RoomtypeSpecification.descriptionContains(roomtypeDTO.getRoomtypeDescription()))
					 .and(RoomtypeSpecification.moneyContains(roomtypeDTO.getMinMoney(), roomtypeDTO.getMaxMoney()));
		
		Pageable pageable = MyPageRequest.of(
				roomtypeDTO.getPageNumber(), 
				10, 
				roomtypeDTO.getSelectedSort(), 
				roomtypeDTO.getAttrOrderBy());
		
		Page<Roomtype> page = roomtypeRepo.findAll(spec, pageable);
		
		List<Roomtype> roomtypes = page.getContent();
		List<RoomtypeDTO> roomtypeDTOs = new ArrayList<>();
		for(Roomtype roomtype : roomtypes) {
			RoomtypeDTO responseRoomtypeDTO = new RoomtypeDTO();
			BeanUtils.copyProperties(roomtype, responseRoomtypeDTO);		
			roomtypeDTOs.add(responseRoomtypeDTO);
		}
		
		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
	
		return Result.success(new PageImpl<>(roomtypeDTOs, newPageable, page.getTotalElements()));
	}

	/**
	 * 根據房間類型名稱獲取房間類型
	 * @param name
	 * @return
	 */
	public Result<List<RoomtypeDTO>> getRoomtypesByName(String roomtypeName) {
		DaoResult<List<Roomtype>> getRoomtypeByNameResult = roomtypeRepo.getRoomtypesByName(roomtypeName);
		
		if(getRoomtypeByNameResult.isFailure()) {
			return Result.failure("根據房間類型名稱獲取房間類型失敗");
		}
		
		List<RoomtypeDTO> list = new ArrayList<>();
		List<Roomtype> roomtypes = getRoomtypeByNameResult.getData();
		
		for(Roomtype roomtype : roomtypes) {
			RoomtypeDTO roomtypeDTO = new RoomtypeDTO();
			BeanUtils.copyProperties(roomtype, roomtypeDTO);
			list.add(roomtypeDTO);
		}
		
		return Result.success(list);
	}
	
	/**
	 * 獲取房間類型
	 * @param roomtypeId
	 * @return
	 */
	public Result<RoomtypeDTO> getRoomtype(Integer roomtypeId) {
		Optional<Roomtype> optional = roomtypeRepo.findById(roomtypeId);
	
		if(optional.isEmpty()) {
			return Result.failure("找不到該房間類型");
		}
		
		Roomtype roomtype = optional.get();
		RoomtypeDTO roomtypeDTO = new RoomtypeDTO();
		BeanUtils.copyProperties(roomtype, roomtypeDTO);
		
		return Result.success(roomtypeDTO);
	}
	
	/**
	 * 添加房間類型
	 * @param roomtype
	 * @return
	 */
	@Transactional
	public Result<Integer> addRoomtype(Roomtype roomtype) {
		DaoResult<?> addRoomtypeResult = roomtypeRepo.addRoomtype(roomtype);
		if(addRoomtypeResult.isFailure()) {
			return Result.failure("新增房間類型失敗");
		}
		roomtype.setRoomtypeId(addRoomtypeResult.getGeneratedId());
		Result<Integer> addRoomResult = roomService.addRoom(roomtype);
		
		if(addRoomResult.isFailure()) {
			return Result.failure("新增空房失敗");
		}
		
		return Result.success(addRoomtypeResult.getGeneratedId());	
	}

	/**
	 * 根據roomtypeId移除房間類型
	 * @param roomtypeId
	 * @return
	 */
	@Transactional
	public Result<String> removeRoomtype(Integer roomtypeId) {
		List<Room> rooms = roomDao.getRoomsByRoomtypeId(roomtypeId).getData();
		
		for(Room room : rooms) {
			if(room.getRoomStatus() != 0) {
				return Result.failure("目前房間已使用，請勿刪除房間類型");
			}
		}
		
		DaoResult<?> removeRoomtypeByIdResult = roomtypeRepo.removeRoomtypeById(roomtypeId);
	    DaoResult<?> removeRoomsByRoomtypeIdResult = roomDao.removeRoomsByRoomtypeId(roomtypeId);		
	    
	    if(removeRoomsByRoomtypeIdResult.isFailure()) {
	    	return Result.failure("根據房型刪除所有房間失敗");
	    }
		
		if(removeRoomtypeByIdResult.isFailure()) {
			return Result.failure("刪除房間類型失敗");
		}

		return Result.success("刪除房間類型成功");
	}

	/**
	 * 更新房間類型、更新需要確認房間是否狀態為0
	 * @param roomtype
	 * @return
	 */
	@Transactional
	public Result<String> updateRoomtype(Roomtype roomtype) {
		Integer oldRoomtypeId = roomtype.getRoomtypeId();
		Optional<Roomtype> optional = roomtypeRepo.findById(oldRoomtypeId);
		
		if(optional.isEmpty()) {
			return Result.failure("找不到房間類型");
		}
		Roomtype oldRoomtype = optional.get();		
		String roomtypeName = roomtype.getRoomtypeName();
		Integer roomtypeCapacity = roomtype.getRoomtypeCapacity();
		Integer roomtypePrice = roomtype.getRoomtypePrice();
		Integer roomtypeQuantity = roomtype.getRoomtypeQuantity();
		String roomtypeDescription = roomtype.getRoomtypeDescription();
		String roomtypeAddress = roomtype.getRoomtypeAddress();
		String roomtypeCity = roomtype.getRoomtypeCity();
		String roomtypeDistrict = roomtype.getRoomtypeDistrict();
		
		if(roomtypeName == null || roomtypeName.isEmpty()) {
			roomtype.setRoomtypeName(oldRoomtype.getRoomtypeName());
		}
		
		if(roomtypeCapacity == null) {
			roomtype.setRoomtypeCapacity(oldRoomtype.getRoomtypeCapacity());
		}
		
		if(roomtypePrice == null) {
			roomtype.setRoomtypePrice(oldRoomtype.getRoomtypePrice());
		}
		
		if(roomtypeQuantity == null) {
			roomtype.setRoomtypeQuantity(oldRoomtype.getRoomtypeQuantity());
		}
		
		if(roomtypeDescription == null || roomtypeDescription.isEmpty()) {
			roomtype.setRoomtypeDescription(oldRoomtype.getRoomtypeDescription());
		}
		
		if(roomtypeAddress == null || roomtypeAddress.isEmpty()) {
			roomtype.setRoomtypeAddress(oldRoomtype.getRoomtypeAddress());
		}
		
		if(roomtypeCity == null || roomtypeCity.isEmpty()) {
			roomtype.setRoomtypeCity(oldRoomtype.getRoomtypeCity());
		}
		
		if(roomtypeDistrict == null || roomtypeDistrict.isEmpty()) {
			roomtype.setRoomtypeDistrict(oldRoomtype.getRoomtypeDistrict());
		}
	
		
		roomtype.setUpdatedTime(LocalDateTime.now());
		roomtype.setCreatedTime(oldRoomtype.getCreatedTime());
		
		DaoResult<?> updateRoomtypeResult = roomtypeRepo.updateRoomtype(roomtype);
		
		if(updateRoomtypeResult.isFailure()) {
			return Result.failure("更新房間類型失敗");
		}	
		
		return Result.success("更新房間類型成功");
	}
}
