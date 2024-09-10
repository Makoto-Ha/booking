package com.booking.dao.shopping;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.internal.build.AllowSysOut;

import com.booking.bean.shopping.Product;
import com.booking.utils.util.DaoResult;

public class ProductDao {

	private Session session;

	public ProductDao(Session session) {
		this.session = session;
	}

	public DaoResult<Product> getProductById(Integer productId) {
		String hql = "FROM Product WHERE productId = :id";
		Product product = session.createQuery(hql, Product.class).setParameter("id", productId).getSingleResult();
		return DaoResult.create(product).setSuccess(product != null);
	}

	public DaoResult<List<Product>> getProductByName(String productName, String sortBy, String sortOrder) {
		String hql = "FROM Product WHERE productName LIKE :name ORDER BY " + sortBy + " " + sortOrder;
		List<Product> products = session.createQuery(hql, Product.class).setParameter("name", "%" + productName + "%")
				.getResultList();
		return DaoResult.create(products).setSuccess(!products.isEmpty());

	}

	public DaoResult<List<Product>> getAllProduct(String sortBy, String sortOrder) {
		String hql = "FROM Product ORDER BY " + sortBy + " " + sortOrder;
		List<Product> products = session.createQuery(hql, Product.class).getResultList();
		return DaoResult.create(products).setSuccess(!products.isEmpty());
	}

	public DaoResult<?> addProduct(Product product) {
		session.persist(product);
		Integer productId = product.getProductId();
		return DaoResult.create().setGeneratedId(productId).setSuccess(productId != null);
	}

	public DaoResult<?> removeProductById(Integer productId) {
		  try {
		        session.beginTransaction();  // 手動開始交易
		        Product product = session.get(Product.class, productId);
		        if (product != null) {
		            session.remove(product);
		            session.getTransaction().commit();  // 提交交易
		            return DaoResult.create().setSuccess(true);
		        } else {
		            System.out.println("未找到對應的產品");
		            session.getTransaction().rollback();  // 回滾交易
		            return DaoResult.create().setSuccess(false);
		        }
		    } catch (Exception e) {
		        session.getTransaction().rollback();  // 出錯時回滾
		        e.printStackTrace();
		        return DaoResult.create().setSuccess(false);
		    }
	}

	public DaoResult<?> updateProduct(Product product) {
		Product updatepProduct = session.merge(product);
		return DaoResult.create().setSuccess(updatepProduct != null);
	}
}
