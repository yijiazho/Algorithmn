package math;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dataStructure.sexpr.Expr;

public class ParserTest {

    private Parser parser;

    @BeforeEach
    public void setup() {
        parser = new Parser();
    }

    @Test
    public void testParseAddSubOnly() {
        // Left-associative: (1+2)-3
        List<Expr> result = parser.parse("1+2-3");
        assertEquals(1, result.size());
        assertEquals("(- (+ 1 2) 3)", result.get(0).expr());
    }

    @Test
    public void testParseMulDivOnly() {
        // Left-associative: (2*3)/4
        List<Expr> result = parser.parse("2*3/4");
        assertEquals(1, result.size());
        assertEquals("(/ (* 2 3) 4)", result.get(0).expr());
    }

    @Test
    public void testParseMixedPrecedence() {
        // * binds tighter: 1+(2*3)
        List<Expr> result = parser.parse("1+2*3");
        assertEquals(1, result.size());
        assertEquals("(+ 1 (* 2 3))", result.get(0).expr());
    }

    @Test
    public void testParseParenthesesOverridePrecedence() {
        // Parens force addition first: (1+2)*3
        List<Expr> result = parser.parse("(1+2)*3");
        assertEquals(1, result.size());
        assertEquals("(* (+ 1 2) 3)", result.get(0).expr());
    }

    @Test
    public void testParseSingleAtom() {
        List<Expr> result = parser.parse("42");
        assertEquals(1, result.size());
        assertTrue(result.get(0).isAtom());
        assertEquals("42", result.get(0).expr());
    }

    @Test
    public void testParseWithSpaces() {
        List<Expr> result = parser.parse("1 + 2 * 3");
        assertEquals(1, result.size());
        assertEquals("(+ 1 (* 2 3))", result.get(0).expr());
    }

    @Test
    public void testParseNestedExpression() {
        // 1+(2*3) - 4/2 → (- (+ 1 (* 2 3)) (/ 4 2))
        List<Expr> result = parser.parse("1+2*3-4/2");
        assertEquals(1, result.size());
        assertEquals("(- (+ 1 (* 2 3)) (/ 4 2))", result.get(0).expr());
    }

    @Test
    public void testParseNestedParentheses() {
        // ((1+2)*3)+4
        List<Expr> result = parser.parse("((1+2)*3)+4");
        assertEquals(1, result.size());
        assertEquals("(+ (* (+ 1 2) 3) 4)", result.get(0).expr());
    }
}
