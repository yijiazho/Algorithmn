package search;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Match {

    /**
     * Match the n players into m places, each player has a list of preferred
     * places. Each person can only be matched to one place, and each place can only
     * be matched to one person or empty.
     * 
     * @param preferPlacesList list of preferred places for each player
     * @param m                number of places, < 20
     * @param n                number of players, equal to preferPlacesList.size(),
     *                         < m
     * @return total number of possible matches
     */
    public int numberOfMatches(List<List<Integer>> preferPlacesList, int m, int n) {

        return search(preferPlacesList, n, 0, 0, new HashMap<>());
    }

    /**
     * Recursive helper method to search for all possible matches.
     * 
     * @param preferPlacesList list of preferred places for each player
     * @param n                number of players
     * @param matchedPlaces    bitmask to keep track of matched places
     * @param playerIndex      current player index
     * @param cache            memoized matches by player index and matched places
     * @return number of possible matches from this state
     */
    private int search(List<List<Integer>> preferPlacesList, int n, int matchedPlaces, int playerIndex,
            Map<Integer, Integer> cache) {
        if (playerIndex == n) {
            return 1;
        }

        int key = createKey(playerIndex, matchedPlaces);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        int totalMatches = 0;
        List<Integer> preferPlaces = preferPlacesList.get(playerIndex);
        for (int place : preferPlaces) {
            int placeMask = 1 << place;
            if ((matchedPlaces & placeMask) == 0) {
                totalMatches += search(preferPlacesList, n, matchedPlaces | placeMask, playerIndex + 1, cache);
            }
        }
        cache.put(key, totalMatches);
        return totalMatches;
    }

    // Create a unique key for the cache based on player index and matched places
    // Considering m < 20, we can use the first 20 bits for matchedPlaces and the
    // next bits for playerIndex
    // And playerIndex is less than n, which is less than m, so 32 bits is enough to
    // store both values in a single integer
    private int createKey(int playerIndex, int matchedPlaces) {
        return (playerIndex << 20) | matchedPlaces;
    }
}
