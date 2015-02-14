package org.mentoring.transaction;

public final class TransactionException extends Exception
{
	public TransactionException()
	{
		super();
	}
	
	public TransactionException(String message)
	{
		super(message);
	}
}
