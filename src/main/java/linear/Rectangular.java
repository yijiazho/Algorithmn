package linear;


import java.util.*;

class Interval {
    int y;
    // start and end for x point
    int start;
    int end;
    // if is bottom boundary
    boolean isBottom;

    public Interval(int _y, int _start, int _end, boolean _isBotton) {
        y = _y;
        start = _start;
        end = _end;
        isBottom = _isBotton;
    }
}


public class Rectangular {

    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        // corner cases
        int A = (ax2 - ax1) * (ay2 - ay1);
        int B = (bx2 - bx1) * (by2 - by1);

        // no intersection
        if (ax2 <= bx1 || ay1 >= by2 || ax1 >= bx2 || ay2 <= by1) {
            return A + B;
        } else {
            List<Integer> listX = new ArrayList<>();
            listX.add(ax1);
            listX.add(ax2);
            listX.add(bx1);
            listX.add(bx2);

            List<Integer> listY = new ArrayList<>();
            listY.add(ay1);
            listY.add(ay2);
            listY.add(by1);
            listY.add(by2);

            Collections.sort(listX);
            Collections.sort(listY);

            int intersection = (listX.get(2) - listX.get(1)) * (listY.get(2) - listY.get(1));
            return A + B - intersection;
        }
    }

    // sort all lines by y, separate by 2 parts
    // also keep a listed intervals of current xs
    public int rectangleArea(int[][] rectangles) {
        int OPEN = 0, CLOSE = 1;
        int[][] events = new int[rectangles.length * 2][];
        int t = 0;
        for (int[] rec: rectangles) {
            events[t++] = new int[]{rec[1], OPEN, rec[0], rec[2]};
            events[t++] = new int[]{rec[3], CLOSE, rec[0], rec[2]};
        }

        Arrays.sort(events, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> active = new ArrayList();
        int cur_y = events[0][0];
        long ans = 0;
        for (int[] event: events) {
            int y = event[0], typ = event[1], x1 = event[2], x2 = event[3];

            // Calculate query
            long query = 0;
            int cur = -1;
            for (int[] xs: active) {
                cur = Math.max(cur, xs[0]);
                query += Math.max(xs[1] - cur, 0);
                cur = Math.max(cur, xs[1]);
            }

            ans += query * (y - cur_y);

            if (typ == OPEN) {
                active.add(new int[]{x1, x2});
                Collections.sort(active, (a, b) -> Integer.compare(a[0], b[0]));
            } else {
                for (int i = 0; i < active.size(); ++i)
                    if (active.get(i)[0] == x1 && active.get(i)[1] == x2) {
                        active.remove(i);
                        break;
                    }
            }

            cur_y = y;
        }

        ans %= 1_000_000_007;
        return (int) ans;
    }


    public static void main(String[] args) {

        Rectangular instance = new Rectangular();
        int[][] rectangulars = new int[][]
                {
                        {0, 0, 2, 2},
                        {1, 0 ,2 ,3},
                        {1, 0, 3, 1}
                };

        int area = instance.rectangleArea(rectangulars);
    }
}
