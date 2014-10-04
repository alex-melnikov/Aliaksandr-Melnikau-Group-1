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
		// first try to find class in the cache
		Class<?> result = findLoadedClass(name);
		if (result != null) {
			logger.info("CLASS " + name + "found in cache");
			return result;
		}
		// than try to load class by parent
		try {
			result = getParent().loadClass(name);
			logger.info("CLASS IS LOADED BY PARENT");
			return result;
		} catch (ClassNotFoundException e) {
			// Load class using this loader
			try {
				JarFile file;
				file = new JarFile(jar);
				JarEntry entry = file.getJarEntry(name.replace('.', '/')
						+ ".class");
				if (entry == null) {
					throw new ClassNotFoundException(name);
				}
				byte[] array = new byte[1024];
				InputStream in = file.getInputStream(entry);
				ByteArrayOutputStream out = new ByteArrayOutputStream(
						array.length);
				int length = in.read(array);
				while (length > 0) {
					out.write(array, 0, length);
					length = in.read(array);
				}
				return defineClass(name, out.toByteArray(), 0, out.size());
			} catch (IOException exception) {
				logger.error("CLASS FILE NOT FOUND");
				throw new ClassNotFoundException(name, exception);
			}
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
}
