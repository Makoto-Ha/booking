package com.booking.service.attraction;


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

import com.booking.bean.dto.attraction.PackageTourDTO;
import com.booking.bean.pojo.attraction.Attraction;
import com.booking.bean.pojo.attraction.PackageTour;
import com.booking.bean.pojo.attraction.PackageTourAttraction;
import com.booking.bean.pojo.attraction.PackageTourAttractionId;
import com.booking.dao.attraction.PackageTourAttractionRepository;
import com.booking.dao.attraction.PackageTourRepository;
import com.booking.dao.attraction.PackageTourSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class PackageTourService {

	@Autowired
	private PackageTourRepository packageTourRepo;

	@Autowired
	private PackageTourAttractionRepository packageTourAttractionRepo;

	
	
	/**
	 * 獲取所有套裝行稱
	 * @param packageTourDTO
	 * @return
	 */
	public Result<PageImpl<PackageTourDTO>> findAllPackageTour(PackageTourDTO packageTourDTO) {

		Integer pageNumber = packageTourDTO.getPageNumber();
		String attrOrderBy = packageTourDTO.getAttrOrderBy();
		Boolean selectedSort = packageTourDTO.getSelectedSort();
		System.out.println(attrOrderBy);
		
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
		Page<PackageTour> page = packageTourRepo.findAll(pageable);
		List<PackageTourDTO> packageTourDTOs = new ArrayList<>();
		List<PackageTour> packageTours = page.getContent();

		for (PackageTour packageTour : packageTours) {
			PackageTourDTO responsepackageTourDTO = new PackageTourDTO();
			BeanUtils.copyProperties(packageTour, responsepackageTourDTO);
			packageTourDTOs.add(responsepackageTourDTO);
		}

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(packageTourDTOs, newPageable, page.getTotalElements()));
	}
	
	
	/**
	 * 模糊查詢
	 * @param packageTourDTO
	 * @return
	 */
	public Result<PageImpl<PackageTourDTO>> findPackagesTours(PackageTourDTO packageTourDTO) {
		Specification<PackageTour> spec = Specification
				.where(PackageTourSpecification.nameContains(packageTourDTO.getPackageTourName()))
				.and(PackageTourSpecification.priceContains(packageTourDTO.getPackageTourPrice()))
				.and(PackageTourSpecification.descriptionContains(packageTourDTO.getPackageTourDescription()));
				
		Pageable pageable = MyPageRequest.of(packageTourDTO.getPageNumber(), 10, packageTourDTO.getSelectedSort(),
				packageTourDTO.getAttrOrderBy());
		
		Page<PackageTour> page = packageTourRepo.findAll(spec, pageable);
		List<PackageTour> packageTours = page.getContent();
		List<PackageTourDTO> packageTourDTOs = new ArrayList<>();

		for (PackageTour packageTour : packageTours) {
			PackageTourDTO responsepackageTourDTO = new PackageTourDTO();

			BeanUtils.copyProperties(packageTour, responsepackageTourDTO);
			packageTourDTOs.add(responsepackageTourDTO);

		}

		PageRequest newPageable = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
		return Result.success(new PageImpl<>(packageTourDTOs, newPageable, page.getTotalElements()));
		
	}
	
	
	
	
	/**
	 * 根據ID取得套裝行程及其景點
	 * @param packageTourId
	 * @return
	 */
    public Result<PackageTourDTO> findPackageTourById(Integer packageTourId) {
        Optional<PackageTour> optional = packageTourRepo.findById(packageTourId);
        if (optional.isEmpty()) {
            return Result.failure("無法找到該套裝行程");
        }

        // 取得行程相關的景點
        DaoResult<List<Attraction>> attractionResult = packageTourAttractionRepo.getAttractionsByPackageTourId(packageTourId);

        if (attractionResult.isFailure()) {
            return Result.failure("無法取得景點");
        }

        List<Attraction> attractions = attractionResult.getData();

        PackageTourDTO packageTourDTO = new PackageTourDTO();
        BeanUtils.copyProperties(optional.get(), packageTourDTO);
        packageTourDTO.setAttractions(attractions);  

        return Result.success(packageTourDTO);
    }
    
	
	
	
	/**
	 * 新增套裝行程
	 * @param packageTourDTO
	 * @param attractionIds
	 * @return
	 */
	@Transactional
	public Result<PackageTour> savePackageTour(PackageTourDTO packageTourDTO, List<Integer> attractionIds) {
		PackageTour packageTour = new PackageTour();
		BeanUtils.copyProperties(packageTourDTO, packageTour);

		
		packageTourRepo.save(packageTour);

		// 新增景點至套裝行程
		for (Integer attractionId : attractionIds) {
			PackageTourAttractionId id = new PackageTourAttractionId(attractionId, packageTour.getPackageTourId());
			PackageTourAttraction packagetourAttraction = new PackageTourAttraction(id, new Attraction(),packageTour);
			packageTourAttractionRepo.save(packagetourAttraction);
		}

		return Result.success("新增套裝行程成功");
	}

	
	
	/**
	 * 刪除套裝行程中的景點
	 * @param packageTourId
	 * @param attractionId
	 * @return
	 */
    @Transactional
    public Result<String> removeAttractionFromPackage(Integer packageTourId, Integer attractionId) {
        packageTourAttractionRepo.removeAttractionFromPackage(packageTourId, attractionId);
        return Result.success("刪除景點成功");
    }
    
    
    
    /**
     * 刪除整個套裝行程
     * @param packageTourId
     * @return
     */
    @Transactional
    public Result<String> deletePackageTourById(Integer packageTourId) {
        packageTourRepo.deleteById(packageTourId); 
        return Result.success("刪除套裝行程成功");
    }
    
    
    
    
    /**
     * 更新套裝行程中的景點
     * @param packageTourAttractionId
     * @param updatedAttraction
     * @return
     */
    @Transactional
    public Result<String> updateAttractionInPackage(PackageTourAttractionId packageTourAttractionId, Attraction updatedAttraction) {
        PackageTourAttraction packagetourAttraction = new PackageTourAttraction(packageTourAttractionId, updatedAttraction, new PackageTour());
        packageTourAttractionRepo.save(packagetourAttraction); 
        return Result.success("更新景點成功");
    }
    

}
