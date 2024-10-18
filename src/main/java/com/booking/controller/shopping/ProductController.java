package com.booking.controller.shopping;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.service.shopping.ProductService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/management/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	/**
	 * 首頁
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping
	public String index(Model model) {

		ProductDTO productDTO = new ProductDTO();
		Result<Page<ProductDTO>> results = productService.findProductAll(productDTO);

		if (results.isFailure()) {
			return "";
		}
		Page<ProductDTO> page = results.getData();
		model.addAttribute("productDTO", productDTO);
		model.addAttribute("page", page);
		return "/management-system/shopping/product-list";
	}

	@GetMapping("/select")
	private String findProducts(@RequestParam Map<String, String> requestParameters, ProductDTO productDTO,
			Model model) {

		Result<PageImpl<ProductDTO>> result = productService.findProducts(productDTO);

		if (result.isFailure()) {
			return "";
		}

		Page<ProductDTO> page = result.getData();
		model.addAttribute("requestParameters", requestParameters);
		model.addAttribute("product", productDTO);
		model.addAttribute("page", page);
		return "/management-system/shopping/product-list";
	}


	@PostMapping("/create")
	private String saveProduct(ProductDTO productDTO, @RequestParam(required = false) MultipartFile imageFile) {
		Result<String> result = productService.saveProduct(productDTO, imageFile);
		if (result.isFailure()) {
			return "";
		}
		return "redirect:/management/product";
	}

	@PostMapping("/delete")
	private ResponseEntity<?> deleteProduct(@RequestParam Integer productId) {
		Result<String> result = productService.deledeProductById(productId);
		String message = result.getMessage();
		if (result.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		return ResponseEntity.ok(message);
	}

	@PostMapping("/update")
	private String updateProductById(ProductDTO productDTO, @SessionAttribute Integer productId,
			@RequestParam(required = false) MultipartFile imageFile) {
		productDTO.setProductId(productId);
		Result<String> result = productService.updateProduct(productDTO, imageFile);
		if (result.isFailure()) {
			return "";
		}
		return "redirect:/management/product";
	}

	@PostMapping("/upload")
	public ResponseEntity<String> uploadImageById(@RequestParam MultipartFile imageFile, Integer attractionId) {
		Result<String> uploadImageResult = productService.uploadImageByProductId(imageFile, attractionId);
		String message = uploadImageResult.getMessage();
		if (uploadImageResult.isFailure()) {
			return ResponseEntity.badRequest().body(message);
		}
		return ResponseEntity.ok(message);
	}

	@GetMapping("/image/{productId}")
	public ResponseEntity<?> findImageById(@PathVariable Integer productId) throws IOException {
		Result<UrlResource> findImageByIdResult = productService.findImageByProductId(productId);

		if (findImageByIdResult.isFailure()) {
			return ResponseEntity.badRequest().body(findImageByIdResult.getMessage());
		}

		Path path = (Path) findImageByIdResult.getExtraData("path");
		UrlResource resource = findImageByIdResult.getData();

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path)).body(resource);
	}

	/**
	 * 前往查詢頁面 return
	 */
	@GetMapping("/select/page")
	private String sendSelectPage() {
		return "/management-system/shopping/product-select";
	}
	
	/**
	 * 前往建立頁面 return
	 */
	@GetMapping("/create/page")
	private String sendCreatePage() {
		return "/management-system/shopping/product-create";
	}
	
	/**
	 * 前往編輯頁面 return
	 */
	@GetMapping("/edit/page")
	private String sendOrderEditPage(@RequestParam Integer productId, HttpSession session, Model model) {
		
		session.setAttribute("productId", productId);
		
		Result<ProductDTO> result = productService.findProductDTOById(productId);
		if (result.isFailure()) {
			return "";
		}
		model.addAttribute("product", result.getData());
		return "/management-system/shopping/product-edit";
	}
}
