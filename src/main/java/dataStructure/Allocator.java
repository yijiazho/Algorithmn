package dataStructure;

import java.util.*;

public class Allocator {

    // freeMap: start -> end (inclusive) of each contiguous free interval, kept sorted
    private final TreeMap<Integer, Integer> freeMap;
    // allocMap: mID -> list of allocated intervals [start, end] (inclusive)
    private final Map<Integer, List<int[]>> allocMap;

    public Allocator(int n) {
        freeMap = new TreeMap<>();
        freeMap.put(0, n - 1);
        allocMap = new HashMap<>();
    }

    /**
     * Allocate a block of size memory and assign it the mID.
     * The memory block should be continuous. Return the start index of the block if
     * the allocation is successful.
     * If there are multiple blocks available, return the leftmost block. Return -1
     * if you cannot allocate the block.
     *
     * @param size the size of the memory block to allocate
     * @param mID  the ID to assign to the allocated memory block
     * @return the start index of the allocated block, or -1 if allocation fails
     */
    public int allocate(int size, int mID) {
        for (Map.Entry<Integer, Integer> entry : freeMap.entrySet()) {
            int start = entry.getKey();
            int end = entry.getValue();
            if (end - start + 1 >= size) {
                freeMap.remove(start);
                if (end - start + 1 > size) {
                    freeMap.put(start + size, end);
                }
                allocMap.computeIfAbsent(mID, k -> new ArrayList<>())
                        .add(new int[]{start, start + size - 1});
                return start;
            }
        }
        return -1;
    }

    /**
     * Free all the memory blocks with the given mID. Return the size of memory
     * blocks freed.
     *
     * @param mID the ID of the memory blocks to free
     * @return the size of the memory blocks freed
     */
    public int freeMemory(int mID) {
        List<int[]> intervals = allocMap.remove(mID);
        if (intervals == null) return 0;
        int freed = 0;
        for (int[] interval : intervals) {
            int s = interval[0];
            int e = interval[1];
            freed += e - s + 1;
            // Merge with the preceding free block if it ends exactly at s-1
            Integer prevStart = freeMap.lowerKey(s);
            if (prevStart != null && freeMap.get(prevStart) == s - 1) {
                freeMap.remove(prevStart);
                s = prevStart;
            }
            // Merge with the following free block if it starts exactly at e+1
            if (freeMap.containsKey(e + 1)) {
                e = freeMap.remove(e + 1);
            }
            freeMap.put(s, e);
        }
        return freed;
    }
}
