package org.mentoring.transaction;

public interface TransactionManager
{
	void commit() throws TransactionException;
	
	void rollBack() throws TransactionException;
	
	void commit(TransactionParticipant participant) throws TransactionException;
	
	void rollBack(TransactionParticipant participant);
	
	void addParticipant(TransactionParticipant participant) throws TransactionException;
}
