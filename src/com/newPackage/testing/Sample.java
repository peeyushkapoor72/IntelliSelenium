package com.csc.testing;

public class Sample {

	public static void main(String[] args) {

		// int[] i = { 0, 5, 5, 5, 8, 9 };
		// int x = 5;
		// int n = i.length;
		//
		// System.out.println(frequency(i, n, x));

		reverseString();
	}

	public static int frequency(int a[], int n, int x) {
		int count = 0;
		for (int i = 0; i < n; i++) {
			if (a[i] == x) {
				count++;
			}
		}
		return count;
	}

	public static void reverseString() {

		String str = "Enter string to reverse";
		StringBuilder sBuild = new StringBuilder();
		sBuild.append(str);

		// Scanner read = new Scanner(System.in);
		// String str = read.nextLine();
		String reverse = "";

		for (int i = str.length() - 1; i >= 0; i--) {
			reverse = reverse + str.charAt(i);
		}

		System.out.println("Reversed string is:");
		System.out.println(reverse);
	}
}