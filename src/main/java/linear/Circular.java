package linear;

public class Circular {

    /**
     * Find the minimal total cost to travel in a circular path given the forward
     * and backward times for each segment if we want to travel all the nodes in
     * order in the queries array.
     * 
     * @param forward  cost that it takes to travel forward in each segment
     * @param backward cost that it takes to travel backward in each segment
     * @param queries  the order in which we want to travel the nodes
     * @return total cost of traveling all the nodes in the queries array
     */
    public long minTotalTime(int[] forward, int[] backward, int[] queries) {
        long totalCost = 0;
        int l = forward.length;

        // create an prefix array for forward cost from 0 to i
        long[] forwardCost = new long[l + 1];
        // create an prefix array for backward cost from l - 1 to i
        long[] backwardCost = new long[l + 1];

        for (int i = 1; i <= l; i++) {
            forwardCost[i] = forwardCost[i - 1] + forward[i - 1];
        }

        for (int i = l; i > 0; i--) {
            backwardCost[i - 1] = backwardCost[i] + backward[i - 1];
        }

        int prev = 0;
        for (int destination : queries) {
            if (destination == prev) {
                continue;
            }
            long cost1 = destination > prev ? forwardCost[destination] - forwardCost[prev]
                    : forwardCost[l] - forwardCost[prev] + forwardCost[destination];
            long cost2 = destination < prev ? backwardCost[destination + 1] - backwardCost[prev + 1]
                    : backwardCost[0] - backwardCost[prev + 1] + backwardCost[destination + 1];

            totalCost += Math.min(cost1, cost2);
            prev = destination;
        }

        return totalCost;
    }
}
