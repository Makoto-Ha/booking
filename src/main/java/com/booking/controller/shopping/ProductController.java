package com.booking.controller.shopping;

import java.io.IOException;
import java.util.List;

import org.hibernate.Session;

import com.booking.bean.shopping.Product;
import com.booking.service.shopping.ProductService;
import com.booking.utils.HibernateUtil;
import com.booking.utils.Result;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/product/create", "/product/delete", "/product/update", "/product/selectUpdate",
		"/product/selectName", "/product/selectAll", "/product","/product/sendCreate" })
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductService productService;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String[] splitURI = requestURI.split("/");
		String path = splitURI[splitURI.length - 1];
		
		Session session=(Session) request.getAttribute("hibernateSession");
		productService = new ProductService(session);
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		request.setAttribute("manageListName", "商城列表");
		
		switch (path) {
		case "create" -> create(request, response);
		case "sendCreate" -> sendCreate(request, response);
		case "delete" -> delete(request, response);
		case "update" -> update(request, response);
		case "selectUpdate" -> selectUpdate(request, response);
		case "selectName" -> selectName(request, response);
		case "selectAll" -> selectAll(request, response);
		case "product" -> sendProductIndex(request, response);

		}
	}

	private void sendProductIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getRequestDispatcher("/adminsystem/shopping/product-select.jsp").forward(request, response);
	}

	private void selectName(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String productName = request.getParameter("product-name");
		String sortBy = request.getParameter("sortBy");
		String sortOrder = request.getParameter("sortOrder");

		Result<List<Product>> productResult = productService.getProductByName(productName, sortBy, sortOrder);
		List<Product> productList = productResult.getData();
		request.setAttribute("products", productList);
		request.setAttribute("seleteName", productName);
		request.getRequestDispatcher("/adminsystem/shopping/product-select.jsp").forward(request, response);

	}

	private void selectAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String sortBy = request.getParameter("sortBy");
		String sortOrder = request.getParameter("sortOrder");

		Result<List<Product>> productResult = productService.getAllProduct(sortBy, sortOrder);
		List<Product> productList = productResult.getData();
		request.setAttribute("products", productList);
		request.getRequestDispatcher("/adminsystem/shopping/product-select.jsp").forward(request, response);

	}
	private void sendCreate(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.getRequestDispatcher("/adminsystem/shopping/product-create.jsp").forward(request, response);
	}
	private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Integer categoryId = Integer.parseInt(request.getParameter("category-id"));
		String productName = request.getParameter("product-name");
		String productDescription = request.getParameter("product-description");
		Integer productPrice = Integer.parseInt(request.getParameter("product-price"));
		Integer productSales = Integer.parseInt(request.getParameter("product-sales"));
		Integer productInventorey = Integer.parseInt(request.getParameter("product-inventorey"));
		Integer productState = Integer.parseInt(request.getParameter("product-state"));
		// 缺圖片
		Product product = new Product(categoryId, productName, null, productDescription, productPrice, productSales,
				productInventorey, productState);

		productService.addProduct(product);
		response.sendRedirect(request.getContextPath() + "/product/selectAll");
	}


	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer productId = Integer.parseInt(request.getParameter("product-id"));
		Result<Product> result = productService.getProductById(productId);
		Product product = result.getData();
		productService.removeProduct(productId);
		
		request.setAttribute("product", product);
		request.setAttribute("deleteId", productId);
		response.sendRedirect(request.getContextPath() + "/product/selectAll");

	}

	private void selectUpdate(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		Integer productId = Integer.parseInt(request.getParameter("product-id"));
		Result<Product> result = productService.getProductById(productId);
		Product product = result.getData();

		request.setAttribute("product", product);
		request.setAttribute("updateId", productId);
		request.getRequestDispatcher("/adminsystem/shopping/product-update.jsp").forward(request, response);
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		Integer productId = Integer.parseInt(request.getParameter("product-id"));
		Integer categoryId = Integer.parseInt(request.getParameter("category-id"));
		String productName = request.getParameter("product-name");
		String productDescription = request.getParameter("product-description");
		Integer productPrice = Integer.parseInt(request.getParameter("product-price"));
		Integer productSales = Integer.parseInt(request.getParameter("product-sales"));
		Integer productInventorey = Integer.parseInt(request.getParameter("product-inventorey"));
		Integer productState = Integer.parseInt(request.getParameter("product-state"));
		// 缺圖片
		Product product = new Product(productId, categoryId, productName, null, productDescription, productPrice,
				productSales, productInventorey, productState);

		productService.updateProduct(product);
		// 利用Referrer，從Update頁面轉回原頁面
		// 沒防Open Redirect攻擊
		String referrer = request.getParameter("referrer");
		response.sendRedirect(referrer);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
