package com.booking.controller.booking;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.dto.booking.RoomDTO;
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
		Result<Page<RoomDTO>> findRoomAllResult = roomService.findRoomAll(roomDTO);
		
		if(findRoomAllResult.isFailure()) {
			return "";
		}
		Page<RoomDTO> page = findRoomAllResult.getData();
		model.addAttribute("room", roomDTO);
		model.addAttribute("page", page);
		return "/management-system/booking/room-list";
	}
	
	/**
	 * 轉去room的搜尋頁面
	 * @return
	 */
	@GetMapping("/select/page")
	private String sendSelectPage() {
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
	private String sendEditPage(@RequestParam Integer roomId, Model model, HttpSession session) {
		Result<RoomDTO> findRoomByIdResult = roomService.findRoomById(roomId);
		if(findRoomByIdResult.isFailure()) {
			return "";
		}
		
		RoomDTO room = findRoomByIdResult.getData();
		
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
			@RequestParam(value = "roomName", defaultValue = "") String roomtypeName,
			@RequestParam(value="roomStatus", required = false) List<Integer> roomStatusAll,
			RoomDTO roomDTO,
			Model model
	) {
		roomDTO.setRoomtypeName(roomtypeName);
		Result<PageImpl<RoomDTO>> findRoomsResult = roomService.findRooms(roomDTO, roomStatusAll);
		
		if(findRoomsResult.isFailure()) {
			return "";
		}
		
		PageImpl<RoomDTO> page = findRoomsResult.getData();
		
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
