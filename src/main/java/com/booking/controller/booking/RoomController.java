package com.booking.controller.booking;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.dto.booking.RoomDTO;
import com.booking.bean.dto.booking.RoomDetailDTO;
import com.booking.bean.pojo.booking.Room;
import com.booking.service.booking.RoomService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/room")
public class RoomController {
	@Autowired
	private RoomService roomService;
	
	/**
	 * 房間首頁
	 * @param requestParameters
	 * @param roomDTO
	 * @param model
	 * @return
	 */
	@GetMapping
	private String sendRoomPage(Model model) {
		// DTO用於分頁所需數據
		RoomDTO roomDTO = new RoomDTO();
		Result<Page<RoomDetailDTO>> findRoomAllResult = roomService.findRoomAll(roomDTO);
		
		if(findRoomAllResult.isFailure()) {
			return "";
		}
		Page<RoomDetailDTO> page = findRoomAllResult.getData();
		model.addAttribute("room", roomDTO);
		model.addAttribute("page", page);
		return "/management-system/booking/room-list";
	}
	
	/**
	 * 根據roomtypeId獲得Room
	 * @param roomtypeId
	 * @param model
	 * @return
	 */
	@GetMapping("/roomtype/{roomtypeId}")
	private String sendRoomPageByRoomtypeId(@PathVariable Integer roomtypeId, Model model) {
		// DTO用於分頁所需數據
		RoomDTO roomDTO = new RoomDTO();
		Result<Page<RoomDetailDTO>> findRoomsByRoomtypeId = roomService.findRoomsByRoomtypeId(roomtypeId, roomDTO);
		if(findRoomsByRoomtypeId.isFailure()) {
			return "";
		}
		
		Page<RoomDetailDTO> page = findRoomsByRoomtypeId.getData();
		model.addAttribute("page", page);
		model.addAttribute("room", roomDTO);
		return "/management-system/booking/room-list";
	}
	
	/**
	 * 轉去room的搜尋頁面
	 * @return
	 */
	@GetMapping("/select/page")
	private String sendSelectPage(Model model) {
		RoomDTO roomDTO = new RoomDTO();
		
		roomDTO.setBookingDate(LocalDate.now());
		
		model.addAttribute("room", roomDTO);
		
		return "/management-system/booking/room-select";
	}
	
	/**
	 * 轉去room的新增頁面
	 * @return
	 */
	@GetMapping("/create/page")
	private String sendCreatePage() {
		return "/management-system/booking/room-create";
	}
	
	/**
	 * 轉去room的編輯頁面
	 * @param roomId
	 * @param model
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/edit/page")
	private String sendEditPage(@RequestParam Integer roomId, Model model, HttpSession session, RoomDTO roomDTO) {
		Result<RoomDetailDTO> findRoomByIdResult = roomService.findRoomById(roomId,roomDTO);
		if(findRoomByIdResult.isFailure()) {
			return "";
		}
		
		RoomDetailDTO room = findRoomByIdResult.getData();
		
		session.setAttribute("roomId", room.getRoomId());
		
		model.addAttribute("room", room);
		
		return "/management-system/booking/room-edit";
	}
	
	/**
	 * 模糊查詢
	 * @param requestParameters
	 * @param roomDTO
	 * @param model
	 * @return
	 */
	@GetMapping("/select")
	private String findRooms(
			@RequestParam Map<String, String> requestParameters,
			// 因為index.js是看網址的類型，也就是/booking/management/room會發送roomName
			@RequestParam(defaultValue = "") String roomName,
			@RequestParam(required = false) Integer bookingStatus,
			@RequestParam(required = false) Integer availableRooms,
			RoomDTO roomDTO,
			Model model
	) {
		roomDTO.setRoomtypeName(roomName);
		Result<Page<RoomDetailDTO>> findRoomsResult = roomService.findRooms(roomDTO, bookingStatus, availableRooms);
		
		if(findRoomsResult.isFailure()) {
			return "";
		}
		
		Page<RoomDetailDTO> page = findRoomsResult.getData();
		
		model.addAttribute("page", page);
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("room", roomDTO);
		return "/management-system/booking/room-list";
	}
	 
	/**
	 * 創建房間
	 * @param room
	 * @param roomtypeName
	 * @return
	 */
	@PostMapping("/create")
	private String saveRoomByRoomtypeName(Room room, @RequestParam String roomtypeName) {
		Result<Integer> saveRoomsByRoomtypeNameResult = roomService.saveRoomsByRoomtypeName(room, roomtypeName);
		if(saveRoomsByRoomtypeNameResult.isFailure()) {
			return "";
		}
		
		return "redirect:/management/room";
	}
	
	/**
	 * 刪除房間
	 * @param roomId
	 * @return
	 */
	@PostMapping("/delete")
	private ResponseEntity<?> deleteById(@RequestParam Integer roomId) {
		Result<String> removeRoomByIdResult = roomService.removeRoomById(roomId);	
		String message = removeRoomByIdResult.getMessage();
		if(removeRoomByIdResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		
		return ResponseEntity.ok(message);
	}

	/**
	 * 更新房間
	 * @param room
	 * @param roomId
	 * @return
	 */
	@PostMapping("/update")
	private String updateById(Room room, @SessionAttribute Integer roomId) {
		room.setRoomId(roomId);
		Result<String> updateRoomResult = roomService.updateRoom(room);
		if(updateRoomResult.isFailure()) {
			return "";
		}
		
		return "redirect:/management/room";
	}
}
