package com.booking.service.booking;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.booking.bean.booking.Room;
import com.booking.bean.booking.Roomtype;
import com.booking.dao.booking.RoomDao;
import com.booking.dao.booking.RoomtypeDao;
import com.booking.dto.booking.RoomtypeDTO;
import com.booking.utils.DaoResult;
import com.booking.utils.Listable;
import com.booking.utils.Result;

public class RoomtypeService {
	
	private RoomtypeDao roomtypeDao = new RoomtypeDao();
	private RoomDao roomDao = new RoomDao();
	private RoomService roomService = new RoomService();
	
	/**
	 * 獲取所有房間類型
	 * @return
	 */
	public Result<List<Listable>> getRoomtypeAll(Integer page) {
		DaoResult<Roomtype> daoResult = roomtypeDao.getRoomtypeAll(page);
		DaoResult<Integer> getTotalCountsDaoResult = roomtypeDao.getTotalCounts();
		
		List<Roomtype> roomtypes = daoResult.getData();
		Integer totalCounts = getTotalCountsDaoResult.getEntity();
		
		if(totalCounts == null) {
			return Result.failure("獲取全部數量失敗");
		}
		
		if(roomtypes == null) {
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
		DaoResult<RoomtypeDTO> daynamicQueryDaoResult = roomtypeDao.dynamicQuery(roomtype, extraValues);
		List<RoomtypeDTO> roomtypesDTO = daynamicQueryDaoResult.getData();
		if(roomtypesDTO == null) {
			return Result.failure("模糊查詢房間類型失敗");
		}
		List<Listable> lists = new ArrayList<>();
		for(RoomtypeDTO dto : roomtypesDTO) {
			lists.add(dto);	
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
		Roomtype roomtype = daoResult.getEntity();
		if(roomtype == null) {
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
		DaoResult<Integer> daoResult = roomtypeDao.addRoomtype(roomtype);
		if(daoResult.getAffectedRows() == null) {
			return Result.failure("新增房間類型失敗");
		}
		roomtype.setRoomtypeId(daoResult.getGeneratedId());
		Result<Integer> roomServiceResult = roomService.addRoom(roomtype);
		
		if(!roomServiceResult.isSuccess()) {
			return Result.failure("新增空房失敗");
		}
		
		return Result.success(daoResult.getGeneratedId());	
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
		
		DaoResult<Integer> roomtypeDaoResult = roomtypeDao.removeRoomtypeById(roomtypeId);
		DaoResult<Integer> roomDaoResult = roomDao.removeRoomsByRoomtypeId(roomtypeId);		
		
		if(roomtypeDaoResult.getAffectedRows() == 0 || roomDaoResult.getAffectedRows() == 0) {
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
		Roomtype oldRoomtype = roomtypeDao.getRoomtypeById(oldRoomtypeId).getEntity();
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
		
		DaoResult<Integer> updateRoomtypeDaoResult = roomtypeDao.updateRoomtype(roomtype);
		
		if(updateRoomtypeDaoResult.getAffectedRows() == null) {
			return Result.failure("更新房間類型失敗");
		}	
		
		// 刪除房間類型中的房間
		DaoResult<Integer> removeRoomsDaoResult = roomDao.removeRoomsByRoomtypeId(oldRoomtypeId);
		
		if(removeRoomsDaoResult.getAffectedRows() == null) {
			return Result.failure("刪除房間類型失敗");
		}
		
		// 添加房間類型的空房間
		Result<Integer> addRoomResult = roomService.addRoom(roomtype);
		
		if(!addRoomResult.isSuccess()) {
			return Result.failure("添加空房失敗");
		}
		
		return Result.success("更新房間類型成功");
	}
}
