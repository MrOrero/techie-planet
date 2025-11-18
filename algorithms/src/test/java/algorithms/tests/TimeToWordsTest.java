package algorithms.tests;

import org.junit.Test;

import algorithms.TimeToWords;

import static org.junit.Assert.assertEquals;

public class TimeToWordsTest {

	@Test
	public void shouldReturnFiveOClockWhenMinutesZero() {
		assertEquals("five o'clock", TimeToWords.convertTimeToWords(5, 0));
	}

	@Test
	public void shouldHandleSingleMinutePast() {
		assertEquals("one minute past five", TimeToWords.convertTimeToWords(5, 1));
	}

	@Test
	public void shouldHandleHalfPast() {
		assertEquals("half past three", TimeToWords.convertTimeToWords(3, 30));
	}

	@Test
	public void shouldHandleQuarterTo() {
		assertEquals("quarter to six", TimeToWords.convertTimeToWords(5, 45));
	}

	@Test
	public void shouldHandleTwentyEightPast() {
		assertEquals("twenty-eight minutes past five", TimeToWords.convertTimeToWords(5, 28));
	}

	@Test
	public void shouldHandleThirteenMinutesToSix() {
		assertEquals("thirteen minutes to six", TimeToWords.convertTimeToWords(5, 47));
	}

	@Test
	public void shouldHandleOneMinuteToOneAtEndOfHour() {
		assertEquals("one minute to one", TimeToWords.convertTimeToWords(12, 59));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectInvalidHour() {
		TimeToWords.convertTimeToWords(13, 10);
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRejectInvalidMinute() {
		TimeToWords.convertTimeToWords(10, 60);
	}
}
