package matrix;

public class SearchMatrix {

    // search in a matrix, where
    // each role is ascending
    // each column is ascending

    // given current i, j, we split the matrix into 4 parts
    // top left is always smaller, and bottom right is always larger
    // wee can use O(1) to shrink the problem into 3/4 size
    // by either removing top left corner or bottom right corner
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;

        return searchPartOfMatrix(matrix, target, 0, m - 1, 0, n - 1);
    }

    private boolean searchPartOfMatrix(int[][] matrix, int target, int rowStart, int rowEnd, int colStart, int colEnd) {
        if (rowStart > rowEnd || colStart > colEnd ||
                target < matrix[rowStart][colStart] || target > matrix[rowEnd][colEnd]) {
            return false;
        }

        int rowMid = rowStart + (rowEnd - rowStart) / 2;
        int colMid = colStart + (colEnd - colStart) / 2;

        // binary search for single row
        if (rowStart == rowEnd) {
            while (colStart <= colEnd) {
                colMid = colStart + (colEnd - colStart) / 2;
                if (matrix[rowStart][colMid] == target) {
                    return true;
                } else if (matrix[rowStart][colMid] > target) {
                    colEnd = colMid - 1;
                } else {
                    colStart = colMid + 1;
                }
            }
            // not in this row
            return false;
        }

        // binary search for single col
        if (colStart == colEnd) {
            while (rowStart <= rowEnd) {
                rowMid = rowStart + (rowEnd - rowStart) / 2;
                if (matrix[rowMid][colStart] == target) {
                    return true;
                } else if (matrix[rowMid][colStart] > target) {
                    rowEnd = rowMid - 1;
                } else {
                    rowStart = rowMid + 1;
                }
            }
            // not in this col
            return false;
        }


        if (matrix[rowMid][colMid] == target) {
            return true;
        } else if (matrix[rowMid][colMid] > target) {
            // eliminate bottom right corner
            return searchPartOfMatrix(matrix, target, rowStart, rowEnd, colStart, colMid - 1) ||
                    searchPartOfMatrix(matrix, target, rowStart, rowMid - 1, colMid, colEnd);

        } else {
            // eliminate top left corner
            return searchPartOfMatrix(matrix, target, rowStart, rowMid, colMid + 1, colEnd) ||
                    searchPartOfMatrix(matrix, target, rowMid + 1, rowEnd, colStart, colEnd);
        }
    }

    public boolean searchMatrix2(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0;
        int j = n - 1;
        while(i < m && j >= 0){
            if(matrix[i][j]==target) return true;
            else if(matrix[i][j] > target){
                j--;
            }
            else{
                i++;
            }
        }
        return false;
    }


    public static final void main(String[] args) {
        int[][] matrix = new int[][]
                {
                        {1,4,7,11,15},
                        {2,5,8,12,19},
                        {3,6,9,16,22},
                        {10,13,14,17,24},
                        {18,21,23,26,30}
                };

        SearchMatrix instance = new SearchMatrix();
        boolean res = instance.searchMatrix(matrix, 24);
        System.out.println(res);
    }
}
