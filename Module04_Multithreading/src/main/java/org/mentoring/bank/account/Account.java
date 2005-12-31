package org.mentoring.bank.account;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.mentoring.bank.Bank;
import org.mentoring.bank.amount.Amount;
import org.mentoring.bank.person.Person;

public class Account extends Thread {

	private long number;
	private Amount amount;
	private Person person;

	private Bank bank;

	/**
	 * Logger
	 */
	private static Logger logger = LogManager.getLogger("logger");

	public Account() {
		super();
	}

	public Account(long number, Amount amount, Person person, Bank bank) {
		super();
		this.number = number;
		this.amount = amount;
		this.person = person;
		this.bank = bank;
	}

	public Account(Amount amount, Person person, Bank bank) {
		super();
		this.amount = amount;
		this.person = person;
		this.bank = bank;
	}

	public long getNumber() {
		return number;
	}

	public void setNumber(long number) {
		this.number = number;
	}

	public synchronized Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void addAmount(BigDecimal amount) {
		this.amount.setAmount(this.amount.getAmount().add(amount));
		logger.info(this.toString() + " encreased to " + amount);
	}

	public void reduceAmount(BigDecimal amount) {
		this.amount.setAmount(this.amount.getAmount().subtract(amount));
		logger.info(this.toString() + " reduced to " + amount);
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public void run() {
		try {
			doMoneyTransfering();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private BigDecimal getAmountToTransfer() {
		BigDecimal randFromDouble = new BigDecimal(Math.random());
		BigDecimal amount = randFromDouble.multiply(this.getAmount()
				.getAmount());
		return amount.divide(new BigDecimal(0.5), 2, RoundingMode.HALF_DOWN);
	}

	private Account getAccountToTransfer() {
		if (getBank().getAccounts().size() == 1) {
			return this;
		}
		Random random = new Random();
		int accountToTransferNumber = random.nextInt(getBank().getAccounts()
				.size() - 1);
		return getBank().getAccounts().get(accountToTransferNumber);
	}

	private synchronized void waitForMoneyTransfering()
			throws InterruptedException {
		while (bank.getRevisor().isRevisionInProgress() && !bank.isStopped()) {
			wait();
		}
	}

	public void tronsferMoney(Account other, BigDecimal amount)
			throws InterruptedException {
		if (other == this)
			return;
		else if (System.identityHashCode(this) < System.identityHashCode(other)) {
			synchronized (this) {
				synchronized (other) {
					if (!bank.getRevisor().isRevisionInProgress()) {
						this.reduceAmount(amount);
						other.addAmount(amount);
					}
				}
			}
		} else {
			synchronized (other) {
				synchronized (this) {
					if (!bank.getRevisor().isRevisionInProgress()) {
						other.addAmount(amount);
						this.reduceAmount(amount);
					}
				}
			}
		}
	}

	private boolean isEnoughMoney(BigDecimal amountToTransfer) {
		return amountToTransfer.compareTo(getAmount().getAmount()) < 0;
	}

	private boolean isMoreThanZero(BigDecimal amount) {
		return amount.compareTo(new BigDecimal(0)) > 0;
	}

	private void doMoneyTransfering() throws InterruptedException {
		boolean isFinished = false;
		while (bank.isStarted() && !bank.isStopped() && !isFinished) {
			waitForMoneyTransfering();
			BigDecimal amountToTransfer = getAmountToTransfer();
			if (!isEnoughMoney(amountToTransfer)) {
				logger.info(toString() + " want to transfer "
						+ amountToTransfer + " but does not have enough");
				isFinished = true;
			} else {
				if (isMoreThanZero(amountToTransfer)) {
					Account account = getAccountToTransfer();
					logger.info(this.toString() + " wants to transfer "
							+ amountToTransfer + " to " + account.toString());
					tronsferMoney(account, amountToTransfer);
					logger.info(this.toString() + " transfered "
							+ amountToTransfer + " to " + account.toString());
				}
			}
		}
	}

	@Override
	public String toString() {
		return "NUMBER " + getNumber() + "; AMOUNT " + amount;
	}
}
