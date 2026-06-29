package dataStructure.sexpr;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

public class Parser {

    /**
     * Parse the input string into a list of Expr objects.
     * Converts infix expressions like "1+2*3" into prefix S-expression trees:
     * ListExpr([AtomExpr("+"), AtomExpr("1"), ListExpr([AtomExpr("*"), AtomExpr("2"), AtomExpr("3")])])
     * Respects standard operator precedence: * / bind tighter than + -
     *
     * @param input
     * @return
     */
    List<Expr> parse(String input) {
        Queue<String> tokens = tokenize(input);
        List<Expr> result = new ArrayList<>();
        while (!tokens.isEmpty()) {
            result.add(parseAddSub(tokens));
        }
        return result;
    }

    private Queue<String> tokenize(String input) {
        Queue<String> tokens = new ArrayDeque<>();
        int i = 0;
        while (i < input.length()) {
            char c = input.charAt(i);
            if (Character.isWhitespace(c)) {
                i++;
            } else if (c == '(' || c == ')' || c == '+' || c == '-' || c == '*' || c == '/') {
                tokens.add(String.valueOf(c));
                i++;
            } else {
                int start = i;
                while (i < input.length() && Character.isDigit(input.charAt(i))) {
                    i++;
                }
                tokens.add(input.substring(start, i));
            }
        }
        return tokens;
    }

    // Handles + and - (lowest precedence, left-associative)
    private Expr parseAddSub(Queue<String> tokens) {
        Expr left = parseMulDiv(tokens);
        while (!tokens.isEmpty() && (tokens.peek().equals("+") || tokens.peek().equals("-"))) {
            String op = tokens.poll();
            Expr right = parseMulDiv(tokens);
            left = new ListExpr(Arrays.asList(new AtomExpr(op), left, right));
        }
        return left;
    }

    // Handles * and / (higher precedence, left-associative)
    private Expr parseMulDiv(Queue<String> tokens) {
        Expr left = parseFactor(tokens);
        while (!tokens.isEmpty() && (tokens.peek().equals("*") || tokens.peek().equals("/"))) {
            String op = tokens.poll();
            Expr right = parseFactor(tokens);
            left = new ListExpr(Arrays.asList(new AtomExpr(op), left, right));
        }
        return left;
    }

    // Handles atoms (numbers) and parenthesized sub-expressions
    private Expr parseFactor(Queue<String> tokens) {
        String token = tokens.poll();
        if (token.equals("(")) {
            Expr expr = parseAddSub(tokens);
            tokens.poll(); // consume ')'
            return expr;
        }
        return new AtomExpr(token);
    }

}
