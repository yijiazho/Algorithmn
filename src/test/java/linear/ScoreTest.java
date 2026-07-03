package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScoreTest {

    private Score score;

    @BeforeEach
    public void setup() {
        score = new Score();
    }

    @Test
    public void testMaxPointsSorted() {
        int[] points = new int[] { 3, 4, 5, 7, 9 };
        int[] ages = new int[] { 7, 8, 9, 10, 6 };

        int maxPoints = score.maxPoints(points, ages);
        assertEquals(19, maxPoints);
    }

    @Test
    public void testMaxPointsUnsorted() {
        int[] points = new int[] { 5, 3, 9, 4, 7 };
        int[] ages = new int[] { 9, 7, 6, 8, 10 };

        int maxPoints = score.maxPoints(points, ages);
        assertEquals(19, maxPoints);
    }

    @Test
    public void testMaxPointsTiedPoint() {
        int[] points = new int[] { 10, 9, 9, 8, 8 };
        int[] ages = new int[] { 2, 3, 9, 7, 6 };

        int maxPoints = score.maxPoints(points, ages);
        assertEquals(25, maxPoints);
    }

    @Test
    public void testMaxPointsTiedAge() {
        int[] points = new int[] { 16, 2, 3, 6, 7 };
        int[] ages = new int[] { 8, 8, 6, 6, 7 };

        int maxPoints = score.maxPoints(points, ages);
        assertEquals(32, maxPoints);
    }
}
