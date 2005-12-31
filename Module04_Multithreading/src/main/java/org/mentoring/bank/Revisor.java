package org.mentoring.bank;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mentoring.bank.account.Account;
import org.mentoring.bank.account.AccountComparator;

public class Revisor implements Runnable {

	private Bank bank;

	private volatile boolean isRevisionInProgress;

	private static Logger logger = LogManager.getLogger("logger");

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public Revisor() {
		super();
	}

	public Revisor(Bank bank) {
		super();
		this.bank = bank;
	}

	public void run() {
		if (getBank().isStarted()) {
			doRevision();
		}
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public void doRevision() {
		setRevisionInProgress(true);
		BigDecimal totalAmount = new BigDecimal(0);
		List<Account> accounts = getBank().getAccounts();
		//AccountComparator comparator = new AccountComparator();
		//Collections.sort(accounts, comparator);
		logger.info("Revision is started");
		for (Account account : accounts) {
//			synchronized (account)
//			{
				totalAmount = totalAmount.add(account.getAmount().getAmount());
//			}
		}
		logger.info("Revision finished. Total amount is " + totalAmount);
//		synchronized (accounts) {
//			logger.info("Revision is started");
//			for (Account account : accounts) {
//				totalAmount = totalAmount.add(account.getAmount().getAmount());
//			}
//			logger.info("Revision finished. Total amount is " + totalAmount);
//		}
		setRevisionInProgress(false);
		for (Account account : accounts) {
			account.notify();
		}
	}

	public boolean isRevisionInProgress() {
		lock.readLock().lock();
		try {
			return isRevisionInProgress;
		} finally {
			lock.readLock().unlock();
		}

	}

	public void setRevisionInProgress(boolean isRevisionInProgress) {
		lock.writeLock().lock();
		try {
			this.isRevisionInProgress = isRevisionInProgress;
		} finally {
			lock.writeLock().lock();
		}
	}
}
