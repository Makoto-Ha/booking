package com.booking.controller.booking;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.service.booking.RoomtypeService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/roomtype")
public class RoomtypeController  {
	
	@Autowired
	private RoomtypeService roomtypeService;
	
	/**
	 * 轉到查詢
	 * @return
	 */
	@GetMapping("/select/page")
	private String sendSelectPage() {
		return "/management-system/booking/roomtype-select";
	}

	/**
	 * 房間類型管理首頁
	 * @param switchPage
	 * @param model
	 * @return
	 */
	@GetMapping
	private String sendRoomtypePage(@RequestParam(required = false, defaultValue = "1") Integer switchPage, Model model) {

		Result<List<RoomtypeDTO>> roomtypeServiceResult = roomtypeService.getRoomtypeAll(switchPage);
		if (roomtypeServiceResult.isFailure()) {
			return "";
		}

		List<RoomtypeDTO> roomtypes = roomtypeServiceResult.getData();
		Map<String, Long> pageNumber = new HashMap<>();
		pageNumber.put("currentPage",  switchPage.longValue());
		
		long totalCounts = roomtypes.isEmpty() ? 0 : roomtypes.get(0).getTotalCounts();
		int pageSize = 10; // 每頁顯示的記錄數量
		long totalPages = (totalCounts + pageSize - 1) / pageSize; // 向上取整的頁數計算
		
		pageNumber.put("totalPages", totalPages);
		model.addAttribute("roomtypes", roomtypes);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("attrOrderBy", "roomtypePrice");
		return "management-system/booking/roomtype-list";
	}

	/**
	 * 轉去create.jsp
	 * @return
	 */
	@GetMapping("/create/page")
	private String sendCreatePage() {
		return "management-system/booking/roomtype-create";
	}

	/**
	 * 轉去edit.jsp
	 * @param roomtypeId
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/page")
	private String sendEditPage(@RequestParam Integer roomtypeId, HttpSession session, Model model) {
		session.setAttribute("roomtypeId", roomtypeId);
		Result<RoomtypeDTO> roomtypeServiceResult = roomtypeService.getRoomtype(roomtypeId);

		if (roomtypeServiceResult.isFailure()) {
			return "";
		}

		model.addAttribute("roomtype", roomtypeServiceResult.getData());
		return "/management-system/booking/roomtype-edit";
	}

	/**
	 * 多重模糊查詢
	 * @param roomtype
	 * @param switchPage
	 * @param maxMoney
	 * @param minMoney
	 * @param attrOrderBy
	 * @param selectedSort
	 * @param model
	 * @return
	 */
	@GetMapping("/select")
	private String findRoomtypes(
			Roomtype roomtype,
			@RequestParam(required = false, defaultValue = "1") Integer switchPage,
			@RequestParam(required = false, defaultValue = "0") Integer maxMoney,
			@RequestParam(required = false, defaultValue = "0") Integer minMoney,
			@RequestParam(required = false, defaultValue = "roomtypePrice") String attrOrderBy,
			@RequestParam(required = false, defaultValue = "asc") String selectedSort,
			Model model
	) {
		if(maxMoney == 0 && minMoney == 0) {
			maxMoney = null;
			minMoney = null;
		}
		
		Map<String, Object> extraValues = new HashMap<>();
		extraValues.put("maxMoney", maxMoney);
		extraValues.put("minMoney", minMoney);
		extraValues.put("switchPage", switchPage);
		extraValues.put("attrOrderBy", attrOrderBy);
		extraValues.put("selectedSort", selectedSort);
		
		Result<List<RoomtypeDTO>> roomtypeServiceResult = roomtypeService.getRoomtypes(roomtype, extraValues);
		
		if(roomtypeServiceResult.isFailure()) {
			return "";
		}
		
		List<RoomtypeDTO> roomtypes = roomtypeServiceResult.getData();
		
		Map<String, Object> requestParameters = new HashMap<>();
		requestParameters.put("paramters", roomtype);
		requestParameters.put("extraValues", extraValues);
		
		Map<String, Long> pageNumber = new HashMap<>();
		pageNumber.put("currentPage", switchPage.longValue());
		
		long totalCounts = roomtypes.isEmpty() ? 0 : roomtypes.get(0).getTotalCounts();
		long totalPages = (totalCounts + 10 - 1) / 10; // 向上取整的頁數計算
		pageNumber.put("totalPages", totalPages);
		model.addAttribute("attrOrderBy", attrOrderBy);
		model.addAttribute("selectedSort", selectedSort);
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("pageNumber", pageNumber);
		model.addAttribute("roomtypes", roomtypes);
		return "/management-system/booking/roomtype-list";
	}

	/**
	 * 新建單筆房間類型
	 * @param roomtype
	 * @return
	 */
	@PostMapping("/create")
	private String createRoomtype(Roomtype roomtype) {
		LocalDateTime now = LocalDateTime.now();
		roomtype.setCreatedTime(now);
		roomtype.setUpdatedTime(now);

		Result<Integer> result = roomtypeService.addRoomtype(roomtype);
		if (result.isFailure()) {
			return "";
		}
		return "redirect:/management/roomtype";
	}

	/**
	 * 刪除房間類型
	 * @param roomtypeId
	 */
	@PostMapping("/delete")
	private void deleteById(@RequestParam Integer roomtypeId) {
		roomtypeService.removeRoomtype(roomtypeId);
	}

	/**
	 * 更新房間類型
	 * @param roomtype
	 * @param roomtypeId
	 * @return
	 */
	@PostMapping("/update")
	private String updateById(Roomtype roomtype, @SessionAttribute Integer roomtypeId) {
		roomtype.setRoomtypeId(roomtypeId);
		Result<String> result = roomtypeService.updateRoomtype(roomtype);

		if (result.isFailure()) {
			return "";
		}
		return "redirect:/management/roomtype";
	}

}
