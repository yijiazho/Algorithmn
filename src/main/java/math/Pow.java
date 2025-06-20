package math;

public class Pow {

    /**
     * Similar to Math.pow, but with log(n) time complexity
     * 
     * @param x the base
     * @param n the exponential
     * @return the value of the pow
     */
    public double pow(double x, int n) {
        if (n < 0) {
            return 1.0 / pow(x, -n);
        }
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        double half = pow(x, n / 2);
        if (n % 2 == 0) {
            return half * half;
        } else {
            return half * half * x;
        }
    }

    /**
     * similar to Math.pow, return the power of a int matrix
     * 
     * @param A, base, must have same length and height
     * @param n, exponential, must be positive
     * @return A^n in linear algebra in log(n) time complexity
     */
    public int[][] pow(int[][] A, int n) {
        int h = A.length;
        int w = A[0].length;
        if (h != w) {
            throw new IllegalArgumentException("Matrix height and width mismatch");
        }

        if (n == 1) {
            return A;
        }

        int[][] half = pow(A, n / 2);
        if (n % 2 == 0) {
            return multiply(half, half);
        } else {
            return multiply(multiply(half, half), A);
        }
    }

    /**
     *
     * @param A matrix, m * n
     * @param B matrix n * p
     * @return Multiplication of two matrix
     */
    private int[][] multiply(int[][] A, int[][] B) {
        int m = A.length;
        int n = A[0].length;
        if (B.length != n) {
            throw new IllegalArgumentException("Matrix size mismatch");
        }
        int p = B[0].length;

        int[][] res = new int[n][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    res[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return res;
    }
}
