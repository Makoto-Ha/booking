package com.booking.dao.shopping;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.booking.bean.pojo.shopping.Product;
import com.booking.utils.DaoResult;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	public DaoResult<Product> getProductById(Integer productId) {
		String hql = "FROM Product p JOIN FETCH p.category WHERE productId = :id";
		Product product = entityManager.createQuery(hql, Product.class).setParameter("id", productId).getSingleResult();
		return DaoResult.create(product).setSuccess(product != null);
	}

	@Override
	public DaoResult<List<Product>> getProductByName(String productName, String sortBy, String sortOrder) {
		String hql = "FROM Product WHERE productName LIKE :name ORDER BY " + sortBy + " " + sortOrder;
		List<Product> products = entityManager.createQuery(hql, Product.class).setParameter("name", "%" + productName + "%")
				.getResultList();
		return DaoResult.create(products).setSuccess(!products.isEmpty());
	}

	@Override
	public DaoResult<List<Product>> getAllProduct(String sortBy, String sortOrder) {
		String hql = "FROM Product ORDER BY " + sortBy + " " + sortOrder;
		List<Product> products = entityManager.createQuery(hql, Product.class).getResultList();
		return DaoResult.create(products).setSuccess(!products.isEmpty());
	}

	@Override
	public DaoResult<?> addProduct(Product product) {
		entityManager.persist(product);
		Integer productId = product.getProductId();
		return DaoResult.create().setGeneratedId(productId).setSuccess(productId != null);
	}

	@Override
	public DaoResult<?> removeProductById(Integer productId) {
		Product product = entityManager.getReference(Product.class, productId);
		if (product != null) {
			entityManager.remove(product);
			return DaoResult.create().setSuccess(true);
		}
		return DaoResult.create().setSuccess(false);
	}

	@Override
	public DaoResult<?> updateProduct(Product product) {
		Product updatepProduct = entityManager.merge(product);
		return DaoResult.create().setSuccess(updatepProduct != null);
	}
}
