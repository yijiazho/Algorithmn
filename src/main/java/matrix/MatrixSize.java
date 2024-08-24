package matrix;

import java.util.*;

public class MatrixSize {
    private static final int[][] DIRECTIONS = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    /**
     * find out the size of the islands suppose we can fill out
     * any position of water and make it a island at most once
     * @param grid Original grid, nxn only contains 0 and 1
     *             0 for water, 1 for island
     * @return the max size of the island of flipping at most
     * one 0 to 1
     */
    public int largestIsland(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];

        // inspired by union find algorithm, but no need to
        // do actual union, only find the root and find the size

        // root is defined as the first entry point
        // root value is i * n + j
        int[][] root = new int[n][n];
        // key is the root of island, value is the size of island
        Map<Integer, Integer> sizeMap = new HashMap<>();
        sizeMap.put(-1, 0);

        int max = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                // find the size and root for each node
                if (grid[i][j] == 1 && !visited[i][j]) {
                    visited[i][j] = true;
                    int size = size(grid, visited, root, i, j, i * n + j);
                    sizeMap.put(i * n + j, size);
                    max = Math.max(max, size);
                } else if (grid[i][j] == 0) {
                    root[i][j] = -1;
                }
            }
        }

        // for each 0 in gird, try to merge neighbours if the root is different
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    max = Math.max(max, merge(grid, root, sizeMap, i, j));
                }
            }
        }

        return max;
    }

    /**
     * Return the size of the island, which is all '1's in the original grid
     * and set all root in this island to the start
     * @param grid size of n * n and made of 0 and 1 only
     * @param visited n * n matrix marking if it's visited
     * @param root root of each island, set to the first encounter value, if 0 the root is -1
     * @param i index
     * @param j index
     * @param start starting position of this traversal in terms of i * n + j
     * @return the size of the island
     */
    private int size(int[][] grid, boolean[][] visited, int[][] root, int i, int j, int start) {
        int size = 1;
        int n = grid.length;
        root[i][j] = start;

        // iterate 4 different directions, add size to it
        for (int[] dir: DIRECTIONS) {
            int ii = i + dir[0];
            int jj = j + dir[1];
            if (ii >= 0 && ii < n && jj >= 0 && jj < n && !visited[ii][jj] && grid[ii][jj] == 1) {
                visited[ii][jj] = true;
                size += size(grid, visited, root, ii, jj, start);
            }
        }

        return size;
    }

    /**
     * try to do an union function for a position with value 0, return the size possible after union
     * @param grid original grid
     * @param root matrix to find the root of each position
     * @param sizeMap map keeping the root of island and size of island
     * @param x index
     * @param y index
     * @return size of island after a union
     */
    private int merge(int[][] grid, int[][] root, Map<Integer, Integer> sizeMap, int x, int y) {
        int size = 1;
        int n = grid.length;
        Set<Integer> uniqueRoots = new HashSet<>();

        for (int[] dir: DIRECTIONS) {
            int xx = x + dir[0];
            int yy = y + dir[1];

            if (xx >= 0 && xx < n && yy >= 0 && yy < n) {
                int rootValue = root[xx][yy];
                uniqueRoots.add(rootValue);
            }
        }

        for (int rootValue: uniqueRoots) {
            size += sizeMap.get(rootValue);
        }

        return size;
    }


    public static final void main(String[] args) {
        MatrixSize matrixSize = new MatrixSize();
        int[][] grid = new int[][]
                {
                        {0, 0, 0, 0, 0, 0, 0},
                        {0, 1, 1, 1, 1, 0, 0},
                        {0, 1, 0, 0, 1, 0, 0},
                        {1, 0, 1, 0, 1, 0, 0},
                        {0, 1, 0, 0, 1, 0, 0},
                        {0, 1, 0, 0, 1, 0, 0},
                        {0, 1, 1, 1, 1, 0, 0}
                };

        int res = matrixSize.largestIsland(grid);
        System.out.println(res);
    }
}
