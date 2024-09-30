package com.booking.controller.booking;

import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.ResponseBody;
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
	private String sendRoomPage(
			@RequestParam Map<String, String> requestParameters,
			RoomDTO roomDTO,
			Model model
		) {
		
		Result<Page<RoomDTO>> roomServiceResult = roomService.findRoomAll(roomDTO);
		
		if(roomServiceResult.isFailure()) {
			return "";
		}
		Page<RoomDTO> page = roomServiceResult.getData();
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("roomDTO", roomDTO);
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
	 * @param session
	 * @return
	 */
	@GetMapping("/edit/page")
	private String sendEditPage(@RequestParam Integer roomId, Model model, HttpSession session) {
		Result<RoomDTO> roomServiceResult = roomService.getRoomById(roomId);
		if(roomServiceResult.isFailure()) {
			return "";
		}
		
		RoomDTO room = roomServiceResult.getData();
		
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
			RoomDTO roomDTO,
			Model model
		) {
		
		PageImpl<RoomDTO> page = roomService.findRooms(roomDTO);
		
		model.addAttribute("page", page);
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("roomDTO", roomDTO);
		return "/management-system/booking/room-list";
	}
	
	/**
	 * 創建房間
	 * @param room
	 * @param roomtypeName
	 * @return
	 */
	@PostMapping("/create")
	private String createRoom(Room room, @RequestParam String roomtypeName) {
		LocalDateTime updatedTime = LocalDateTime.now();
		LocalDateTime createdTime = LocalDateTime.now();
		room.setCreatedTime(createdTime);
		room.setUpdatedTime(updatedTime);
		
		Result<Integer> roomServiceResult = roomService.addRoom(room, roomtypeName);
		if(!roomServiceResult.isSuccess()) {
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
	@ResponseBody
	private String deleteById(@RequestParam Integer roomId) {
		Result<String> removeRoomtypeResult = roomService.removeRoom(roomId);	
		return removeRoomtypeResult.getMessage();
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
		Result<String> roomServiceResult = roomService.updateRoom(room);
		if(roomServiceResult.isFailure()) {
			return "";
		}
		
		return "redirect:/management/room";
	}
}
