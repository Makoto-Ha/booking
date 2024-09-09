package com.booking.service.attraction;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;

import com.booking.bean.attraction.Attraction;
import com.booking.dao.attraction.AttractionDao;
import com.booking.dto.attraction.AttractionDTO;
import com.booking.utils.Listable;
import com.booking.utils.Result;
import com.booking.utils.util.DaoResult;


public class AttractionService {
	private AttractionDao attractionDao;
	
	public AttractionService(Session session) {
		this.attractionDao = new AttractionDao(session);
	}

	/**
	 * 獲取所有景點
	 * @return
	 */
	public Result<List<Listable>> getAttractionAll() {
		DaoResult<List<Attraction>> getAllResult = attractionDao.getAttractionAll();
		List<Attraction> attractions = getAllResult.getData();
		List<Listable> lists = new ArrayList<>();
		for(Attraction attraction : attractions) {
			AttractionDTO attractionDTO = new AttractionDTO();
			try {
				BeanUtils.copyProperties(attractionDTO, attraction);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			} 
			lists.add(attractionDTO);
		}
		if(getAllResult.isFailure()) {
			return Result.failure("查詢所有景點失敗");
		}
		return Result.success(lists);
	}
	
	/**
	 * 依模糊查詢得到多筆景點
	 * @param attraction
	 * @return
	 */
	public Result<List<Listable>> getAttractions(Attraction attraction) {
		DaoResult<List<Attraction>> daynamicQueryDaoResult = attractionDao.dynamicQuery(attraction);
		List<Attraction> attractions = daynamicQueryDaoResult.getData();
		
		if(daynamicQueryDaoResult.isFailure()) {
			return Result.failure("無法查詢");
		}
		List<Listable> attractionsDTO = new ArrayList<>();
		for(Attraction attractionOne : attractions) {
			AttractionDTO attractionDTO = new AttractionDTO();
			try {
				BeanUtils.copyProperties(attractionDTO, attractionOne);
				attractionsDTO.add(attractionDTO);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return Result.success(attractionsDTO);
	}
	
	
	/**
	 * 依id獲取景點
	 * @param attractionId
	 * @return
	 */
	public Result<Attraction> getAttractionById(Integer attractionId) {
		DaoResult<Attraction> daoResult = attractionDao.getAttractionById(attractionId);
		Attraction attraction = daoResult.getData();
		if(daoResult.isFailure()) {
			return Result.failure("沒有此景點");
		}
		return Result.success(attraction);
	}
	
	
	
	/**
	 * 新增景點
	 * @param attraction
	 * @return
	 */
	public Result<Integer> addAttraction(Attraction attraction) {
		DaoResult<?> addAttractionResult = attractionDao.addAttraction(attraction);
		if(addAttractionResult.isFailure()) {
			return Result.failure("新增失敗");
		}
		return Result.success(addAttractionResult.getGeneratedId());
	}
	
	
	/**
	 * 依id刪除景點
	 * @param attractionId
	 */
	public Result<String> removeAttraction(Integer attractionId) {
		DaoResult<?> removeAttractionResult = attractionDao.removeAddractionById(attractionId);
		if(removeAttractionResult.isFailure()) {
			return Result.failure("刪除失敗");
		}
		return Result.success("刪除成功");
	}
	
	/**
	 * 更新景點
	 * @param attraction
	 * @return
	 */
	public Result<String> updateAttraction(Attraction attraction) {
		Integer oldAttractionId = attraction.getAttractionId();
		Attraction oldAttraction = attractionDao.getAttractionById(oldAttractionId).getData();
		String attractionName = attraction.getAttractionName();
		String attractionCity = attraction.getAttractionCity();
		String address = attraction.getAddress();
		String openingHour = attraction.getOpeningHour();
		String attractionType = attraction.getAttractionType();
		String attractionDescription = attraction.getAttractionDescription();
		
		if(attractionName == null || attractionName.isEmpty()) {
			attraction.setAttractionName(oldAttraction.getAttractionName());
		}
		if(attractionCity == null || attractionCity.isEmpty()) {
			attraction.setAddress(oldAttraction.getAddress());
		}
		if(address == null || address.isEmpty()) {
			attraction.setAddress(oldAttraction.getAddress());
		}
		if (openingHour == null || openingHour.isEmpty()) {
			attraction.setOpeningHour(oldAttraction.getOpeningHour());
		}
		if (attractionType == null || attractionType.isEmpty()) {
			attraction.setAttractionType(oldAttraction.getAttractionType());
		}
		if (attractionDescription == null || attractionDescription.isEmpty()) {
			attraction.setAttractionDescription(oldAttraction.getAttractionDescription());
		}

		DaoResult<?> updateAttractionResult = attractionDao.updateAttraction(attraction);
		if(updateAttractionResult.isFailure()) {
			return Result.failure("更新失敗");
		}
		return Result.success("更新成功");
	}
		
}
