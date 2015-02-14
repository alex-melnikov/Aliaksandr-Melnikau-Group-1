package org.mentoring.transaction.audittrail;

import org.apache.log4j.Logger;
import org.mentoring.transaction.TransactionRecorder;

public class LogTransactionRecorder implements TransactionRecorder
{
	private static final Logger LOG = Logger.getLogger(LogTransactionRecorder.class);

	public void process(TransactionRecord record)
	{
		LOG.info(record.toString());
	}
}
