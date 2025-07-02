package interval;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IntervalTest {

    private Interval interval;

    @BeforeEach
    public void setup() {
        interval = new Interval();
    }

    @Test
    public void testMergeIntervals() {
        int[][] intervals = new int[][] {
                { 4, 6 },
                { 3, 8 },
                { 1, 2 },
                { 6, 9 }
        };
        List<int[]> result = interval.merge(intervals);
        assertEquals(2, result.size());
        assertArrayEquals(new int[] { 1, 2 }, result.get(0));
        assertArrayEquals(new int[] { 3, 9 }, result.get(1));
    }

    @Test
    public void testMergeIntervalsOverlapBoundary() {
        int[][] intervals = new int[][] {
                { 4, 6 },
                { 3, 4 },
                { 1, 3 },
                { 6, 10 }
        };
        List<int[]> result = interval.merge(intervals);
        assertEquals(1, result.size());
        assertArrayEquals(new int[] { 1, 10 }, result.get(0));
    }

    @Test
    public void testInsertIntervals() {
        int[][] intervals = new int[][] {
                { 1, 2 },
                { 3, 4 },
                { 5, 8 },
                { 9, 10 }
        };
        int[] newInterval = new int[] { 3, 6 };
        int[][] result = interval.insert(intervals, newInterval);
        int[][] expected = new int[][] {
                { 1, 2 },
                { 3, 8 },
                { 9, 10 }
        };
        assertArrayEquals(expected, result);
    }

    @Test
    public void testInsertIntervalsNoOverlap() {
        int[][] intervals = new int[][] {
                { 1, 2 },
                { 3, 4 },
                { 9, 10 }
        };
        int[] newInterval = new int[] { 5, 6 };
        int[][] result = interval.insert(intervals, newInterval);
        int[][] expected = new int[][] {
                { 1, 2 },
                { 3, 4 },
                { 5, 6 },
                { 9, 10 }
        };
        assertArrayEquals(expected, result);
    }

    @Test
    public void testInsertIntervalsBoundary() {
        int[][] intervals = new int[][] {
                { 1, 2 },
                { 3, 4 },
                { 5, 8 },
                { 9, 10 }
        };
        int[] newInterval = new int[] { 4, 9 };
        int[][] result = interval.insert(intervals, newInterval);
        int[][] expected = new int[][] {
                { 1, 2 },
                { 3, 10 }
        };
        assertArrayEquals(expected, result);
    }
}
