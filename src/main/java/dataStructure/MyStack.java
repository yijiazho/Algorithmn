package dataStructure;

import java.util.LinkedList;
import java.util.Queue;

public class MyStack<T> {

    private Queue<Node<T>> queue;

    public MyStack() {
        queue = new LinkedList<>();
    }

    public void push(T value) {
        Queue<Node<T>> newQueue = new LinkedList<>();

        newQueue.offer(new Node<>(value, queue));
        queue = newQueue;
    }

    public T pop() {
        Node<T> node = queue.poll();
        if (node == null) {
            return null;
        }
        queue = node.next != null ? node.next : new LinkedList<>();
        return node.value;
    }

    public T top() {
        Node<T> node = queue.peek();
        return node != null ? node.value : null;
    }

    public boolean empty() {
        return queue.isEmpty();
    }

    private static class Node<T> {
        T value;
        Queue<Node<T>> next;

        Node(T value, Queue<Node<T>> next) {
            this.value = value;
            this.next = next;
        }
    }
}
