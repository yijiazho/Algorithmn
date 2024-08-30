package utility;

import java.util.LinkedList;
import java.util.Queue;

public class Calculator {

    /**
     * Calculate the input string as numerical expression, assume the result
     * does not overflow
     * @param s input string, with only +,-,*,\/,( and )
     * @return the result in integer
     */
    public static int calculate(String s) {
        Queue<Character> q = new LinkedList<>();
        for (char c: s.toCharArray()) {
            q.offer(c);
        }
        q.offer('+');

        return calculate(q);

    }

    private static int calculate(Queue<Character> q) {
        char op = '+';
        int sum = 0;
        int prev = 0;
        int num = 0;
        /*
        Make sure the expression is in the form of
        sum + prev operator num cur,
        while cur is operator we calculate based on the operator
        +: (sum + prev) + num cur (next number)
        -: (sum + prev) + (-num) cur (next number)
        *: sum + (prev * num) cur (next number)
        /: sum + (prev / num) cur (next number)
         */

        while (!q.isEmpty()){
            char c = q.poll();
            if (c >= '0' && c <= '9') {
                num = num * 10 + (c - '0');

            } else if (c == '(') {
                num = calculate(q);

            } else {
                if (op == '+') {
                    sum += prev;
                    prev = num;
                } else if (op == '-') {
                    sum += prev;
                    prev = -num;
                } else if (op == '*') {
                    prev *= num;
                } else if (op == '/') {
                    prev /= num;
                }
                if (c == ')') {
                    break;
                }
                num = 0;
                op = c;
            }
        }

        return sum + prev;
    }
}
