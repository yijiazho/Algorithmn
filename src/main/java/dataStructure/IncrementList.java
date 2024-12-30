package dataStructure;

import java.util.Map;
import java.util.Set;

public class IncrementList<K> {
    Map<Integer, Set<Node<K>>> freqToNode;
    Map<K, Integer> keyToFreq;
    Node<K> head;
    Node<K> tail;

    public IncrementList() {

    }

    public void inc(K key) {

    }

    public void dec(K key) {

    }

    public K getMaxKey() {

        return null;
    }

    public K getMinKey() {

        return null;
    }


}

class Node<K> {
    Node left;
    Node right;
    K key;

    public Node(Node left, Node right, K key) {
        this.left = left;
        this.right = right;
        this.key = key;
    }
}
