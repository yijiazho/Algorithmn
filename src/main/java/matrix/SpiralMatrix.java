package matrix;

public class SpiralMatrix {
    // r, d, l, u
    private static final int[][] DIRECTIONS = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int[][] generateMatrix(int n) {

        int[][] matrix = new int[n][n];
        if (n == 1) {
            matrix[0][0] = 1;
            return matrix;
        }

        int leftBound = 0;
        int rightBound = n - 1;
        int upBound = 0;
        int botBound = n - 1;
        // right -> down -> left -> up -> right
        int dir = 0;

        int xx = 0;
        int yy = -1;
        for (int i = 1; i <= n * n; i++) {
            int[] direction = DIRECTIONS[dir];
            int x = xx;
            int y = yy;
            if (x + direction[0] >= upBound && x + direction[0] <= botBound &&
                    y + direction[1] >= leftBound && y + direction[1] <= rightBound) {

                xx = x + direction[0];
                yy = y + direction[1];
            } else {
                if (dir == 0) {
                    upBound++;
                } else if (dir == 1) {
                    rightBound--;
                } else if (dir == 2) {
                    botBound--;
                } else {
                    leftBound++;
                }

                // change direction
                dir = (dir + 1) % 4;
                direction = DIRECTIONS[dir];
                xx = x + direction[0];
                yy = y + direction[1];
            }

            matrix[xx][yy] = i;
        }

        return matrix;
    }


    public static final void main(String[] args) {
        SpiralMatrix instance = new SpiralMatrix();
        int[][] output = instance.generateMatrix(3);


    }
}
