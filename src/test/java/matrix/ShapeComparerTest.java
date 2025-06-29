package matrix;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShapeComparerTest {

    private ShapeComparer shapeComparer;

    @BeforeEach
    public void setup() {
        shapeComparer = new ShapeComparer();
    }

    @Test
    public void testShapeCompareEquals() {
        int[][] A = new int[][] {
                { 1, 1, 1 },
                { 1, 0, 0 },
                { 0, 0, 0 }
        };

        int[][] B = new int[][] {
                { 0, 1, 1 },
                { 0, 0, 1 },
                { 0, 0, 1 }
        };

        int[][] C = new int[][] {
                { 0, 1, 1 },
                { 0, 1, 0 },
                { 0, 1, 0 }
        };

        int[][] D = new int[][] {
                { 0, 1, 0 },
                { 0, 1, 0 },
                { 1, 1, 0 }
        };

        assertTrue(shapeComparer.areShapesEquivalent(A, B));
        assertTrue(shapeComparer.areShapesEquivalent(A, C));
        assertTrue(shapeComparer.areShapesEquivalent(A, D));
    }

    @Test
    public void testShapeCompareNotEquals() {
        int[][] A = new int[][] {
                { 1, 1, 1 },
                { 1, 0, 0 },
                { 0, 0, 0 }
        };

        int[][] B = new int[][] {
                { 0, 1, 1 },
                { 0, 0, 1 },
                { 0, 0, 0 }
        };

        int[][] C = new int[][] {
                { 0, 0, 0 },
                { 0, 1, 1 },
                { 0, 1, 1 }
        };

        int[][] D = new int[][] {
                { 1, 0, 0 },
                { 0, 1, 0 },
                { 1, 1, 0 }
        };

        assertFalse(shapeComparer.areShapesEquivalent(A, B));
        assertFalse(shapeComparer.areShapesEquivalent(A, C));
        assertFalse(shapeComparer.areShapesEquivalent(A, D));
    }
}
