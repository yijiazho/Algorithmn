import java.util.*;
import java.util.stream.Collectors;

class TrieNode{

    Map<Character, TrieNode> children;
    boolean isEndOfTheWord;
    int count;
    String word;

    public TrieNode() {
        children = new HashMap<>();
    }
}
public class Trie {
    private TrieNode root;
    public Trie() {
        root = new TrieNode();
    }

    // insert a string into the data structure
    public void insert(String s) {
        TrieNode cur = root;
        for (char c: s.toCharArray()) {
            if (!cur.children.containsKey(c)) {
                cur.children.put(c, new TrieNode());
            }
            cur = cur.children.get(c);
        }
        cur.isEndOfTheWord = true;
    }

    // return the string starts with the given substring, ordered by frequency.
    public List<String> startWith(String s) {
        List<String> res = new ArrayList<>();
        TrieNode cur = root;
        for (char c: s.toCharArray()) {
            if (!cur.children.containsKey(c)) {
                return null;
            } else {
                cur = cur.children.get(c);
            }
        }

        PriorityQueue<TrieNode> queue = new PriorityQueue<TrieNode>((node1, node2) -> {
            return node1.count - node2.count;
        });
        searchFromRoot(cur, queue, 5);
        return queue.stream()
                .map(trieNode -> trieNode.word)
                .collect(Collectors.toList());
    }

    private void searchFromRoot(TrieNode root, PriorityQueue<TrieNode> queue, int k) {
        if (root.isEndOfTheWord) {
            queue.offer(root);
            if (queue.size() > k){
                queue.poll();
            }
        }
        for (Map.Entry<Character, TrieNode> entry :root.children.entrySet()) {
            searchFromRoot(entry.getValue(), queue, k);
        }
    }
}
