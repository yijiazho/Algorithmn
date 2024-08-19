package game;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Probability {
    /**
     * @param n the final probability of having less than n points
     * @param k the points threshold until stop drawing
     * @param maxPoints the maximum points of a card, which is randomly
     *                  distributed in range[1, maxPoints]
     * @return the probability of having less or equal than n points
     * when current playing keeps drawing until first time reaches k
     * or more points if all cards are evenly distributed in range
     * [1, maxPoints]
     */
    public double game21Point(int n, int k, int maxPoints) {
        // corner cases

        return search(n, k, maxPoints, new HashMap<>());
    }

    private double search(int n, int k, int maxPoints, Map<Integer, Double> cache) {
        // not possible if total points are negative
        if (n < 0) {
            return 0.0;
        }
        // no card to draw if k <= 0
        if (k <= 0) {
            return 1.0;
        }
        int key = n * 10000 + k;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        double res = 0.0;
        double probabilityOfEachPoint = 1.0 / maxPoints;

        // points not enough, add and go to next round
        for (int i = 1; i <= maxPoints; i++) {
            res += probabilityOfEachPoint * game21Point(n - i, k - i, maxPoints);
        }
        cache.put(key, res);
        return res;
    }

    public static final void main(String[] args) {
        Probability instance = new Probability();
        double res = instance.game21Point(10, 1, 10);
        System.out.println(res);
    }
}
