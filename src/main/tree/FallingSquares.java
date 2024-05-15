package tree;

import java.util.*;

class Point {
    int x;
    int height;
    // true for left, false for right
    boolean left;
    int index;

    public Point(int _x, int _height, boolean _left, int _index) {
        x = _x;
        height = _height;
        left = _left;
        index = _index;
    }
}

// lc 699
public class FallingSquares {

    public List<Integer> fallingSquares(int[][] positions) {
        List<Integer> res = new ArrayList<>();
        int max = 0;

        TreeSet<Point> treeSet = new TreeSet<>((p1, p2) -> {
            if (p1.x != p2.x) {
                return p1.x - p2.x;
            }
            // if left and right boundary has same x, the order should be right, left
            if (p1.left != p2.left) {
                return p1.left ? 1 : -1;
            }
            // make sure higher can find all previous with same x
            return p2.index - p1.index;
        });

        for (int i = 0; i < positions.length; i++) {
            int[] position = positions[i];
            Point leftPoint = new Point(position[0], 0, true, i);
            Point rightPoint = new Point(position[0] + position[1], 0, false, i);

            // check if anything falls in range [position[0], position[0] + position[1]]
            int height = 0;
            Point cur = leftPoint;
            while (true) {
                cur = treeSet.higher(cur);
                if (cur == null){
                    break;
                }

                // remove any point higher, until reaches a boundary
                // if it's a left boundary
                if (cur.left) {
                    if (cur.x < position[0] + position[1]) {
                        height = Math.max(height, cur.height);
                        treeSet.remove(cur);
                    } else {
                        break;
                    }

                } else {
                    // if it's right boundary, we can continue
                    // as it's left has not been touched yet
                    height = Math.max(height, cur.height);
                }
            }

            leftPoint.height = height + position[1];
            rightPoint.height = height + position[1];
            treeSet.add(leftPoint);
            treeSet.add(rightPoint);

            // check max height right now
            max = Math.max(max, leftPoint.height);
            res.add(max);

        }
        return res;
    }


    public static final void main(String[] args) {
        FallingSquares instance = new FallingSquares();
        int[][] squares = new int[][]{{1, 5},{2, 2},{7, 5}};

        List<Integer> res = instance.fallingSquares(squares);
        System.out.println(res);
    }
}
