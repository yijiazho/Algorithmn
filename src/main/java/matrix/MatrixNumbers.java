package matrix;

import java.util.ArrayList;
import java.util.List;

public class MatrixNumbers {

    private static final int MOD = 1_000_000_007;

    private static final int[][] DIRECTIONS = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    public int countIncreasingPaths(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] numberOfIncreasingPaths = new int[m][n];

        List<Integer> sortedCells = new ArrayList<>();
        for (int i = 0; i < m * n; i++) {
            sortedCells.add(i);
        }

        for (int i = 0; i < m * n; i++) {
            int cellIndex = sortedCells.get(i);
            int row = cellIndex / n;
            int col = cellIndex % n;
            numberOfIncreasingPaths[row][col] = 1;
            search(matrix, row, col, numberOfIncreasingPaths);
        }

        int totalPaths = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                totalPaths += numberOfIncreasingPaths[i][j];
                totalPaths %= MOD;
            }
        }
        return totalPaths;
    }

    private void search(int[][] matrix, int row, int col, int[][] numberOfIncreasingPaths) {
        int m = matrix.length;
        int n = matrix[0].length;

        for (int[] dir : DIRECTIONS) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (newRow >= 0 && newRow < m && newCol >= 0 && newCol < n) {
                if (matrix[newRow][newCol] > matrix[row][col]) {
                    numberOfIncreasingPaths[row][col] += numberOfIncreasingPaths[newRow][newCol];
                    numberOfIncreasingPaths[row][col] %= MOD;
                }
            }
        }
    }
}
