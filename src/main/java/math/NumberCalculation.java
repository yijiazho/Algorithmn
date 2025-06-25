package math;

public class NumberCalculation {

    /**
     * Find out the minimum operations to make the inout number to 0
     * The only allowed operations are to add and remove by 2^k, while k>=0
     * 
     * @param n positive input integer, smaller than 10^9
     * @return the minimum number of operations to make n to 0
     */
    public int minOperations(int n) {

        // in terms of bit operations, removing 2^k is equivalent to change 1 to 0,
        // adding 2^k is equivalent to change consecutive 1s to 0s and adding a 1 to
        // head
        // so for single 1, we need 1 operation to change to 0
        // for consecutive 1s, we need 2 operations to change to 0

        int res = 0;
        while (n > 0) {
            if ((n & 3) == 3) {
                // consecutive 1s
                n++;
                res++;
            } else {
                // 01, 00, or 10
                res += n & 1;
                n >>= 1;
            }
        }
        return res;

    }

}
