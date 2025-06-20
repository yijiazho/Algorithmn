package interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeIntervals {
    public List<int[]> merge(int[][] intervals) {
        // sort all intervals
        Arrays.sort(intervals, (i1, i2) -> {
            return i1[0] != i2[0] ? i1[0] - i2[0] : i1[1] - i2[1];
        });

        // make sure each time we merge 2 intervals only
        List<int[]> list = new ArrayList<>();

        for (int[] interval : intervals) {
            // if no overlap, add to list
            // otherwise, merge the tail and this interval
            if (list.isEmpty() || !overlap(interval, list.get(list.size() - 1))) {
                list.add(interval);
            } else {
                int[] tail = list.remove(list.size() - 1);
                list.add(new int[] { Math.min(tail[0], interval[0]), Math.max(tail[1], interval[1]) });
            }
        }

        return list;
    }

    private boolean overlap(int[] interval1, int[] interval2) {
        return !(interval1[1] <= interval2[0] || interval2[1] <= interval1[0]);
    }
}
