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

    private long                accountNumber;

    private List<Account>       accounts;

    private Amount              amount;

    private volatile boolean    isStarted;

    private volatile boolean    isStopped;

    private Revisor             revisor;

    private final ReadWriteLock lock   = new ReentrantReadWriteLock();

    /**
     * Logger
     */
    private static Logger       logger = LogManager.getLogger("logger");

    public Bank() {
        super();
        this.init();
    }

    public Bank(final Amount amount, final int numberOfAccounts) {
        super();
        this.init();
        this.amount = amount;
    }

    /**
     * This method add account to the list of accounts
     *
     * @param account
     *            Account
     */
    public void addAccount(final Account account) {
        // add account to the list
        if (account != null) {
            account.setNumber(this.getNextAccountNumber());
            this.accounts.add(account);
            Bank.logger.info(account.getNumber() + " have been created with initial amaunt "
                    + account.getAmount());
        }
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Bank other = (Bank) obj;
        if (this.accounts == null) {
            if (other.accounts != null) {
                return false;
            }
        } else if (!this.accounts.equals(other.accounts)) {
            return false;
        }
        return true;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public Amount getAmount() {
        return this.amount;
    }

    /**
     * This method return next account number
     *
     * @return next account number
     */
    public synchronized long getNextAccountNumber() {
        return this.accountNumber++;
    }

    public Revisor getRevisor() {
        return this.revisor;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.accounts == null) ? 0 : this.accounts.hashCode());
        return result;
    }

    public boolean isStarted() {

        return this.isStarted;
    }

    public boolean isStopped() {
        return this.isStopped;
    }

    public void setAccounts(final List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setAmount(final Amount amount) {
        this.amount = amount;
    }

    public void setRevisor(final Revisor revisor) {
        this.revisor = revisor;
    }

    public void setStarted(final boolean isStarted) {
        this.isStarted = isStarted;
        Bank.logger.info("Started");
    }

    public void setStopped(final boolean isStopped) {
        this.isStopped = isStopped;
    }

    public void start() {
        this.setStarted(true);
        final List<Account> accounts = this.getAccounts();
        for (final Account account : accounts) {
            account.setPriority(10);
            account.start();
        }
    }

    public void stop() {
        this.setStopped(true);
        for (final Account account : this.accounts) {
            synchronized (account) {
                if (account.isAlive()) {
                    account.notify();
                }
            }
        }
        Bank.logger.info("Stopped");
    }

    private void init() {
        this.accounts = new ArrayList<Account>();
    }
}
