package com.booking.service.attraction;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.bean.pojo.attraction.Attraction;
import com.booking.dao.attraction.AttractionRepository;
import com.booking.dao.attraction.AttractionSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyModelMapper;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;
import com.booking.utils.UploadImageFile;

@Service
public class AttractionService {

	@Autowired
	private AttractionRepository attractionRepo;

	/**
	 * 獲取所有景點
	 * 
	 * @param attractionDTO
	 * @return
	 */
	public Result<PageImpl<AttractionDTO>> findAttractionAll(AttractionDTO attractionDTO) {

		Integer pageNumber = attractionDTO.getPageNumber();
		String attrOrderBy = attractionDTO.getAttrOrderBy();
		Boolean selectedSort = attractionDTO.getSelectedSort();
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
		Page<Attraction> page = attractionRepo.findAll(pageable);
		List<AttractionDTO> attractionDTOs = new ArrayList<>();
		List<Attraction> attractions = page.getContent();

		for (Attraction attraction : attractions) {
			AttractionDTO responseAttractionDTO = new AttractionDTO();
			BeanUtils.copyProperties(attraction, responseAttractionDTO);
			attractionDTOs.add(responseAttractionDTO);
		}

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(attractionDTOs, newPageable, page.getTotalElements()));
	}
	/**
	 * 依模糊查詢得到多筆景點
	 * 
	 * @param attraction
	 * @return
	 */
	public Result<PageImpl<AttractionDTO>> findAttractions(AttractionDTO attractionDTO) {
		Specification<Attraction> spec = Specification
				.where(AttractionSpecification.nameContains(attractionDTO.getAttractionName()))
				.and(AttractionSpecification.cityContains(attractionDTO.getAttractionCity()))
				.and(AttractionSpecification.addressContains(attractionDTO.getAddress()))
				.and(AttractionSpecification.openingHourContains(attractionDTO.getOpeningHour()))
				.and(AttractionSpecification.typeContains(attractionDTO.getAttractionType()))
				.and(AttractionSpecification.descriptionContains(attractionDTO.getAttractionDescription()));

		Pageable pageable = MyPageRequest.of(attractionDTO.getPageNumber(), 10, attractionDTO.getSelectedSort(),
				attractionDTO.getAttrOrderBy());

		Page<Attraction> page = attractionRepo.findAll(spec, pageable);
		List<Attraction> attractions = page.getContent();
		List<AttractionDTO> attractionsDTOs = new ArrayList<>();

		for (Attraction attraction : attractions) {
			AttractionDTO responseAttractionDTO = new AttractionDTO();

			BeanUtils.copyProperties(attraction, responseAttractionDTO);
			attractionsDTOs.add(responseAttractionDTO);

		}

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(attractionsDTOs, newPageable, page.getTotalElements()));


	}

	/**
	 * 依名稱獲取景點
	 * 
	 * @param attractionName
	 * @return
	 */
	public Result<List<AttractionDTO>> findAttractionByName(String attractionName) {
		DaoResult<List<Attraction>> getAttractionByNameResult = attractionRepo.getattractionByName(attractionName);

		if (getAttractionByNameResult.isFailure()) {
			return Result.failure("根據景點名稱獲取景點失敗");
		}

		List<AttractionDTO> list = new ArrayList<>();
		List<Attraction> attractions = getAttractionByNameResult.getData();

		for (Attraction attraction : attractions) {
			AttractionDTO attractionDTO = new AttractionDTO();
			BeanUtils.copyProperties(attraction, attractionDTO);
			list.add(attractionDTO);
		}

		return Result.success(list);
	}

	/**
	 * 依id獲取景點
	 * 
	 * @param attractionId
	 * @return
	 */
	public Result<AttractionDTO> findAttractionById(Integer attractionId) {
		Optional<Attraction> optional = attractionRepo.findById(attractionId);
		if (optional.isEmpty()) {
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

		return Result.success("刪除景點成功");

	}

	/**
	 * 更新景點
	 * @param attractionDTO
	 * @return
	 */
	public Result<String> updateAttraction(AttractionDTO attractionDTO, MultipartFile imageFile) {
		Result<String> uploadResult = UploadImageFile.upload(imageFile);
		
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
		
		Path path = (Path) uploadImageResult.getExtraData("path");
		attraction.setImagesFile(path.toString());
		
		return Result.success("上傳圖片成功");
	}
		
	
	public Result<UrlResource> findImageById(Integer attractionId) {
		Attraction attraction = attractionRepo.findById(attractionId).orElse(null);
		
		if(attraction == null) {
			return Result.failure("根據ID查找不到房間類型");
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
