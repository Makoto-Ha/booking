package com.booking.service.booking;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.pojo.booking.Room;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.dao.booking.RoomDao;
import com.booking.dao.booking.RoomtypeRepository;
import com.booking.dao.booking.RoomtypeSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;
import com.booking.utils.UploadImageFile;

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
		Boolean selectedSort = roomtypeDTO.getSelectedSort();
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
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
	public Result<PageImpl<RoomtypeDTO>> findRoomtypes(RoomtypeDTO roomtypeDTO, List<Integer> roomtypeCapacityAll) {
		
		Specification<Roomtype> spec = Specification
					 .where(RoomtypeSpecification.nameContains(roomtypeDTO.getRoomtypeName()))
					 .and(RoomtypeSpecification.priceContains(roomtypeDTO.getRoomtypePrice()))
					 .and(RoomtypeSpecification.quantityContains(roomtypeDTO.getRoomtypeQuantity()))
					 .and(RoomtypeSpecification.cityContains(roomtypeDTO.getRoomtypeCity()))
					 .and(RoomtypeSpecification.districtContains(roomtypeDTO.getRoomtypeDistrict()))
					 .and(RoomtypeSpecification.descriptionContains(roomtypeDTO.getRoomtypeDescription()))
					 .and(RoomtypeSpecification.moneyContains(roomtypeDTO.getMinMoney(), roomtypeDTO.getMaxMoney()));
		
		if(roomtypeCapacityAll != null) {
			Specification<Roomtype> specCapacity = Specification.where(null);
			for(Integer roomtypeCapacity : roomtypeCapacityAll) {
				specCapacity = specCapacity.or(RoomtypeSpecification.capacityContains(roomtypeCapacity));
			}
			spec = spec.and(specCapacity);
		}
		
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
	public Result<List<RoomtypeDTO>> findRoomtypesByName(String roomtypeName) {
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
	public Result<RoomtypeDTO> findRoomtypeById(Integer roomtypeId) {
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
	public Result<String> saveRoomtype(MultipartFile imageFile, Roomtype roomtype) {
		Result<String> uploadResult = UploadImageFile.upload(imageFile);
		
		if(uploadResult.isSuccess()) {
			String fileName = imageFile.getOriginalFilename();
			roomtype.setImagePath("uploads" + "/" + fileName);
		}
			
		Roomtype saveRoomtype = roomtypeRepo.save(roomtype);
		
		Result<String> roomServiceResult = roomService.saveRoomsByRoomtype(saveRoomtype);
	
		if(roomServiceResult.isFailure()) {
			return Result.failure("新增空房失敗");
		}
		
		return Result.success("新增房間類型成功");	
	}

	/**
	 * 根據roomtypeId移除房間類型
	 * @param roomtypeId
	 * @return
	 */
	@Transactional
	public Result<String> deleteRoomtypeById(Integer roomtypeId) {
		List<Room> rooms = roomDao.getRoomsByRoomtypeId(roomtypeId).getData();
		
		for(Room room : rooms) {
			if(room.getRoomStatus() != 0) {
				return Result.failure("目前房間已使用，請勿刪除房間類型");
			}
		}
		
		roomtypeRepo.deleteById(roomtypeId);	
	    
		return Result.success("刪除房間類型成功");
	}

	/**
	 * 更新房間類型、更新需要確認房間是否狀態為0
	 * @param roomtype
	 * @return
	 */
	@Transactional
	public Result<String> updateRoomtype(MultipartFile imageFile, Roomtype roomtype) {
		Result<String> uploadResult = UploadImageFile.upload(imageFile);
		
		if(uploadResult.isSuccess()) {
			String fileName = imageFile.getOriginalFilename();
			roomtype.setImagePath("uploads" + "/" + fileName);
		}else {
			roomtype.setImagePath("uploads/default.jpg");
		}
		
		Integer oldRoomtypeId = roomtype.getRoomtypeId();
		Roomtype oldRoomtype = roomtypeRepo.findById(oldRoomtypeId).get();
			
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
	
	/**
	 * 根據roomtypeId上傳圖片
	 * @param imageFile
	 * @param roomtypeId
	 * @return
	 */
	public Result<String> uploadImageById(MultipartFile imageFile, Integer roomtypeId) {
		Roomtype roomtype = roomtypeRepo.findById(roomtypeId).orElse(null);
		if(roomtype == null) {
			return Result.failure("根據roomtypeId查找不到房間類型");
		}
		Result<String> uploadImageResult = UploadImageFile.upload(imageFile);
		
		
		if(uploadImageResult.isFailure()) {
			return Result.failure(uploadImageResult.getMessage());
		}
		
		Path path = (Path) uploadImageResult.getExtraData("path");
		
		roomtype.setImagePath(path.toString());
		
		return Result.success("上傳圖片成功");
	}
	
	/**
	 * 根據roomtypeId查找圖片
	 * @param roomtypeId
	 * @return
	 */
	public Result<UrlResource> findImageById(Integer roomtypeId) {
		Roomtype roomtype = roomtypeRepo.findById(roomtypeId).orElse(null);
		
		if(roomtype == null) {
			return Result.failure("根據roomtypeId查找不到房間類型");
		}
		
		String imagePath = roomtype.getImagePath();
		
		Path path = Paths.get(imagePath);
		try {
			UrlResource urlResource = new UrlResource(path.toUri());
			 if (urlResource.exists() || urlResource.isReadable()) {
		    	return Result.success(urlResource).setExtraData("path", path);
		    } 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	   return Result.failure("獲取圖片失敗");
		
	}
}
