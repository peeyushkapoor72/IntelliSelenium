package com.csc.intellis.log4j;

import java.io.File;
import java.io.IOException;
import com.csc.test.intellis;
import org.apache.log4j.Logger;

public class Log4jInit {

	final static Logger logger = Logger.getLogger(intellis.class);

	public Log4jInit() throws IOException {
	}

	/**
	 * @param Directory
	 * @throws IOException
	 */
	public static void createdir(String Directory) throws IOException {
		File dir = new File(Directory);
		if (!dir.exists()) {
			dir.mkdir();
		} else {
			if (!(dir.listFiles().length == 0)) {
				File files[] = dir.listFiles();
				File logfile = files[0];
				logfile.delete();
			}
		}
	}

	/**
	 * @param data
	 */
	public void logInfo(Object data) {
		if (logger.isInfoEnabled())
			logger.info(data);
	}

	/**
	 * @param data
	 */
	public void logError(Object data) {
		logger.error(data);

	}
}