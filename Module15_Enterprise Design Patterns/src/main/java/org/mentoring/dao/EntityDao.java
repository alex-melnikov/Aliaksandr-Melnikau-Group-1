package org.mentoring.dao;

public interface EntityDao
{
	void update(Object obj) throws DaoException;
	
	<T> T getEntity(Long id, Class<T> clazz);
}
