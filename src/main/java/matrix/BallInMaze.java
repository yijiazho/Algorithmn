package matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

public class BallInMaze {
    private static final int[][] DIRECTIONS = new int[][] { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };

    // lexicographical order is down, left, right, up
    private static final Map<Character, int[]> DIRECTION_MAP = Map.of(
            'd', new int[] { 1, 0 },
            'l', new int[] { 0, -1 },
            'r', new int[] { 0, 1 },
            'u', new int[] { -1, 0 });

    /**
     * For a maze with 0 and 1s, 0 for space and 1 for walls,
     * if the ball can start from position start and end at
     * position destination, given the ball only stops after
     * reaching a wall
     * 
     * @param maze        int[][] for maze
     * @param start       position for start, int[2]
     * @param destination position for destination, int[2]
     * @return if the ball can stops at the ending position
     */
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        // do a search, each time push the ball in 4 directions,
        // and find the next position, which the the 0 before 1

        Queue<int[]> positionQueue = new LinkedList<>();
        positionQueue.offer(start);

        boolean[][] visited = new boolean[maze.length][maze[0].length];
        visited[start[0]][start[1]] = true;

        while (!positionQueue.isEmpty()) {
            int[] position = positionQueue.poll();

            if (position[0] == destination[0] && position[1] == destination[1]) {
                return true;
            }

            for (int[] dir : DIRECTIONS) {
                int[] nextPosition = rollInDirection(maze, position, destination, dir);
                if (nextPosition != null && !visited[nextPosition[0]][nextPosition[1]]) {
                    visited[nextPosition[0]][nextPosition[1]] = true;
                    positionQueue.offer(nextPosition);
                }
            }
        }
        // traverse all possible positions, but none same
        return false;
    }

    /**
     * return the stopping position for a ball in the maze,
     * starting at position start, with initial velocity at
     * direction, if no wall is ahead return null
     * 
     * @param maze      int[m][n] with only 0 and 1
     * @param start     start position, int[2]
     * @param direction velocity direction, int[2]
     * @return the ending position, or null if rolls out of maze
     */
    private int[] rollInDirection(int[][] maze, int[] start, int[] destination, int[] direction) {
        int m = maze.length;
        int n = maze[0].length;
        int i = start[0];
        int j = start[1];

        while (i >= 0 && i < m && j >= 0 && j < n && maze[i][j] == 0) {
            if (i == destination[0] && j == destination[1]) {
                return new int[] { i, j };
            }

            i += direction[0];
            j += direction[1];
        }
        return new int[] { i - direction[0], j - direction[1] };
    }

    /**
     * Find the shortest path for a ball from start to destination in a maze. The
     * ball can only roll in four directions until it hits a wall or destination.
     * The path is represented by a string of directions: 'u' (up), 'd' (down), 'l'
     * (left), and 'r' (right). If there are multiple shortest paths, return the
     * lexicographically smallest one. If the ball cannot reach the destination,
     * return "impossible".
     * 
     * @param maze        int[][] representing the maze, where 0 is an empty space
     *                    and 1 is a wall
     * @param start       int[] representing the starting position of the ball
     * @param destination int[] representing the destination position of the ball
     * @return String representing the shortest path or "impossible"
     */
    public String findShortestPath(int[][] maze, int[] start, int[] destination) {
        int m = maze.length;
        int n = maze[0].length;
        // build a graph, each postion is vertex, and the weighted edge is the distance
        // to the next position by hitting the wall or destination.

        List<Map<Character, Edge>> graph = new ArrayList<>();
        for (int i = 0; i < m * n; i++) {
            graph.add(new HashMap<>());
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (maze[i][j] == 0 && (i != destination[0] || j != destination[1])) {
                    int key = i * n + j;
                    for (char dir : DIRECTION_MAP.keySet()) {
                        int[] direction = DIRECTION_MAP.get(dir);

                        // if contains this direction in the graph, skip it
                        if (graph.get(key).containsKey(dir)) {
                            continue;
                        }

                        int x = i;
                        int y = j;
                        int distance = 0;
                        // keep going in the direction until hitting a wall or destination
                        while (x >= 0 && x < m && y >= 0 && y < n && maze[x][y] == 0) {
                            if (x == destination[0] && y == destination[1]) {
                                break;
                            }
                            x += direction[0];
                            y += direction[1];
                            distance++;
                        }

                        if (x < 0 || x >= m || y < 0 || y >= n || maze[x][y] == 1) {
                            // if hit a wall, go back to the last valid position
                            x -= direction[0];
                            y -= direction[1];
                            distance--;
                        }

                        // create edges from [(i, j), (x, y)] to (x, y) with weight distance and
                        // direction dir to avoid duplicate traversal in same direction.
                        int nextI = i;
                        int nextJ = j;
                        while (distance > 0) {
                            Edge edge = new Edge(x * n + y, distance);
                            graph.get(nextI * n + nextJ).put(dir, edge);
                            nextI += direction[0];
                            nextJ += direction[1];
                            distance--;
                        }
                    }
                }
            }
        }

        // then use Dikstra's algorithm to find the shortest path

        int[] dist = new int[m * n];
        String[] path = new String[m * n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(path, "x");
        dist[start[0] * n + start[1]] = 0;
        path[start[0] * n + start[1]] = "";

        PriorityQueue<Integer> pq = new PriorityQueue<>((i, j) -> {
            if (dist[i] != dist[j]) {
                return dist[i] - dist[j];
            } else {
                return path[i].compareTo(path[j]);
            }
        });

        pq.offer(start[0] * n + start[1]);
        while (!pq.isEmpty()) {
            int cur = pq.poll();

            if (cur == destination[0] * n + destination[1]) {
                return path[cur];
            }

            if (graph.get(cur) != null) {
                // Update the distance and path for each neighbor
                for (Map.Entry<Character, Edge> entry : graph.get(cur).entrySet()) {
                    Character direction = entry.getKey();
                    Edge edge = entry.getValue();
                    int next = edge.to;
                    int newDist = dist[cur] + edge.weight;
                    String newPath = path[cur] + direction;
                    if (newDist < dist[next] || (newDist == dist[next] && newPath.compareTo(path[next]) < 0)) {
                        dist[next] = newDist;
                        path[next] = newPath;
                        pq.offer(next);
                    }
                }
            }
        }

        return "impossible";
    }

    class Edge {
        int to;
        int weight;

        public Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }
}
