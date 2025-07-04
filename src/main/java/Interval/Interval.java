package interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Interval {
    /**
     * Merge an array of intervals, also merge the same start and end time
     * 
     * @param intervals non empty, with each element is [start, end]
     * @return
     */
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

    /**
     * Insert the newInterval into an array of intervals.
     * 
     * @param intervals   an array of [start, end] pairs, sorted and non overlapping
     * @param newInterval a pair of [start, end]
     * @return inserted array of intervals
     */
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> list = new LinkedList<>();
        int l = intervals.length;
        int i = 0;

        // before overlapping
        while (i < l && intervals[i][1] < newInterval[0]) {
            list.add(intervals[i]);
            i++;
        }

        int[] merged = new int[] { newInterval[0], newInterval[1] };
        // overlapping
        while (i < l && intervals[i][0] <= newInterval[1]) {
            merged[0] = Math.min(intervals[i][0], merged[0]);
            merged[1] = Math.max(intervals[i][1], merged[1]);
            i++;
        }
        list.add(merged);

        // after overlapping
        while (i < l) {
            list.add(intervals[i]);
            i++;
        }

        int[][] result = new int[list.size()][2];
        for (int k = 0; k < result.length; k++) {
            result[k] = list.get(k);
        }
        return result;
    }

    public int[][] intervalInsersection(int[][] firstList, int[][] secondList) {
        if (firstList.length == 0 || secondList.length == 0) {
            return new int[0][];
        }

        List<int[]> resultList = new ArrayList<>();

        int i = 0;
        int j = 0;

        while (i < firstList.length && j < secondList.length) {
            int[] A = firstList[i];
            int[] B = secondList[j];

            if (overlap(A, B)) {
                resultList.add(new int[] { Math.max(A[0], B[0]), Math.min(A[1], B[1]) });

                // remove the event ends first
                if (A[1] == B[1]) {
                    i++;
                    j++;
                } else if (A[1] < B[1]) {
                    i++;
                } else {
                    j++;
                }
            } else {
                if (A[1] < B[0]) {
                    i++;
                } else {
                    j++;
                }
            }
        }

        int[][] result = new int[resultList.size()][2];
        for (int k = 0; k < resultList.size(); k++) {
            result[k] = resultList.get(k);
        }
        return result;
    }

    private boolean overlap(int[] interval1, int[] interval2) {
        return !(interval1[1] < interval2[0] || interval2[1] < interval1[0]);
    }
}
