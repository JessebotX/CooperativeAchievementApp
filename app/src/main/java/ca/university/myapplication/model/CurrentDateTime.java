package ca.university.myapplication.model;

import java.time.LocalDateTime;

/**
 * Represents the current date and time
 */
public class CurrentDateTime implements Comparable<CurrentDateTime> {
	private static final int BASE = 10;
	private static final int TENS_VALUE = 1;
	private static final int HUNDREDS_VALUE = 2;
	private static final int THOUSANDS_VALUE = 3;
	private static final int TEN_THOUSANDS_VALUE = 4;
	private static final int HUNDRED_THOUSANDS_VALUE = 5;

	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;

	public CurrentDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();

		this.year = localDateTime.getYear();
		this.month = localDateTime.getMonthValue();
		this.day = localDateTime.getDayOfMonth();
		this.hour = localDateTime.getHour();
		this.minute = localDateTime.getMinute();
		this.second = localDateTime.getSecond();
	}

	public int getYear() {
		return year;
	}

	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}

	public int getSecond() {
		return second;
	}

	@Override
	public int compareTo(CurrentDateTime other) {
		int compareYear = Integer.compare(this.year, other.year) * (int)Math.pow(BASE, HUNDRED_THOUSANDS_VALUE);
		int compareMonth = Integer.compare(this.month, other.month) * (int)Math.pow(BASE, TEN_THOUSANDS_VALUE);
		int compareDay = Integer.compare(this.day, other.day) * (int)Math.pow(BASE, THOUSANDS_VALUE);
		int compareHour = Integer.compare(this.hour, other.hour) * (int)Math.pow(BASE, HUNDREDS_VALUE);
		int compareMinute = Integer.compare(this.minute, other.minute) * (int)Math.pow(BASE, TENS_VALUE);
		int compareSecond = Integer.compare(this.second, other.second);

		return compareYear + compareMonth + compareDay + compareHour + compareMinute + compareSecond;
	}
}
