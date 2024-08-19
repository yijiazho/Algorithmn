package linear;

import java.util.*;

interface NestedInteger {
    public boolean isInteger();

    // return single integer
    // otherwise return null
    public Integer getInteger();

    // return a list if holding a list
    // otherwise return empty
    public List<NestedInteger> getList();
}

// flatten in constructor
public class NestedIterator implements Iterator<Integer> {
    private final Queue<Integer> queue = new LinkedList<>();
    private int count = 0;

    public NestedIterator(final List<NestedInteger> nestedList) {
        if (nestedList != null || nestedList.size() != 0) {
            flatten(nestedList, queue);
            count = queue.size();
        }
    }

    @Override
    public boolean hasNext() {
        return count != 0;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        Integer next = queue.poll();
        count--;
        return next;
    }

    private void flatten(final List<NestedInteger> nestedIntegers, final Queue<Integer> queue) {
        for (NestedInteger nestedInteger: nestedIntegers) {
            if (nestedInteger.isInteger()) {
                queue.offer(nestedInteger.getInteger());
            } else {
                flatten(nestedInteger.getList(), queue);
            }
        }
    }
}

class NestedIterator2 implements Iterator<Integer> {
    private Stack<ListIterator> stack;
    private Integer cache;

    public NestedIterator2 (final List<NestedInteger> nestedList) {
        stack = new Stack<>();
        stack.push(nestedList.listIterator());
        cache = null;
    }

    @Override
    public boolean hasNext() {
        cacheNext();
        return cache != null;
    }

    @Override
    public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        Integer result = cache;
        cache = null;
        return result;
    }

    // cache the next value
    private void cacheNext() {
        if (cache != null) {
            return;
        }

        while (!stack.isEmpty()) {
            ListIterator<NestedInteger> topIterator = stack.peek();
            if (!topIterator.hasNext()) {
                stack.pop();
                continue;
            }

            NestedInteger next = topIterator.next();

            if (next.isInteger()) {
                cache = next.getInteger();
                return;
            } else {
                stack.push(next.getList().listIterator());
            }
        }
    }
}
