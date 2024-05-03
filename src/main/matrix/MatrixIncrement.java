import java.util.*;

public class MatrixIncrement {
    // one dimension
    public int[] rangeAddQueriesArray(int n, int[][] queries) {
        // Step 1: Initialize the difference array
        // diff[i] is res[i] - res[i - 1]
        int[] diff = new int[n + 1];

        // Step 2: Apply the queries to the difference array
        for (int[] query : queries) {
            int start = query[0];
            int end = query[1];

            diff[start] += 1;
            diff[end + 1] -= 1;
        }

        // Step 3: Build the final array using the difference array
        int[] result = new int[n];
        result[0] = diff[0]; // Initialize the first element
        for (int i = 1; i < n; i++) {
            result[i] = result[i - 1] + diff[i];
        }

        return result;
    }


    public int[][] rangeAddQueries(int n, int[][] queries) {
        // diff[i][j] = matrix[i][j] - matrix[i - 1][j]
        int[][] diff = new int[n + 1][n];

        for (int[] query: queries) {
            int row1 = query[0];
            int col1 = query[1];
            int row2 = query[2];
            int col2 = query[3];

            for (int j = col1; j <= col2; j++) {
                diff[row1][j]++;
                diff[row2 + 1][j]--;
            }
        }

        int[][] matrix = new int[n][n];
        for (int j = 0; j < n; j++) {
            matrix[0][j] = diff[0][j];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = matrix[i - 1][j] + diff[i][j];
            }
        }

        return matrix;
    }

    public static final void main(String[] args) {
        System.out.println(123);
    }
}