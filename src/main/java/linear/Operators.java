package linear;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Operators {

    /**
     * Given a string of numbers, return a list of string that
     * inserts operators +, -, *, to make the result to be target
     * @param num input string, with only numbers
     * @param target target to form
     * @return all possible strings
     */
    public List<String> addOperators(String num, int target) {
        List<String> res = new ArrayList<>();
        buildPath(res, new StringBuilder(), num, target, 0);
        return res;
    }

    private void buildPath(List<String> res, StringBuilder path, String num,
                           int target, int index) {
        // used all number in the string
        if (index == num.length()) {
            if (calculate(path.toString()) == target) {
                res.add(path.toString());
                return;
            }
        }

        // out of boundary
        if (index >= num.length()) {
            return;
        }

        int len = path.length();
        int val = 0;
        // convert [index, i] to value
        for (int i = index; i < num.length(); i++) {
            val = 10 * val + num.charAt(i) - '0';
            if (i == num.length() - 1) {
                // add val to path
                path.append(val);
                buildPath(res, path, num, target, i + 1);
                path.setLength(len);
            } else {
                // add val and +, -, * to path
                path.append(val);
                path.append('+');
                buildPath(res, path, num, target, i + 1);
                path.setLength(len);

                path.append(val);
                path.append('-');
                buildPath(res, path, num, target, i + 1);
                path.setLength(len);

                path.append(val);
                path.append('*');
                buildPath(res, path, num, target, i + 1);
                path.setLength(len);

                // no leading 0 like 012
                if (val == 0) {
                    break;
                }
            }
        }
    }

    /**
     * return the integer as a result of an expression
     * represented by the given string
     * @param s given string, with only 0-9, +,-,*,\/
     * @return the result as integer
     */
    public int calculate(String s) {
        Queue<Character> queue = preHandle(s);
        queue.offer('+');
        /*
        Mark the expression as
        sum + lastNum op val curOp
        if op is + -, calculate in order
        if op is * \/, calculate teh lastNum and val first

        This way we can solve this in order
         */
        int sum = 0;
        int lastNum = 0;
        char op = '+';
        int val = 0;

        while (!queue.isEmpty()) {
            char cur = queue.poll();
            if (cur >= '0' && cur <= '9') {
                val = val * 10 + cur - '0';
            } else {
                if (op == '+') {
                    sum += lastNum;
                    lastNum = val;
                } else if (op == '-') {
                    sum += lastNum;
                    lastNum = -val;
                } else if (op == '*') {
                    lastNum *= val;
                } else if (op == '/') {
                    lastNum /= val;
                }
                op = cur;
                val = 0;
            }
        }
        return sum + lastNum;
    }

    private Queue<Character> preHandle(String s) {
        Queue<Character> queue = new LinkedList<>();
        for (char c: s.toCharArray()) {
            if ((c >= '0' && c <= '9') ||
                    (c == '+' || c == '-' || c == '*' || c == '/')) {
                queue.offer(c);
            }
        }
        return queue;
    }


    public static final void main(String[] args) {
        Operators operators = new Operators();
        String input = "1203";
        List<String> output = operators.addOperators(input, 6);

        System.out.println(output);
    }
}
