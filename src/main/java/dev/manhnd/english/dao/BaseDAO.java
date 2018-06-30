package dev.manhnd.english.dao;

import java.util.List;

public interface BaseDAO {

	public Long create(Object o) throws Exception;

	public boolean update(Object o) throws Exception;

	public boolean delete(Object o) throws Exception;

	public Object get(Class<?> clazz, long id) throws Exception;

	public Object get(Class<?> clazz, String uniqueColumnName, String uniqueColumnValue) throws Exception;
	
	public Object get(String hql) throws Exception;
	
	public Object getLastRecord(String hql) throws Exception;

	public List<?> getResultList(String hql) throws Exception;

	public List<?> find(String hql) throws Exception;

}
