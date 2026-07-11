package matrix.robotCleaner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

public class RobotManager {

    private static final Map<Character, int[]> DIRECTIONS = ImmutableMap.of(
            'U', new int[] { 0, 1 },
            'D', new int[] { 0, -1 },
            'L', new int[] { -1, 0 },
            'R', new int[] { 1, 0 });

    private static final Map<Character, Character> OPPOSITE_DIRECTIONS = ImmutableMap.of(
            'U', 'D',
            'D', 'U',
            'L', 'R',
            'R', 'L');

    /**
     * Find the shortest path from the robot's current position to the target cell,
     * which is guaranteed to exist and different from original cell.
     * 
     * @param master
     * @return
     */
    public int findShortestPath(GridMaster master) {

        // rebuild the graph, key is the unblocked cell, value is the map of direction
        // to the next unblocked cell
        Map<Cell, Map<Character, Cell>> graph = new HashMap<>();
        Cell start = new Cell(0, 0);
        buildGraph(master, graph, new HashSet<>(), start);

        // traverse to find shortest path
        Queue<Cell> queue = new LinkedList<>();
        queue.offer(start);
        return findShortestPath(master, graph, queue, new HashSet<>());
    }

    private int findShortestPath(GridMaster master, Map<Cell, Map<Character, Cell>> graph, Queue<Cell> queue,
            Set<Cell> visited) {
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Cell current = queue.poll();
                if (master.isTarget()) {
                    return steps;
                }

                for (Cell nextCell : graph.get(current).values()) {
                    if (visited.add(nextCell)) {
                        queue.offer(nextCell);
                    }
                }
            }
            steps++;
        }
        return -1;
    }

    private void buildGraph(GridMaster master, Map<Cell, Map<Character, Cell>> graph, Set<Cell> visited, Cell current) {

        // build the graph from the current cell
        for (Map.Entry<Character, int[]> entry : DIRECTIONS.entrySet()) {
            char dir = entry.getKey();
            int[] delta = entry.getValue();

            // check if not blocked
            if (master.canMove(dir)) {
                // build an edge from start to the next cell
                int newX = current.x + delta[0];
                int newY = current.y + delta[1];

                // Create a new cell. I do not care if it's the target in this stage
                Cell nextCell = new Cell(newX, newY);
                // not visited yet, add to graph and traverse
                if (visited.add(nextCell)) {
                    graph.computeIfAbsent(current, k -> new HashMap<>()).put(dir, nextCell);
                    graph.computeIfAbsent(nextCell, k -> new HashMap<>()).put(OPPOSITE_DIRECTIONS.get(dir), current);

                    // move to next cell and traverse
                    master.move(dir);
                    buildGraph(master, graph, visited, nextCell);
                    master.move(OPPOSITE_DIRECTIONS.get(dir));
                }
            }
        }
    }

    class Cell {
        int x;
        int y;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object that) {
            if (this == that) {
                return true;
            }
            if (that == null || getClass() != that.getClass()) {
                return false;
            }
            Cell cell = (Cell) that;
            return x == cell.x && y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
