package matrix;

import java.util.HashSet;
import java.util.Set;

public class HittingBrick{

    private static final int[][] DIRECTIONS = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    int[] parent;
    int[] size;
    boolean[] connectedToCeiling;

    /**
     * In a 2D grid made of bricks and empty spaces, each time we hit a position in the grid.
     * If the grid is brick it will be removed, and so will be any dangling bricks. A brick 
     * is dangling if it could not connect to the ceiling. All bricks in grid[0] are connected
     * to the ceiling. Find out in each hit, how many dangling bricks will fall. 
     * 
     * @param grid 2D grid made of 1s and 0s. 1 for brick and 0 for empty space
     * @param hits an array of the position in the grid to be hit, with size l
     * @return an array of dangling bricks fall after each hit, with size l
     */
    public int[] hitBricks(int[][] grid, int[][] hits) {
        int m = grid.length;
        int n = grid[0].length;
        int l = hits.length;

        int[] result = new int[l];

        // reverse the union find algorithm, in reverse order of hits
        // starting from the end state, each time we union the neighbors,
        // if it could connect to the ceiling, then the number of danglings
        // is the result of that operation

        // Create the end state
        int[][] endState = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                endState[i][j] = grid[i][j];
            }
        }
        for (int [] hit: hits) {
            endState[hit[0]][hit[1]] = 0;
        }


        // build based on start state, but union based on end state
        parent = new int[m * n];
        size = new int[m * n];
        connectedToCeiling = new boolean[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    // initialize, and union only 2 directions
                    int index = i * n + j;
                    size[index] = 1;
                    parent[index] = index;

                    if (endState[i][j] == 0) {
                        continue;
                    }

                    int top = index - n;
                    if (top >= 0 && endState[top / n][top % n] == 1) {
                        union(index, top);
                    }
                    int left = index - 1;
                    if (left >= 0 && j > 0 && endState[left / n][left % n] == 1) {
                        union(index, left);
                    }
                } else {
                    parent[i * n + j] = i * n + j;
                }
            }
        }

        for (int j = 0; j < n; j++) {
            // make sure all root of the first row point to ceiling
            if (grid[0][j] == 1) {
                connectedToCeiling[root(j)] = true;
            }
        }

        // in reverse order, try to union if the hit position is a brick
        for (int i = l - 1; i >= 0; i--) {
            int[] hit = hits[i];
            int index = hit[0] * n + hit[1];
            if (grid[hit[0]][hit[1]] == 0) {
                // this hit hits the empty cell, do nothing
                continue;
            }

            // update state
            endState[hit[0]][hit[1]] = 1;
            size[index] = 1;

            // for all 4 directions, find those connected to ceiling, and those danglings
            Set<Integer> stableRootIndices = new HashSet<>();
            Set<Integer> danglingRootIndices = new HashSet<>();

            for (int[] dir: DIRECTIONS) {
                int nextI = dir[0] + hit[0];
                int nextJ = dir[1] + hit[1];

                if (nextI >= 0 && nextI < m && nextJ >= 0 && nextJ < n && endState[nextI][nextJ] == 1) {
                    int nextIndex = nextI * n + nextJ;
                    int nextRoot = root(nextIndex);
                    if (connectedToCeiling[nextRoot]) {
                        stableRootIndices.add(nextRoot);
                    } else {
                        danglingRootIndices.add(nextRoot);
                    }
                }
            }

            int danglingSize = 0;
            for (int stableRoot: stableRootIndices) {
                union(index, stableRoot);
            }
            for (int danglingRoot: danglingRootIndices) {
                // if exists stable connection to ceiling, or index itself connected to ceiling 
                if (!stableRootIndices.isEmpty() || connectedToCeiling[index]) {
                    danglingSize += size[danglingRoot];
                }
                union(index, danglingRoot);
            }
            result[i] = danglingSize;
        }

        return result;
    }


    private int root(int p) {
        int cur = p;
        while (parent[parent[cur]] != cur) {
            parent[cur] = parent[parent[cur]];
            cur = parent[cur];
        }
        parent[p] = cur;
        return cur;
    }

    private boolean find(int p, int q) {
        return root(p) == root(q);
    }

    private void union(int p, int q) {
        int rootP = root(p);
        int rootQ = root(q);

        if (rootP == rootQ) {
            return;
        }

        if (size[rootP] >= size[rootQ]) {
            size[rootP] += size[rootQ];
            parent[rootQ] = rootP;
            connectedToCeiling[rootP] = connectedToCeiling[rootP] || connectedToCeiling[rootQ];
        } else {
            size[rootQ] += size[rootP];
            parent[rootP] = rootQ;
            connectedToCeiling[rootQ] = connectedToCeiling[rootP] || connectedToCeiling[rootQ];
        }
    }
}
