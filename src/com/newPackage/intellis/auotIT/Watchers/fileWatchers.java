package com.csc.intellis.auotIT.Watchers;

import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.nio.file.Path;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.WatchKey;
import java.io.BufferedReader;
import java.nio.file.WatchEvent;
import java.nio.file.WatchService;
import java.nio.file.StandardWatchEventKinds;

import com.csc.test.intellis;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

public class fileWatchers {

	public static String strFilePath = "P:\\MFAutomation\\";// "C:\\Users\\pkapoor22\\Desktop\\filePath\\";

	public static void main(String[] args) throws IOException,
			InterruptedException {

		WatchMyFolder(strFilePath);

		// readFile("C:\\Users\\pkapoor22\\Desktop\\Tc\\123.txt", "Peeyush");
		// String strNewFileName = getFileName(strFilePath);
		// System.out.println(strNewFileName.substring(0,
		// strNewFileName.length() - 4));
	}

	/**
	 * @param strFilePath
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void WatchMyFolder(String strFilePath) throws IOException,
			InterruptedException {

		// Deleting the contents of results folder before the execution
		intellis.deleteContent(new File(strFilePath));
		intellis.createdir(strFilePath);

		boolean bool = false;
		File dir = new File(strFilePath);
		Path myDir = dir.toPath();
		try {
			Boolean isFolder = (Boolean) Files.getAttribute(myDir,
					"basic:isDirectory", NOFOLLOW_LINKS);
			if (!isFolder) {
				throw new IllegalArgumentException("Path: " + myDir
						+ " is not a folder");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("Watching path: " + myDir);
		Thread.sleep(15000);
		try {
			WatchService watcher = myDir.getFileSystem().newWatchService();
			myDir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);// ,
			// StandardWatchEventKinds.ENTRY_DELETE,
			// StandardWatchEventKinds.ENTRY_MODIFY);

			WatchKey watckKey = watcher.take();

			List<WatchEvent<?>> events = watckKey.pollEvents();

			for (WatchEvent<?> event : events) {

				// For Newly Created Files
				if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
					System.out.println("Created: " + event.kind().toString());

					String strNewFileName = getFileName(strFilePath);
					System.out.println(strNewFileName.substring(0,
							strNewFileName.length() - 4));
					System.out.println(strFilePath + "\\" + strNewFileName);

					// Lets Read the File Now
					bool = readFile(strFilePath + "\\" + strNewFileName, "PASS");

					if (bool == false) {
						System.out.println(" Text Not Found. ");
						System.exit(0);
					} else {
						System.out.println(" Text Found. ");
					}
				}

				// // For Deleted Files
				// if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
				// System.out.println("Delete: " + event.context().toString());
				// }
				//
				// // For Modified Files
				// if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
				// System.out.println("Modify: " + event.context().toString());
				// }

			}
		} catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
	}

	/**
	 * @return
	 * @param strPath
	 */
	public static String getFileName(String strPath) {

		String returnFileName = "";
		File folder = new File(strPath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				returnFileName = listOfFiles[i].getName();
			} else if (listOfFiles[i].isDirectory()) {
				System.out.println("Directory " + listOfFiles[i].getName());
			}
		}
		return returnFileName;
	}

	/**
	 * @param data
	 * @param strFilePath
	 */
	public static boolean readFile(String strFilePath, String data) {

		boolean bool = false;
		String line;
		try {
			File file = new File(strFilePath);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains(data)) {
					System.out.println("Text  -  " + line
							+ "  -  Appears, Coming Out of the File. ");
					bool = true;
					break;
				}
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bool;
	}
}