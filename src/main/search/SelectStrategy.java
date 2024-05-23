package search;

import java.util.ArrayList;
import java.util.List;

public class SelectStrategy {
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {

        List<List<Integer>> prefixSum = new ArrayList<>();
        for (List<Integer> pile: piles) {
            List<Integer> list = new ArrayList<>();
            list.add(0);

            for (int n: pile) {
                list.add(n + list.get(list.size() - 1));
            }

            prefixSum.add(list);
        }

        int[][] mem = new int[piles.size()][k + 1];
        maxValue(0, k, mem, prefixSum);

        return mem[0][k];
    }


    private int maxValue(int index, int coins, int[][] mem, List<List<Integer>> prefixSum) {
        if (index == mem.length) {
            return coins == 0 ? 0 : -1;
        }
        if (mem[index][coins] != 0) {
            return mem[index][coins];
        }

        int max = 0;
        // choose some coins at i = index
        for (int i = 0; i <= coins; i++) {
            if (i == prefixSum.get(index).size()) {
                break;
            }

            // get the sum of next level
            int next = maxValue(index + 1, coins - i, mem, prefixSum);
            // prefix sum[i][j] is the sum of top j elements in pile i
            if (next != -1) {
                max = Math.max(max, next  + prefixSum.get(index).get(i));
            }
        }

        mem[index][coins] = max;
        return max;
    }

    public static final void main(String[] args) {
        SelectStrategy selectStrategy = new SelectStrategy();
        List<List<Integer>> input = new ArrayList<>();
        input.add(List.of(1,100,3));
        input.add(List.of(7,8,9));

        int res = selectStrategy.maxValueOfCoins(input, 4);
        System.out.println(res);
    }
}
