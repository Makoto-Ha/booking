package com.booking.controller.shopping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.booking.bean.pojo.shopping.Product;
import com.booking.service.shopping.ProductService;
import com.booking.utils.Result;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/management/product")
	public String index() {
		return "/management-system/shopping/product-select"; // 返回視圖名稱
	}

	@GetMapping("/product/sendCreate")
	public String sendCreate() {
		return "/management-system/shopping/product-create"; // 返回視圖名稱
	}

	@PostMapping("/product/create")
	public String create(@RequestParam Integer categoryId, @RequestParam String productName,
			@RequestParam String productDescription, @RequestParam Integer productPrice,
			@RequestParam Integer productSales, @RequestParam Integer productInventorey,
			@RequestParam Integer productState) {

		Product product = new Product(categoryId, productName, null, productDescription, productPrice, productSales,
				productInventorey, productState);
		productService.addProduct(product);

		return "redirect:/product/select"; // 重定向到商品列表
	}

	@GetMapping("/product/select")
	public String selectName(@RequestParam(required = false) String searchName,
			@RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortOrder,
			HttpSession session, Model model) {

		Result<List<Product>> productResult;

		if (searchName == null || searchName.trim().isEmpty()) {
			productResult = productService.getAllProduct(sortBy, sortOrder);
		} else {
			productResult = productService.getProductByName(searchName, sortBy, sortOrder);
		}

		List<Product> productList = productResult.getData();
		
		model.addAttribute("products", productList);
		model.addAttribute("searchName", searchName);
		model.addAttribute("sortBy", sortBy); // 添加排序依據
		model.addAttribute("sortOrder", sortOrder); // 添加排序順序
		
		return "/management-system/shopping/product-select"; // 返回視圖名稱
	}

	@GetMapping("/product/delete")
	public String delete(@RequestParam Integer productId) {
		productService.removeProduct(productId);
		return "redirect:/product/select"; // 重定向到商品列表
	}

	@GetMapping("/product/selectUpdate")
	public String selectUpdate(@RequestParam Integer productId,
			@RequestParam(required = false) String searchName,
			@RequestParam(required = false) String sortBy, 
			@RequestParam(required = false) String sortOrder,
			Model model) {
		
		Result<Product> result = productService.getProductById(productId);
		Product product = result.getData();
		model.addAttribute("product", product);
		model.addAttribute("productId", productId);
		model.addAttribute("searchName", searchName); // 加入 searchName
		model.addAttribute("sortBy", sortBy); // 加入 sortBy
		model.addAttribute("sortOrder", sortOrder); // 加入 sortOrder
		return "/management-system/shopping/product-update"; // 返回視圖名稱
	}

	@PostMapping("/product/update")
	public String update(Product product,
			@RequestParam(required = false) String searchName,
			@RequestParam(required = false) String sortBy, 
			@RequestParam(required = false) String sortOrder,
			RedirectAttributes redirectAttributes) {
		
		productService.updateProduct(product);

		redirectAttributes.addAttribute("sortBy", sortBy);
		redirectAttributes.addAttribute("sortOrder", sortOrder);
		redirectAttributes.addAttribute("searchName", searchName);
		return "redirect:/product/select";
	}
}
