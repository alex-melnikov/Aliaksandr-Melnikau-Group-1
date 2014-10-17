package org.mentoring.memorymanagement;

public class TestClassLoader extends ClassLoader {

	public TestClassLoader() {
		super();
	}

	public Class<?> _defineClass(String name, byte[] byteCodes) {
		return super.defineClass(name, byteCodes, 0, byteCodes.length);
	}

}
