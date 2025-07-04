package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinarySearchTest {

    private BinarySearch binarySearch;

    @BeforeEach
    public void setup() {
        binarySearch = new BinarySearch();
    }

    @Test
    public void testFindInIncreasingArray() {
        int[] array = new int[] { -3, 2, 3, 4, 4, 4, 6 };
        int result = binarySearch.find(array, 4);
        assertEquals(3, result);
    }

    @Test
    public void testFindInIncreasingArrayNotExist() {
        int[] array = new int[] { -3, -2, 3, 3, 4, 4, 4, 6 };
        int result = binarySearch.find(array, 5);
        assertEquals(6, result);
    }
}
