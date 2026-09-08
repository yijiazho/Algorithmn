package dataStructure.rangeQuery;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SparseTableTest {

    private SparseTable sparseTable;

    @BeforeEach
    public void setup() {
        sparseTable = new SparseTable(new int[] {3, 1, 7, -2, 4, 0, 9, 3, 11, 2});
    }

    @Test
    public void testRangeQueryMin() {
        assertEquals(3, sparseTable.rangeQueryMinimum(0, 0));
        assertEquals(-2, sparseTable.rangeQueryMinimum(0, 9));
        assertEquals(1, sparseTable.rangeQueryMinimum(0, 2));
        assertEquals(0, sparseTable.rangeQueryMinimum(5, 9));
    }

}
