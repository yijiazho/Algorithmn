package linear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GasTest {

    private Gas gas;

    @BeforeEach
    public void setup() {
        gas = new Gas();
    }

    @Test
    public void testMinRefillsToDestinationWithNoStation() {
        int[][] stations = new int[][] {};
        int result = gas.minRefillsToDestination(100, 20, stations);
        assertEquals(-1, result);
    }

    @Test
    public void testMinRefillsToDestination() {
        int[][] stations = new int[][] {{10, 30}, {30, 50}, {50, 50}, {80, 30}};
        int result = gas.minRefillsToDestination(100, 20, stations);
        assertEquals(2, result);
    }

    @Test
    public void testMinRefillsToDestinationNoPath() {
        int[][] stations = new int[][] {{30, 50}, {50, 50}, {80, 30}};
        int result = gas.minRefillsToDestination(100, 20, stations);
        assertEquals(-1, result);
    }

    @Test
    public void testMinRefillsToDestinationRefillWithEmptyTank() {
        int[][] stations = new int[][] {{10, 30}, {40, 60}, {50, 50}, {80, 30}};
        int result = gas.minRefillsToDestination(100, 10, stations);
        assertEquals(2, result);
    }

    @Test
    public void testMinRefillsToDestinationNoRefill() {
        int[][] stations = new int[][] {{10, 30}, {30, 50}, {50, 50}, {80, 30}};
        int result = gas.minRefillsToDestination(100, 100, stations);
        assertEquals(0, result);
    }
}
