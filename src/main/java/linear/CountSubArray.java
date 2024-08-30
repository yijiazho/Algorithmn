package linear;

public class CountSubArray {
    /**
     * Count how many subarrays are there whose score is lesser
     * than k, the score is defined by the sum of subarray
     * times the length of subarray, the subarray should be non-empty
     * @param nums The original integer array, value > 0
     * @param k threshold k
     * @return number of subarrays
     */
    public long countSubarrays(int[] nums, long k) {
        /*
        In order to find a subarray, we need a left and right boundary
        the score of an array is increasing as the length increases
        so once left boundary is fixed we can use binary search to find
        right boundary
         */

        /*
        Thinking about it again, if [i, j] is valid, [i + 1, j] is also valid
        so that we can skip this check and use greedy solution
         */

        int n = nums.length;
        long res = 0L;
        long sum = 0L;
        int left = 0;

        for (int right = 0; right < n; right++) {
            sum += nums[right];

            while (sum * (right - left + 1) >= k) {
                sum -= nums[left];
                left++;
            }

            // [left, right] is valid, [left - 1, right] is not
            // which makes [left - 1, right + 1] not a valid combo
            // next level just check [left, right + 1]
            res += right - left + 1;
        }


        return res;
    }

    /**
     * return the last index as right boundary that makes the score
     * smaller than target, with left boundary fixed
     * @param prefix prefix sum array
     * @param leftBoundary left boundary
     * @param target target threshold
     * @return the largest right boundary
     */
    private int binarySearch(long[] prefix, int leftBoundary, long target) {
        int left = leftBoundary;
        int right = prefix.length - 1;

        // find the last right boundary that score smaller target
        while (left <= right) {
            int mid = (left + right) / 2;
            // [leftBoundary, mid]
            long score = (mid - leftBoundary + 1) * (prefix[mid + 1] - prefix[leftBoundary]);

            if (score >= target) {
                // still too large
                right = mid - 1;
            } else {
                // try to expand
                left = mid + 1;
            }
        }

        return right;
    }

    public static final void main(String[] args) {
        int[] nums = new int[] {9,5,3,8,4,7,2,7,4,5,4,9,1,4,8,10,8,10,4,7};
        CountSubArray countSubArray = new CountSubArray();
        long res = countSubArray.countSubarrays(nums, 4);
        System.out.println(res);
    }
}
