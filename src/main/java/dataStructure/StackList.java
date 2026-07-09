package dataStructure;

import java.util.Stack;
import java.util.TreeMap;

public class StackList {

    TreeMap<Integer, Stack<Integer>> emptyStacks;
    TreeMap<Integer, Stack<Integer>> fullStacks;
    TreeMap<Integer, Stack<Integer>> halfFilledStacks;
    int capacity;
    int totalStacks;

    public StackList(int capacity) {
        this.capacity = capacity;
        emptyStacks = new TreeMap<>((a, b) -> a - b);
        fullStacks = new TreeMap<>((a, b) -> a - b);
        halfFilledStacks = new TreeMap<>((a, b) -> a - b);

        emptyStacks.put(0, new Stack<>());
        totalStacks = 1;
    }

    public void push(int value) {
        Integer firstEmptyIndex = emptyStacks.isEmpty() ? Integer.MAX_VALUE : emptyStacks.firstKey();
        Integer firstHalfFilledIndex = halfFilledStacks.isEmpty() ? Integer.MAX_VALUE : halfFilledStacks.firstKey();

        if (firstEmptyIndex < firstHalfFilledIndex) {
            // insert to the first empty stack
            Stack<Integer> stack = emptyStacks.get(firstEmptyIndex);
            stack.push(value);
            if (capacity == 1) {
                emptyStacks.remove(firstEmptyIndex);
                fullStacks.put(firstEmptyIndex, stack);
            } else {
                emptyStacks.remove(firstEmptyIndex);
                halfFilledStacks.put(firstEmptyIndex, stack);
            }
        } else {
            // insert to the first half-filled stack
            Stack<Integer> stack = halfFilledStacks.get(firstHalfFilledIndex);
            stack.push(value);
            if (stack.size() == capacity) {
                halfFilledStacks.remove(firstHalfFilledIndex);
                fullStacks.put(firstHalfFilledIndex, stack);
            }
        }

        if (fullStacks.size() == totalStacks) {
            // all stacks are full, create a new empty stack
            emptyStacks.put(totalStacks, new Stack<>());
            totalStacks++;
        }
    }

    public int pop() {
        if (fullStacks.isEmpty() && halfFilledStacks.isEmpty()) {
            return -1;
        }

        Integer lastFullIndex = fullStacks.isEmpty() ? Integer.MIN_VALUE : fullStacks.lastKey();
        Integer lastHalfFilledIndex = halfFilledStacks.isEmpty() ? Integer.MIN_VALUE : halfFilledStacks.lastKey();

        if (lastFullIndex > lastHalfFilledIndex) {
            // pop from the last full stack
            Stack<Integer> stack = fullStacks.get(lastFullIndex);
            int value = stack.pop();
            if (capacity == 1) {
                fullStacks.remove(lastFullIndex);
                emptyStacks.put(lastFullIndex, stack);
            } else {
                fullStacks.remove(lastFullIndex);
                halfFilledStacks.put(lastFullIndex, stack);
            }
            return value;
        } else {
            // pop from the last half-filled stack
            Stack<Integer> stack = halfFilledStacks.get(lastHalfFilledIndex);
            int value = stack.pop();
            if (stack.isEmpty()) {
                halfFilledStacks.remove(lastHalfFilledIndex);
                emptyStacks.put(lastHalfFilledIndex, stack);
            }
            return value;
        }
    }

    public int popAtStack(int stackIndex) {
        if (stackIndex < 0 || stackIndex >= totalStacks) {
            return -1;
        }

        if (fullStacks.containsKey(stackIndex)) {
            Stack<Integer> stack = fullStacks.get(stackIndex);
            int value = stack.pop();
            if (capacity == 1) {
                fullStacks.remove(stackIndex);
                emptyStacks.put(stackIndex, stack);
            } else {
                fullStacks.remove(stackIndex);
                halfFilledStacks.put(stackIndex, stack);
            }
            return value;
        } else if (halfFilledStacks.containsKey(stackIndex)) {
            Stack<Integer> stack = halfFilledStacks.get(stackIndex);
            int value = stack.pop();
            if (stack.isEmpty()) {
                halfFilledStacks.remove(stackIndex);
                emptyStacks.put(stackIndex, stack);
            }
            return value;
        } else {
            // the stack is empty
            return -1;
        }
    }
}
