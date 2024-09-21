//package com.booking.listeners;
//import com.booking.utils.HibernateUtil;
//
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//
//@WebListener
//public class BookingServletContextListener implements ServletContextListener {
//	
//	@Override
//	public void contextInitialized(ServletContextEvent sce) {
//		// 啟動APP加載獲得Hibernate的SessionFactory
//		HibernateUtil.getSessionFactory();
//	}
//
//	@Override
//	public void contextDestroyed(ServletContextEvent sce) {
//		// 關閉APP後，關閉Hibernate的SessionFactory
//		HibernateUtil.closeSessionFactory();
//	}
//	
//}
