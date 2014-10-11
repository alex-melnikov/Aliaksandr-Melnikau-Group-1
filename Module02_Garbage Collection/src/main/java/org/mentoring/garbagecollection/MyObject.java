package org.mentoring.garbagecollection;



/**
 * Object to eat memory
 *
 */
public class MyObject {
	
	/**
	 * Helps to eat memory
	 */
	static byte [] data = new byte[2000000];
	
	/**
	 * Default constructor
	 */
	public MyObject()
	{
		super();
		System.out.println("MyObject created");
	}
	
	@Override
	protected void finalize() throws Throwable {
		System.out.println(this.toString() + " finalaze");
		super.finalize();
	}
}
