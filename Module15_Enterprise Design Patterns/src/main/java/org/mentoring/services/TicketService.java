package org.mentoring.services;

import java.math.BigDecimal;
import java.util.Date;
import org.apache.log4j.Logger;
import org.mentoring.dao.EntityDao;
import org.mentoring.dao.TicketDao;
import org.mentoring.domain.Ticket;
import org.mentoring.domain.Ticket.TicketWrapper;
import org.mentoring.transaction.TransactionManager;
import org.mentoring.transaction.TransactionException;
import org.mentoring.transaction.acid.TransactionManagerImpl;


public class TicketService
{
	private static final Logger LOG = Logger.getLogger(TicketService.class);
	
	private EntityDao dao = new TicketDao();
	
	private TransactionManager transactionManager = TransactionManagerImpl.INSTANCE;
	
	public void updateTicket1(Long id, String newDescription, BigDecimal newPrice, Date newPurchaseDate)
	{
		TicketWrapper ticket = dao.getEntity(id, Ticket.class).new TicketWrapper(transactionManager);
		try
		{
			ticket.begin();
			if (newDescription != null)
			{
				ticket.setDescription(newDescription);
			}
			if (newPrice != null)
			{
				ticket.setPrice(newPrice);
			}
			if (newPurchaseDate != null)
			{
				ticket.setPurchaseDate(newPurchaseDate);
			}
			ticket.commit();
		}
		catch (TransactionException e)
		{
			ticket.rollBack();
			LOG.error(e.getLocalizedMessage());
		}
	}
	
	public void updateTicket2(Long id, String newDescription, BigDecimal newPrice, Date newPurchaseDate)
	{
		TicketWrapper ticket = dao.getEntity(id, Ticket.class).new TicketWrapper(transactionManager);
		try
		{
			ticket.begin();
			if (newDescription != null)
			{
				ticket.setDescription(newDescription);
			}
			if (newPrice != null)
			{
				ticket.setPrice(newPrice);
			}
			if (newPurchaseDate != null)
			{
				ticket.setPurchaseDate(newPurchaseDate);
			}
			transactionManager.commit();
		}
		catch (TransactionException e)
		{
			LOG.error(e.getLocalizedMessage());
		}
		
	}
}
