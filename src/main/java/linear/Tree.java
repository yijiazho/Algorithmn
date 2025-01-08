package linear;

import java.util.TreeSet;

public class Tree {
    /**
     *  Find if the int array contains a pair of i, j,
     *  where i != j, and abs (i - j) <= k
     *  and abs(nums[i] - nums[j]) <= t
     * @param nums int array, at least 2 elements
     * @param k positive integer
     * @param t positive integer
     * @return boolean value
     */
    public boolean conatainsPair(int[] nums, int k, int t) {

        TreeSet<Pair> treeSet = new TreeSet<>((p1, p2) -> {
            if (p1.value != p2.value) {
                return p1.value - p2.value;
            } else {
                return p1.key - p2.key;
            }
        });

        for (int i = 0; i < nums.length; i++) {
            if (inTreeSetRange(treeSet, nums, i, t)) {
                return true;
            } else {
                treeSet.add(new Pair(i, nums[i]));

                if (i >= k) {
                    treeSet.remove(new Pair(i - k, nums[i - k]));
                }
            }
        }
        return false;
    }

    private boolean inTreeSetRange(TreeSet<Pair> treeSet, int[] num, int index, int t) {
        // for index value pair, find if treeSet contains value in range
        // (num[index] - target, num[index] + target)
        // both inclusive
        int min = num[index] - t;
        int max = num[index] + t;
        Pair maxPair = new Pair(num.length, max);
        Pair lowerMaxPair = treeSet.lower(maxPair);

        if (lowerMaxPair != null && lowerMaxPair.value >= min) {
            return true;
        }
        return false;
    }
}

class Pair {
    int key;
    int value;

    public Pair(int key, int value) {
        this.key = key;
        this.value = value;
    }
}