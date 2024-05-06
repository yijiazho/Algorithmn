package linear;

public class IntegerCount {

    private static final int MOD = (int) (1e9 + 1);

    // count integers in range [num1, num2]
    // with total count in range [min_sum, max_sum]
    public int count(String num1, String num2, int min_sum, int max_sum) {
        int[][] cache = new int[num2.length()][max_sum + 1];
        int diff = num2.length() - num1.length();
        String num1Modified = "0".repeat(diff) + num1;

        int smallerThanMax = countNumbers(0, max_sum, num1Modified, num2, cache, true, true);
        int smallerThanMin = countNumbers(0, min_sum - 1, num1Modified, num2, cache, true, true);

        return (smallerThanMax - smallerThanMin + MOD) % MOD;
    }

    // count all integers whose sum is smaller or equal to target
    // in range [num1, num2]
    // i is the current index in length
    // lowBound is whether we choose all previous number equal to num1
    private int countNumbers(int i, int target, String num1, String num2, int[][] cache, boolean lowBound, boolean highBound) {
        if (target < 0) {
            return 0;
        }
        if (i == num2.length()) {
            return 1;
        }
        if (cache[i][target] != 0) {
            return cache[i][target];
        }

        // low and high depends on if we reached the max/min boundary in [0, i-1]
        int low = lowBound ? num1.charAt(i) - '0' : 0;
        int high = highBound ? num2.charAt(i) - '0' : 9;
        long count = 0L;


        for (int sum = low; sum <= high; sum++) {
            count = (count +
                    countNumbers(i + 1, target - sum, num1, num2, cache,
                            lowBound && sum == low, highBound && sum == high)) %
                    MOD;
        }
        cache[i][target] = (int)count;
        return cache[i][target];
    }

    public static final void main(String[] args) {
        IntegerCount instance = new IntegerCount();
        int res = instance.count("1", "12", 1, 8);
        System.out.println(res);
    }
}
