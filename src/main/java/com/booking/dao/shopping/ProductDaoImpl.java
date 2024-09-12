package com.booking.dao.shopping;

import java.util.List;

import org.hibernate.Session;

import com.booking.bean.shopping.Product;
import com.booking.utils.util.DaoResult;

public class ProductDaoImpl implements ProductDao {

	private Session session;

	public ProductDaoImpl(Session session) {
		this.session = session;
	}

	@Override
	public DaoResult<Product> getProductById(Integer productId) {
		String hql = "FROM Product WHERE productId = :id";
		Product product = session.createQuery(hql, Product.class).setParameter("id", productId)
				.getSingleResult();
		return DaoResult.create(product).setSuccess(product != null);
	}

	@Override
	public DaoResult<List<Product>> getProductByName(String productName, String sortBy, String sortOrder) {
		 String hql = "FROM Product WHERE productName LIKE :name ORDER BY " + sortBy + " " + sortOrder;
         List<Product> products = session.createQuery(hql, Product.class)
                               .setParameter("name", "%" + productName + "%")
                               .getResultList();
         return DaoResult.create(products).setSuccess(!products.isEmpty());
		
	}

	@Override
	public DaoResult<List<Product>> getAllProduct(String sortBy, String sortOrder) {
		 String hql = "FROM Product ORDER BY " + sortBy + " " + sortOrder;
         List<Product> products = session.createQuery(hql, Product.class).getResultList();
         return DaoResult.create(products).setSuccess(!products.isEmpty());
	}

	
	@Override
	public DaoResult<?> addProduct(Product product) {
		session.persist(product);
		Integer productId = product.getProductId();
		return DaoResult.create().setGeneratedId(productId).setSuccess(productId != null);
	}

	
	@Override
	public DaoResult<?> removeProductById(Integer productId) {
		Product product = session.get(Product.class, productId);
		if(product!= null) {
			session.remove(product);
			return DaoResult.create().setSuccess(true);
		}
		return DaoResult.create().setSuccess(false);
	}

	
	@Override
	public DaoResult<?> updateProduct(Product product) {
		Product updatepProduct = session.merge(product);
		return DaoResult.create().setSuccess(updatepProduct!= null);
	}
}
