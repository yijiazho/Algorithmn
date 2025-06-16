package graph;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MergeUntilConnectTest {

    private MergeUntilConnect mergeUntilConnect;

    @BeforeEach
    public void setup() {
        mergeUntilConnect = new MergeUntilConnect();
    }

    @Test
    public void testMergeUntilConnect() {
        int[][] matrix = new int[][] {
                { 9, 2, 3 },
                { 4, 8, 4 },
                { 7, 4, 9 }
        };
        List<Integer> result = mergeUntilConnect.mergeUntilConnect(matrix);
        assertEquals(List.of(2, 3, 4, 1, 1, 1), result);
    }

    @Test
    public void testMergeUntilConnectSameValues() {
        int[][] matrix = new int[][] {
                { 1, 1, 1 },
                { 1, 1, 1 },
                { 1, 1, 1 }
        };
        List<Integer> result = mergeUntilConnect.mergeUntilConnect(matrix);
        assertEquals(List.of(1), result);
    }

    @Test
    public void testMergeUntilConnectUnconnected() {
        int[][] matrix = new int[][] {
                { 9, 1, 9 },
                { 1, 5, 1 },
                { 9, 1, 9 }
        };
        List<Integer> result = mergeUntilConnect.mergeUntilConnect(matrix);
        assertEquals(List.of(4, 5, 1), result);
    }
}
