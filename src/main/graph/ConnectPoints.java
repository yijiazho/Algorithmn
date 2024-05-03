import java.util.*;
// lc 1595
public class ConnectPoints {

    int max = Integer.MAX_VALUE;

    public int connectTwoGroups2(List<List<Integer>> cost) {
        // store already linked nodes
        // Set<Integer> set1 = new HashSet<>();
        // Set<Integer> set2 = new HashSet<>();
        int set1 = 0;
        int set2 = 0;
        // store the min cost to connect every nodes in set1 and set2
        // regardless of the order Map<List<Set<Integer>>, Integer> cache = new HashMap<>()
        // use an integer to represent the key since size1 size2 < 12
        // 0 - 11 for size 2, 12 - 23 for size 1
        Map<Integer, Integer> cache = new HashMap<>();
        search2(set1, set2, cost, 0, cache);
        return max;
    }

    private void search2(int set1, int set2, List<List<Integer>> cost, int total,
                        Map<Integer, Integer> cache) {
        int size1 = cost.size();
        int size2 = cost.get(0).size();
        int count1 = 0;
        int count2 = 0;
        int mask2 = 1;
        for (int i = 0; i < size2; i++) {
            // this position is added to set
            if ((set2 & mask2) != 0) {
                count2++;
            }
            mask2 <<= 1;
        }
        int mask1 = 1 << 12;
        for (int i = 0; i < size1; i++) {
            if ((set1 & mask1) != 0) {
                count1++;
            }
            mask1 <<= 1;
        }

        if (count1 == size1 && count2 == size2) {
            if (total < max) {
                max = total;
            }
            return;
        }

        int key = set1 | set2;

        // this is not the optimized cost, skip
        if (total >= cache.getOrDefault(key, Integer.MAX_VALUE)) {
            return;
        }
        // new optimized solution
        cache.put(key, total);


        mask1 = 1 << 12;
        for (int i = 0; i < cost.size(); i++) {
            mask2 = 1;
            for (int j = 0; j < cost.get(0).size(); j++) {
                // link i, j, if one is not linked
                if ((mask1 & set1) == 0 && (mask2 & set2) == 0) {
                    set1 |= mask1;
                    set2 |= mask2;
                    search2(set1, set2, cost, total + cost.get(i).get(j), cache);
                    set1 &= ~mask1;
                    set2 &= ~mask2;
                } else if ((set1 & mask1) != 0 && (set2 & mask2) == 0) {
                    set2 |= mask2;
                    search2(set1, set2, cost, total + cost.get(i).get(j), cache);
                    set2 &= ~mask2;
                } else if ((set1 & mask1) == 0 && (set2 & mask2) != 0) {
                    set1 |= mask1;
                    search2(set1, set2, cost, total + cost.get(i).get(j), cache);
                    set1 &= ~mask1;
                }
                mask2 <<= 1;
            }
            mask1 <<= 1;
        }

    }


    public static final void main(String[] args) {
        ConnectPoints instance = new ConnectPoints();
        List<List<Integer>> input = new ArrayList<>();
//        input.add(List.of(1, 3, 5));
//        input.add(List.of(4, 1, 1));
//        input.add(List.of(1, 5, 3));
        input.add(List.of(42,99,99,56,68,94,84));
        input.add(List.of(52,77,29,68,46,62,99));
        input.add(List.of(4,41,42,4,35,29,73));
        input.add(List.of(82,58,68,20,24,23,29));
        input.add(List.of(40,64,61,94,39,83,28));
        input.add(List.of(83,90,10,78,42,63,3));
        input.add(List.of(92,11,80,30,2,58,80));

        System.out.println(new Date(System.currentTimeMillis()));
        int res = instance.connectTwoGroups(input);
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println(res);
    }

    public int connectTwoGroups(List<List<Integer>> cost) {
        // store already linked nodes
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        // store the min cost to connect every nodes in set1 and set2
        // regardless of the order
        Map<List<Set<Integer>>, Integer> cache = new HashMap<>();
        search(set1, set2, cost, 0, cache);
        return max;
    }

    private void search(Set<Integer> set1, Set<Integer> set2, List<List<Integer>> cost, int total,
                        Map<List<Set<Integer>>, Integer> cache) {
        if (set1.size() == cost.size() &&
                set2.size() == cost.get(0).size()) {
            if (total < max) {
                max = total;
            }
            return;
        }

        List<Set<Integer>> key = List.of(set1, set2);
        // this is not the optimized cost, skip
        if (total >= cache.getOrDefault(key, Integer.MAX_VALUE)) {
            return;
        }
        // new optimized solution
        cache.put(key, total);

        for (int i = 0; i < cost.size(); i++) {
            for (int j = 0; j < cost.get(0).size(); j++) {
                // link i, j, if one is not linked
                if (!set1.contains(i) && !set2.contains(j)) {
                    set1.add(i);
                    set2.add(j);
                    search(set1, set2, cost, total + cost.get(i).get(j), cache);
                    set1.remove(i);
                    set1.remove(j);
                } else if (set1.contains(i) && !set2.contains(j)) {
                    set2.add(j);
                    search(set1, set2, cost, total + cost.get(i).get(j), cache);
                    set2.remove(j);
                } else if (!set1.contains(i) && set2.contains(j)) {
                    set1.add(i);
                    search(set1, set2, cost, total + cost.get(i).get(j), cache);
                    set1.remove(i);
                }
            }
        }
    }

}