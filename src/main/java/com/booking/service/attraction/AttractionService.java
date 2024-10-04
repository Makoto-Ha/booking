package com.booking.service.attraction;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
<<<<<<< HEAD
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
=======

import org.springframework.beans.BeanUtils;


import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.bean.pojo.attraction.Attraction;
import com.booking.dao.attraction.AttractionRepository;
import com.booking.dao.attraction.AttractionSpecification;
import com.booking.utils.DaoResult;
<<<<<<< HEAD
import com.booking.utils.MyModelMapper;
=======
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;
import com.booking.utils.UploadImageFile;

@Service
public class AttractionService {

	@Autowired
	private AttractionRepository attractionRepo;
<<<<<<< HEAD

	/**
	 * 獲取所有景點
	 * 
	 * @param attractionDTO
	 * @return
	 */
	public Result<PageImpl<AttractionDTO>> findAttractionAll(AttractionDTO attractionDTO) {

=======
	

   /**
	* 獲取所有景點
	* @param attractionDTO
	* @return
	*/
	public Result<PageImpl<AttractionDTO>> findAttractionAll(AttractionDTO attractionDTO) {
		
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
		Integer pageNumber = attractionDTO.getPageNumber();
		String attrOrderBy = attractionDTO.getAttrOrderBy();
		Boolean selectedSort = attractionDTO.getSelectedSort();
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
		Page<Attraction> page = attractionRepo.findAll(pageable);
		List<AttractionDTO> attractionDTOs = new ArrayList<>();
		List<Attraction> attractions = page.getContent();
<<<<<<< HEAD

		for (Attraction attraction : attractions) {
=======
		
		
		for(Attraction attraction : attractions) {
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
			AttractionDTO responseAttractionDTO = new AttractionDTO();
			BeanUtils.copyProperties(attraction, responseAttractionDTO);
			attractionDTOs.add(responseAttractionDTO);
		}
<<<<<<< HEAD

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(attractionDTOs, newPageable, page.getTotalElements()));
	}
	
	/**
	 * 返回所有景點
	 * @return
	 */
	public List<AttractionDTO> findAllAttractions() {
	    List<Attraction> allAttractions = attractionRepo.findAll();
	    return allAttractions.stream()
	            .map(attraction -> {
	                AttractionDTO attractionDTO = new AttractionDTO();
	                BeanUtils.copyProperties(attraction, attractionDTO);
	                return attractionDTO;
	            })
	            .collect(Collectors.toList());
=======
		
		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(attractionDTOs, newPageable, page.getTotalElements()));
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
	}
	
	/**
	 * 依模糊查詢得到多筆景點
	 * 
	 * @param attractionDTO
	 * @return
	 */
	public Result<PageImpl<AttractionDTO>> findAttractions(AttractionDTO attractionDTO) {
		Specification<Attraction> spec = Specification
<<<<<<< HEAD
				.where(AttractionSpecification.nameContains(attractionDTO.getAttractionName()))
				.and(AttractionSpecification.cityContains(attractionDTO.getAttractionCity()))
				.and(AttractionSpecification.addressContains(attractionDTO.getAddress()))
				.and(AttractionSpecification.typeContains(attractionDTO.getAttractionType()));

		Pageable pageable = MyPageRequest.of(attractionDTO.getPageNumber(), 10, attractionDTO.getSelectedSort(),
				attractionDTO.getAttrOrderBy());

		Page<Attraction> page = attractionRepo.findAll(spec, pageable);
		List<Attraction> attractions = page.getContent();
		List<AttractionDTO> attractionsDTOs = new ArrayList<>();

		for (Attraction attraction : attractions) {
=======
				 .where(AttractionSpecification.nameContains(attractionDTO.getAttractionName()))
				 .and(AttractionSpecification.cityContains(attractionDTO.getAttractionCity()))
				 .and(AttractionSpecification.addressContains(attractionDTO.getAddress()))
				 .and(AttractionSpecification.openingHourContains(attractionDTO.getOpeningHour()))
				 .and(AttractionSpecification.typeContains(attractionDTO.getAttractionType()))
				 .and(AttractionSpecification.descriptionContains(attractionDTO.getAttractionDescription()));

		
		Pageable pageable = MyPageRequest.of(
				attractionDTO.getPageNumber(), 
				10, 
				attractionDTO.getSelectedSort(), 
				attractionDTO.getAttrOrderBy());
		
		Page<Attraction> page = attractionRepo.findAll(spec, pageable);
		List<Attraction> attractions = page.getContent();
		List<AttractionDTO> attractionsDTOs = new ArrayList<>();
		
		for(Attraction attraction : attractions) {
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
			AttractionDTO responseAttractionDTO = new AttractionDTO();

			BeanUtils.copyProperties(attraction, responseAttractionDTO);
			attractionsDTOs.add(responseAttractionDTO);

<<<<<<< HEAD
		}

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(attractionsDTOs, newPageable, page.getTotalElements()));


	}

	/**
	 * 依名稱獲取景點
	 * 
=======

		}
		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(attractionsDTOs, newPageable, page.getTotalElements()));
	}
	
	/**
	 * 依名稱獲取景點
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
	 * @param attractionName
	 * @return
	 */
	public Result<List<AttractionDTO>> findAttractionByName(String attractionName) {
		DaoResult<List<Attraction>> getAttractionByNameResult = attractionRepo.getattractionByName(attractionName);
<<<<<<< HEAD

		if (getAttractionByNameResult.isFailure()) {
			return Result.failure("根據景點名稱獲取景點失敗");
		}

		List<AttractionDTO> list = new ArrayList<>();
		List<Attraction> attractions = getAttractionByNameResult.getData();

		for (Attraction attraction : attractions) {
=======
		
		if(getAttractionByNameResult.isFailure()) {
			return Result.failure("根據景點名稱獲取景點失敗");
		}
		
		List<AttractionDTO> list = new ArrayList<>();
		List<Attraction> attractions = getAttractionByNameResult.getData();
		
		for(Attraction attraction : attractions) {
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
			AttractionDTO attractionDTO = new AttractionDTO();
			BeanUtils.copyProperties(attraction, attractionDTO);
			list.add(attractionDTO);
		}
<<<<<<< HEAD

		return Result.success(list);
	}

=======
		
		return Result.success(list);
	}
	
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
	/**
	 * 依id獲取景點
	 * 
	 * @param attractionId
	 * @return
	 */
	public Result<AttractionDTO> findAttractionById(Integer attractionId) {
		Optional<Attraction> optional = attractionRepo.findById(attractionId);
<<<<<<< HEAD
		if (optional.isEmpty()) {
=======
		if(optional.isEmpty()) {
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
			return Result.failure("沒有此景點");
		}
		Attraction attraction = optional.get();
		AttractionDTO attractionDTO = new AttractionDTO();
		BeanUtils.copyProperties(attraction, attractionDTO);
		return Result.success(attractionDTO);
	}
	

	/**
	 * 新增景點
	 * 
	 * @param attraction
	 * @return
	 */
	@Transactional
<<<<<<< HEAD
	public Result<String> saveAttraction(AttractionDTO attractionDTO, MultipartFile imageFile) {
		Result<String> uploadResult = UploadImageFile.upload(imageFile);
		
		if(uploadResult.isSuccess()) {
			String fileName = imageFile.getOriginalFilename();
			attractionDTO.setImagesFile("uploads" + "/" + fileName);
		}else {
			attractionDTO.setImagesFile("uploads/default.jpg");
		}
		Attraction attraction = new Attraction();
		
		BeanUtils.copyProperties(attractionDTO, attraction);
		
=======
	public Result<String> saveAttraction(Attraction attraction) {
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
		attractionRepo.save(attraction);

		return Result.success("新增景點成功");
	}

	/**
	 * 依id刪除景點
	 * 
	 * @param attractionId
	 */
	@Transactional
	public Result<String> deledeAttractionById(Integer attractionId) {
		attractionRepo.deleteById(attractionId);
<<<<<<< HEAD

=======
		
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
		return Result.success("刪除景點成功");

	}

	/**
	 * 更新景點
	 * @param attractionDTO
	 * @return
	 */
<<<<<<< HEAD
	public Result<String> updateAttraction(AttractionDTO attractionDTO, MultipartFile imageFile) {
		Result<String> uploadResult = UploadImageFile.upload(imageFile);
=======
	@Transactional
	public Result<String> updateAttraction(Attraction attraction) {
		Integer oldAttractionId = attraction.getAttractionId();
		Attraction oldAttraction = attractionRepo.findById(oldAttractionId).get();
		String attractionName = attraction.getAttractionName();
		String attractionCity = attraction.getAttractionCity();
		String address = attraction.getAddress();
		String openingHour = attraction.getOpeningHour();
		String attractionType = attraction.getAttractionType();
		String attractionDescription = attraction.getAttractionDescription();
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
		
		if(uploadResult.isSuccess()) {
			String fileName = imageFile.getOriginalFilename();
			attractionDTO.setImagesFile("uploads" + "/" + fileName);
		}else {
			attractionDTO.setImagesFile("uploads/default.jpg");
		}
		
		Attraction updateAttraction = attractionRepo.findById(attractionDTO.getAttractionId()).orElse(null);
		MyModelMapper.map(attractionDTO, updateAttraction);
		attractionRepo.save(updateAttraction);
		return Result.success("更新景點成功");
	}
	
	
	/**
	 * 根據id上傳圖片
	 * @param imageFile
	 * @param attractionId
	 * @return
	 */
	public Result<String> uploadImageById(MultipartFile imageFile, Integer attractionId){
		Attraction attraction = attractionRepo.findById(attractionId).orElse(null);
		if(attraction == null) {
			return Result.failure("沒有此景點");
		}
		
		Result<String> uploadImageResult = UploadImageFile.upload(imageFile);
		
		if(uploadImageResult.isFailure()) {
			return Result.failure(uploadImageResult.getMessage());
		}
<<<<<<< HEAD
		
		Path path = (Path) uploadImageResult.getExtraData("path");
		attraction.setImagesFile(path.toString());
		
		return Result.success("上傳圖片成功");
=======
		if (openingHour == null || openingHour.isEmpty()) {
			attraction.setOpeningHour(oldAttraction.getOpeningHour());
		}
		if (attractionType == null || attractionType.isEmpty()) {
			attraction.setAttractionType(oldAttraction.getAttractionType());
		}
		if (attractionDescription == null || attractionDescription.isEmpty()) {
			attraction.setAttractionDescription(oldAttraction.getAttractionDescription());
		}

		DaoResult<?> updateAttractionResult = attractionRepo.updateAttraction(attraction);
		if(updateAttractionResult.isFailure()) {
			return Result.failure("更新失敗");
		}
		return Result.success("更新景點成功");
>>>>>>> 38e5de9 (鄭家霖.修改: Attraction的SpringMVC移植成SpringBoot)
	}
		
	
	public Result<UrlResource> findImageById(Integer attractionId) {
		Attraction attraction = attractionRepo.findById(attractionId).orElse(null);
		
		if(attraction == null) {
			return Result.failure("根據ID查找不到景點");
		}
		
		String imagesFile = attraction.getImagesFile();
		
		Path path = Paths.get(imagesFile);
		try {
			UrlResource urlResource = new UrlResource(path.toUri());
			 if (urlResource.exists() || urlResource.isReadable()) {
		    	return Result.success(urlResource).setExtraData("path", path);
		    } 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		DaoResult<?> updateAttractionResult = attractionRepo.updateAttraction(attraction);
		if (updateAttractionResult.isFailure()) {
			return Result.failure("更新失敗");
		}
		return Result.success("更新景點成功");
	}

}
