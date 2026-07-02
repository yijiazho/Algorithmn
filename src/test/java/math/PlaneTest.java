package math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlaneTest {
    private Plane plane;

    @BeforeEach
    public void setup() {
        plane = new Plane();
    }

    @Test
    public void testMinDistance() {
        int[][] points = new int[][] {
                { -3, 4 },
                { -3, 2 },
                { -1, 3 },
                { -2, 1 },
                { 0, 2 },
                { 1, 2 },
                { 3, 4 },
                { 3, 1 }
        };
        double minDist = plane.minDistance(points);
        assertEquals(1.0, minDist);
    }

    @Test
    public void testMinDistanceSameX() {
        int[][] points = new int[][] {
                { 0, 4 },
                { 0, 2 },
                { 0, 3 },
                { 0, 1 },
                { 0, 7 }
        };
        double minDist = plane.minDistance(points);
        assertEquals(1.0, minDist);
    }
}
