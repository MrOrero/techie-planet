package algorithms;

import java.util.Scanner;

public class TimeToWords {

	private static final String INVALID_TIME_MESSAGE = "Invalid time";
	private static final String[] BASE_NUMBERS = {
		"",
		"one",
		"two",
		"three",
		"four",
		"five",
		"six",
		"seven",
		"eight",
		"nine",
		"ten",
		"eleven",
		"twelve",
		"thirteen",
		"fourteen",
		"fifteen",
		"sixteen",
		"seventeen",
		"eighteen",
		"nineteen"
	};
	private static final String ZERO = "zero";

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("Enter hour: ");
			if (!scanner.hasNextLine()) {
				System.out.println(INVALID_TIME_MESSAGE);
				return;
			}
			String hourInput = scanner.nextLine().trim();
			System.out.print("Enter minute: ");
			if (!scanner.hasNextLine()) {
				System.out.println(INVALID_TIME_MESSAGE);
				return;
			}
			String minuteInput = scanner.nextLine().trim();
			int hour = Integer.parseInt(hourInput);
			int minute = Integer.parseInt(minuteInput);
			try {
				String result = convertTimeToWords(hour, minute);
				System.out.println(result);
			} catch (IllegalArgumentException ex) {
				System.out.println(INVALID_TIME_MESSAGE);
			}
		} catch (NumberFormatException ex) {
			System.out.println(INVALID_TIME_MESSAGE);
		}
	}

	public static String convertTimeToWords(int hour, int minute) {
		validateTime(hour, minute);
		if (minute == 0) {
			return convertNumberToWords(hour) + " o'clock";
		}
		if (minute == 15) {
			return "quarter past " + convertNumberToWords(hour);
		}
		if (minute == 30) {
			return "half past " + convertNumberToWords(hour);
		}
		if (minute == 45) {
			return "quarter to " + convertNumberToWords(nextHour(hour));
		}
		if (minute < 30) {
			String minuteinWords = convertNumberToWords(minute);
			String minuteLabel = minute == 1 ? " minute" : " minutes";
			return minuteinWords + minuteLabel + " past " + convertNumberToWords(hour);
		}
		int remainingMinutes = 60 - minute;
		String minuteinWords = convertNumberToWords(remainingMinutes);
		String minuteLabel = remainingMinutes == 1 ? " minute" : " minutes";
		return minuteinWords + minuteLabel + " to " + convertNumberToWords(nextHour(hour));
	}

	private static void validateTime(int hour, int minute) {
		if (hour < 1 || hour > 12 || minute < 0 || minute > 59) {
			throw new IllegalArgumentException("Time values out of range");
		}
	}

	private static int nextHour(int hour) {
		return hour == 12 ? 1 : hour + 1;
	}

	private static String convertNumberToWords(int number) {
		if (number == 0) {
			return ZERO;
		}
		if (number < BASE_NUMBERS.length) {
			return BASE_NUMBERS[number];
		}
		if (number < 30) {
			int remainder = number % 10;
			return remainder == 0 ? "twenty" : "twenty-" + BASE_NUMBERS[remainder];
		}
		throw new IllegalArgumentException("Number out of supported range: " + number);
	}
}