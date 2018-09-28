package com.csc.testing;

import java.util.Date;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ArrayList;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;

/**
 * 
 * @author pkapoor22
 *
 *         setDate(+/-Year,+/-Month,+/-Day)
 * 
 */

public class newDateLogic {

	public static void main(String[] args) {
		// setDate("-1,+2,");
		// String abc = setDate("*5016,*12,*31");

		String abc = setDate(",-5,");
		System.out.println(abc + "==========================");
	}

	/**
	 * @param inputDate
	 */
	public static String setDate(String inputDate) {

		Calendar cal = Calendar.getInstance();
		ArrayList<String> aList = new ArrayList<String>(Arrays.asList(inputDate
				.split(",")));
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		Date date = null;

		for (int i = 0; i < aList.size(); i++) {

			char first = 0;
			try {
				first = aList.get(i).charAt(0);
			} catch (StringIndexOutOfBoundsException siobe) {
				System.out
						.println("Either of Year, Month or Day is missing in the req.");
			}
			String firstChar = String.valueOf(first);
			System.out.println(aList.get(i));

			if (i == 2) {

				if (firstChar.contains("+")) {
					System.out.println("Add Year");
					cal.add(Calendar.YEAR, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("-")) {
					System.out.println("Sub Year");
					cal.add(Calendar.YEAR, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("*")) {
					System.out
							.println("User Wants to set specific Year" + "\n");
					int setYear = Integer.parseInt(aList.get(i).substring(1));
					System.out.println(setYear);
					cal.set(Calendar.YEAR, setYear);
					date = cal.getTime();

				}
			} else if (i == 0) {

				if (firstChar.contains("+")) {
					System.out.println("Add MONTH");
					cal.add(Calendar.MONTH, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("-")) {
					System.out.println("Sub MONTH");
					cal.add(Calendar.MONTH, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("*")) {
					System.out.println("User Wants to set specific Month"
							+ "\n");
					cal.set(Calendar.MONTH,
							(Integer.parseInt(aList.get(i).substring(1)) - 1));
					date = cal.getTime();

				}
			} else if (i == 1) {

				if (firstChar.contains("+")) {
					System.out.println("Add DATE");
					cal.add(Calendar.DATE, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();

				} else if (firstChar.contains("-")) {
					System.out.println("Sub DATE");
					cal.add(Calendar.DATE, +Integer.parseInt(aList.get(i)));
					date = cal.getTime();
					System.out.println(date);

				} else if (firstChar.contains("*")) {
					System.out
							.println("User Wants to set specific Date" + "\n");
					int setDate = Integer.parseInt(aList.get(i).substring(1));
					System.out.println(setDate);
					cal.set(Calendar.DATE, setDate);
					date = cal.getTime();
				}
			}
		}
		String newDateAfterChange = null;
		newDateAfterChange = format1.format(date);
		System.out.println(newDateAfterChange);
		return newDateAfterChange;
	}

	public static String getMonthForInt(int num) {
		String month = "wrong";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11) {
			month = months[num];
		}
		return month;
	}

	public static String getMonthName(int num) {
		String month = "wrong";

		String[] months = new String[13];
		months[1] = "January";
		months[2] = "Febuary";
		months[3] = "March";
		months[4] = "April";
		months[5] = "May";
		months[6] = "June";
		months[7] = "July";
		months[8] = "August";
		months[9] = "September";
		months[10] = "October";
		months[11] = "November";
		months[12] = "December";

		if (num >= 1 && num <= 12) {
			month = months[num];
		}
		return month;
	}
}