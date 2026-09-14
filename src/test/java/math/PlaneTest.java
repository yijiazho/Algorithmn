package math;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testmaxMinimumDistance() {
        int[][] points = new int[][] {
                { 2, 0 },
                { 0, 2 },
                { 0, 0 },
                { 2, 2 }
        };
        int maxMinDistance = plane.maxMinimumDistance(points);
        assertEquals(4, maxMinDistance);
    }

    @Test
    public void testmaxMinimumDistanceWithSingleSet() {
        int[][] points = new int[][] {
                { 10, 0 },
                { 0, 1 },
                { 0, 0 }
        };
        int maxMinDistance = plane.maxMinimumDistance(points);
        assertEquals(11, maxMinDistance);
    }

    @Test
    public void testNumberOfBlockersNoBlocking() {
        int result = plane.numberOfBlockers(1);
        assertEquals(8, result);
    }

    @Test
    public void testNumberOfBlockersWithBlocking() {
        int result = plane.numberOfBlockers(2);
        assertEquals(16, result);
    }

    @Test
    public void testIsRectangleParallelToCoordinates() {
        int[][] points = new int[][] {
                {0, 0},
                {2, 0},
                {0, 1},
                {2, 1}
        };
        assertTrue(plane.isRectangle(points));
    }

    @Test
    public void testIsRectangleNotParallelToCoordinates() {
        int[][] points = new int[][] {
                {0, 0},
                {1, 3},
                {-1, 1},
                {2, 2}
        };
        assertTrue(plane.isRectangle(points));
    }

    @Test
    public void testIsRectangleFalse() {
        int[][] points = new int[][] {
                {0, 1},
                {1, 3},
                {-1, 1},
                {2, 2}
        };
        assertFalse(plane.isRectangle(points));
    }

    @Test
    public void testMinRotationDegree() {
        int[][] points = new int[][] {
                {1, 0},
                {1, 1},
                {0, 1},
                {-1, 1},
                {-1, 0},
                {-1, -1},
                {0, -1},
                {1, -1}
        };
        double result = plane.minRotationDegree(points, 0, 5);
        assertEquals(180.0, result);
    }

    @Test
    public void testMinRotationDegreeWithFieldOfView() {
        int[][] points = new int[][] {
                {1, 0},
                {1, 1},
                {0, 1},
                {-1, 1},
                {-1, 0},
                {-1, -1},
                {0, -1},
                {1, -1}
        };
        double result = plane.minRotationDegree(points, 45, 5);
        assertEquals(135.0, result);
    }

    @Test
    public void testMinRotationDegreeWithFieldOfViewAndNoROtationRequired() {
        int[][] points = new int[][] {
                {1, 0},
                {1, 1},
                {0, 1},
                {-1, 1},
                {-1, 0},
                {-1, -1},
                {0, -1},
                {1, -1}
        };
        double result = plane.minRotationDegree(points, 45, 2);
        assertEquals(0.0, result);
    }

    @Test
    public void testMinRotationDegreeWithFieldOfViewAndPointsWithSameDegree() {
        int[][] points = new int[][] {
                {1, 0},
                {1, 1},
                {0, 1},
                {-1, 1},
                {-1, 0},
                {-1, -1},
                {0, -1},
                {1, -1},
                {2, 2},
                {3, 3}
        };
        double result = plane.minRotationDegree(points, 45, 5);
        assertEquals(45.0, result);
    }

    @Test
    public void testMinRotationDegreeWithFieldOfViewAndUnevenDistribution() {
        int[][] points = new int[][] {
                {1, 0},
                {1, 1},
                {0, 1},
                {-1, 1},
                {-1, 0},
                {-1, -1},
                {0, -1},
                {1, -1},
                {1, 2},
                {2, 1}
        };
        double result = plane.minRotationDegree(points, 45, 5);
        assertEquals(45.0, result);
    }
}
