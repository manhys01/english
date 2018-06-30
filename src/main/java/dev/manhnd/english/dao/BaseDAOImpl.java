package dev.manhnd.english.dao;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import dev.manhnd.english.utils.HibernateUtil;

public class BaseDAOImpl implements BaseDAO {

	@Override
	public Long create(Object o) throws Exception {
		if (o == null) {
			throw new NullPointerException();
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			Long id = (Long) session.save(o);
			tx.commit();
			return id;
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
				System.out.println("Rollback...");
			}
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean update(Object o) throws Exception {
		if (o == null) {
			throw new NullPointerException();
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(o);
			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
				System.out.println("Rollback...");
			}
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public boolean delete(Object o) throws Exception {
		if (o == null) {
			throw new NullPointerException();
		}
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(o);
			tx.commit();
			return true;
		} catch (Exception e) {
			if (tx.isActive()) {
				tx.rollback();
				System.out.println("Rollback...");
			}
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public Object get(Class<?> clazz, long id) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			Object o = session.get(clazz, id);
			tx.commit();
			return o;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Object get(Class<?> clazz, String propertyName, String propertyValue) throws Exception {
		if (propertyName == null) {
			throw new Exception("Property name is empty!");
		}
		if (propertyValue == null) {
			throw new Exception("Property value is empty!");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session
					.createQuery(String.format("FROM %s o WHERE o.%s=:value", clazz.getSimpleName(), propertyName));
			query.setParameter("value", propertyValue);
			Object o;
			try {
				o = query.getSingleResult();
			} catch (NoResultException e) {
				o = null;
			}
			tx.commit();
			return o;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<?> getResultList(String hql) throws Exception {
		if (hql == null)
			throw new Exception("HQL is empty!");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> list = session.createQuery(hql).getResultList();
			tx.commit();
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<?> find(String hql) throws Exception {
		if (hql == null)
			throw new Exception("HQL is empty!");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List<?> list = session.createQuery(hql).getResultList();
			tx.commit();
			return list;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Object getLastRecord(String hql) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setMaxResults(1);
			Object result = query.getSingleResult();
			tx.commit();
			return result;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Object get(String hql) throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setMaxResults(1);
			Object result = query.getSingleResult();
			tx.commit();
			return result;
		} catch (Exception e) {
			throw e;
		}
	}
}
