package linear;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringOperation {
    


    /**
     * Figure out the cost to convert source to target. Each time, we choose a substring in the source, 
     * the substring matches patterns[i], and converts to replacements[i], with a cost of costs[i].
     * Each index can be converted at most once. patterns[i] might contain wild match '*',
     * which could match any characters. Find the min cost to covert source to target.
     * 
     * @param source original string 
     * @param target target string
     * @param patterns list of pattern with size n. pattern and replacement has the same length
     * @param replacements list of replacement with size n. pattern and replacement has the same length
     * @param costs cost array of size n
     * @return min cost to convert source string to target string
     */
    public int minCost(String source, String target, List<String> patterns, List<String> replacements, int[] costs) {
        int m = source.length();
        int n = costs.length;
        // each index in source can be replaced at most once, so use 0/1 to represent
        // If an index is 1, then it must be matching to the target string of that index

        // the state is the cost and the 0/1s for each position, use bitset
        BitSet bitset = new BitSet(m);
        Map<BitSet, Integer> minCost = new HashMap<>();

        // for each {pattern, replacement, cost}, we need to find out 
        // 1. the substring in source, whose position is all 0 in bitset
        // and matches the pattern, and 2. flip all positions to 1, and then 
        // add cost to current cost
        // So old state -> flips consecutive 0s to 1s -> new state

        StringMatching stringMatching = new StringMatching();
        List<List<Integer>> patternMatchingInSource = stringMatching.multiplePatternMatching(source, patterns);
        List<List<Integer>> patternMatchingInTarget = stringMatching.multiplePatternMatching(target, replacements);

        return 0;
    }

    private void traverseAndFlip(String source, String target, BitSet state, List<List<String>> rules, int[] costs, Map<BitSet, Integer> minCost) {

        // how to efficiently match if source.substring matches pattern
        // and target.substring matches replacement

    }
}
