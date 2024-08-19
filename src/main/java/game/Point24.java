package game;

import java.util.ArrayList;
import java.util.List;

public class Point24 {
    private static final int TARGET = 24;
    private static final double EPS = 0.00000001;

    public boolean point24(int[] cards) {
        List<Double> list = new ArrayList<>();
        for (int card: cards) {
            list.add((double) card);
        }
         return calculate(list);
    }

    // curList is a list of integer formed by calculation
    private boolean calculate(List<Double> curList) {
        if (curList.size() == 1) {
            return Math.abs(curList.get(0) - TARGET) < EPS;
        }

        // choose all combinations
        for (int i = 0; i < curList.size(); i++) {
            for (int j = i + 1; j < curList.size(); j++) {

                Double second = curList.remove(j);
                Double first = curList.remove(i);

                // +
                curList.add(first + second);
                if (calculate(curList)) {
                    return true;
                }
                curList.remove(first + second);

                // -
                curList.add(Math.abs(first - second));
                if (calculate(curList)){
                    return true;
                }
                curList.remove(Math.abs(first - second));

                // *
                curList.add(first * second);
                if (calculate(curList)) {
                    return true;
                }
                curList.remove(first * second);

                // \\/
                if (first > EPS) {
                    curList.add(second / first);
                    if (calculate(curList)) {
                        return true;
                    }
                    curList.remove(second / first);
                }

                if (second > EPS) {
                    curList.add(first / second);
                    if (calculate(curList)) {
                        return true;
                    }
                    curList.remove(first / second);
                }

                curList.add(i, first);
                curList.add(j, second);
            }
        }

        return false;
    }

    public static final void main(String[] args) {
        Point24 instance = new Point24();
        int[] input = new int[]{1, 1, 8, 7};
        boolean res = instance.point24(input);
        System.out.println(res);
    }
}
