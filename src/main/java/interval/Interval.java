package interval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
     * Minimum number of intervals to remove to make the rest of the intervals
     * non-overlapping
     * 
     * @param intervals an array of intervals where each interval is represented as
     *                  [start, end]
     * @return the minimum number of intervals to remove
     */
    public int minRemovals(int[][] intervals) {
        if (intervals.length == 0) {
            return 0;
        }

        // sort intervals by end time
        // If a non-overlapping interval sequence can be append to an interval,
        // then it can also be append to the interval with the earlier end time
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[1], b[1]));

        int count = 0;
        int end = intervals[0][1];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] < end) {
                // overlap, remove the interval with the larger end time
                count++;
            } else {
                // no overlap, update the end time
                end = intervals[i][1];
            }
        }

        return count;
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

    public List<int[]> mergeIntervals(int[][] intervals) {
        List<Event> events = new ArrayList<>();

        for (int[] interval : intervals) {
            events.add(new Event(interval[0], EventType.START));
            events.add(new Event(interval[1], EventType.END));
        }

        // sort all events by time, if tie, START goes before END
        Collections.sort(events);

        List<int[]> result = new ArrayList<>();
        int count = 0;
        int start = 0;

        for (Event event : events) {
            if (event.type == EventType.START) {
                if (count == 0) {
                    start = event.time;
                }
                count++;
            } else {
                count--;
                if (count == 0) {
                    result.add(new int[] { start, event.time });
                }
            }
        }

        return result;
    }
}

class Event implements Comparable<Event> {
    int time;
    EventType type;

    public Event(int time, EventType type) {
        this.time = time;
        this.type = type;
    }

    @Override
    public int compareTo(Event other) {
        if (this.time != other.time) {
            return this.time - other.time;
        }
        return this.type.ordinal() - other.type.ordinal();
    }
}

enum EventType {
    START, END
}
