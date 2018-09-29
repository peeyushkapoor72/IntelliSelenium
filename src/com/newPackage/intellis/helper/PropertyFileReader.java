package com.csc.intellis.helper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.csc.intellis.exception.IntelliSException;

public class PropertyFileReader {

	private static volatile PropertyFileReader instance = null;

	/**
	 * Method to get an instance of this class
	 * 
	 * @return
	 */
	public static PropertyFileReader getInstance() {
		if (instance == null) {
			synchronized (PropertyFileReader.class) {
				if (instance == null)
					instance = new PropertyFileReader();
			}
		}
		return instance;
	}

	public Properties readFile(String filename) throws IntelliSException,
			FileNotFoundException {

		Properties prop = new Properties();

		// InputStream ip = this.getClass().getClassLoader()
		// .getResourceAsStream(filename);
		InputStream ip = new FileInputStream(filename);
		try {
			prop.load(ip);
		} catch (IOException ioe) {
			throw new IntelliSException("500",
					"Error in loading configuration file !!");
		}
		return prop;
	}
}