package com.csc.testing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class setDate {

	static int currYear;
	static int currDate;
	static int currMonth;

	/**
	 * SETDATE(+30,current,1)
	 */
	public static void main(String[] args) {

		new SimpleDateFormat("DD-MMM-yyyy");
		Calendar nowDate = Calendar.getInstance();
		nowDate = Calendar.getInstance();

		String inputDate = "+1,,";// "+30,+2,+10";
		ArrayList<String> aList = new ArrayList<String>(Arrays.asList(inputDate
				.split(",")));

		for (int i = 0; i < aList.size(); i++) {

			System.out.println(aList.get(i));

			if (i == 0) {

				System.out.println("year");

				char first = aList.get(i).charAt(0);
				String firstCharOfYear = String.valueOf(first);
				System.out.println(firstCharOfYear);
				int year = Integer.parseInt(aList.get(i).substring(1));

				if (firstCharOfYear.contains("+")) {

					System.out.println("we have to add years");
					System.out.println(year);
					nowDate.add(Calendar.YEAR, +year);
					currYear = nowDate.get(Calendar.YEAR);

				} else if (firstCharOfYear.contains("-")) {

					System.out.println("we have to subtract years");
					nowDate.add(Calendar.YEAR, -year);
					currYear = nowDate.get(Calendar.YEAR);

				} else {

					System.out
							.println("no logical operator was mentioned, so skiiping the scenario.");

				}

			} else if (i == 1) {

				System.out.println("month");

				char first = aList.get(i).charAt(0);
				String firstCharOfMonth = String.valueOf(first);
				System.out.println(firstCharOfMonth);
				int month = Integer.parseInt(aList.get(i).substring(1));
				System.out.println(month);

				if (firstCharOfMonth.contains("+")) {

					nowDate.add(Calendar.MONTH, +month);
					currMonth = nowDate.get(Calendar.MONTH);

				} else if (firstCharOfMonth.contains("-")) {

					nowDate.add(Calendar.MONTH, -month);
					currMonth = nowDate.get(Calendar.MONTH);

				} else {

					System.out
							.println("no logical operator was mentioned, so skiiping the scenario.");
				}

			} else if (i == 2) {

				System.out.println("date");

				char first = aList.get(i).charAt(0);
				String firstCharOfDate = String.valueOf(first);
				System.out.println(firstCharOfDate);
				int date = Integer.parseInt(aList.get(i).substring(1));
				System.out.println(date);

				if (firstCharOfDate.contains("+")) {

					nowDate.add(Calendar.DATE, -date);
					currMonth = nowDate.get(Calendar.DATE);

				} else if (firstCharOfDate.contains("-")) {

					nowDate.add(Calendar.DATE, -date);
					currMonth = nowDate.get(Calendar.DATE);

				} else {

					System.out
							.println("no logical operator was mentioned, so skiiping the scenario.");
				}
			}
		}
		System.out.println(currYear + "-" + currMonth + "-" + currDate);
	}

	/**
	 * @param data
	 */
	public static void newDate(String data) {

		char first = data.charAt(0);
		String firstChar = String.valueOf(first);
		// System.out.println(firstChar);
		if (firstChar.contains("+")) {
			System.out.println("Got +");
		} else if (firstChar.contains("-")) {
			System.out.println("Got -");
		} else {
			System.out.println("non of the above detected.");
		}
	}

	/**
	 * @param subMonth
	 */
	public static void subMonth(int subMonth) {
		Calendar now = Calendar.getInstance();
		now = Calendar.getInstance();
		now.add(Calendar.MONTH, -subMonth);
		System.out.println("date before " + subMonth + " months : "
				+ (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE)
				+ "-" + now.get(Calendar.YEAR));
	}

	/**
	 * @param addMonth
	 */
	public static void addMonth(int addMonth) {
		Calendar now = Calendar.getInstance();
		now = Calendar.getInstance();
		now.add(Calendar.MONTH, +addMonth);
		System.out.println("date after " + addMonth + " months : "
				+ (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE)
				+ "-" + now.get(Calendar.YEAR));
	}

	/**
	 * @param subYear
	 */
	public static void subYear(int subYear) {
		Calendar now = Calendar.getInstance();
		now = Calendar.getInstance();
		now.add(Calendar.YEAR, -subYear);
		System.out.println("date before " + subYear + " months : "
				+ (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE)
				+ "-" + now.get(Calendar.YEAR));
	}

	/**
	 * @param addYear
	 */
	public static void addYear(int addYear) {
		Calendar nowYear = Calendar.getInstance();
		nowYear = Calendar.getInstance();
		nowYear.add(Calendar.YEAR, +addYear);
		System.out
				.println("date before " + addYear + " months : "
						+ (nowYear.get(Calendar.MONTH) + 1) + "-"
						+ nowYear.get(Calendar.DATE) + "-"
						+ nowYear.get(Calendar.YEAR));
	}

	/**
	 * @param subDate
	 */
	public static void subDate(int subDate) {
		Calendar now = Calendar.getInstance();
		now = Calendar.getInstance();
		now.add(Calendar.DATE, -subDate);
		System.out.println("date before " + subDate + " months : "
				+ (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE)
				+ "-" + now.get(Calendar.YEAR));
	}

	/**
	 * @param addDate
	 */
	public static void addDate(int addDate) {
		Calendar now = Calendar.getInstance();
		now = Calendar.getInstance();
		now.add(Calendar.YEAR, +addDate);
		System.out.println("date before " + addDate + " months : "
				+ (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.DATE)
				+ "-" + now.get(Calendar.YEAR));
	}
}