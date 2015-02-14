package org.mentoring.transaction.acid;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.mentoring.transaction.TransactionManager;
import org.mentoring.transaction.TransactionParticipant;
import org.mentoring.transaction.TransactionRecorder;
import org.mentoring.transaction.State;
import org.mentoring.transaction.TransactionException;
import org.mentoring.transaction.audittrail.LogTransactionRecorder;
import org.mentoring.transaction.audittrail.TransactionRecord;


public enum TransactionManagerImpl implements TransactionManager
{
	INSTANCE;
	
	private Set<TransactionParticipant> participants = Collections.synchronizedSet(new HashSet<TransactionParticipant>());
	
	private TransactionRecorder recoder = new LogTransactionRecorder();
	
	public synchronized void commit() throws TransactionException
	{
		for (TransactionParticipant participant : participants)
		{
			participant.commit();
		}
	}

	public synchronized void rollBack() throws TransactionException
	{
		for (TransactionParticipant participant : participants)
		{
			participant.rollBack();
		}
	}

	public void commit(TransactionParticipant participant) throws TransactionException
	{
		if (participants.contains(participant))
		{
			participant.setState(State.COMMITED);
			participants.remove(participant);
			recoder.process(new TransactionRecord(participant));
		}
	}

	public void rollBack(TransactionParticipant participant)
	{
		if (participants.contains(participant))
		{
			participants.remove(participant);
			participant.setState(State.ROLLEDBACK);
			recoder.process(new TransactionRecord(participant));
		}
	}

	public void addParticipant(TransactionParticipant participant) throws TransactionException
	{
		if (participants.contains(participant))
		{
			throw new TransactionException("transaction is already opened for this object");
		}
		participants.add(participant);
		participant.setState(State.INPROGRESS);
		recoder.process(new TransactionRecord(participant));
	}
	
}
