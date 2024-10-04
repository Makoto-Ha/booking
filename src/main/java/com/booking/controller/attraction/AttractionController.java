package com.booking.controller.attraction;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.pojo.attraction.Attraction;
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
	 * @param 
	 * @param 
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
	private String saveAttraction(Attraction attraction) {
		Result<String> saveAttractionResult = attractionService.saveAttraction(attraction);
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
	private void delete(@RequestParam Integer attractionId) {
		attractionService.deledeAttractionById(attractionId);
	}

	/**
	 * 更新景點
	 * @param attraction
	 * @param attractionId
	 */
	@PostMapping("/update")
	private String update(Attraction attraction, @SessionAttribute Integer attractionId) {
		attraction.setAttractionId(attractionId);
		Result<String> updateAttractionResult = attractionService.updateAttraction(attraction);
		
		if(updateAttractionResult.isFailure()) {
			return "";
		}
		return "redirect:/management/attraction";
	}

}
