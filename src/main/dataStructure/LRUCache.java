import java.util.HashMap;
import java.util.Map;

// use a Double Linked List and HashMap to construct, making sure O(1) read and write
public class LRUCache<K, V> {
    class Node {
        K key;
        V value;
        Node left;
        Node right;

        public Node(K _key, V _value) {
            key = _key;
            value = _value;
        }

        public Node() {

        }
    }

    int capacity;
    int size;
    Node head;
    Node tail;
    Map<K, Node> map;

    public LRUCache(int _capacity) {
        capacity = _capacity;
        size = 0;
        
        head = new Node();
        tail = new Node();
        head.right = tail;
        tail.left = head;
        map = new HashMap<>();
    }

    public V get(K key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            insertToTail(node);
            return node.value;
        } else {
            return null;
        }
    }

    public void put(K key, V value) {

        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            remove(node);
            insertToTail(node);
        } else {
            Node newNode = new Node(key, value);
            map.put(key, newNode);
            insertToTail(newNode);
            size++;
            if (size > capacity) {
                Node removedNode = removeFromHead();
                size--;
                map.remove(removedNode.key);
            }
        }
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
}