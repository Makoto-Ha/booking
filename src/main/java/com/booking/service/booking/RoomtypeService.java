package com.booking.service.booking;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import com.booking.bean.booking.Room;
import com.booking.bean.booking.Roomtype;
import com.booking.dao.booking.RoomDao;
import com.booking.dao.booking.RoomtypeDao;
import com.booking.dto.booking.RoomtypeDTO;
import com.booking.utils.util.DaoResult;
import com.booking.utils.Listable;
import com.booking.utils.Result;

public class RoomtypeService {
	private RoomtypeDao roomtypeDao;
	private RoomDao roomDao = new RoomDao();
	private RoomService roomService = new RoomService();
	
	
	public RoomtypeService(Session session) {
		this.roomtypeDao = new RoomtypeDao(session);
	}
	
	/**
	 * 獲取所有房間類型
	 * @return
	 */
	public Result<List<Listable>> getRoomtypeAll(Integer page) {
		DaoResult<List<Roomtype>> getAllResult = roomtypeDao.getRoomtypeAll(page);
		DaoResult<Integer> getTotalCountsResult = roomtypeDao.getTotalCounts();
		
		List<Roomtype> roomtypes = getAllResult.getData();
		Integer totalCounts = getTotalCountsResult.getData();
		
		if(getAllResult.isFailure()) {
			return Result.failure("獲取全部數量失敗");
		}
		
		if(getTotalCountsResult.isFailure()) {
			return Result.failure("查詢所有房間類型失敗");
		}
		
		List<Listable> lists = new ArrayList<>();
		for(Roomtype roomtype : roomtypes) {
			RoomtypeDTO roomtypeDTO = new RoomtypeDTO();
			roomtypeDTO.setTotalCounts(totalCounts);
			try {
				BeanUtils.copyProperties(roomtypeDTO, roomtype);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			} 
			lists.add(roomtypeDTO);
		}
	
		return Result.success(lists);
	}
	
	/**
	 * 根據模糊查詢得到多筆房間類型
	 * @param roomtype
	 * @return
	 */
	public Result<List<Listable>> getRoomtypes(Roomtype roomtype, Map<String, Object> extraValues) {
		DaoResult<List<Roomtype>> daynamicQueryResult = roomtypeDao.dynamicQuery(roomtype, extraValues);
		List<Roomtype> roomtypes = daynamicQueryResult.getData();
		Integer totalCounts = ((Long) daynamicQueryResult.getExtraData("totalCounts")).intValue();
		if(daynamicQueryResult.isFailure()) {
			return Result.failure("模糊查詢房間類型失敗");
		}
		
		List<Listable> lists = new ArrayList<>();
		for(Roomtype roomtypeEntity : roomtypes) {
			RoomtypeDTO roomtypeDTO = new RoomtypeDTO();
			roomtypeDTO.setTotalCounts(totalCounts);
			try {
				BeanUtils.copyProperties(roomtypeDTO, roomtypeEntity);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			lists.add(roomtypeDTO);
		}
		
		return Result.success(lists);
	}
	
	/**
	 * 獲取房間類型
	 * @param roomtypeId
	 * @return
	 */
	public Result<Roomtype> getRoomtype(Integer roomtypeId) {
		DaoResult<Roomtype> daoResult = roomtypeDao.getRoomtypeById(roomtypeId);
		Roomtype roomtype = daoResult.getData();
		if(daoResult.isFailure()) {
			return Result.failure("找不到該房間類型");
		}
		return Result.success(roomtype);
	}
	
	/**
	 * 添加房間類型
	 * @param roomtype
	 * @return
	 */
	public Result<Integer> addRoomtype(Roomtype roomtype) {
		DaoResult<?> addRoomtypeResult = roomtypeDao.addRoomtype(roomtype);
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
	public Result<String> removeRoomtype(Integer roomtypeId) {
		List<Room> rooms = roomDao.getRoomsByRoomtypeId(roomtypeId).getData();
		
		for(Room room : rooms) {
			if(room.getRoomStatus() != 0) {
				return Result.failure("目前房間已使用，請勿刪除房間類型");
			}
		}
		
		DaoResult<?> removeRoomtypeByIdResult = roomtypeDao.removeRoomtypeById(roomtypeId);
		// DaoResult<Integer> removeRoomsByRoomtypeIdResult = roomDao.removeRoomsByRoomtypeId(roomtypeId);		
		
		if(removeRoomtypeByIdResult.isFailure()) {
			return Result.failure("刪除房間類型失敗");
		}

		return Result.success("刪除房間類型成功");
	}

	/**TODO 更新需要確認房間是否狀態為0
	 * 更新房間類型
	 * @param roomtype
	 * @return
	 */
	public Result<String> updateRoomtype(Roomtype roomtype) {
		Integer oldRoomtypeId = roomtype.getRoomtypeId();
		Roomtype oldRoomtype = roomtypeDao.getRoomtypeById(oldRoomtypeId).getData();
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
		
		DaoResult<?> updateRoomtypeResult = roomtypeDao.updateRoomtype(roomtype);
		
		if(updateRoomtypeResult.isFailure()) {
			return Result.failure("更新房間類型失敗");
		}	
		
		return Result.success("更新房間類型成功");
	}
}
