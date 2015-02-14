package org.mentoring.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.mentoring.transaction.TransactionManager;
import org.mentoring.transaction.TransactionParticipant;
import org.mentoring.transaction.State;
import org.mentoring.transaction.TransactionException;


public class Ticket implements Serializable
{
	private Long id;
	
	private BigDecimal price;
	
	private Date purchaseDate;
	
	private String description;
	
	public Ticket()
	{
		
	}
	
	public Ticket(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}

	public BigDecimal getPrice()
	{
		return price;
	}

	public void setPrice(BigDecimal price)
	{
		this.price = price;
	}

	public Date getPurchaseDate()
	{
		return purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate)
	{
		this.purchaseDate = purchaseDate;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		return builder.append("id:").append(getId()).append(",").append("price:").append(getPrice()).append(",")
			.append("purchaseDate:").append(getPurchaseDate()).append(",").append("description:").append(getDescription()).toString();
	}
	
	public class TicketWrapper extends Ticket implements TransactionParticipant
	{
		private State state = State.NEW;
		
		private TransactionManager manager;
		
		public TicketWrapper(TransactionManager manager)
		{
			this.manager = manager;
		}
		
		public Long getId()
		{
			return Ticket.this.getId();
		}
		
		public BigDecimal getPrice()
		{
			return super.getPrice() == null ? Ticket.this.getPrice() : super.getPrice();
		}
		
		public Date getPurchaseDate()
		{
			return super.getPurchaseDate() == null ? Ticket.this.getPurchaseDate() : super.getPurchaseDate();
		}
		
		public String getDescription()
		{
			return super.getDescription() == null ? Ticket.this.getDescription() : super.getDescription();
		}

		public void begin() throws TransactionException
		{
			manager.addParticipant(this);
		}

		public void commit() throws TransactionException
		{
			manager.commit(this);
			merge();
		}

		public void rollBack()
		{
			manager.rollBack(this);
		}
		
		private void merge()
		{
			if (this.getDescription() != null)
			{
				Ticket.this.setDescription(this.getDescription());
			}
			if (this.getPrice() != null)
			{
				Ticket.this.setPrice(this.getPrice());
			}
			if (this.getPurchaseDate() != null)
			{
				Ticket.this.setPurchaseDate(this.getPurchaseDate());
			}
		}
		
		public String toString()
		{
			StringBuilder builder = new StringBuilder("transactionState:");
			return builder.append(getState()).append(",").append(super.toString()).toString();
		}

		public State getState()
		{
			return state;
		}

		public void setState(State state)
		{
			this.state = state;
		}
	}
}
