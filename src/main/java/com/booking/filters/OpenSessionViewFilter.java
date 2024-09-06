package com.booking.filters;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.booking.utils.HibernateUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class OpenSessionViewFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		SessionFactory factory = HibernateUtil.getSessionFactory();
		Session session = factory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			System.out.println("交易開啟");
			chain.doFilter(request, response);
			
			session.getTransaction().commit();
			System.out.println("交易結束");
		}catch(Exception e) {
			session.getTransaction().rollback();
			
			System.out.println("交易回滾");
			e.printStackTrace();
		}finally {
			session.close();
			System.out.println("Hibernate Session關閉");
		}

	}

}
