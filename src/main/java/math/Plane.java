package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Plane {

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
}
