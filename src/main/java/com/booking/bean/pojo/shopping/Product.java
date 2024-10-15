package com.booking.bean.pojo.shopping;

import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
@DynamicInsert
public class Product {
	
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	private ProductCategory category;

	@Column(name = "product_name")
	private String productName;

	@Column(name = "product_image")
	private String productImage;

	@Column(name = "product_description")
	private String productDescription;

	@Column(name = "product_price")
	private Integer productPrice;

	@Column(name = "product_sales")
	private Integer productSales;

	@Column(name = "product_inventory")
	private Integer productInventory;

	@Column(name = "product_state")
	private Integer productState;
	
	@Column(name="created_At")
	private LocalDateTime createdAt;
	
	@Column(name="updated_At")
	private LocalDateTime updatedAt;

}