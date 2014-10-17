package org.mentoring.memorymanagement;

public class HeapOutOfMemory {

	public static void main(String[] args) {
		Object[] ref = new Object[1];
		while (true) {
			ref = new Object[] { ref };
		}
	}
}
