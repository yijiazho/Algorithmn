package matrix;

public class MatrixMultiplication {

    public int[][] multiply(int[][] mat1, int[][] mat2) {
        int row1 = mat1.length;
        int col1 = mat1[0].length;
        int row2 = mat2.length;
        int col2 = mat2[0].length;

        if (col1 != row2) {
            throw new IllegalArgumentException("Invalid Matrix Input");
        }
        int[][] res = new int[row1][col2];

        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                for (int k = 0; k < col1; k++) {
                    res[i][j] += mat1[i][k] * mat2[k][j];
                }
            }
        }

        return res;
    }

    public static final void main(String[] args) {


    }

}
