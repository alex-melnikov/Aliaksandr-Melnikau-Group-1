package org.mentoring.memorymanagement;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

public class PermGenOutOfMemory {
	
	/**
	 * Logger
	 */
	static final Logger logger = Logger.getLogger(PermGenOutOfMemory.class);
	
	public static void main(String[] args) throws Exception {
		Class<?> clazz = PermGenOutOfMemory.class;
		byte[] buffer = loadByteCode(clazz, clazz.getName());

		TestClassLoader loader = new TestClassLoader();
		for (long index = 0; index < Long.MAX_VALUE; index++) {
			String newClassName = "_"
					+ String.format("%0" + (clazz.getSimpleName().length() - 1)
							+ "d", index);
			byte[] newClassData = new String(buffer, "latin1").replaceAll(
					clazz.getSimpleName(), newClassName).getBytes("latin1");
			loader._defineClass(
					clazz.getName()
							.replace(clazz.getSimpleName(), newClassName),
					newClassData);
		}
	}

	/** This Method Loads Class By Code
	 * @param loader Class Loader
	 * @param className Class Name
	 * @return
	 * @throws IOException
	 */
	public static byte[] loadByteCode(Class<?> loader, String className)
			throws IOException {
		InputStream is = null;
		ByteArrayOutputStream os = null;
		byte[] result = null;
		try {
			logger.info("Load Class " + className);
			String fileName = "/" + className.replaceAll("\\.", "/") + ".class";
			is = loader.getResourceAsStream(fileName);
			os = new ByteArrayOutputStream(4096);
			byte[] buff = new byte[1024];
			int i;
			while ((i = is.read(buff)) >= 0) {
				os.write(buff, 0, i);
			}
			result = os.toByteArray();
		} finally {
			if (is != null) {
				is.close();
			}
			if (os != null) {
				os.close();
			}
		}

		return result;
	}
}
