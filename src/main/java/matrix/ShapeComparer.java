package matrix;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShapeComparer {

    private static final int[][][] TRANSFORMS = {
            { { 1, 0 }, { 0, 1 } }, // identity
            { { 0, 1 }, { -1, 0 } }, // rotate 90
            { { -1, 0 }, { 0, -1 } }, // rotate 180
            { { 0, -1 }, { 1, 0 } }, // rotate 270
            { { -1, 0 }, { 0, 1 } }, // reflect vertical
            { { 1, 0 }, { 0, -1 } }, // reflect horizontal
            { { 0, 1 }, { 1, 0 } }, // reflect diagonal
            { { 0, -1 }, { -1, 0 } } // reflect anti-diagonal
    };

    /**
     * Compare if two matrix are equivalent under rotation/reflection/translation.
     * The two matrix should have same size, and contains 0 and 1 only
     * 
     * @param A Any integer matrix A
     * @param B Any integer matrix B
     * @return
     */
    public boolean areShapesEquivalent(int[][] A, int[][] B) {
        Set<String> aShapes = normalize(A);
        Set<String> bShapes = normalize(B);
        return aShapes.equals(bShapes);
    }

    /**
     * Create all shapes under 8 transformations
     * 
     * @param matrix int array of array
     * @return a set of string representing the forms
     */
    private Set<String> normalize(int[][] matrix) {
        List<int[]> points = new ArrayList<>();
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 1)
                    points.add(new int[] { i, j });
            }
        }
        Set<String> shapes = new HashSet<>();
        for (int[][] t : TRANSFORMS) {
            List<int[]> list = new ArrayList<>();
            for (int[] p : points) {
                int x = p[0] * t[0][0] + p[1] * t[0][1];
                int y = p[0] * t[1][0] + p[1] * t[1][1];
                list.add(new int[] { x, y });
            }
            list.sort(Comparator.comparingInt(a -> a[0] * 1000 + a[1]));

            // Normalize shape by shifting top-left point to (0,0)
            int baseX = list.get(0)[0];
            int baseY = list.get(0)[1];
            StringBuilder sb = new StringBuilder();
            for (int[] p : list) {
                sb.append("(").append(p[0] - baseX).append(",").append(p[1] - baseY).append(");");
            }
            shapes.add(sb.toString());
        }
        return shapes;
    }
}
