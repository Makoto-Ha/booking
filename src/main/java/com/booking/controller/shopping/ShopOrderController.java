package com.booking.controller.shopping;

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

import com.booking.bean.dto.shopping.ShopOrderDTO;
import com.booking.service.shopping.ShopOrderService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/shopOrder")
public class ShopOrderController {

	@Autowired
	private ShopOrderService shopOrderService;

	/**
	 * 首頁
	 * 
	 * @param model
	 * @return
	 */

	@GetMapping
	public String index(Model model) {
		ShopOrderDTO shopOrderDTO = new ShopOrderDTO();
		Result<Page<ShopOrderDTO>> result = shopOrderService.findOrderAll(shopOrderDTO);

		model.addAttribute("shopOrderDTO", shopOrderDTO);
		model.addAttribute("page", result.getData());
		return "/management-system/shopping/shopOrder-list";
	}

	// -----CRUD------------------------------------------------------------

	@GetMapping("/select")
	private String findShopOrders(@RequestParam Map<String, String> requestParameters, ShopOrderDTO shopOrderDTO,
			Model model) {

		Result<PageImpl<ShopOrderDTO>> result = shopOrderService.findOrders(shopOrderDTO);
		if (result.isFailure()) {
			return "";
		}
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("shopOrderDTO", shopOrderDTO);
		model.addAttribute("page", result.getData());
		return "/management-system/shopping/shopOrder-list";
	}

	@PostMapping("/create")
	private String saveShopOrder(ShopOrderDTO shopOrderDTO) {

		if (shopOrderDTO.getOrderState() == null) {
			shopOrderDTO.setOrderState(1);
		}
		if (shopOrderDTO.getPaymentState() == null) {
			shopOrderDTO.setPaymentState(1);
		}
		System.out.println("shopOrderDTO: " + shopOrderDTO);
		Result<String> result = shopOrderService.saveOrder(shopOrderDTO);
		if (result.isFailure()) {
			return "";
		}
		return "redirect:/management/shopOrder";
	}

	@PostMapping("/delete")
	private ResponseEntity<?> deleteShopOrder(@RequestParam Integer shopOrderId) {
		Result<String> message = shopOrderService.deleteOrderById(shopOrderId);
		if (message.isFailure()) {
			return ResponseEntity.badRequest().body(message.getMessage());
		}
		return ResponseEntity.ok(message.getMessage());
	}

	@PostMapping("/update")
	private String updateShopOrderById(ShopOrderDTO shopOrderDTO, @SessionAttribute Integer shopOrderId) {
		shopOrderDTO.setOrderId(shopOrderId);
		Result<String> result = shopOrderService.updateOrder(shopOrderDTO);
		if (result.isFailure()) {
			return "";
		}
		return "redirect:/management/shopOrder";
	}

	// ---------------------------------------------------------------------

	/**
	 * 前往查詢頁面 return
	 */
	@GetMapping("/select/page")
	private String sendSelectPage() {
		return "/management-system/shopping/shopOrder-select";
	}

	/**
	 * 前往建立頁面 return
	 */
	@GetMapping("/create/page")
	private String sendCreatePage() {
		return "/management-system/shopping/shopOrder-create";
	}

	/**
	 * 前往編輯頁面 return
	 */
	@GetMapping("/edit/page")
	private String sendOrderEditPage(@RequestParam Integer shopOrderId, HttpSession session, Model model) {
		session.setAttribute("shopOrderId", shopOrderId);
		Result<ShopOrderDTO> result = shopOrderService.findOrderDTOById(shopOrderId);
		if (result.isFailure()) {
			return "";
		}
		model.addAttribute("shopOrderDTO", result.getData());
		return "/management-system/shopping/shopOrder-edit";
	}

}
