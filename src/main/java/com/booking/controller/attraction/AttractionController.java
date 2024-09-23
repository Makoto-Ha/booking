package com.booking.controller.attraction;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.attraction.Attraction;
import com.booking.dto.attraction.AttractionDTO;
import com.booking.service.attraction.AttractionService;
import com.booking.utils.JsonUtil;
import com.booking.utils.Listable;
import com.booking.utils.Result;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/attraction")
public class AttractionController {
	
	@Autowired
	private AttractionService attractionService;


	/**
	 * 返回所有景點的數據
	 * @param attractionid
	 * return
	 */
	@GetMapping("/json/{id}")
	@ResponseBody
	private String getAttractionJSON(@PathVariable Integer id) {
		Result<AttractionDTO> attractionServiceResult = attractionService.getAttractionById(id);
		if(attractionServiceResult.isFailure()) {
			return null;
		}
		return JsonUtil.toJson(attractionServiceResult.getData());
	}
	
	
	/**
	 * 轉到景點首頁
	 * @param model
	 */
	@GetMapping("")
	private String attraction(Model model) {
		
		Result<List<Listable>> attractionServiceResult = attractionService.getAttractionAll();
		if(attractionServiceResult.isFailure()) {
			return "";
		}
		List<Listable> attractions = attractionServiceResult.getData();
		
		
		model.addAttribute("lists", attractions);
		model.addAttribute("pageInfos", AttractionDTO.pageInfos);
		model.addAttribute("listInfos", AttractionDTO.listInfos);
		model.addAttribute("manageListName", AttractionDTO.manageListName);
		return "/adminsystem/index";
	}
	
	/**
	 * 轉到查詢
	 * @throws ServletException
	 * @throws IOException
	 */
	@GetMapping("/select/jsp")
	private String sendSelectJsp() throws ServletException, IOException {	
		return "/adminsystem/attraction/attraction-select";
	}

	
	/**
	 * 轉去create.jsp
	 * @throws ServletExceptions
	 * @throws IOException
	 */
	@GetMapping("/create/jsp")
	private String sendCreateJsp() throws ServletException, IOException {
		return "/adminsystem/attraction/attraction-create";
	}
	

	/**
	 * 轉去edit.jsp
	 * @param attractionId
	 * @param session
	 * @param model
	 */
	@GetMapping("/edit/jsp")
	private String sendEditJsp(@RequestParam Integer attractionId, HttpSession session, Model model) {
		session.setAttribute("attractionId", attractionId);
		Result<AttractionDTO> attractionServiceResult = attractionService.getAttractionById(attractionId);
		
		if(attractionServiceResult.isFailure()) {
			return "";
		}
		
		model.addAttribute("attraction", attractionServiceResult.getData());
		return "/adminsystem/attraction/attraction-edit";
	}
	
	/**
	 * 模糊查詢
	 * @param 
	 * @param 
	 */
	@GetMapping("/select")
	private String select(Attraction attraction, Model model) {
		
		Result<List<Listable>> attractionServiceResult = attractionService.getAttractions(attraction);
		if(attractionServiceResult.isFailure()) {
			return "";
		}
		List<Listable> attractions = attractionServiceResult.getData();
		
		Map<String, Object> requestParameters = new HashMap<>();
		requestParameters.put("paramters", attraction);
		
		JsonUtil.setNonNull();
		String jsonData = JsonUtil.toJson(requestParameters);
		
		model.addAttribute("lists", attractions);
		model.addAttribute("requestParameters", jsonData);
		model.addAttribute("pageInfos", AttractionDTO.pageInfos);
		model.addAttribute("listInfos", AttractionDTO.listInfos);
		model.addAttribute("manageListName", AttractionDTO.manageListName);
		return "/adminsystem/index";	
	}
	
	/**
	 * 新增景點
	 * @param attraction
	 */
	@PostMapping("/create")
	private String create(Attraction attraction) {
		Result<Integer> result = attractionService.addAttraction(attraction);
		if (result.isFailure()) {
			return "";
		}
		return "redirect:/attraction";
		
	}

	/**
	 * 刪除景點
	 * @param attractionId
	 */
	@PostMapping("/delete")
	private void delete(@RequestParam Integer attractionId) {
		attractionService.removeAttraction(attractionId);
	}

	/**
	 * 更新景點
	 * @param attraction
	 * @param attractionId
	 */
	@PostMapping("/update")
	private String update(Attraction attraction, @SessionAttribute Integer attractionId) {
		attraction.setAttractionId(attractionId);
		Result<String> result = attractionService.updateAttraction(attraction);
		
		if(result.isFailure()) {
			return "";
		}
		return "redirect:/attraction";
	}

}
