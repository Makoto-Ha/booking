package com.booking.controller.booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.booking.Room;
import com.booking.dto.booking.RoomDTO;
import com.booking.service.booking.RoomService;
import com.booking.utils.Listable;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/room")
public class RoomController {
	@Autowired
	private RoomService roomService;
	
	/**
	 * 房間首頁
	 * @param model
	 * @return
	 */
	@GetMapping
	private String room(Model model) {
		Result<List<Listable>> roomServiceResult = roomService.getRoomAll();
		if(roomServiceResult.isFailure()) {
			return "";
		}
		List<Listable> rooms = roomServiceResult.getData();
		model.addAttribute("lists", rooms);
		model.addAttribute("pageInfos", RoomDTO.pageInfos);
		model.addAttribute("listInfos", RoomDTO.listInfos);
		return "adminsystem/index";
	}
	
	/**
	 * 轉去room的新增頁面
	 * @return
	 */
	@GetMapping("/create/jsp")
	private String sendCreateJsp() {
		return "/adminsystem/booking/room-create";
	}
	
	/**
	 * 轉去room的編輯頁面
	 * @param roomId
	 * @param model
	 * @return
	 */
	@GetMapping("/edit/jsp")
	private String sendEditJsp(@RequestParam Integer roomId, Model model, HttpSession session) {
		Result<RoomDTO> roomServiceResult = roomService.getRoomById(roomId);
		if(roomServiceResult.isFailure()) {
			return "";
		}
		
		RoomDTO room = roomServiceResult.getData();
		
		session.setAttribute("roomId", room.getId());
		
		model.addAttribute("room", room);
		
		return "/adminsystem/booking/room-edit";
	}
	
	/**
	 * 查找多筆房間
	 */
	@GetMapping("/select")
	private void select() {
		roomService.getRooms(1);
	}
	
	/**
	 * 創建房間
	 * @param room
	 * @return
	 */
	@PostMapping("/create")
	private String create(Room room, @RequestParam String roomtypeName) {
		LocalDateTime updatedTime = LocalDateTime.now();
		LocalDateTime createdTime = LocalDateTime.now();
		room.setCreatedTime(createdTime);
		room.setUpdatedTime(updatedTime);
		
		Result<Integer> roomServiceResult = roomService.addRoom(room, roomtypeName);
		if(!roomServiceResult.isSuccess()) {
			return "";
		}
		
		return "redirect:/room";
	}
	
	/**
	 * 刪除房間
	 * @param roomId
	 * @return
	 */
	@PostMapping("/delete")
	@ResponseBody
	private String delete(@RequestParam Integer roomId) {
		Result<String> removeRoomtypeResult = roomService.removeRoom(roomId);	
		return removeRoomtypeResult.getMessage();
	}

	/**
	 * 更新房間
	 * @param room
	 * @return
	 */
	@PostMapping("/update")
	private String update(Room room, @SessionAttribute Integer roomId) {
		room.setRoomId(roomId);
		Result<String> roomServiceResult = roomService.updateRoom(room);
		if(roomServiceResult.isFailure()) {
			return "";
		}
		
		return "redirect:/room";
	}
	
//	@GetMapping("/select/{name}")
//	private String selectByName(@PathVariable String name) {
//		
//	}
}
