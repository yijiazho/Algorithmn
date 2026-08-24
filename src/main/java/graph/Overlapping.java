package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Overlapping {

    public int numberToRemove(int[][] intervals) {

        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int i = 0; i < intervals.length; i++) {
            graph.put(i, new HashSet<>());
        }

        for (int i = 0; i < intervals.length; i++) {
            for (int j = i + 1; j < intervals.length; j++) {
                if (overlap(intervals[i], intervals[j])) {
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        // Sort by the number of edges, and then by the index of the interval
        TreeSet<Integer> treeSet = new TreeSet<>((a, b) -> {
            if (graph.get(a).size() == graph.get(b).size()) {
                return a - b;
            } else {
                return graph.get(a).size() - graph.get(b).size();
            }
        });
        treeSet.addAll(graph.keySet());

        int count = 0;
        while (!graph.get(treeSet.last()).isEmpty()) {
            int index = treeSet.last();
            treeSet.remove(index);
            for (int neighbor : graph.get(index)) {
                treeSet.remove(neighbor);
                graph.get(neighbor).remove(index);
                treeSet.add(neighbor);
            }
            count++;
        }

        return count;
    }

    private boolean overlap(int[] interval1, int[] interval2) {
        return interval1[0] < interval2[1] && interval2[0] < interval1[1];
    }
}
