package dataStructure.cache;

import java.util.HashMap;
import java.util.Map;

public class LFUCache<K, V> implements Cache<K, V> {

    Map<K, Node> map;
    int size;
    int capacity;
    Node head;
    Node tail;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        head = new Node();
        tail = new Node();
        head.right = tail;
        tail.left = head;
        map = new HashMap<>();
    }

    @Override
    public V getById(K key) {
        Node node = map.get(key);
        if (node == null) {
            return null;
        }
        promote(node);
        return node.value;
    }

    @Override
    public boolean insert(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            promote(node);
            return true;
        } else {
            if (size == capacity) {
                Node toRemove = removeFromTail();
                map.remove(toRemove.key);
            }
            Node newNode = new Node(key, value);
            insertToHead(newNode);
            map.put(key, newNode);
            return true;
        }
    }

    @Override
    public boolean update(K key, V value) {
        Node node = map.get(key);
        if (node == null) {
            return false;
        }
        node.value = value;
        promote(node);
        return true;
    }

    @Override
    public V delete(K key) {
        Node node = map.get(key);
        if (node == null) {
            return null;
        }
        remove(node);
        map.remove(key);
        return node.value;
    }

    private void remove(Node node) {
        Node left = node.left;
        Node right = node.right;
        left.right = right;
        right.left = left;
        size--;
    }

    private void insertToHead(Node node) {
        Node first = head.right;
        head.right = node;
        node.left = head;
        node.right = first;
        first.left = node;
        size++;
    }

    private Node removeFromTail() {
        Node last = tail.left;
        remove(last);
        return last;
    }

    private Node promote(Node node) {
        remove(node);
        insertToHead(node);
        return node;
    }

    class Node {
        K key;
        V value;
        Node left;
        Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node() {

        }
    }
}
