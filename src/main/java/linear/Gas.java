package linear;

import java.util.HashMap;
import java.util.Map;

public class Gas {

    /**
     * Find out if its possible to travel to the destination, with startFuel as original fuel
     * each 1 amount of distance consumes 1 amount of fuel. You have unlimited tank size and
     * can refuel in the stations
     *
     * @param destination destination of travel
     * @param startFuel original fuel amount
     * @param stations array of station, each station is represented by a pair of position and
     *                 fuel. The position is unique and sorted in ascending order, between 0
     *                 and destination
     * @return if it's possible to travel to destination
     */
    public boolean travelToDestination(int destination, int startFuel, int[][] stations) {
        int cur = 0;
        int curFuel = startFuel;
        for (int[] station: stations) {
            curFuel += cur - station[0];
            if (curFuel < 0) {
                return false;
            }
            curFuel += station[1];
            cur = station[0];
        }

        return destination - cur <= curFuel;
    }

    /**
     * Find out if it's possible to travel to the destination, if so, what is the minimum stops.
     * The vehicle starts with startFuel, and each 1 distance consumes 1 fuel. There are no
     * tank size limit.
     *
     * @param destination destination
     * @param startFuel original fuel amount
     * @param stations array of station, each station is an array of position and fuel reserve.
     *                 The position is unique and sorted in ascending order between 0 and destination.
     * @return -1 if it's not possible to travel to destination, or minimum stops to refill
     */
    public int minRefillsToDestination(int destination, int startFuel, int[][] stations) {
        // the state is stop, current distance, and current tank

        int prev = 0;
        // key is position, value is a map whose key is possible stops, and value is best tank size
        Map<Integer, Map<Integer, Integer>> bestStateAtEachPosition = new HashMap<>();
        Map<Integer, Integer> startingState = new HashMap<>();
        // at position 0, all possible state is:
        // with 0 stops, tank = startingFuel
        startingState.put(0, startFuel);
        bestStateAtEachPosition.put(0, startingState);
        for (int[] station: stations) {
            Map<Integer, Integer> lastState = bestStateAtEachPosition.get(prev);
            Map<Integer, Integer> currentState = new HashMap<>();
            int diff = station[0] - prev;

            // traverse all possible last states
            for (Map.Entry<Integer, Integer> entry: lastState.entrySet()) {
                int stops = entry.getKey();
                int lastFuel = entry.getValue();

                if (diff <= lastFuel) {
                    // we can either stop or not
                    // stop: key + 1, value + station[0] - diff
                    // if there is no such state, or there is a worse state, whose tank is smaller
                    if (!currentState.containsKey(stops + 1) || currentState.get(stops + 1) < lastFuel + station[1] - diff) {
                        currentState.put(stops + 1, lastFuel + station[1] - diff);
                    }

                    // skip: key, value - diff
                    if (!currentState.containsKey(stops) || currentState.get(stops) < lastFuel - diff) {
                        currentState.put(stops, lastFuel - diff);
                    }
                }
            }
            // if the position cannot be reached
            if (currentState.isEmpty()) {
                return -1;
            }
            bestStateAtEachPosition.put(station[0], currentState);
            prev = station[0];
        }

        // at destination, post process and select best
        int minStops = stations.length + 1;
        for (Map.Entry<Integer, Integer> entry: bestStateAtEachPosition.get(prev).entrySet()) {
            int stops = entry.getKey();
            int fuel = entry.getValue();
            int diff = destination - prev;
            if (fuel >= diff) {
                minStops = Math.min(minStops, stops);
            }
        }
        return minStops == stations.length + 1 ? - 1: minStops;
    }
}
