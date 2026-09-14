package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utility.MathUtils.gcd;

public class Plane {

    /**
     * In a 2D plane [-n, n] ^ 2, we can choose any int pair to set a blocker
     * The observer is located at (0, 0). Any positions after that blocker
     * will be blocked. If we place blocker at (x, y), then (2x, 2y), (3x, 3y),
     * ... , (kx, ky) will be blocked. Find out the minimum number of blockers
     * @param n the half size of the border
     * @return minimum number of blockers
     */
    public int numberOfBlockers(int n) {
        // only consider the range of (0, n]
        // We need to set blockers at (x, y) where gcd(x, y) = 1
        // otherwise gcd(x, y) = k, then we can set block at x/k, y/k

        int count = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (gcd(i, j) == 1) {
                    count++;
                }
            }
        }
        // mirror it in 4 parts, with 4 axis directions
        return 4 * (count + 1);
    }

    /**
     * Observer at position (0, 0) and starts rotating, with the field of view
     * How many degrees must observer rotates to cover at least k points.
     * Points will not block other points with at the same angle.
     * @param points original points in 2d coordinate, non empty [x, y] coordinates
     * @param fov original field of view of observer, which means it can view
     *            fov without rotation. in degrees [0, 360]
     * @param k number of points need to be scanned
     * @return minimum rotation degrees, [0, 360]
     */
    public double minRotationDegree(int[][] points, double fov, int k) {
        int n = points.length;

        // copy over the angles by + 360
        double[] angles = new double[2 * n];

        for (int i = 0; i < n; i++) {
            int x = points[i][0];
            int y = points[i][1];

            double angle = Math.toDegrees(Math.atan2(y, x));
            if (angle < 0) {
                angle += 360.0;
            }
            angles[i] = angle;
        }

        Arrays.sort(angles, 0, n);
        for (int i = 0; i < n; i++) {
            angles[i + n] = angles[i] + 360.0;
        }

        double min = 360.0;
        for (int i = 0; i < n; i++) {
            // try to cover from i to i + k - 1
            double width = angles[i + k - 1] - angles[i];
            min = Math.min(min, width);
        }
        return Math.max(0.0, min - fov);
    }

    /**
     * Find out the min distance between any possible combination
     * of two unique points in the input
     * 
     * @param points an array of (x, y) pair, guarantee unique
     * @return the min distance
     */
    public double minDistance(int[][] points) {
        // According to master theorm, T(n) = 2 T(n/2) + O (k)
        // if we can make O(k) linear, the overall complexity is
        // O(nlogn)

        Arrays.sort(points, (a, b) -> a[0] - b[0] == 0 ? a[1] - b[1] : a[0] - b[0]);
        // solve left and right half problems
        return minDistanceHelper(points, 0, points.length - 1);
    }

    /**
     * Split the points into two subsets. In each subset, there will be a min
     * manhattan distance among all random pairs in the subset. If the subset
     * only contains one value, the min manhattan distance is 0.
     * Find out the maximum of minimum manhattan distance among all possible splits
     * 
     * @param points an array of (x, y) pair
     * @return the max min manhattan distance
     */
    public int maxMinimumDistance(int[][] points) {
        // bipartisan problem
        // for any distance d
        // we want to know if we can achieve min distance >= d in each subset (1)
        // And then do binary search for each d (2)

        // To resolve question (1), we can construct a graph where each point is a
        // vertex
        // and add an edge between two points if manhattan distance < d

        // find the max distance
        int n = points.length;
        int right = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                right = Math.max(right, manhattanDistance(points[i], points[j]));
            }
        }

        // binary search on the distance
        int left = 0;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (canSplit(points, mid)) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    /**
     * Tell if the 4 given points in 2D plane can form rectangular
     * @param points 4 (x, y) pairs in 2D coordinates
     * @return if they can form a rectangle
     */
    public boolean isRectangle(int[][] points) {

        int[] distSquare = new int[6];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                distSquare[index] = distanceSquare(points[i], points[j]);
                index++;
            }
        }

        // after sorting, we get a^2, a^2, b^2, b^2, a^2 + b^2, a^2 + b^2
        Arrays.sort(distSquare);
        return distSquare[0] > 0 &&
                distSquare[0] == distSquare[1] &&
                distSquare[2] == distSquare[3] &&
                distSquare[4] == distSquare[5] &&
                distSquare[0] + distSquare[2] == distSquare[5];
    }

    private boolean canSplit(int[][] points, int limit) {
        int n = points.length;

        List<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (manhattanDistance(points[i], points[j]) < limit) {
                    graph[i].add(j);
                    graph[j].add(i);
                }
            }
        }

        // check if bipartisan graph by painting
        // 0 for untouched, 1 for red, -1 for blue
        int[] color = new int[n];

        for (int i = 0; i < n; i++) {
            // i as the entry point, paint all connected vertex to the opposite color
            if (color[i] == 0) {
                color[i] = 1;
                if (!isBipartisan(graph, color, i)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isBipartisan(List<Integer>[] graph, int[] color, int cur) {
        for (int next : graph[cur]) {
            if (color[next] == 0) {
                // untouched
                color[next] = -color[cur];
                if (!isBipartisan(graph, color, next)) {
                    return false;
                }
            } else if (color[next] * color[cur] == -1) {
                // valid but already touched
                continue;
            } else {
                // invalid
                return false;
            }
        }
        return true;
    }

    private double minDistanceHelper(int[][] points, int startIndex, int endIndex) {
        if (startIndex == endIndex) {
            return Double.MAX_VALUE;
        }
        if (startIndex + 1 == endIndex) {
            return distance(points[startIndex], points[endIndex]);
        }

        int mid = startIndex + (endIndex - startIndex) / 2;
        double leftMin = minDistanceHelper(points, startIndex, mid);
        double rightMin = minDistanceHelper(points, mid + 1, endIndex);
        double minDist = Math.min(leftMin, rightMin);

        // now find all pairs of combinations with one leg in the left side and other
        // leg in the right
        // by definition, we only need to search for x in [mid - minDist, mid + minDist]
        // and y in [y, y + minDist]
        int midX = points[mid][0];
        // a list of points with sorted y
        List<int[]> list = new ArrayList<>();

        for (int[] point : points) {
            if (point[0] >= midX - minDist && point[0] <= midX + minDist) {
                list.add(point);
            }
        }
        list.sort((p0, p1) -> p0[1] - p1[1]);

        // Consider we divide the points into 8 squares of size minDist / 2,
        // Inside each square, the min distance INSIDE the box is minDist / sqrt(2)
        // If there exist 2 points within the same square, the distance is less than
        // minDist,
        // and the both points are in the left half or the right half, which is already
        // covered by the leftMin and rightMin
        // So this contradicts with minDist, and we conclude in each square, there is at
        // most 1 point.

        // And based on the conclusion, we only need to traverse the next 7 points for
        // each point in order
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size() && j < i + 8; j++) {
                double dist = distance(list.get(i), list.get(j));
                minDist = Math.min(minDist, dist);
            }
        }
        return minDist;
    }

    private double distance(int[] p1, int[] p2) {
        int dx = p1[0] - p2[0];
        int dy = p1[1] - p2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }

    private int manhattanDistance(int[] p1, int[] p2) {
        int dx = p1[0] - p2[0];
        int dy = p1[1] - p2[1];
        return Math.abs(dx) + Math.abs(dy);
    }

    private int distanceSquare(int[] p1, int[] p2) {
        int dx = p1[0] - p2[0];
        int dy = p1[1] - p2[1];
        return dx * dx + dy * dy;
    }
}
