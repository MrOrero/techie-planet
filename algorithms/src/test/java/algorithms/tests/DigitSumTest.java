package algorithms.tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import algorithms.DigitSum;

public class DigitSumTest {

    @Test
    public void testSumDigits() {
        assertEquals(15, DigitSum.sumDigits("12345"));
        assertEquals(0, DigitSum.sumDigits(""));
        assertEquals(9, DigitSum.sumDigits("9"));
        assertEquals(18, DigitSum.sumDigits("99"));
    }

    @Test
    public void testDigitalRoot() {
        assertEquals(6, DigitSum.digitalRoot("12345"));
        assertEquals(0, DigitSum.digitalRoot(""));
        assertEquals(9, DigitSum.digitalRoot("9"));
        assertEquals(9, DigitSum.digitalRoot("99"));
        assertEquals(1, DigitSum.digitalRoot("10"));
    }
}
