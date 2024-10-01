package com.booking.controller.booking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
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
	private String sendRoomtypePage(
			@RequestParam Map<String, String> requestParameters,
			RoomtypeDTO roomtypeDTO,
			Model model
	) {

		Result<PageImpl<RoomtypeDTO>> findRoomtypeAllResult = roomtypeService.findRoomtypeAll(roomtypeDTO);
		
		if (findRoomtypeAllResult.isFailure()) {
			return "";
		}

		Page<RoomtypeDTO> page = findRoomtypeAllResult.getData();	
		
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("roomtypeDTO", roomtypeDTO);
		model.addAttribute("page", page);
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
		Result<RoomtypeDTO> findRoomtypeById = roomtypeService.findRoomtypeById(roomtypeId);

		if (findRoomtypeById.isFailure()) {
			return "";
		}

		model.addAttribute("roomtype", findRoomtypeById.getData());
		return "/management-system/booking/roomtype-edit";
	}

	/**
	 * 多重模糊查詢
	 * @param requestParameters
	 * @param roomtypeDTO
	 * @param model
	 * @return
	 */
	@GetMapping("/select")
	private String findRoomtypes(
			@RequestParam Map<String, String> requestParameters,
			RoomtypeDTO roomtypeDTO,
			Model model
	) {

		Result<PageImpl<RoomtypeDTO>> findRoomtypesResult = roomtypeService.findRoomtypes(roomtypeDTO);
		
		if(findRoomtypesResult.isFailure()) {
			return "";
		}
		
		Page<RoomtypeDTO> page = findRoomtypesResult.getData();
		
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("roomtypeDTO", roomtypeDTO);
		model.addAttribute("page", page);
		return "/management-system/booking/roomtype-list";
	}

	/**
	 * 新建單筆房間類型
	 * @param roomtype
	 * @return
	 */
	@PostMapping("/create")
	private String saveRoomtype(@RequestParam(required = false) MultipartFile imageFile, Roomtype roomtype) {
		LocalDateTime now = LocalDateTime.now();
		roomtype.setCreatedTime(now);
		roomtype.setUpdatedTime(now);

		Result<String> saveRoomtypeResult = roomtypeService.saveRoomtype(imageFile, roomtype);
		if (saveRoomtypeResult.isFailure()) {
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
		roomtypeService.deleteRoomtypeById(roomtypeId);
	}

	/**
	 * 更新房間類型
	 * @param roomtype
	 * @param roomtypeId
	 * @return
	 */
	@PostMapping("/update")
	private String updateById(
			@RequestParam(required = false) MultipartFile imageFile, 
			Roomtype roomtype, 
			@SessionAttribute Integer roomtypeId
	) {
		roomtype.setRoomtypeId(roomtypeId);
		Result<String> updateRoomtypeResult = roomtypeService.updateRoomtype(imageFile, roomtype);

		if (updateRoomtypeResult.isFailure()) {
			return "";
		}
		return "redirect:/management/roomtype";
	}
	
	/**
	 * 根據RoomtypeId上傳圖片
	 * @param imageFile
	 * @param roomtypeId
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseEntity<String> uploadImageById(@RequestParam MultipartFile imageFile, Integer roomtypeId) {
		Result<String> uploadImageResult = roomtypeService.uploadImageById(imageFile, roomtypeId);
		String message = uploadImageResult.getMessage();
		if(uploadImageResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		
		return ResponseEntity.ok(message);
	}
	
	/**
	 * 根據roomtypeId獲取圖片
	 * @param roomtypeId
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/image/{roomtypeId}")
	public ResponseEntity<?> findImageById(@PathVariable Integer roomtypeId) throws IOException {
		Result<UrlResource> findImageByIdResult = roomtypeService.findImageById(roomtypeId);
		
		if(findImageByIdResult.isFailure()) {
			return ResponseEntity.badRequest().body(findImageByIdResult.getMessage());
		}
		
		Path path = (Path) findImageByIdResult.getExtraData("path");
		UrlResource resource = findImageByIdResult.getData();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
				.body(resource);
				
	}
}
