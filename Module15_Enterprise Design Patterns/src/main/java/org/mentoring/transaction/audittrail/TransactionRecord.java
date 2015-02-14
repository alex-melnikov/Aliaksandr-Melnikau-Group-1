package org.mentoring.transaction.audittrail;

import java.util.Date;

import org.mentoring.transaction.TransactionParticipant;

public class TransactionRecord
{
	private Date date = new Date();
	
	private TransactionParticipant transaction;
	
	public TransactionRecord(TransactionParticipant transaction)
	{
		this.transaction = transaction;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder(date.toString());
		return builder.append(" new checkpoint: ").append(transaction.toString()).toString();
	}
}
