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

	public Account(final Amount amount, final Person person, final Bank bank) {
		super();
		this.amount = amount;
		this.person = person;
		this.bank = bank;
	}

	public Account(final long number, final Amount amount, final Person person,
			final Bank bank) {
		super();
		this.number = number;
		this.amount = amount;
		this.person = person;
		this.bank = bank;
	}

	public void addAmount(final BigDecimal amount) {
		this.amount.setAmount(this.amount.getAmount().add(amount));
		Account.logger.info(this.toString() + " encreased to " + amount);
	}

	public synchronized Amount getAmount() {
		return this.amount;
	}

	public Bank getBank() {
		return this.bank;
	}

	public long getNumber() {
		return this.number;
	}

	public Person getPerson() {
		return this.person;
	}

	public void reduceAmount(final BigDecimal amount) {
		this.amount.setAmount(this.amount.getAmount().subtract(amount));
		Account.logger.info(this.toString() + " reduced to " + amount);
	}

	@Override
	public void run() {
		try {
			this.doMoneyTransfering();
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setAmount(final Amount amount) {
		this.amount = amount;
	}

	public void setBank(final Bank bank) {
		this.bank = bank;
	}

	public void setNumber(final long number) {
		this.number = number;
	}

	public void setPerson(final Person person) {
		this.person = person;
	}

	@Override
	public String toString() {
		return "NUMBER " + this.getNumber() + "; AMOUNT " + this.amount;
	}

	public void tronsferMoney(final Account other, final BigDecimal amount)
			throws InterruptedException {
		if (other == this) {
			return;
		} else {
			if (System.identityHashCode(this) < System.identityHashCode(other)) {
				synchronized (this) {
					synchronized (other) {
						if (!this.bank.getRevisor().isRevisionInProgress()) {
							this.reduceAmount(amount);
							other.addAmount(amount);
							Account.logger.info(this.toString()
									+ " transfered " + amount + " to "
									+ other.toString());
						} else {
							Account.logger.info(this.toString()
									+ " cannot transfer " + amount + " to "
									+ other.toString()
									+ " due to revision is started");
						}

					}
				}
			} else {
				synchronized (other) {
					synchronized (this) {
						if (!this.bank.getRevisor().isRevisionInProgress()) {
							other.addAmount(amount);
							this.reduceAmount(amount);
							Account.logger.info(this.toString()
									+ " transfered " + amount + " to "
									+ other.toString());
						} else {
							Account.logger.info(this.toString()
									+ " cannot transfer " + amount + " to "
									+ other.toString()
									+ " due to revision is started");
						}
					}
				}
			}
		}
	}

	private void doMoneyTransfering() throws InterruptedException {
		while (this.bank.isStarted() && !this.bank.isStopped()) {
			this.waitForMoneyTransfering();
			final BigDecimal amountToTransfer = this.getAmountToTransfer();
			if (this.isMoreThanZero(amountToTransfer)
					&& this.isEnoughMoney(amountToTransfer)) {
				final Account account = this.getAccountToTransfer();
				Account.logger.info(this.toString() + " wants to transfer "
						+ amountToTransfer + " to " + account.toString());
				this.tronsferMoney(account, amountToTransfer);
				Thread.sleep(1000);
			}
		}
	}

	private Account getAccountToTransfer() {
		if (this.getBank().getAccounts().size() == 1) {
			return this;
		}
		final Random random = new Random();
		final int accountToTransferNumber = random.nextInt(this.getBank()
				.getAccounts().size() - 1);
		return this.getBank().getAccounts().get(accountToTransferNumber);
	}

	private BigDecimal getAmountToTransfer() {
		final BigDecimal randFromDouble = new BigDecimal(Math.random());
		final BigDecimal amount = randFromDouble.multiply(this.getAmount()
				.getAmount());
		return amount.divide(new BigDecimal(0.5), 2, RoundingMode.HALF_DOWN);
	}

	private boolean isEnoughMoney(final BigDecimal amountToTransfer) {
		return amountToTransfer.compareTo(this.getAmount().getAmount()) < 0;
	}

	private boolean isMoreThanZero(final BigDecimal amount) {
		return amount.compareTo(new BigDecimal(0)) > 0;
	}

	private synchronized void waitForMoneyTransfering()
			throws InterruptedException {
		while (this.bank.getRevisor().isRevisionInProgress()
				&& !this.bank.isStopped()) {
			this.wait();
		}
	}
}
