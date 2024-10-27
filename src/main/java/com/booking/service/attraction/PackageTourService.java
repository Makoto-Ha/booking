package com.booking.service.attraction;


import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.booking.bean.dto.attraction.PackageTourDTO;
import com.booking.bean.pojo.attraction.Attraction;
import com.booking.bean.pojo.attraction.PackageTour;
import com.booking.bean.pojo.attraction.PackageTourAttraction;
import com.booking.bean.pojo.attraction.PackageTourAttractionId;
import com.booking.dao.attraction.AttractionRepository;
import com.booking.dao.attraction.PackageTourAttractionRepository;
import com.booking.dao.attraction.PackageTourRepository;
import com.booking.dao.attraction.PackageTourSpecification;
import com.booking.utils.DaoResult;
import com.booking.utils.MyPageRequest;
import com.booking.utils.Result;
import com.booking.utils.UploadImageFile;

@Service
public class PackageTourService {

	@Autowired
	private PackageTourRepository packageTourRepo;

	@Autowired
	private PackageTourAttractionRepository packageTourAttractionRepo;

	@Autowired
	private AttractionRepository attractionRepo;
	
	/**
	 * 獲取所有套裝行程
	 * @param packageTourDTO
	 * @return
	 */
	public Result<PageImpl<PackageTourDTO>> findAllPackageTour(PackageTourDTO packageTourDTO) {
	    Integer pageNumber = packageTourDTO.getPageNumber();
	    String attrOrderBy = packageTourDTO.getAttrOrderBy();
	    Boolean selectedSort = packageTourDTO.getSelectedSort();
	    
	    Pageable pageable = MyPageRequest.of(pageNumber, 10, selectedSort, attrOrderBy);
	    Page<PackageTour> page = packageTourRepo.findAllWithAttractions(pageable);
	    List<PackageTourDTO> packageTourDTOs = new ArrayList<>();

	    for (PackageTour packageTour : page.getContent()) {
	        PackageTourDTO responsePackageTourDTO = new PackageTourDTO();
	        BeanUtils.copyProperties(packageTour, responsePackageTourDTO);
	        
	        List<String> attractionNames = new ArrayList<>();
	        for (PackageTourAttraction pta : packageTour.getPackageTourAttractions()) {
	            Integer attractionId = pta.getId().getAttractionId();
	            Optional<Attraction> attractionOpt = attractionRepo.findById(attractionId);
	            if (attractionOpt.isPresent()) {
	                Attraction attraction = attractionOpt.get();
	                attractionNames.add(attraction.getAttractionName());
	            }
	        }
	        
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
     * @param imageFile
     * @return
     */
    @Transactional
    public Result<PackageTour> savePackageTour(PackageTourDTO packageTourDTO, List<Integer> attractionIds, MultipartFile imageFile) {
        PackageTour packageTour = new PackageTour();
        BeanUtils.copyProperties(packageTourDTO, packageTour);
        
        if (imageFile != null && !imageFile.isEmpty()) {
            Result<String> uploadResult = UploadImageFile.upload(imageFile);
            if(uploadResult.isSuccess()) {
                String fileName = imageFile.getOriginalFilename();
                packageTour.setPackageTourImg("uploads" + "/" + fileName);
            } else {
                packageTour.setPackageTourImg("uploads/default.jpg");
            }
        } else {
            packageTour.setPackageTourImg("uploads/default.jpg");
        }
        
        packageTour = packageTourRepo.save(packageTour);
        packageTour.setPackageTourAttractions(new ArrayList<>());
        List<PackageTourAttraction> newAttractions = new ArrayList<>();
        
        for (Integer attractionId : attractionIds) {
            Optional<Attraction> attractionOpt = attractionRepo.findById(attractionId);
            if (attractionOpt.isEmpty()) {
                continue; 
            }
            
            PackageTourAttractionId packageTourAttractionId = new PackageTourAttractionId();
            packageTourAttractionId.setAttractionId(attractionId);
            packageTourAttractionId.setPackageTourId(packageTour.getPackageTourId());
            
            PackageTourAttraction packageTourAttraction = new PackageTourAttraction();
            packageTourAttraction.setId(packageTourAttractionId);
            packageTourAttraction.setAttraction(attractionOpt.get());
            packageTourAttraction.setPackageTour(packageTour);
            
            newAttractions.add(packageTourAttraction);
        }
        
        if (!newAttractions.isEmpty()) {
            packageTourAttractionRepo.saveAll(newAttractions);
        }
        
        return Result.success("新增套裝行程成功");
    }

    
    
    /**
     * 刪除整個套裝行程
     * @param packageTourId
     * @return
     */
    @Transactional
    public Result<String> deletePackageTourById(Integer packageTourId) {
        Optional<PackageTour> optional = packageTourRepo.findById(packageTourId);
        if (optional.isEmpty()) {
            return Result.failure("找不到指定的套裝行程");
        }
        
        PackageTour packageTour = optional.get();
        
        packageTour.getPackageTourAttractions().clear();
        packageTourRepo.save(packageTour);
        
        packageTourRepo.deleteById(packageTourId);
        
        return Result.success("刪除套裝行程成功");
    }
    
    
    
    
    /**
     * 更新套裝行程
     * @param packageTourDTO
     * @param packageTourImg
     * @return
     */
    @Transactional
    public Result<String> updatePackageTour(PackageTourDTO packageTourDTO, MultipartFile packageTourImg) {
        if (packageTourDTO.getPackageTourId() == null) {
            return Result.failure("套裝行程ID不能為空");
        }

        Optional<PackageTour> optional = packageTourRepo.findById(packageTourDTO.getPackageTourId());
        if (optional.isEmpty()) {
            return Result.failure("找不到指定的套裝行程");
        }
        PackageTour packageTour = optional.get();

        if (packageTourImg != null && !packageTourImg.isEmpty()) {
            Result<String> uploadResult = UploadImageFile.upload(packageTourImg);
            if(uploadResult.isSuccess()) {
                String fileName = packageTourImg.getOriginalFilename();
                packageTour.setPackageTourImg("uploads" + "/" + fileName);
            }
        }

        packageTour.setPackageTourName(packageTourDTO.getPackageTourName());
        packageTour.setPackageTourPrice(packageTourDTO.getPackageTourPrice());
        packageTour.setPackageTourDescription(packageTourDTO.getPackageTourDescription());

        List<PackageTourAttraction> currentAttractions = new ArrayList<>(packageTour.getPackageTourAttractions());
        packageTour.getPackageTourAttractions().clear();
        
        packageTour = packageTourRepo.save(packageTour);

        for (PackageTourAttraction pta : currentAttractions) {
            packageTourAttractionRepo.delete(pta);
        }

        if (packageTourDTO.getSelectedAttractionIds() != null) {
            for (Integer attractionId : packageTourDTO.getSelectedAttractionIds()) {
                Optional<Attraction> attractionOpt = attractionRepo.findById(attractionId);
                if (attractionOpt.isPresent()) {
                    Attraction attraction = attractionOpt.get();
                    
                    PackageTourAttraction pta = new PackageTourAttraction();
                    PackageTourAttractionId ptaId = new PackageTourAttractionId(attractionId, packageTour.getPackageTourId());
                    pta.setId(ptaId);
                    pta.setPackageTour(packageTour);
                    pta.setAttraction(attraction);
                    
                    packageTourAttractionRepo.save(pta);
                    packageTour.getPackageTourAttractions().add(pta);
                }
            }
        }

        packageTourRepo.save(packageTour);
        return Result.success("套裝行程更新成功");
    }
    
    
    
	public Result<String> uploadImageById(MultipartFile packageTourImg, Integer packageTourId){
		PackageTour packageTour = packageTourRepo.findById(packageTourId).orElse(null);
		if(packageTour == null) {
			return Result.failure("沒有此套裝行程");
		}
		
		Result<String> uploadImageResult = UploadImageFile.upload(packageTourImg);
		
		if(uploadImageResult.isFailure()) {
			return Result.failure(uploadImageResult.getMessage());
		}
		
		Path path = (Path) uploadImageResult.getExtraData("path");
		packageTour.setPackageTourImg(path.toString());
		
		return Result.success("上傳圖片成功");
	}
	
	
	public Result<UrlResource> findImageById(Integer packageTourId) {
		PackageTour packageTour = packageTourRepo.findById(packageTourId).orElse(null);
		
		if(packageTour == null) {
			return Result.failure("根據ID查找不到套裝行程");
		}
		
		String packageTourImg = packageTour.getPackageTourImg();
		
		Path path = Paths.get(packageTourImg);
		try {
			UrlResource urlResource = new UrlResource(path.toUri());
			 if (urlResource.exists() || urlResource.isReadable()) {
		    	return Result.success(urlResource).setExtraData("path", path);
		    } 
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		DaoResult<?> updatePackageTourResult = packageTourRepo.updatePackageTour(packageTour);
		if (updatePackageTourResult.isFailure()) {
			return Result.failure("更新失敗");
		}
		return Result.success("更新套裝行程成功");
	}
}
