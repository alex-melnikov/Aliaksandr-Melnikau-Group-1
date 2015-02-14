package org.mentoring.transaction;

import org.mentoring.transaction.audittrail.TransactionRecord;

public interface TransactionRecorder
{
	void process(TransactionRecord record);
}
