package com.booking.dao.shopping;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.booking.bean.shopping.Product;
import com.booking.utils.util.DaoResult;

@Repository // 表明這是一個 DAO 類別
@Transactional
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;

	// 取得當前的 Hibernate Session
	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public DaoResult<Product> getProductById(Integer productId) {
		String hql = "FROM Product WHERE productId = :id";
		Product product = getCurrentSession().createQuery(hql, Product.class).setParameter("id", productId).getSingleResult();
		return DaoResult.create(product).setSuccess(product != null);
	}

	@Override
	public DaoResult<List<Product>> getProductByName(String productName, String sortBy, String sortOrder) {
		String hql = "FROM Product WHERE productName LIKE :name ORDER BY " + sortBy + " " + sortOrder;
		List<Product> products = getCurrentSession().createQuery(hql, Product.class).setParameter("name", "%" + productName + "%")
				.getResultList();
		return DaoResult.create(products).setSuccess(!products.isEmpty());

	}

	@Override
	public DaoResult<List<Product>> getAllProduct(String sortBy, String sortOrder) {
		String hql = "FROM Product ORDER BY " + sortBy + " " + sortOrder;
		List<Product> products = getCurrentSession().createQuery(hql, Product.class).getResultList();
		return DaoResult.create(products).setSuccess(!products.isEmpty());
	}

	@Override
	public DaoResult<?> addProduct(Product product) {
		getCurrentSession().persist(product);
		Integer productId = product.getProductId();
		return DaoResult.create().setGeneratedId(productId).setSuccess(productId != null);
	}

	@Override
	public DaoResult<?> removeProductById(Integer productId) {
		Product product = getCurrentSession().get(Product.class, productId);
		if (product != null) {
			getCurrentSession().remove(product);
			return DaoResult.create().setSuccess(true);
		}
		return DaoResult.create().setSuccess(false);
	}

	@Override
	public DaoResult<?> updateProduct(Product product) {
		Product updatepProduct = getCurrentSession().merge(product);
		return DaoResult.create().setSuccess(updatepProduct != null);
	}
}
