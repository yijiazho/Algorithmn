package graph;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.RetryingTest;

import utility.TimeComplexityAnalyzer;
import utility.TimeComplexityAnalyzer.Complexity;

public class UnionFindTest {

    @Test
    public void testUnionFind() {
        UnionFind unionFind = new UnionFind(3);
        assertFalse(unionFind.find(0, 1));
        unionFind.union(0, 1);
        assertTrue(unionFind.find(0, 1));

        assertFalse(unionFind.find(0, 2));
        assertFalse(unionFind.find(1, 2));
        unionFind.union(1, 2);
        assertTrue(unionFind.find(0, 2));
        assertTrue(unionFind.find(1, 2));
    }

    @RetryingTest(3)
    public void testMergeSortComplexity() {
        Complexity complexity = TimeComplexityAnalyzer.analyze(n -> {
            Random random = new Random(42);
            UnionFind unionFind = new UnionFind(n);
            for (int i = 0; i < n; i++) {
                int first = random.nextInt(n);
                int second = random.nextInt(n);
                unionFind.union(first, second);
                unionFind.find(first, second);
            }
        }, 100, 100000, 500);
        assertEquals(Complexity.N_LOG_N, complexity);
    }
}
