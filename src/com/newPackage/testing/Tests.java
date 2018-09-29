package com.csc.testing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Tests {

	public static void main(String args[]) throws ParseException {
		String s = "1234567891011121";
		try {
			long l = Long.parseLong(s);
			System.out.println("long l = " + l);
		} catch (NumberFormatException nfe) {
			System.out.println("NumberFormatException: " + nfe.getMessage());
		}
	}

	/**
	 * @return yesterdaysDate
	 */
	public static String getYesterdayDate() {

		Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
		String yesterdaysDate = new SimpleDateFormat("MM/dd/yyyy").format(yesterday);
		System.out.println(yesterdaysDate);

		return yesterdaysDate;
	}

	/**
	 * @return tomorrowsDate
	 */
	public static String getTommorowsDate() {

		Date dt = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dt);
		calendar.add(Calendar.DATE, 1);
		dt = calendar.getTime();
		String tomorrowsDate = new SimpleDateFormat("MM/dd/yyyy").format(dt);
		System.out.println(tomorrowsDate);

		return tomorrowsDate;
	}
}