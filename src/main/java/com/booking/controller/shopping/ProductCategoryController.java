package com.booking.controller.shopping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.booking.bean.dto.shopping.ProductCategoryDTO;
import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.service.shopping.ProductCategoryService;
import com.booking.service.shopping.ProductService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/productCategory")
public class ProductCategoryController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductCategoryService productCategoryService;

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
	private String sendOrderEditPage(@RequestParam Integer categoryId, HttpSession session, Model model) {

		return "/management-system/shopping/productCategory-edit";
	}

	@GetMapping("/select")
	private String findProductCategorys() {
		
		return "/management-system/shopping/productCategory-list";
	}
	
	@PostMapping("/create")
	private String saveProductCategory(ProductDTO productDTO) {

		return "redirect:/management/productCategory";
	}

	@PostMapping("/delete")
	private ResponseEntity<?> deleteProductCategory(@RequestParam Integer productId) {

		return ResponseEntity.ok("test");
	}

	@PostMapping("/update")
	private String updateProductCategoryById(ProductDTO productDTO, @SessionAttribute Integer productId) {

		return "redirect:/management/productCategory";
	}

}
