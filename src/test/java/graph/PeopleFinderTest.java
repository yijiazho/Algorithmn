package graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PeopleFinderTest {

    private PeopleFinder peopleFinder;

    @BeforeEach
    public void setup() {
        peopleFinder = new PeopleFinder();
    }

    @Test
    public void testPeopleFinder() {
        int[][] meetings = new int[][] { { 0, 2, 2 }, { 1, 3, 2 }, { 4, 5, 1 }, { 3, 5, 4 } };
        List<Integer> result = peopleFinder.findAllPeople(6, meetings, 1);
        assertEquals(List.of(0, 1, 2, 3, 5), result);
    }

    @Test
    public void testPeopleFinderAtTheSameTime() {
        int[][] meetings = new int[][] { { 0, 2, 2 }, { 1, 3, 2 }, { 4, 5, 2 }, { 3, 5, 2 } };
        List<Integer> result = peopleFinder.findAllPeople(6, meetings, 1);
        assertEquals(List.of(0, 1, 2, 3, 4, 5), result);
    }
}
