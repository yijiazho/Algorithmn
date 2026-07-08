package math;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NumberParser {

    /**
     * Parse the input string into a list of all possible answers.
     * The input string is a mathematical expression that may contain numbers and
     * operators (+, -, *, /).
     * However, the order of operations is unknown, which means +/- may be evaluated
     * before * or /, and vice versa.
     * The function should return a list of all possible results from evaluating the
     * expression in different orders of operations.
     * 
     * @param s the input string representing a mathematical expression It only
     *          contains digits and the operators +, -, *, /.
     *          The input string is guaranteed to be a valid expression.
     * @return a list of all possible results from evaluating the expression in
     *         different orders of operations.
     */
    public Set<Integer> allPossibleAnswers(String s) {
        List<String> tokens = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int start = i;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    i++;
                }
                tokens.add(s.substring(start, i));
            } else {
                tokens.add(String.valueOf(c));
                i++;
            }
        }

        Set<Integer> resultSet = search(tokens, 0, null, null, null, new HashMap<>());
        return resultSet;
    }

    /**
     * Traverse the tokens and evaluate the expression in different orders of
     * operations.
     * 
     * @param tokens The parsed tokens from the input string, which can be numbers
     *               or operators.
     * @param index  The current index in the tokens list being processed.
     * @param left   The left operand in the current expression being evaluated.
     * @param op     The operator in the current expression being evaluated.
     * @param right  The right operand in the current expression being evaluated.
     * @param cache  A cache to store previously computed results for
     *               sub-expressions to avoid redundant calculations.
     * @return
     */
    private Set<Integer> search(List<String> tokens, int index, String left, String op, String right,
            Map<String, Set<Integer>> cache) {
        if (index == tokens.size()) {
            if (left != null && op != null && right != null) {
                int evaluated = evaluate(left, op, right);
                Set<Integer> result = new HashSet<>();
                result.add(evaluated);

                return result;
            }
        }
        String token = tokens.get(index);
        if (left == null) {
            return search(tokens, index + 1, token, op, right, cache);
        } else if (op == null) {
            return search(tokens, index + 1, left, token, right, cache);
        } else if (right == null) {
            return search(tokens, index + 1, left, op, token, cache);
        }

        String cacheKey = getCacheKey(left, op, right, index);
        if (cache.containsKey(cacheKey)) {
            return cache.get(cacheKey);
        }

        // For the expression left op right, we can evaluate it in two ways:
        // 1. newLeft = evaluate(left op right), and then combine it with the rest of
        // the tokens
        // 2. combine right with the rest of the tokens first, and then evaluate (left
        // op newRight)

        int newLeft = evaluate(left, op, right);
        Set<Integer> option1 = search(tokens, index + 1, String.valueOf(newLeft), token, null, cache);

        Set<Integer> newRight = search(tokens, index + 1, right, token, null, cache);
        Set<Integer> option2;
        option2 = new HashSet<>();
        for (Integer nr : newRight) {
            int option2Value = evaluate(left, op, String.valueOf(nr));
            option2.add(option2Value);
        }

        // return the combined results from both options
        option1.addAll(option2);
        // key is index - 3 because we have consumed 3 tokens (left, op, right)
        cache.put(cacheKey, option1);
        return option1;
    }

    private int evaluate(String left, String op, String right) {
        int leftNum = Integer.parseInt(left);
        int rightNum = Integer.parseInt(right);
        switch (op) {
            case "+":
                return leftNum + rightNum;
            case "-":
                return leftNum - rightNum;
            case "*":
                return leftNum * rightNum;
            case "/":
                return leftNum / rightNum; // Assuming integer division
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }

    private String getCacheKey(String left, String op, String right, int index) {
        return left + op + right + String.valueOf(index);
    }
}
