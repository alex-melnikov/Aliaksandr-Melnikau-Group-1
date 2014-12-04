package org.mentoring.bank.account;

import java.util.Comparator;

public class AccountComparator implements Comparator<Account>{

	public int compare(Account o1, Account o2) {
		return System.identityHashCode(o1) - System.identityHashCode(o2);
	}
}
