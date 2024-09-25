package com.booking.dao.booking;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.booking.bean.booking.Room;
import com.booking.bean.booking.Roomtype;
import com.booking.utils.util.DaoResult;

@Repository
public class RoomDaoImpl implements RoomDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	/**
	 * 查詢所有房間
	 * @return
	 */
	@Override
	public DaoResult<List<Room>> getRoomAll() {
		String hql = "From Room r ORDER BY r.roomNumber";
		Query<Room> query = sessionFactory.getCurrentSession().createQuery(hql, Room.class);
		query.setFirstResult(0);
		query.setMaxResults(10);
		List<Room> rooms = query.getResultList();
		return DaoResult.create(rooms).setSuccess(rooms != null);
	}
	
	/**
	 * 根據roomId查找單筆房間
	 * @param roomId
	 * @return
	 */
	@Override
	public DaoResult<Room> getRoomById(Integer roomId) {
		Room room = sessionFactory.getCurrentSession().get(Room.class, roomId);		
		return DaoResult.create(room).setSuccess(room != null); 
	}
	
	/**
	 * 根據roomtypeId查找多筆房間
	 * @param roomtypeId
	 * @return
	 */
	@Override
	public DaoResult<List<Room>> getRoomsByRoomtypeId(Integer roomtypeId) {
		Roomtype roomtype = sessionFactory.getCurrentSession().get(Roomtype.class, roomtypeId);
		List<Room> rooms = roomtype.getRooms();
		return DaoResult.create(rooms).setSuccess(rooms != null);
	}

	/**
	 * 添加空房間
	 * @param room
	 * @return
	 */
	@Override
	public DaoResult<?> addRoom(Room room) {
		sessionFactory.getCurrentSession().persist(room);
		Integer roomId = room.getRoomId();
		return DaoResult.create().setGeneratedId(roomId).setSuccess(roomId != null); 
	}
	
	/**
	 * 根據roomId刪除房間
	 * @param roomId
	 * @return
	 */
	@Override
	public DaoResult<?> removeRoomById(Integer roomId) {
		Room room = sessionFactory.getCurrentSession().get(Room.class, roomId);
		if(room != null) {
			sessionFactory.getCurrentSession().remove(room);
			return DaoResult.create().setSuccess(true);
		}
		return DaoResult.create().setSuccess(false);
	}
	
	/**
	 * 根據roomtypeId刪除多筆房間
	 * @param roomtypeId
	 * @return
	 */
	@Override
	public DaoResult<?> removeRoomsByRoomtypeId(Integer roomtypeId) {
		Roomtype roomtype = sessionFactory.getCurrentSession().get(Roomtype.class, roomtypeId);
		if(roomtype != null) {
			sessionFactory.getCurrentSession().remove(roomtype);
			return DaoResult.create().setSuccess(true);
		}
		return DaoResult.create().setSuccess(false);
	}

	/**
	 * 更新房間
	 * @param room
	 * @return
	 */
	@Override
	public DaoResult<?> updateRoom(Room room) {
		Room mergeRoom = sessionFactory.getCurrentSession().merge(room);
		return DaoResult.create().setSuccess(mergeRoom != null);
	}
	
	/**
	 * 刪除房間減一房型數量
	 * @param roomtypeId
	 * @return
	 */
	@Override
	public DaoResult<?> decrementRoomtypeQuantity(Integer roomId) {
		Room room = sessionFactory.getCurrentSession().get(Room.class, roomId);
		Roomtype roomtype = room.getRoomtype();
		Integer quantity = roomtype.getRoomtypeQuantity();
		roomtype.setRoomtypeQuantity(--quantity);
		
		return DaoResult.create().setSuccess(true);
	}
	
	/**
	 * 刪除房間加一房型數量
	 * @param roomtypeId
	 * @return
	 */
	@Override
	public DaoResult<?> inecrementRoomtypeQuantity(Integer roomtypeId) {
		Roomtype roomtype = sessionFactory.getCurrentSession().get(Roomtype.class, roomtypeId);
		Integer roomtypeQuantity = roomtype.getRoomtypeQuantity();
		roomtype.setRoomtypeQuantity(++roomtypeQuantity);
		
		return DaoResult.create().setSuccess(true);
	}
}
