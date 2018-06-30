package dev.manhnd.english.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {

	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		try {
			if (sessionFactory == null) {
				final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.configure(HibernateUtil.class.getResource("/cfg/hibernate.cfg.xml")).build();
				sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sessionFactory;
	}

	public static void closeSessionFactory() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

}
