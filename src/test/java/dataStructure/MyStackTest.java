package dataStructure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MyStackTest {

    private MyStack<Integer> stack;

    @BeforeEach
    public void setup() {
        stack = new MyStack<>();
    }

    @Test
    public void testStackPushPopAndTop() {
        stack.push(1);
        assertEquals(1, stack.top());
        stack.push(2);
        stack.push(3);
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertTrue(stack.empty());
    }

    @Test
    public void testPopEmptyStack() {
        assertNull(stack.pop());
    }
}
