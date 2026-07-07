package search;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MatchTest {

    private Match match;

    @BeforeEach
    public void setup() {
        match = new Match();
    }

    @Test
    public void testNumberOfMatches() {
        int m = 5;
        int n = 3;
        List<List<Integer>> preferPlacesList = List.of(
                List.of(0, 1),
                List.of(1, 2),
                List.of(2));
        int result = match.numberOfMatches(preferPlacesList, m, n);
        assertEquals(1, result);
    }

    @Test
    public void testNumberOfMatchesWithMultipleOptions() {
        int m = 4;
        int n = 3;
        List<List<Integer>> preferPlacesList = List.of(
                List.of(0, 1, 2, 3),
                List.of(0, 1, 2, 3),
                List.of(0, 1, 2, 3));
        int result = match.numberOfMatches(preferPlacesList, m, n);
        assertEquals(24, result);
    }

    @Test
    public void testNumberOfMatchesNoAvailablePlace() {
        int m = 3;
        int n = 2;
        List<List<Integer>> preferPlacesList = List.of(
                List.of(0),
                List.of(0));

        int result = match.numberOfMatches(preferPlacesList, m, n);
        assertEquals(0, result);
    }

    @Test
    public void testNumberOfMatchesExactAssignment() {
        int m = 3;
        int n = 3;
        List<List<Integer>> preferPlacesList = List.of(
                List.of(0),
                List.of(1),
                List.of(2));

        int result = match.numberOfMatches(preferPlacesList, m, n);
        assertEquals(1, result);
    }

    @Test
    public void testNumberOfMatchesWithUnusedPlaces() {
        int m = 5;
        int n = 2;
        List<List<Integer>> preferPlacesList = List.of(
                List.of(0, 1, 2),
                List.of(2, 3));

        int result = match.numberOfMatches(preferPlacesList, m, n);
        assertEquals(5, result);
    }

    @Test
    public void testNumberOfMatchesWithEmptyPreferenceList() {
        int m = 4;
        int n = 3;
        List<List<Integer>> preferPlacesList = List.of(
                List.of(0, 1),
                List.of(),
                List.of(2, 3));

        int result = match.numberOfMatches(preferPlacesList, m, n);
        assertEquals(0, result);
    }

    @Test
    public void testNumberOfMatchesUsesHighPlaceBits() {
        int m = 20;
        int n = 3;
        List<List<Integer>> preferPlacesList = List.of(
                List.of(0, 19),
                List.of(19),
                List.of(1, 18));

        int result = match.numberOfMatches(preferPlacesList, m, n);
        assertEquals(2, result);
    }
}
