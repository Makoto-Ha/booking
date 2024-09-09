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
		"/product/select", "/product/selectName", "/product/selectAll", "/product" })
public class ProductController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ProductService productService;
	
	@Override
	public void init() throws ServletException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		this.productService = new ProductService(session);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String[] splitURI = requestURI.split("/");
		String path = splitURI[splitURI.length - 1];

		response.setHeader("Access-Control-Allow-Origin", "*");

		switch (path) {
//		case "select" -> select(request, response);
		case "create" -> create(request, response);
		case "delete" -> delete(request, response);
		case "update" -> update(request, response);
		case "selectUpdate" -> selectUpdate(request, response);
		case "selectName" -> selectName(request, response);
		case "selectAll" -> selectAll(request, response);
		case "product" -> sendProductIndex(request, response);

		}
	}

//	private void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//		Integer productId = Integer.parseInt(request.getParameter("product-id"));
//		Result<Product> productResult = productService.getProductById(productId);
//		Product product = productResult.getData();
//
//		request.setAttribute("product", product);
//		request.setAttribute("seleteId", productId);
//		request.getRequestDispatcher("/product/SelectProduct.jsp").forward(request, response);
//
//	}
	
	private void sendProductIndex(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("manageListName", "商城列表");
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

		if (sortBy == null || sortBy.isEmpty()) {
			sortBy = "productId"; // 默認排序字段
		}
		if (sortOrder == null || sortOrder.isEmpty()) {
			sortOrder = "DESC"; // 默認排序方式
		}

		Result<List<Product>> productResult = productService.getAllProduct(sortBy, sortOrder);
		List<Product> productList = productResult.getData();

		request.setAttribute("products", productList);
		request.getRequestDispatcher("/adminsystem/shopping/product-select.jsp").forward(request, response);

	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		 Session session = (Session) request.getAttribute("hibernateSession");
		    ProductService productService = new ProductService(session);
		Integer categoryId = Integer.parseInt(request.getParameter("category-id"));
		String productName = request.getParameter("product-name");
		String productDescription = request.getParameter("product-description");
		Integer productPrice = Integer.parseInt(request.getParameter("product-price"));
		Integer productSales = Integer.parseInt(request.getParameter("product-sales"));
		Integer productInventorey = Integer.parseInt(request.getParameter("product-inventorey"));
		Integer productState = Integer.parseInt(request.getParameter("product-state"));

		Product product = new Product(categoryId, productName, null, productDescription, productPrice, productSales,
				productInventorey, productState);

		productService.addProduct(product);
		response.sendRedirect(request.getContextPath() + "/product/selectAll");
	}

	class CreateResponse {
		private boolean success;
		private Object data;

		public CreateResponse(boolean success, Object data) {
			this.success = success;
			this.data = data;
		}

		public boolean isSuccess() {
			return success;
		}

		public Object getData() {
			return data;
		}
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

	/**
	 * 選取要更新的那筆
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
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
//		String productImage = request.getParameter("product-image");
		String productDescription = request.getParameter("product-description");
		Integer productPrice = Integer.parseInt(request.getParameter("product-price"));
		Integer productSales = Integer.parseInt(request.getParameter("product-sales"));
		Integer productInventorey = Integer.parseInt(request.getParameter("product-inventorey"));
		Integer productState = Integer.parseInt(request.getParameter("product-state"));

		Product product = new Product(productId, categoryId, productName, null, productDescription, productPrice,
				productSales, productInventorey, productState);

		productService.updateProduct(product);

		String referrer = request.getParameter("referrer");
		response.sendRedirect(referrer);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
