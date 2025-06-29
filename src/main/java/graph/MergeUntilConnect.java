package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MergeUntilConnect {

    private static final int[][] DIRECTIONS = {
            { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 }
    };

    List<Integer> mergeUntilConnect(int[][] matrix) {
        int n = matrix.length;
        List<Integer> list = new ArrayList<>();
        int totalSize = 0;
        Set<Integer> visited = new HashSet<>();

        // initialize
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                list.add(i * n + j);
            }
        }
        Collections.sort(list, (l1, l2) -> {
            int i1 = l1 / n, j1 = l1 % n;
            int i2 = l2 / n, j2 = l2 % n;
            return matrix[i2][j2] - matrix[i1][j1];
        });
        List<List<Integer>> groups = new ArrayList<>();
        for (int i = 0; i < n * n; i++) {
            int size = groups.size();
            if (size == 0 || matrix[list.get(i) / n][list.get(i)
                    % n] != matrix[groups.get(size - 1).get(0) / n][groups.get(size - 1).get(0) % n]) {
                List<Integer> level = new ArrayList<>();
                level.add(list.get(i));
                groups.add(level);
            } else {
                groups.get(size - 1).add(list.get(i));
            }
        }

        List<Integer> res = new ArrayList<>();
        // use union find to merge until all nodes are connected
        UnionFind uf = new UnionFind(n * n);
        for (List<Integer> group : groups) {
            for (int i = 0; i < group.size(); i++) {
                visited.add(group.get(i));
                totalSize++;
                int row = group.get(i) / n;
                int col = group.get(i) % n;

                // union with neighbors if they are already visisted
                for (int[] dir : DIRECTIONS) {
                    int newRow = row + dir[0];
                    int newCol = col + dir[1];
                    if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < n
                            && visited.contains(newRow * n + newCol)) {
                        if (!uf.find(row * n + col, newRow * n + newCol)) {
                            uf.union(row * n + col, newRow * n + newCol);
                            totalSize--;
                        }
                    }
                }
            }
            res.add(totalSize);
        }
        return res;
    }

}
