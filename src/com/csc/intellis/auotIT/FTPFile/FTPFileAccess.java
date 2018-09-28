package com.csc.intellis.auotIT.FTPFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPFileAccess {

	public static int port = 21; // ftp uses default port Number 21
	public static String serverAddress = "20.14.209.1";
	public static String strUserName = "Y00938";
	public static String strPassword = "1qaz2wsx";

	public static void main(String[] args) {

		ftpFileFromMainframe(serverAddress, port, strUserName, strPassword,
				"/filename.txt", "E:/ftpServerFile.txt");
	}

	/**
	 * @param port
	 * @param strUserName
	 * @param strPassword
	 * @param serverAddress
	 */
	public static void ftpFileFromMainframe(String serverAddress, int port,
			String strUserName, String strPassword, String remoteFilePath,
			String localfile) {

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(serverAddress, port);
			ftpClient.login(strUserName, strPassword);
			if (ftpClient.isConnected()) {
				ftpClient.enterLocalPassiveMode();
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				// / FTP.ASCII_FILE_TYPE);
				OutputStream outputStream = new BufferedOutputStream(
						new FileOutputStream(localfile));
				boolean success = ftpClient.retrieveFile(remoteFilePath,
						outputStream);
				outputStream.close();

				if (success) {
					System.out.println("Ftp file successfully download.");
				}
			} else {
				System.out.println("Not able to connect using " + strUserName
						+ " and password " + strPassword);
			}

		} catch (IOException ex) {
			System.out
					.println("Error occurs in downloading files from ftp Server : "
							+ ex.getMessage());
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}