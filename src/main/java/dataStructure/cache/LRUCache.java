package dataStructure.cache;

import java.util.HashMap;
import java.util.Map;

// use a Double Linked List and HashMap to construct, making sure O(1) read and write
// to implement a read through cache
public class LRUCache<K, V> implements Cache<K, V> {
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

    private int capacity;
    private int size;
    private Node head;
    private Node tail;
    private Map<K, Node> map;

    private Client<K, V> client;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        size = 0;

        head = new Node();
        tail = new Node();
        head.right = tail;
        tail.left = head;
        map = new HashMap<>();
    }

    private void insertToTail(Node node) {
        Node last = tail.left;
        last.right = node;
        node.left = last;
        node.right = tail;
        tail.left = node;
    }

    private Node remove(Node node) {
        Node left = node.left;
        Node right = node.right;
        left.right = right;
        right.left = left;

        return node;
    }

    private Node removeFromHead() {
        return remove(head.right);
    }

    @Override
    public V getById(K key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            insertToTail(node);
            return node.value;
        } else {
            V value = client.getById(key);
            if (value != null) {
                insert(key, value);
            }
            return value;
        }
    }

    @Override
    public boolean insert(K key, V value) {
        if (map.containsKey(key)) {
            return false;
        }
        boolean success = client.insert(key, value);
        if (success) {
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            insertToTail(newNode);
            size++;
            if (size > capacity) {
                Node removedNode = removeFromHead();
                size--;
                map.remove(removedNode.key);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean update(K key, V value) {
        if (!map.containsKey(key)) {
            return false;
        }
        boolean success = client.update(key, value);
        if (success) {
            Node node = map.get(key);
            node.value = value;
            remove(node);
            insertToTail(node);
            return true;
        }
        return false;
    }

    @Override
    public V delete(K key) {
        Node node = map.remove(key);
        if (node == null) {
            return null;
        }
        remove(node);
        size--;
        return client.delete(key);
    }
}