package dataStructure;

import java.util.LinkedList;
import java.util.Queue;

public class MyStack<T> {

    private Queue<Object> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }

    public void push(T value) {
        Queue<Object> newQueue = new LinkedList<>();

        newQueue.offer(value);
        newQueue.offer(queue);

        queue = newQueue;
    }

    public T pop() {
        T value = (T) queue.poll();
        queue = (Queue) queue.peek();
        return value;
    }

    public T top() {
        return (T) queue.peek();
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    /*
    {}
    push 1 {1}
    push 2 {2, {1}}
    push 3 {3, {2, {1}}}
    pop ->  queue.poll()
    peek -> queue.peek()


     */
}
