package linear;

import java.util.*;

public class BuildingPoint implements Comparable<BuildingPoint>{
    int x;
    int h;
    // a key to the original array, guaranteed to be unique
    int secondaryKey;
    // true for left point, false for right point
    boolean left;

    public BuildingPoint(int x, int h, int secondaryKey, boolean left) {
        this.x = x;
        this.h = h;
        this.secondaryKey = secondaryKey;
        this.left = left;
    }


    public static void main(String[] args) {
        int[][] buildings = {{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}};
        // [2,9,10],[3,7,15],[5,12,12],[15,20,10],[19,24,8]
        getSkyline(buildings);

    }

    @Override
    public int compareTo(BuildingPoint that) {
        if (this.x != that.x) {
            return this.x - that.x;
        } else if (this.left && that.left) {
            return that.h - this.h;
        } else if (!this.left && !that.left) {
            return this.h - that.h;
        } else {
            return this.left? -1: 1;
        }
    }


    public static List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        List<BuildingPoint> list = new ArrayList<>();

        // keep a mapping from secondaryKey -> BuildingPoint(left)
        List<BuildingPoint> leftList = new ArrayList<>();


        for (int i = 0; i < buildings.length; i++) {
            int[] building = buildings[i];
            list.add(new BuildingPoint(building[0], building[2], i,true));
            list.add(new BuildingPoint(building[1], building[2], i,false));
            leftList.add(new BuildingPoint(building[0], building[2], i, true));
        }
        Collections.sort(list);

        TreeSet<Integer> set = new TreeSet<>((i1, i2) -> {
            if (leftList.get(i1).h != leftList.get(i2).h) {
                return leftList.get(i1).h - leftList.get(i2).h;
            } else {
                return i1 - i2;
            }
        });

        for (BuildingPoint point: list) {
            if (point.left) {
                int height = set.isEmpty() ? 0 : leftList.get(set.last()).h;
                if (point.h > height) {
                    res.add(Arrays.asList(point.x, point.h));
                }
                set.add(point.secondaryKey);
            } else {
                set.remove(point.secondaryKey);
                int height = set.isEmpty() ? 0 : leftList.get(set.last()).h;
                if (point.h > height) {
                    res.add(Arrays.asList(point.x, height));
                }
            }

        }

        return res;
    }
}
