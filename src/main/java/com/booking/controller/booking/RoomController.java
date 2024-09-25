package com.booking.controller.booking;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.booking.bean.booking.Room;
import com.booking.dto.booking.RoomDTO;
import com.booking.service.booking.RoomService;
import com.booking.utils.JsonUtil;
import com.booking.utils.Listable;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpServlet;

@Controller
@RequestMapping("/room")
public class RoomController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Autowired
	private RoomService roomService;
	
	/**
	 * 房間首頁
	 * @param model
	 * @return
	 */
	@GetMapping("")
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
	 * 根據id返回room的json數據
	 * @param roomId
	 * @return
	 */
	@GetMapping("/json/{id}")
	@ResponseBody
	private String getRoomJSON(@PathVariable("id") Integer roomId) {
		Result<RoomDTO> roomServiceResult = roomService.getRoom(roomId);
		if(roomServiceResult.isFailure()) {
			return "";
		}
		
		JsonUtil.setNonNull();
		return JsonUtil.toJson(roomServiceResult.getData());	
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
	private String create(Room room) {
		LocalDateTime updatedTime = LocalDateTime.now();
		LocalDateTime createdTime = LocalDateTime.now();
		room.setCreatedTime(createdTime);
		room.setUpdatedTime(updatedTime);
		
		Result<Integer> roomServiceResult = roomService.addRoom(room);
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
	private String update(Room room) {	
		Result<String> roomServiceResult = roomService.updateRoom(room);
		if(roomServiceResult.isFailure()) {
			return "";
		}
		
		return "redirect:/room";
	}
}
