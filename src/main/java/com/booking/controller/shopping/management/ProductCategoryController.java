package com.booking.controller.shopping.management;


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

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.service.shopping.management.ProductCategoryService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/productCategory")
public class ProductCategoryController {

	@Autowired
	public ProductCategoryService productCategoryService;

	/**
	 * 首頁
	 * 
	 * @param model
	 * @return
	 */

	@GetMapping
	public String index(Model model) {


		ProductCategoryDTO productCategoryDTO = new ProductCategoryDTO();
		Result<Page<ProductCategoryDTO>> result = productCategoryService.findProductCategoryAll(productCategoryDTO);

		model.addAttribute("productCategoryDTO", productCategoryDTO);
		model.addAttribute("page", result.getData());
		return "/management-system/shopping/productCategory-list";
	}


	// -----CRUD------------------------------------------------------------

	/**
	 * 
	 * @param requestParameters
	 * @param productCategoryDTO
	 * @param model
	 * @return
	 */

	@GetMapping("/select")
	private String findProductCategorys(@RequestParam Map<String, String> requestParameters,
			ProductCategoryDTO productCategoryDTO, Model model) {

		Result<PageImpl<ProductCategoryDTO>> result = productCategoryService.findCategorys(productCategoryDTO);
		if (result.isFailure()) {
			return "";
		}
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("productCategoryDTO", productCategoryDTO);
		model.addAttribute("page", result.getData());

		return "/management-system/shopping/productCategory-list";
	}

	/**
	 * 
	 * @param productCategoryDTO
	 * @return
	 */

	@PostMapping("/create")
	private String saveProductCategory(ProductCategoryDTO productCategoryDTO) {

		if (productCategoryDTO.getCategoryDescription() == null
				|| productCategoryDTO.getCategoryDescription().isEmpty()) {
			productCategoryDTO.setCategoryDescription("未輸入");
		}
		Result<String> result = productCategoryService.saveProductCategory(productCategoryDTO);
		if (result.isFailure()) {
			return "";
		}
		return "redirect:/management/productCategory";
	}

	/**
	 * 
	 * @param categoryId
	 * @return
	 */

	@PostMapping("/delete")
	private ResponseEntity<?> deleteProductCategory(@RequestParam Integer productCategoryId) {
		Result<String> result = productCategoryService.deleteProductCategoryById(productCategoryId);
		String message = result.getMessage();
		if (result.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		return ResponseEntity.ok(message);
	}

	@PostMapping("/update")
	private String updateProductCategoryById(ProductCategoryDTO productCategoryDTO,
			@SessionAttribute Integer categoryId) {
		
		productCategoryDTO.setCategoryId(categoryId);
		Result<String> result = productCategoryService.updateProductCategory(productCategoryDTO);
		if (result.isFailure()) {
			return "";
		}
		return "redirect:/management/productCategory";
	}

	// ---------------------------------------------------------------------

	/**
	 * 前往查詢頁面 return
	 */
	@GetMapping("/select/page")
	private String sendSelectPage() {
		return "/management-system/shopping/productCategory-select";
	}

	/**
	 * 前往建立頁面 return
	 */
	@GetMapping("/create/page")
	private String sendCreatePage() {
		return "/management-system/shopping/productCategory-create";
	}

	/**
	 * 前往編輯頁面 return
	 */
	@GetMapping("/edit/page")
	private String sendOrderEditPage(@RequestParam Integer productCategoryId, HttpSession session, Model model) {
		session.setAttribute("categoryId", productCategoryId);
		Result<ProductCategoryDTO> result = productCategoryService.findCategoryDTOById(productCategoryId);
		if (result.isFailure()) {
			return "";
		}
		model.addAttribute("productCategoryDTO",result.getData());
		return "/management-system/shopping/productCategory-edit";
	}

}
