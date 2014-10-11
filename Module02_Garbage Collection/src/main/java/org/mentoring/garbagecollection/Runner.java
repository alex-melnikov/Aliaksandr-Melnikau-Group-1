package org.mentoring.garbagecollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Runner {

	/**
	 * List to eat memory
	 */
	static List<MyObject> objects = null;

	/**
	 * Number of the objects to create
	 */
	private static final int numberObjectsToCreate = 10;

	/**
	 * Number of objects to remove
	 */
	private static final int numberObjectsToRemove = 5;
	/**
	 * Scanner to read from the console
	 */
	private static Scanner scanner;

	public static void main(String[] args) throws Exception {
		boolean repeat = true;
		// The execution will be continued until repeat is true.
		// Repeat will be set to false in case if exit option will be selected.
		while (repeat) {
			System.out.println("Please specify action:");
			System.out.println("1 - eat memory");
			System.out.println("2 - release memory");
			System.out.println("3 - call System.gc()");
			System.out.println("0 - exit");
			// Scanner to read options
			if (scanner == null) {
				scanner = new Scanner(System.in);
			}
			if (objects == null) {
				objects = new ArrayList<MyObject>();
			}
			// Read action
			int action = scanner.nextInt();
			switch (action) {
			case 0:
				repeat = false;
				objects = null;
				scanner = null;
				break;
			// Eat Memory
			case 1:
				for (int i = 0; i < numberObjectsToCreate; i++) {
					objects.add(new MyObject());
				}
				break;
			// Release Memory
			case 2:
				for (int i = 0; i < numberObjectsToRemove; i++) {
					if (objects.size() > 0) {
						objects.remove(0);
					}
				}
				break;
			case 3:
				System.gc();
				break;
			default:
				System.out.println("Incorrect value, please, try again");
				break;
			}
		}
	}
}
