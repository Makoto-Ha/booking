package com.booking.controller.booking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.booking.bean.dto.booking.RoomtypeDTO;
import com.booking.bean.pojo.booking.Roomtype;
import com.booking.bean.pojo.common.Amenity;
import com.booking.service.booking.RoomtypeService;
import com.booking.service.common.AmenityService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/roomtype")
public class RoomtypeController {

	@Autowired
	private RoomtypeService roomtypeService;
	
	@Autowired
	private AmenityService amenityService;

	/**
	 * 轉到查詢
	 * 
	 * @return
	 */
	@GetMapping("/select/page")
	private String sendSelectPage(Model model) {
		List<Amenity> amenities = amenityService.findAll();
		model.addAttribute("amenities", amenities);
		return "/management-system/booking/roomtype-select";
	}

	/**
	 * 房間類型管理首頁
	 * 
	 * @param switchPage
	 * @param model
	 * @return
	 */
	@GetMapping
	private String sendRoomtypePage(Model model) {
		// DTO用於分頁所需數據
		RoomtypeDTO roomtypeDTO = new RoomtypeDTO();
		Result<Page<RoomtypeDTO>> findRoomtypeAllResult = roomtypeService.findRoomtypeAll(roomtypeDTO);

		if (findRoomtypeAllResult.isFailure()) {
			return "";
		}

		Page<RoomtypeDTO> page = findRoomtypeAllResult.getData();

		model.addAttribute("roomtype", roomtypeDTO);
		model.addAttribute("page", page);
		return "management-system/booking/roomtype-list";
	}

	/**
	 * 轉去create.jsp
	 * 
	 * @return
	 */
	@GetMapping("/create/page")
	private String sendCreatePage(Model model) {
		List<Amenity> amenities = amenityService.findAll();
		model.addAttribute("amenities", amenities);
		return "management-system/booking/roomtype-create";
	}

	/**
	 * 轉去edit.jsp
	 * 
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

		RoomtypeDTO roomtypeDTO = findRoomtypeById.getData();
		Object allAmenities = findRoomtypeById.getExtraData("allAmenities");

		model.addAttribute("roomtype", roomtypeDTO);
		model.addAttribute("allAmenities", allAmenities);
		return "/management-system/booking/roomtype-edit";
	}

	/**
	 * 多重模糊查詢
	 * 
	 * @param requestParameters
	 * @param roomtypeDTO
	 * @param model
	 * @return
	 */
	@GetMapping("/select")
	private String findRoomtypes(
			@RequestParam Map<String, String> requestParameters,
			@RequestParam(value = "roomtypeCapacity", required = false) List<Integer> roomtypeCapacityAll,
			@RequestParam(required = false) List<Integer> amenitiesId,
			RoomtypeDTO roomtypeDTO, 
			Model model
	){
		
		List<Amenity> amenities = null;
		if(amenitiesId != null && amenitiesId.size() >= 1) {
			amenities = amenityService.findAmenitiesByIds(amenitiesId);
		}
		
		Result<PageImpl<RoomtypeDTO>> findRoomtypesResult = roomtypeService.findRoomtypes(roomtypeDTO, roomtypeCapacityAll, amenities);

		if (findRoomtypesResult.isFailure()) {
			return "";
		}

		Page<RoomtypeDTO> page = findRoomtypesResult.getData();

		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("roomtype", roomtypeDTO);
		model.addAttribute("page", page);
		return "/management-system/booking/roomtype-list";
	}

	/**
	 * 新建單筆房間類型
	 * 
	 * @param roomtype
	 * @return
	 */
	@PostMapping("/create")
	private String saveRoomtype(@RequestParam(required = false) MultipartFile imageFile, Roomtype roomtype, @RequestParam List<Integer> amenitiesId) {
		Result<String> saveRoomtypeResult = roomtypeService.saveRoomtype(imageFile, roomtype, amenitiesId);
		if (saveRoomtypeResult.isFailure()) {
			return "";
		}
		return "redirect:/management/roomtype";
	}

	/**
	 * 刪除房間類型
	 * 
	 * @param roomtypeId
	 */
	@PostMapping("/delete")
	private ResponseEntity<?> deleteById(@RequestParam Integer roomtypeId) {
		Result<String> deleteRoomtypeResult = roomtypeService.deleteRoomtypeById(roomtypeId);
		String message = deleteRoomtypeResult.getMessage();
		if (deleteRoomtypeResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}

		return ResponseEntity.ok(message);
	}

	/**
	 * 更新房間類型
	 * 
	 * @param roomtype
	 * @param roomtypeId
	 * @return
	 */
	@PostMapping("/update")
	private String updateById(
			@RequestParam(required = false) MultipartFile imageFile, 
			Roomtype roomtype,
			@RequestParam(required = false) List<Integer> amenitiesId,
			@SessionAttribute Integer roomtypeId
	) {
		roomtype.setRoomtypeId(roomtypeId);
		
		if(amenitiesId == null) {
			amenitiesId = new ArrayList<>();
		}
		
		Result<String> updateRoomtypeResult = roomtypeService.updateRoomtype(imageFile, roomtype, amenitiesId);

		if (updateRoomtypeResult.isFailure()) {
			return "";
		}
		return "redirect:/management/roomtype";
	}
	
	/**
	 * 根據RoomtypeId上傳圖片
	 * 
	 * @param imageFile
	 * @param roomtypeId
	 * @return
	 */
	@PostMapping("/upload")
	private ResponseEntity<String> uploadImageById(@RequestParam MultipartFile imageFile, Integer roomtypeId) {
		Result<String> uploadImageResult = roomtypeService.uploadImageById(imageFile, roomtypeId);
		String message = uploadImageResult.getMessage();
		if (uploadImageResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}

		return ResponseEntity.ok(message);
	}

	/**
	 * 根據roomtypeId獲取圖片
	 * 
	 * @param roomtypeId
	 * @return
	 * @throws IOException
	 */
	@GetMapping("/image/{roomtypeId}")
	private ResponseEntity<?> findImageById(@PathVariable Integer roomtypeId) throws IOException {
		Result<UrlResource> findImageByIdResult = roomtypeService.findImageById(roomtypeId);

		if (findImageByIdResult.isFailure()) {
			return ResponseEntity.badRequest().body(findImageByIdResult.getMessage());
		}

		Path path = (Path) findImageByIdResult.getExtraData("path");
		UrlResource resource = findImageByIdResult.getData();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path)).body(resource);
	}
	
	/**
	 * 上傳圖片到AWS3
	 * @param imageFile
	 * @param roomtypeId
	 * @param imgOriginalKey
	 * @return
	 */
	@PostMapping("/aws3/upload")
	@ResponseBody
	private String uploadImageByAWS(@RequestParam MultipartFile imageFile, Integer roomtypeId, @RequestParam(required = false) String imgOriginalKey) {
		Result<String> uploadImageByAWS = roomtypeService.uploadImageByAWS(imageFile, roomtypeId, imgOriginalKey);
		return uploadImageByAWS.getMessage();
	}
	
	/**
	 * 獲取AWS3的圖片
	 * @param roomtypeId
	 * @return
	 */
	@GetMapping("/aws3/{roomtypeId}")
	@ResponseBody
	private List<String> getImageListByAWS(@PathVariable Integer roomtypeId) {
		return roomtypeService.getImageListByAWS(roomtypeId);
	}
}
