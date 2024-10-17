package com.booking.service.attraction;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.bean.dto.attraction.PackageTourDTO;
import com.booking.bean.pojo.attraction.Attraction;
import com.booking.bean.pojo.attraction.PackageTour;
import com.booking.bean.pojo.attraction.PackageTourAttraction;
import com.booking.bean.pojo.attraction.PackageTourAttractionId;
import com.booking.dao.attraction.AttractionRepository;
import com.booking.dao.attraction.PackageTourAttractionRepository;
import com.booking.dao.attraction.PackageTourRepository;
import com.booking.dao.attraction.PackageTourSpecification;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;

@Service
public class PackageTourService {

	@Autowired
	private PackageTourRepository packageTourRepo;

	@Autowired
	private PackageTourAttractionRepository packageTourAttractionRepo;

	@Autowired
	private AttractionRepository attractionRepo;
	
	/**
	 * 獲取所有套裝行稱
	 * @param packageTourDTO
	 * @return
	 */
	public Result<PageImpl<PackageTourDTO>> findAllPackageTour(PackageTourDTO packageTourDTO) {

		Integer pageNumber = packageTourDTO.getPageNumber();
		String attrOrderBy = packageTourDTO.getAttrOrderBy();
		Boolean selectedSort = packageTourDTO.getSelectedSort();
		
		Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
		Page<PackageTour> page = packageTourRepo.findAll(pageable);
		List<PackageTourDTO> packageTourDTOs = new ArrayList<>();
		List<PackageTour> packageTours = page.getContent();

	    for (PackageTour packageTour : packageTours) {
	        PackageTourDTO responsePackageTourDTO = new PackageTourDTO();
	        BeanUtils.copyProperties(packageTour, responsePackageTourDTO);
	        
	        List<String> attractionNames = packageTour.getPackageTourAttractions().stream()
	            .map(packageToutaAttraction -> packageToutaAttraction.getAttraction().getAttractionName())
	            .collect(Collectors.toList());
	        responsePackageTourDTO.setAttractionNames(attractionNames);
	        
	        packageTourDTOs.add(responsePackageTourDTO);
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
	            .and(PackageTourSpecification.priceBetween(packageTourDTO.getMinPrice(), packageTourDTO.getMaxPrice()));

	    Pageable pageable = MyPageRequest.of(packageTourDTO.getPageNumber(), 10, packageTourDTO.getSelectedSort(),
	            packageTourDTO.getAttrOrderBy());

	    Page<PackageTour> page = packageTourRepo.findAll(spec, pageable);
	    List<PackageTour> packageTours = page.getContent();
	    List<PackageTourDTO> packageTourDTOs = new ArrayList<>();

	    for (PackageTour packageTour : packageTours) {
	        PackageTourDTO responsePackageTourDTO = new PackageTourDTO();

	        BeanUtils.copyProperties(packageTour, responsePackageTourDTO);
	        List<String> attractionNames = packageTour.getPackageTourAttractions().stream()
	                .map(packageTourAttraction -> packageTourAttraction.getAttraction().getAttractionName())
	                .collect(Collectors.toList());
	        responsePackageTourDTO.setAttractionNames(attractionNames);

	        packageTourDTOs.add(responsePackageTourDTO);
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
	    if (optional.isPresent()) {
	        PackageTour packageTour = optional.get();
	        PackageTourDTO packageTourDTO = new PackageTourDTO();
	        BeanUtils.copyProperties(packageTour, packageTourDTO);

	        List<AttractionDTO> selectedAttractions = packageTour.getPackageTourAttractions().stream()
	            .map(packageTourAttraction -> {
	                AttractionDTO attractionDTO = new AttractionDTO();
	                BeanUtils.copyProperties(packageTourAttraction.getAttraction(), attractionDTO);
	                return attractionDTO;
	            })
	            .collect(Collectors.toList());
	        packageTourDTO.setSelectedAttractions(selectedAttractions);

	        return Result.success(packageTourDTO);
	    }
	    return Result.failure("找不到指定的套裝行程");
	}
    
	/**
	 * 根據套裝行程ID找到景點名稱
	 * @param packageTourId
	 * @return
	 */
    public Result<PackageTourDTO> getPackageTourDetailsWithAttractionNames(Integer packageTourId) {
        Optional<PackageTour> optional = packageTourRepo.findById(packageTourId);
        if (optional.isPresent()) {
            PackageTour packageTour = optional.get();
            PackageTourDTO packageTourDTO = new PackageTourDTO();
            BeanUtils.copyProperties(packageTour, packageTourDTO);

            List<String> attractionNames = packageTour.getPackageTourAttractions().stream()
                .map(packageTourAttraction -> packageTourAttraction.getAttraction().getAttractionName())
                .collect(Collectors.toList());
            packageTourDTO.setAttractionNames(attractionNames);

            return Result.success(packageTourDTO);
        }
        return Result.failure("找不到指定的套裝行程");
        
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

	    for (Integer attractionId : attractionIds) {
	        Attraction attraction = attractionRepo.findById(attractionId).orElseThrow(() -> new RuntimeException("景點不存在"));
	        PackageTourAttractionId id = new PackageTourAttractionId(attractionId, packageTour.getPackageTourId());
	        PackageTourAttraction packagetourAttraction = new PackageTourAttraction(id, attraction, packageTour);
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
    public Result<String> updatePackageTour(PackageTourDTO packageTourDTO) {
        if (packageTourDTO.getPackageTourId() == null) {
            return Result.failure("套裝行程ID不能為空");
        }

        Optional<PackageTour> optionalPackageTour = packageTourRepo.findById(packageTourDTO.getPackageTourId());
        if (optionalPackageTour.isEmpty()) {
            return Result.failure("找不到指定的套裝行程");
        }

        PackageTour packageTour = optionalPackageTour.get();
        BeanUtils.copyProperties(packageTourDTO, packageTour, "packageTourAttractions");

        // 更新景點關聯
        packageTour.getPackageTourAttractions().clear();
        if (packageTourDTO.getSelectedAttractionIds() != null) {
            for (Integer attractionId : packageTourDTO.getSelectedAttractionIds()) {
                Attraction attraction = attractionRepo.findById(attractionId)
                    .orElseThrow(() -> new RuntimeException("景點不存在: " + attractionId));
                PackageTourAttraction pta = new PackageTourAttraction();
                pta.setPackageTour(packageTour);
                pta.setAttraction(attraction);
                packageTour.getPackageTourAttractions().add(pta);
            }
        }

        packageTourRepo.save(packageTour);
        return Result.success("套裝行程更新成功");
    }
}
