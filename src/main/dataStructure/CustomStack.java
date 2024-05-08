package dataStructure;

import java.util.Objects;

public class CustomStack<T> {
    private T[] array;
    private int capacity;
    private int size;

    public CustomStack(int maxSize) {
        array = (T[]) new Object[maxSize];
        capacity = maxSize;
        size = 0;
    }

    public void push(T x) {
        if (size < capacity) {
            array[size] = x;
            size++;
        } else {
            throw new IllegalArgumentException("Out of capacity: " + capacity);
        }
    }

    public T pop() {
        if (size != 0) {
            size--;
            return array[size];
        } else {
            throw new IllegalArgumentException("The Stack is empty");
        }
    }

    // O(k) update top k elements
    public void increment(int k, T val) {
        for (int i = 0; i < Math.min(k, size); i++) {
            array[i] = val;
        }
    }

    public static final void main(String[] args) {

    }

}
