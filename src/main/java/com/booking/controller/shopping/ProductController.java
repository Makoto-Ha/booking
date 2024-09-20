package com.booking.controller.shopping;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.booking.bean.shopping.Product;
import com.booking.service.shopping.ProductService;
import com.booking.utils.Result;

@Controller
public class ProductController {

	@Autowired
	private ProductService productService;

	@GetMapping("/product/create")
	public String sendCreate() {
		return "shopping/product-create"; // 返回視圖名稱
	}

	@PostMapping("/product/create")
	public String create(@RequestParam Integer categoryId, @RequestParam String productName,
			@RequestParam String productDescription, @RequestParam Integer productPrice,
			@RequestParam Integer productSales, @RequestParam Integer productInventory,
			@RequestParam Integer productState) {

		Product product = new Product(categoryId, productName, null, productDescription, productPrice, productSales,
				productInventory, productState);
		productService.addProduct(product);
		return "redirect:/product/selectAll"; // 重定向到商品列表
	}

	@GetMapping("/product/selectAll")
	public String selectAll(@RequestParam(required = false) String sortBy,
			@RequestParam(required = false) String sortOrder, Model model) {
		Result<List<Product>> productResult = productService.getAllProduct(sortBy, sortOrder);
		List<Product> productList = productResult.getData();
		model.addAttribute("products", productList);
		model.addAttribute("manageListName", "商城列表");
		return "shopping/product-select"; // 返回視圖名稱
	}

	@GetMapping("/product/selectName")
	public String selectName(@RequestParam("product-name") String productName,
			@RequestParam(required = false) String sortBy, @RequestParam(required = false) String sortOrder,
			Model model) {
		Result<List<Product>> productResult = productService.getProductByName(productName, sortBy, sortOrder);
		List<Product> productList = productResult.getData();
		model.addAttribute("products", productList);
		model.addAttribute("seleteName", productName);
		return "shopping/product-select"; // 返回視圖名稱
	}

	@GetMapping("/product/delete")
	public String delete(@RequestParam("product-id") Integer productId) {
		productService.removeProduct(productId);
		return "redirect:/product/selectAll"; // 重定向到商品列表
	}

	@GetMapping("/product/selectUpdate")
	public String selectUpdate(@RequestParam("product-id") Integer productId, Model model) {
		Result<Product> result = productService.getProductById(productId);
		Product product = result.getData();
		model.addAttribute("product", product);
		model.addAttribute("updateId", productId);
		return "shopping/product-update"; // 返回視圖名稱
	}

	@PostMapping("/product/update")
	public String update(@RequestParam Integer productId, @RequestParam Integer categoryId,
			@RequestParam String productName, @RequestParam String productDescription,
			@RequestParam Integer productPrice, @RequestParam Integer productSales,
			@RequestParam Integer productInventorey, @RequestParam Integer productState,
			@RequestParam String referrer) {

		Product product = new Product(productId, categoryId, productName, null, productDescription, productPrice,
				productSales, productInventorey, productState);
		productService.updateProduct(product);
		return "redirect:" + referrer; // 利用Referrer轉回原頁面
	}
}
