package com.booking.service.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.booking.RoomDTO;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.booking.RoomDao;
import com.booking.dao.booking.RoomRepository;
import com.booking.dao.booking.RoomSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class RoomService {
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	private RoomRepository roomRepo;

	/**
	 * 獲取單筆房間
	 * 
	 * @return
	 */
	public Result<RoomDTO> findRoomById(Integer roomId) {
		DaoResult<Room> getRoomByIdResult = roomDao.getRoomById(roomId);
	
		if (getRoomByIdResult.isFailure()) {
			return Result.failure("獲取房間失敗");
		}
		
		Room room = getRoomByIdResult.getData();
		
		Roomtype roomtype = (Roomtype) getRoomByIdResult.getExtraData("roomtype");
		
		RoomDTO roomDTO = new RoomDTO();
		
		BeanUtils.copyProperties(room, roomDTO);
		
		roomDTO.setRoomtypeName(roomtype.getRoomtypeName());
		
		return Result.success(roomDTO);
	}

	/**
	 * 獲取所有房間
	 * 
	 * @return
	 */
	public Result<Page<RoomDTO>> findRoomAll(RoomDTO roomDTO) {
		String attrOrderBy = roomDTO.getAttrOrderBy();
		Boolean selectedSort = roomDTO.getSelectedSort();
		Integer pageNumber = roomDTO.getPageNumber();
		
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
		
		Page<RoomDTO> page = roomRepo.findRoomDTOAll(pageable);
		return Result.success(page);
	}
	
	/**
	 * 根據房間名稱獲得房間
	 * @param name
	 * @return
	 */
	public Result<List<Room>> findRoomsByName(String name) {
		DaoResult<List<Room>> getRoomByNameResult = roomDao.getRoomByName(name);
		
		if(getRoomByNameResult.isFailure()) {
			return Result.failure("根據名稱獲取所有房間失敗");
		}
		
		List<Room> rooms = getRoomByNameResult.getData();
		
		RoomDTO roomDTO = new RoomDTO();
		
		List<RoomDTO> list = new ArrayList<>();
		
		for(Room room : rooms) {
			BeanUtils.copyProperties(room, roomDTO);
			list.add(roomDTO);
		}
		
		return Result.success(rooms);
	}

	/**
	 * 根據roomtypeId獲取多筆房間
	 * 
	 * @param roomtypeId
	 * @return
	 */
	public Result<Page<RoomDTO>> findRoomsByRoomtypeId(Integer roomtypeId, RoomDTO roomDTO) {
		DaoResult<List<Room>> getRoomsByRoomtypeIdResult = roomDao.getRoomsByRoomtypeId(roomtypeId);
		if (getRoomsByRoomtypeIdResult.isFailure()) {
			return Result.failure("獲取房間失敗");
		}
		List<Room> rooms = getRoomsByRoomtypeIdResult.getData();
		
		// 這邊rooms的roomtype都是一樣的，所以不用放在迴圈裡面
		String roomtypeName = rooms.size() > 0 ? rooms.get(0).getRoomtype().getRoomtypeName() : null;
		List<RoomDTO> roomDTOs = new ArrayList<>();
		
		for(Room room : rooms) {
			RoomDTO responseRoomDTO = new RoomDTO();
			BeanUtils.copyProperties(room, responseRoomDTO);
			responseRoomDTO.setRoomtypeId(roomtypeId);
			responseRoomDTO.setRoomtypeName(roomtypeName);
			roomDTOs.add(responseRoomDTO);
		}
		
		Pageable pageable = MyPageRequest.of(1, 10, roomDTO.getSelectedSort(), roomDTO.getAttrOrderBy());

		return Result.success(new PageImpl<>(roomDTOs, pageable, roomDTOs.size()));
	}

	/**
	 * 創建房間
	 * 
	 * @param room
	 * @return
	 */
	@Transactional
	public Result<Integer> saveRoomsByRoomtypeName(Room room, String roomtypeName) {
		DaoResult<?> addRoomResult = roomDao.addRoom(room, roomtypeName);
		
		if (addRoomResult.isFailure()) {
			return Result.failure("新增房間失敗");
		}
		
		DaoResult<?> incrementRoomtypeQuantityResult = roomDao.incrementRoomtypeQuantity(room.getRoomtype().getRoomtypeId());

		if (incrementRoomtypeQuantityResult.isFailure()) {
			return Result.failure("房型數量加一失敗");
		}

		return Result.success("新增空房成功");
	}

	/**
	 * 根據roomtype添加房間
	 * 
	 * @param roomtype
	 * @return
	 */
	@Transactional
	public Result<String> saveRoomsByRoomtype(Roomtype roomtype) {
		LocalDateTime updatedTime = roomtype.getUpdatedTime();
		LocalDateTime createdTime = roomtype.getCreatedTime();
		String roomtypeDescription = roomtype.getRoomtypeDescription();
		Integer roomtypeQuanity = roomtype.getRoomtypeQuantity();
		
		for (int i = 0; i < roomtypeQuanity; i++) {
			Room room = new Room("沒有", 0, roomtypeDescription, updatedTime, createdTime);
			room.setRoomtype(roomtype);
			roomRepo.save(room);
		}
		return Result.success("新增空房成功");
	}

	@Transactional
	public Result<String> removeRoomById(Integer roomId) {
		DaoResult<?> decrementRoomtypeQuantityResult = roomDao.decrementRoomtypeQuantity(roomId);
		if (decrementRoomtypeQuantityResult.isFailure()) {
			return Result.failure("房間類型數量減一失敗");
		}
		DaoResult<?> removeRoomByIdResult = roomDao.removeRoomById(roomId);

		if (removeRoomByIdResult.isFailure()) {
			return Result.failure("房間不是空房，刪除房間失敗");
		}

		return Result.success("刪除房間類型成功");
	}

	/**
	 * 更新房間
	 * 
	 * @param room
	 * @return
	 */
	@Transactional
	public Result<String> updateRoom(Room room) {
		Room oldRoom = roomDao.getRoomById(room.getRoomId()).getData();

		String roomNumber = room.getRoomNumber();
		String roomDescription = room.getRoomDescription();
		Integer roomStatus = room.getRoomStatus();

		if (roomNumber == null || roomNumber.isEmpty()) {
			room.setRoomNumber(oldRoom.getRoomNumber());
		}

		if (roomDescription == null || roomDescription.isEmpty()) {
			room.setRoomDescription(oldRoom.getRoomDescription());
		}

		if (roomStatus == null) {
			room.setRoomStatus(oldRoom.getRoomStatus());
		}

		room.setUpdatedTime(LocalDateTime.now());
		
		room.setRoomtype(oldRoom.getRoomtype());

		DaoResult<?> updateRoomResult = roomDao.updateRoom(room);

		if (updateRoomResult.isFailure()) {
			return Result.failure("更新房間失敗");
		}

		return Result.success("更新房間成功");
	}
	
	/**
	 * 模糊查詢
	 * @param room
	 * @param extraValues
	 * @return
	 */
	public Result<PageImpl<RoomDTO>> findRooms(RoomDTO roomDTO, List<Integer> roomStatusAll) {
		Specification<Room> spec = Specification.where(RoomSpecification.numberContains(roomDTO.getRoomNumber()))
												.and(RoomSpecification.descriptionContains(roomDTO.getRoomDescription()));
													
		if(roomStatusAll != null) {
			Specification<Room> statusSpec = Specification.where(null);
			for(Integer status : roomStatusAll) {
				statusSpec = statusSpec.or(RoomSpecification.statusContains(status));
			}
			spec = spec.and(statusSpec);
		}
													
	    spec = spec.and(RoomSpecification.hasRoomtypeName(roomDTO.getRoomtypeName()));											
			
		String attrOrderBy = roomDTO.getAttrOrderBy();
		Boolean selectedSort = roomDTO.getSelectedSort();
		Integer pageNumber = roomDTO.getPageNumber();
	
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
	
		Page<Room> page = roomRepo.findAll(spec, pageable);
		
		List<Room> rooms = page.getContent();
		
		List<RoomDTO> roomDTOs = new ArrayList<>();
		
		for(Room r : rooms) {
			Roomtype roomtype = r.getRoomtype();
			RoomDTO responseRoomDTO = new RoomDTO();
			BeanUtils.copyProperties(r, responseRoomDTO);
			responseRoomDTO.setRoomtypeName(roomtype.getRoomtypeName());	
			responseRoomDTO.setRoomtypeId(roomtype.getRoomtypeId());
			roomDTOs.add(responseRoomDTO);
		}
		
		Pageable newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		
		return Result.success(new PageImpl<>(roomDTOs, newPageable, page.getTotalElements()));
	}

	/**
	 * 根據房型名稱搜尋房間
	 * 還沒用到
	 * @param roomtypeName
	 * @return
	 */
	public Result<Page<RoomDTO>> findRoomsByRoomtypeName(String roomtypeName, RoomDTO roomDTO) {
		Pageable pageable = MyPageRequest.of(roomDTO.getPageNumber(), 10, roomDTO.getSelectedSort(), roomDTO.getAttrOrderBy());
		return Result.success(roomRepo.findRoomsByRoomtypeName(pageable, roomtypeName));
	}

}
