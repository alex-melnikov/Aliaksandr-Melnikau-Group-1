package org.mentoring.classloading;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * ClassLoader for loading classes from jar
 * 
 */
public class JarClassLoader extends ClassLoader {

	/**
	 * Path to jar file
	 */
	private String jar;
	/**
	 * Jar File
	 */
	private JarFile jarFile;

	private static Logger logger = LogManager.getLogger("logger");

	/**
	 * Class Loader
	 */
	static ClassLoader classLoader;

	public String getJar() {
		return jar;
	}

	public void setJar(String jar) {
		this.jar = jar;
	}

	/**
	 * @return Logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	public JarClassLoader() {
		// calls the parent class loader's constructor
		super(JarClassLoader.class.getClassLoader());
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		// Load class using this loader
		JarFile file = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			file = new JarFile(jar);
			JarEntry entry = file
					.getJarEntry(name.replace('.', '/') + ".class");
			if (entry == null) {
				throw new ClassNotFoundException(name);
			}
			byte[] array = new byte[1024];
			in = file.getInputStream(entry);
			out = new ByteArrayOutputStream(array.length);
			int length = in.read(array);
			while (length > 0) {
				out.write(array, 0, length);
				length = in.read(array);
			}
			return defineClass(name, out.toByteArray(), 0, out.size());
		} catch (IOException exception) {
			logger.error("CLASS FILE NOT FOUND");
			throw new ClassNotFoundException(name, exception);
		} finally {
			closeResources(file, in, out);
		}
	}

	@Override
	protected URL findResource(String name) {
		JarEntry entry = this.jarFile.getJarEntry(name);
		if (entry == null) {
			return null;
		}
		try {
			return new URL("jar:file:" + this.jarFile.getName() + "!/"
					+ entry.getName());
		} catch (MalformedURLException exception) {
			logger.error("EXCEPTION:" + exception.getMessage());
			return null;
		}
	}

	@Override
	protected Enumeration findResources(final String name) {
		return new Enumeration() {
			private URL element = findResource(name);

			public boolean hasMoreElements() {
				return this.element != null;
			}

			public URL nextElement() {
				if (this.element != null) {
					URL element = this.element;
					this.element = null;
					return element;
				}
				logger.error("NO SUCH ELEMENT");
				throw new NoSuchElementException();
			}
		};
	}

	/**
	 * This method close resources
	 * 
	 * @param file
	 *            JarFile
	 * @param in
	 *            InputStream
	 * @param out
	 *            ByteArrayOutputStream
	 */
	private static void closeResources(JarFile file, InputStream in,
			ByteArrayOutputStream out) {
		// Each recourse is closed in the separate try catch block to be sure
		// that other will
		// be closed if something will go wrong with first o second
		if (file != null) {
			try {
				file.close();
			} catch (IOException e) {
				logger.error(e);
			}
		}
		if (in != null)
			try {
				in.close();
			} catch (IOException e) {
				logger.error(e);
			}
		if (out != null)
			try {
				out.close();
			} catch (IOException e) {
				logger.error(e);
			}
	}
}
