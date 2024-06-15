package dataStructure;

import java.util.Comparator;

public class MyHeap<V extends Comparable<V>> {
/*
    Keep a tree structure but use an array to implement
    Make sure parent.compareTo(child) < 0
              0
        1          2
      3   4     5     6
     7 8 9 10 11 12 13 14

 */

    private V[] array;
    private Comparator<V> comparator;
    int size;

    public MyHeap(int n) {
        this(n, (v1, v2) -> v1.compareTo(v2));
    }

    public MyHeap(int n, Comparator<V> comparator) {
        array = (V[]) new Object[n];
        this.comparator = comparator;
        size = 0;
    }

    public void offer(V v) {
        if (size == array.length) {
            expand();
        } else {
            array[size] = v;
            heapifyUp(size);
            size++;
        }
    }

    private void swap(int p, int q) {
        V tmp = array[p];
        array[p] = array[q];
        array[q] = tmp;
    }

    private void heapifyUp(int cur) {
        while (cur != 0) {
            int parent = (cur - 1) / 2;
            if (comparator.compare(array[parent], array[cur]) < 0) {
                break;
            } else {
                swap(parent, cur);
                cur = parent;
            }
        }
    }

    private void heapifyDown(int cur) {
        while (2 * cur + 1 < size) {
            int leftChild = 2 * cur + 1;
            int rightChild = 2 * cur + 2;
            int smallerChild;
            if (rightChild == size) {
                smallerChild = leftChild;
            } else {
                smallerChild = comparator
                            .compare(array[leftChild], array[rightChild]) < 0 ?
                        leftChild :
                        rightChild;
            }
            if (comparator.compare(array[cur], array[smallerChild]) < 0) {
                return;
            }
            swap(cur, smallerChild);
            cur = smallerChild;
        }
    }

    private void expand() {
        V[] newArray = (V[]) new Object[2 * size];
        for (int i = 0; i < size; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }

    public V poll() {
        if (size == 0) {
            return null;
        } else {
            V res = array[0];
            swap(0, size - 1);
            array[size - 1] = null;
            size--;
            heapifyDown(0);
            return res;
        }
    }

    public V peek() {
        if (size == 0) {
            return null;
        } else {
            return array[0];
        }
    }

    public static void main(String[] args) {

    }
}
