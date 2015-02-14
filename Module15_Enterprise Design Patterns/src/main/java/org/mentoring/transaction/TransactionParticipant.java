package org.mentoring.transaction;

public interface TransactionParticipant
{
	void begin() throws TransactionException;
	
	void commit() throws TransactionException;
	
	void rollBack();
	
	State getState();
	
	void setState(State state);
}
