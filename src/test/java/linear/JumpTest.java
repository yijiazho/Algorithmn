package linear;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JumpTest {

    private Jump jump;

    @BeforeEach
    public void setup() {
        jump = new Jump();
    }

    @Test
    public void testMaxJumps() {
        int[] nums = { 6, 4, 14, 6, 8, 13, 9, 7, 10, 6, 12 };
        int d = 2;
        int result = jump.maxJumps(nums, d);
        assertEquals(4, result);
    }

    @Test
    public void testMaxJumpsWithNoAvailableJumps() {
        int[] nums = { 3, 3, 3, 3, 3 };
        int d = 1;
        int result = jump.maxJumps(nums, d);
        assertEquals(1, result);
    }

    @Test
    public void testMaxJumpsWithSingleElement() {
        int[] nums = { 10 };
        int d = 1;
        int result = jump.maxJumps(nums, d);
        assertEquals(1, result);
    }

    @Test
    public void testMaxJumpsWithIncreasingSequence() {
        int[] nums = { 1, 2, 3, 4, 5 };
        int d = 2;
        int result = jump.maxJumps(nums, d);
        assertEquals(5, result);
    }

    @Test
    public void testMaxJumpPathSum() {
        int[] nums = { 1, -1, -2, 4, -4, 5 };
        int result = jump.maxJumpPathSum(nums, 2);
        assertEquals(9, result);
    }

    @Test
    public void testMaxJumpPathSumAllPositive() {
        int[] nums = { 1, 2, 3, 4, 5 };
        int result = jump.maxJumpPathSum(nums, 2);
        assertEquals(15, result);
    }

    @Test
    public void testMaxJumpPathSumAllNegative() {
        int[] nums = { -1, -2, -3, -4, -5 };
        int result = jump.maxJumpPathSum(nums, 2);
        assertEquals(-9, result);
    }

    @Test
    public void testMinJumpCost() {
        int[] nums = new int[] { 3, 2, 4, 4, 1 };
        int[] costs = new int[] { 3, 7, 6, 4, 2 };
        long result = jump.minJumpCost(nums, costs);
        assertEquals(8L, result);
    }

    @Test
    public void testMinJumpCostSingleElement() {
        int[] nums = new int[] { 5 };
        int[] costs = new int[] { 10 };
        long result = jump.minJumpCost(nums, costs);
        assertEquals(0L, result);
    }
}
