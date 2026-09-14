package interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class MaxPinsInWindow {

    /**
     * Find the max number of pins can be displayed in the same window
     *
     * @param pinList    a list of pins, including start, end and Column
     * @param windowSize the size of the window
     * @return
     */
    public int maxInWindow(List<Pin> pinList, int windowSize) {
        /*
         * Regardless of L, R
         * if we sort the pinList and put in a data structure and traverse it
         * if current pair is (start, end) all we want to know is the
         * index whose start is end - windowSize or larger
         */

        // sort the list, assume non overlap, and keep the index

        // create a treeMap, key is the start time, v is index in list

        // traverse
        // try to find the smallest larger or equal end - windowSize
        // if null then the total is 1
        // otherwise the total is index - lastIndex + 1

        /*
         * Now consider L and R
         * keep 2 sorted list, and two map
         * 
         * in the traversal, we have (start, end)
         * try to find the smallest larger or equal for end - windowSize
         * in both treeMaps,
         * suppose this one is left.
         * then for left column, we do exactly like mentioned, get totalLeft
         * For the right column, we also find the smallest larger/equal for ()
         * ands compare with the tail of the right
         * total = (totalLeft + totalRight)
         */

        Collections.sort(pinList, (p1, p2) -> p1.start - p2.start);
        int leftIndex = -1;
        int rightIndex = -1;
        TreeMap<Integer, Integer> leftMap = new TreeMap<>();
        TreeMap<Integer, Integer> rightMap = new TreeMap<>();
        Integer ceilingKey;
        Integer lastKey;
        int leftTotal;
        int rightTotal;
        int max = 0;

        for (Pin pin : pinList) {
            if (Column.LEFT.equals(pin.column)) {
                leftIndex++;
                leftTotal = 0;
                rightTotal = 0;

                // calculate leftTotal
                ceilingKey = leftMap.ceilingKey(pin.end - windowSize);
                if (ceilingKey == null) {
                    leftTotal = pin.end - pin.start <= windowSize ? 1 : 0;
                } else {
                    leftTotal = leftIndex - leftMap.get(ceilingKey) + 1;
                }

                // calculate rightTotal
                ceilingKey = rightMap.ceilingKey(pin.end - windowSize);
                if (ceilingKey == null) {
                    rightTotal = 0;
                } else {
                    // last key is guaranteed exist and smaller than current time
                    lastKey = rightMap.lastKey();
                    rightTotal = rightMap.get(lastKey) - rightMap.get(ceilingKey) + 1;
                }
                // update left map
                leftMap.put(pin.start, leftIndex);
                // update max
                max = Math.max(max, leftTotal + rightTotal);
            } else {
                rightIndex++;
                leftTotal = 0;
                rightTotal = 0;

                // calculate rightTotal
                ceilingKey = rightMap.ceilingKey(pin.end - windowSize);
                if (ceilingKey == null) {
                    rightTotal = pin.end - pin.start <= windowSize ? 1 : 0;
                } else {
                    rightTotal = rightIndex - rightMap.get(ceilingKey) + 1;
                }

                // calculate leftTotal
                ceilingKey = leftMap.ceilingKey(pin.end - windowSize);
                if (ceilingKey == null) {
                    leftTotal = 0;
                } else {
                    // last key is guaranteed exist and smaller than current time
                    lastKey = leftMap.lastKey();
                    leftTotal = leftMap.get(lastKey) - leftMap.get(ceilingKey) + 1;
                }
                // update right map
                rightMap.put(pin.start, rightIndex);
                max = Math.max(max, leftTotal + rightTotal);
            }
        }

        return max;
    }

    public static final void main(String[] args) {
        MaxPinsInWindow maxPinsInWindow = new MaxPinsInWindow();
        List<Pin> pinList = new ArrayList<>();
        pinList.add(new Pin(0, 4, Column.LEFT));
        pinList.add(new Pin(8, 10, Column.LEFT));
        pinList.add(new Pin(12, 14, Column.LEFT));
        pinList.add(new Pin(18, 20, Column.LEFT));

        pinList.add(new Pin(3, 9, Column.RIGHT));
        pinList.add(new Pin(10, 13, Column.RIGHT));
        pinList.add(new Pin(14, 15, Column.RIGHT));
        pinList.add(new Pin(19, 30, Column.RIGHT));

        int res = maxPinsInWindow.maxInWindow(pinList, 13);
        System.out.println(res);
    }
}

class Pin {
    int start;
    int end;
    Column column;

    public Pin(int start, int end, Column column) {
        this.start = start;
        this.end = end;
        this.column = column;
    }
}

enum Column {
    LEFT,
    RIGHT
}