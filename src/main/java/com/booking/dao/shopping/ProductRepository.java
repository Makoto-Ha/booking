package com.booking.dao.shopping;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.booking.bean.dto.shopping.ProductDTO;
import com.booking.bean.dto.shopping.ProductSalesDTO;
import com.booking.bean.pojo.shopping.Product;
import com.booking.utils.Result;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

	@Query("SELECT new com.booking.bean.dto.shopping.ProductDTO(p.productId, p.productName, p.productDescription, p.productPrice, p.productSales, p.productInventory, p.productState, p.category.categoryId, p.category.categoryName,p.updatedAt,p.createdAt, p.productImage) FROM Product p")
	Page<ProductDTO> findProductDTOAll(Pageable pageable);

	@Query("SELECT new com.booking.bean.dto.shopping.ProductDTO(p.productId, p.productName, p.productDescription, p.productPrice, p.productSales, p.productInventory, p.productState, p.category.categoryId, p.category.categoryName,p.updatedAt,p.createdAt, p.productImage) FROM Product p WHERE p.productName LIKE %:productName%")
	Result<List<Product>> findProductByNameContaining(String productName);

	@Query("SELECT new com.booking.bean.dto.shopping.ProductDTO(p.productId, p.productName, p.productDescription, p.productPrice, p.productSales, p.productInventory, p.productState, p.category.categoryId, p.category.categoryName,p.updatedAt,p.createdAt, p.productImage) FROM Product p WHERE p.category.categoryId = :categoryId")
	Page<ProductDTO> findProductByCategoryIdWithPage(@Param("categoryId") Integer categoryId, Pageable pageable);
	
	@Query("SELECT new com.booking.bean.dto.shopping.ProductDTO(p.productId, p.productName, p.productDescription, p.productPrice, p.productSales, p.productInventory, p.productState, p.category.categoryId, p.category.categoryName,p.updatedAt,p.createdAt, p.productImage) FROM Product p WHERE p.category.categoryId = :categoryId")
	List<ProductDTO> findProductByCategoryId(Integer categoryId);

	@Query("SELECT new com.booking.bean.dto.shopping.ProductDTO(p.productId, p.productName, p.productDescription, p.productPrice, p.productSales, p.productInventory, p.productState, p.category.categoryId, p.category.categoryName,p.updatedAt,p.createdAt, p.productImage) FROM Product p WHERE p.productId = :productId")
	ProductDTO findProductDTOById(Integer productId);

	@EntityGraph(attributePaths = { "category" })
	Page<Product> findAll(Specification<Product> spec, Pageable pageable);
	
	@Query("SELECT new com.booking.bean.dto.shopping.ProductDTO(p.productId, p.productName, p.productDescription, p.productPrice, p.productSales, p.productInventory, p.productState, p.category.categoryId, p.category.categoryName,p.updatedAt,p.createdAt, p.productImage) FROM Product p ORDER BY p.productSales DESC")
	List<ProductDTO> findTopSellingProductDTOs(Pageable pageable);

	 @Query("SELECT new com.booking.bean.dto.shopping.ProductSalesDTO(soi.product.productName, SUM(soi.quantity)) " +
	           "FROM ShopOrderItem soi GROUP BY soi.product.productName")
	    List<ProductSalesDTO> findProductSalesData();
}
