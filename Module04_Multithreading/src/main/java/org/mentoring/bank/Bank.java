package org.mentoring.bank;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mentoring.bank.account.Account;
import org.mentoring.bank.amount.Amount;

public class Bank {

	private long accountNumber;

	private List<Account> accounts;

	private Amount amount;

	private volatile boolean isStarted;

	private volatile boolean isStopped;

	private Revisor revisor;

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Logger
	 */
	private static Logger logger = LogManager.getLogger("logger");

	public Bank() {
		super();
		init();
	}

	public Bank(Amount amount, int numberOfAccounts) {
		super();
		init();
		this.amount = amount;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accounts == null) ? 0 : accounts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bank other = (Bank) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		return true;
	}

	/**
	 * This method return next account number
	 * 
	 * @return next account number
	 */
	public synchronized long getNextAccountNumber() {
		return accountNumber++;
	}

	/**
	 * This method add account to the list of accounts
	 * 
	 * @param account
	 *            Account
	 */
	public void addAccount(Account account) {
		// add account to the list
		if (account != null) {
			account.setNumber(getNextAccountNumber());
			accounts.add(account);
			logger.info(account.getNumber()
					+ " have been created with initial amaunt "
					+ account.getAmount());
		}
	}

	private void init() {
		accounts = new ArrayList<Account>();
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public boolean isStarted() {
//		lock.readLock().lock();
//		try {
			return isStarted;
//		} finally {
//			lock.readLock().unlock();
//		}
	}

	public void setStarted(boolean isStarted) {
		//lock.writeLock().lock();
		//try {
			this.isStarted = isStarted;
//		} finally {
//			lock.writeLock().lock();
//		}
		logger.info("Started");
	}

	public boolean isStopped() {
//		lock.readLock().lock();
//		try {
			return isStopped;
//		} finally {
//			lock.readLock().unlock();
//		}
	}

	public void setStopped(boolean isStopped) {
//		lock.writeLock().lock();
//		try {
			this.isStopped = isStopped;
//		} finally {
//			lock.writeLock().lock();
//		}
	}

	public void start() {
		setStarted(true);
		List<Account> accounts = getAccounts();
		for (Account account : accounts) {
			account.setPriority(10);
			account.start();
		}
	}

	public synchronized void stop() {
		setStopped(isStopped);
		for (Account account : accounts) {
			if (account.isAlive()) {
				account.notify();
			}
		}
		logger.info("Stopped");
	}

	public Revisor getRevisor() {
		return revisor;
	}

	public void setRevisor(Revisor revisor) {
		this.revisor = revisor;
	}
}
