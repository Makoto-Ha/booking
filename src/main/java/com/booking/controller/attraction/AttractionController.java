package com.booking.controller.attraction;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.booking.bean.dto.attraction.AttractionDTO;
import com.booking.service.attraction.AttractionService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/attraction")
public class AttractionController {
	
	@Autowired
	private AttractionService attractionService;

	
	
	/**
	 * 轉到景點首頁
	 * @param model
	 */
	@GetMapping
	private String attraction(Model model) {
		// DTO用於分頁所需數據
		AttractionDTO attractionDTO = new AttractionDTO();
		Result<PageImpl<AttractionDTO>> findAttractionAllResult = attractionService.findAttractionAll(attractionDTO);
		
		if(findAttractionAllResult.isFailure()) {
			return "";
		}
		
		Page<AttractionDTO> page = findAttractionAllResult.getData();
		
		model.addAttribute("attractionDTO", attractionDTO);
		model.addAttribute("page", page);

		return "management-system/attraction/attraction-list";
	}
	
	/**
	 * 轉到查詢
	 * return
	 */
	@GetMapping("/select/page")
	private String sendSelectPage() {	
		return "/management-system/attraction/attraction-select";
	}

	
	/**
	 * 轉去create-page
	 * return
	 */
	@GetMapping("/create/page")
	private String sendCreatePage() {
		return "management-system/attraction/attraction-create";
	}
	

	/**
	 * 轉去edit-page
	 * @param attractionId
	 * @param session
	 * @param model
	 */
	@GetMapping("/edit/page")
	private String sendEditPage(@RequestParam Integer attractionId, HttpSession session, Model model) {
		session.setAttribute("attractionId", attractionId);
		Result<AttractionDTO> attractionServiceResult = attractionService.findAttractionById(attractionId);
		
		if(attractionServiceResult.isFailure()) {
			return "";
		}
		
		model.addAttribute("attraction", attractionServiceResult.getData());
		return "/management-system/attraction/attraction-edit";
	}
	

	/**
	 * 模糊查詢
	 * @param requestParameters
	 * @param attractionDTO
	 * @param model
	 * @return
	 */
	@GetMapping("/select")
	private String select(@RequestParam Map<String, String> requestParameters, AttractionDTO attractionDTO, Model model) {
		
		Result<PageImpl<AttractionDTO>> attractionServiceResult = attractionService.findAttractions(attractionDTO);
		
		if(attractionServiceResult.isFailure()) {
			return "";
		}
		
		Page<AttractionDTO> page = attractionServiceResult.getData();
		
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("attractionDTO", attractionDTO);
		model.addAttribute("page", page);

		return "/management-system/attraction/attraction-list";	
	}
	
	
	/**
	 * 新增景點
	 * @param attraction
	 */
	@PostMapping("/create")
	private String saveAttraction(AttractionDTO attractionDTO,@RequestParam(required = false) MultipartFile imageFile) {
		Result<String> saveAttractionResult = attractionService.saveAttraction(attractionDTO, imageFile);
		if (saveAttractionResult.isFailure()) {
			return "";
		}
		return "redirect:/management/attraction";
		
	}

	/**
	 * 刪除景點
	 * @param attractionId
	 */
	@PostMapping("/delete")
	private ResponseEntity<?> deleteAttractionById(@RequestParam Integer attractionId) {
		Result<String> deleteAttractionResult = attractionService.deledeAttractionById(attractionId);
		String message = deleteAttractionResult.getMessage();
		if(deleteAttractionResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		
		return ResponseEntity.ok(message);
	}

	/**
	 * 更新景點
	 * @param attraction
	 * @param attractionId
	 */
	@PostMapping("/update")
	private String updateAttractionById(AttractionDTO attractionDTO, @SessionAttribute Integer attractionId,
			@RequestParam(required = false) MultipartFile imageFile) {
		attractionDTO.setAttractionId(attractionId);
		Result<String> updateAttractionResult = attractionService.updateAttraction(attractionDTO, imageFile);
		
		if(updateAttractionResult.isFailure()) {
			return "";
		}
		return "redirect:/management/attraction";
	}
	
	
	
	/**
	 * 根據ID上傳圖片
	 * @param imageFile
	 * @param attractionId
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseEntity<String> uploadImageById(@RequestParam MultipartFile imageFile, Integer attractionId) {
		Result<String> uploadImageResult = attractionService.uploadImageById(imageFile, attractionId);
		String message = uploadImageResult.getMessage();
		if(uploadImageResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		
		return ResponseEntity.ok(message);
	}
	
	
	
	/**
	 * 根據ID取得圖片
	 * @param attractionId
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/image/{attractionId}")
	public ResponseEntity<?> findImageById(@PathVariable Integer attractionId) throws IOException {
		Result<UrlResource> findImageByIdResult = attractionService.findImageById(attractionId);
		
		if(findImageByIdResult.isFailure()) {
			return ResponseEntity.badRequest().body(findImageByIdResult.getMessage());
		}
		
		Path path = (Path) findImageByIdResult.getExtraData("path");
		UrlResource resource = findImageByIdResult.getData();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.body(resource);
				
	}
	
	
	/**
	 * 景點分析
	 * @param model
	 * @return
	 */
    @GetMapping("/analysis")
    public String showAnalysis(Model model) {
        Map<String, Object> analytics = attractionService.getAttractionAnalytics();
        model.addAttribute("totalAttractions", analytics.get("totalAttractions"));
        model.addAttribute("totalCities", analytics.get("totalCities"));
        model.addAttribute("totalTypes", analytics.get("totalTypes"));
        model.addAttribute("cityStats", analytics.get("cityStats"));
        model.addAttribute("typeStats", analytics.get("typeStats"));
        
        return "management-system/attraction/attraction-analysis";
    }

}
