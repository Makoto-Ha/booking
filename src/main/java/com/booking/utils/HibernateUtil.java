//package com.booking.utils;
//
//import org.hibernate.SessionFactory;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//
//public class HibernateUtil {
//	// session工廠
//    private static final SessionFactory sessionFactory;
//    static {
//        try {
//        	StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
//            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
//        } catch (Throwable ex) {
//            throw new ExceptionInInitializerError(ex);
//        }
//    }
//
//    // 獲取Session工廠
//    public static SessionFactory getSessionFactory() {
//        return sessionFactory;
//    }
//    
//    // 關閉Session工廠
//    public static void closeSessionFactory() {
//    	if(sessionFactory != null) {
//			sessionFactory.close();
//		}
//    }
//}