package org.mentoring.threadtask;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.mentoring.bank.Bank;
import org.mentoring.bank.Revisor;
import org.mentoring.bank.account.Account;
import org.mentoring.bank.amount.Amount;
import org.mentoring.bank.currency.Currency;
import org.mentoring.bank.person.Person;

public class ThreadTask {

	private static int numberOfAccounts;

	private static BigDecimal totalAmount;

	private final static String DEFOULT_CURRANCY = "EUR";
	/**
	 * Scanner to read from the console
	 */
	private static Scanner scanner;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Please enter accouunt number:");
		// Scanner to read options
		if (scanner == null) {
			scanner = new Scanner(System.in);
		}
		// Read number of accounts
		numberOfAccounts = scanner.nextInt();

		System.out.println("Please enter total amount:");
		totalAmount = scanner.nextBigDecimal();

		Bank bank = createBank(numberOfAccounts, totalAmount);
		ScheduledExecutorService scheduledExecutorService = Executors
				.newScheduledThreadPool(1);
		boolean isNotSelectedOption = true;
		// option to start
		while (isNotSelectedOption) {
			System.out.println("Please enter 1 to start execution:");
			int option = scanner.nextInt();
			switch (option) {
			case 1:
				Revisor revisor = new Revisor(bank);
				bank.setRevisor(revisor);
				scheduledExecutorService.
				scheduleWithFixedDelay(revisor,
						0, 5, TimeUnit.SECONDS);
				bank.start();
				isNotSelectedOption = false;
				break;
			default:
				break;
			}
		}
		isNotSelectedOption = true;
		// option to stop
		while (isNotSelectedOption) {
			System.out.println("Please enter 2 to stop execution:");
			int option = scanner.nextInt();
			switch (option) {
			case 2:
				bank.stop();
				scheduledExecutorService.shutdown();
				isNotSelectedOption = false;
				break;
			default:
				break;
			}
		}
	}

	public static Bank createBank(int numberOfAccounts, BigDecimal totalAmount) {
		Currency currancy = new Currency(DEFOULT_CURRANCY);
		Amount amount = new Amount(totalAmount, currancy);
		Bank bank = new Bank(amount, numberOfAccounts);
		Person person = new Person("Jeck", "Brown", new Date(), "Yelow str, 5");
		BigDecimal averageAmmount = totalAmount.divide(new BigDecimal(
				numberOfAccounts), 2);
		BigDecimal lastElementAmmount = totalAmount.subtract(averageAmmount
				.multiply(new BigDecimal(numberOfAccounts - 1)));

		for (int i = 0; i < numberOfAccounts - 1; i++) {
			createAccount(bank, new Amount(averageAmmount, currancy), person);
		}
		createAccount(bank, new Amount(lastElementAmmount, currancy), person);
		return bank;
	}

	public static void createAccount(Bank bank, Amount amount, Person person) {
		bank.addAccount(new Account(amount, person, bank));
	}
}
