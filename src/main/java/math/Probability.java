package math;

public class Probability {
    


    /**
     * Given n vertices, randomly create n edges so that each vertex has
     * exact one indegree and one out degree. There must exist cycle in 
     * the graph. Find the probabilities where the longest cycle for each length
     * @param n total number of vertices, positive integers
     * @return probabilities of longest cycle for each length. Specifically,
     *         probabilities[i] is the probability when longest cycle in graph is i 
     */
    public double[] longestCycleProbabilities(int n) {

        // define p[i][j] as the probability of using i nodes to form the graph
        // with each vertex has exact one indegree and one outdegree,
        // while the longest cycle has length no larger than j
        double[][] p = new double[n + 1][n + 1];

        // recurrence relation
        // Total ways to construct the graph is the permutation, n!
        // Split the graph into 2 parts, the cycle contains vertex 1, and the remaining
        // For the cycle contains vertex 1, the total ways to construct is 
        // C_{n-1}^{j-1} (j-1)!, select j-1 vertex from n-1 possibilities, and permutate
        // The remainig part must has cycle length no larger than k, total ways are
        // (n-j)! p[n-j][k]
        // Sum for all possible j, from 1 to k
        // n! p[n][k] = sum_{j=1}^{k} C_{n-1}^{j-1} (j-1)! (n-j)! p[n-j][k]
        // simplify gives p[n][k] = sum{j=1}^{k} p[n-j][k] / n

        

        // initialize
        for (int j = 0; j <= n; j++) {
            p[0][j] = 1.0;
        }
        p[1][1] = 1.0;
        

        // we could improve this to use a sliding window. 
        // p[i][k] = (p[i-1][k] + p[i-2][k] + ... + p[i-k][k]) / i
        // We do not need to calculate the sum again
        // Define window w[i][k] = p[i-1][k] + p[i-2][k] + ... + p[i-k][k]
        // w[i][k] - w[i-1][k] = p[i-1][k] - p[i-k-1][k]
        
        for (int i = 2; i <= n; i++) {
            for (int k = 1; k <= i; k++) {
                double sum = 0.0;

                // keep a window of size k
                sum += p[i - 1][k];
                if (i - k - 1 >= 0) {
                    sum -= p[i - k - 1][k];
                }

                p[i][k] = sum / i;
            }
        }

        return p[n];
    }
}
