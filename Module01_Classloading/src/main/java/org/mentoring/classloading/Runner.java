package org.mentoring.classloading;

import java.lang.reflect.Method;
import java.util.Scanner;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Runner {

	/**
	 * Logger
	 */
	private static Logger logger = LogManager.getLogger("logger");

	/**
	 * Scanner to read from the console
	 */
	private static Scanner scanner;

	/**
	 * Class Loader
	 */
	static JarClassLoader classLoader;

	/**
	 * @return Logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	public static void main(String[] args) throws Exception {
		boolean repeat = true;
		// The execution will be continued until repeat is true.
		// Repeat will be set to false in case if exit option will be selected.
		while (repeat) {
			System.out.println("Please specify action:");
			System.out.println("1 - load class");
			System.out.println("2 - reload class");
			System.out.println("0 - exit");
			// Scanner to read options
			scanner = new Scanner(System.in);
			// Read action
			int action = scanner.nextInt();
			logger.info(action + " ACTION HAS BEEN SELECTED");
			switch (action) {
			case 0:
				repeat = false;
				logger.info("COMPLETED");
				break;
			// Load Class
			case 1:
				// Only initialize class loader
				if (classLoader == null) {
					classLoader = new JarClassLoader();
				}
				classLoader.setJar(getJarPath());
				loadAndInvoceClass(classLoader, getClassName());
				break;
			// Reload Class
			case 2:
				//Class loader will be recreated each time
				classLoader = new JarClassLoader();
				classLoader.setJar(getJarPath());
				loadAndInvoceClass(classLoader, getClassName());
				break;
			default:
				System.out.println("Incorrect value, please, try again");
				break;
			}
		}
	}

	/**
	 * This method loads class creates the class instance, print class methods
	 * and invoke selected one
	 * 
	 * @param ClassLoader
	 *            classLoader
	 * @param String
	 *            className
	 * @throws Exception
	 *             exception
	 */
	private static void loadAndInvoceClass(ClassLoader classLoader,
			String className) throws Exception {
		String methodName = null;
		try {
			Class<?> clazz = classLoader.loadClass(className);
			Object instance = clazz.newInstance();
			Method[] methods = clazz.getDeclaredMethods();
			// Try again if no methods found
			if (methods == null || methods.length == 0) {
				logger.info("NO METHODS DEFINED IN THE CLASS");
				System.out.println("No methods to invoce, please try again");
				return;
			}
			System.out.println("Please select methode to invoce:");
			for (Method method : methods) {
				System.out.println(method.getName());
			}
			methodName = scanner.next();
			clazz.getMethod(methodName).invoke(instance);
			// Unable load class
		} catch (ClassNotFoundException e) {
			logger.error("CLASS NOT FOUND " + className);
			System.out.println("Class not found, please try again");
			return;
			// No this method in the class
		} catch (NoSuchMethodException e) {
			logger.error("NO SUCH METHOD " + methodName);
			System.out.println("No such method, please try again");
			// Something went wrong
		} catch (Exception e) {
			logger.error(e);
			System.out.println("Something went wrong, please try again");
		}
	}

	/** This method returns jar path from the output
	 * @return jar path
	 */
	private static String getJarPath() {
		System.out.println("Please enter jar path");
		String jar = scanner.next();
		logger.info("JAR:" + jar);
		return jar;
	}
	
	/** This method returns class name from the output
	 * @return class name
	 */
	private static String getClassName() {
		System.out.println("Please enter class name");
		String className = scanner.next();
		logger.info("CLASS:" + className);
		return className;
	}
}
