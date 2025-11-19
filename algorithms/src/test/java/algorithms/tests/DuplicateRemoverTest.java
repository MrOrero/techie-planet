package algorithms.tests;

import org.junit.Test;

import algorithms.DuplicateRemover;

import static org.junit.Assert.assertArrayEquals;

public class DuplicateRemoverTest {

    @Test
    public void testRemoveDuplicates() {
        int[][] input = {
            {1, 3, 1, 2, 3, 4, 4, 3, 5},
            {1, 1, 1, 1, 1, 1, 1}
        };
        
        int[][] expected = {
            {1, 3, 0, 2, 0, 4, 0, 0, 5},
            {1, 0, 0, 0, 0, 0, 0}
        };
        
        int[][] actual = DuplicateRemover.removeDuplicates(input);
        
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testWithNoDuplicates() {
        int[][] input = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int[][] expected = {
            {1, 2, 3},
            {4, 5, 6}
        };
        int[][] actual = DuplicateRemover.removeDuplicates(input);
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testWithEmptyArray() {
        int[][] input = {};
        int[][] expected = {};
        int[][] actual = DuplicateRemover.removeDuplicates(input);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testWithEmptySubArrays() {
        int[][] input = {
            {},
            {}
        };
        int[][] expected = {
            {},
            {}
        };
        int[][] actual = DuplicateRemover.removeDuplicates(input);
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i]);
        }
    }

    @Test
    public void testWithNegativeNumbers() {
        int[][] input = {
            {-1, -2, -1},
            {1, -1, 2, -2}
        };
        int[][] expected = {
            {-1, -2, 0},
            {1, -1, 2, -2}
        };
        int[][] actual = DuplicateRemover.removeDuplicates(input);
        for (int i = 0; i < expected.length; i++) {
            assertArrayEquals(expected[i], actual[i]);
        }
    }
}
