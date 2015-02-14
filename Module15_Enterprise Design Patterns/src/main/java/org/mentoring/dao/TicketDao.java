package org.mentoring.dao;

import java.util.LinkedHashMap;
import java.util.Map;

import org.mentoring.domain.Ticket;

public class TicketDao implements EntityDao
{
	private Map<Long, Ticket> storage = new LinkedHashMap<Long, Ticket>();
	
	public TicketDao()
	{
		Long id1 = new Long(1);
		Long id2 = new Long(2);
		storage.put(id1, new Ticket(id1));
		storage.put(id2, new Ticket(id2));
	}
	
	public void update(Object obj) throws DaoException
	{
		// TODO Auto-generated method stub
	}

	public <T> T getEntity(Long id, Class<T> clazz)
	{
		return (T) (storage.containsKey(id) ? storage.get(id) : null);
	}

}
