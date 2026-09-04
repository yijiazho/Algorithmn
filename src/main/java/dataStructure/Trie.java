package dataStructure;

import java.util.ArrayList;
import java.util.List;

public class Trie {
    
    TrieNode root;

    public Trie(List<String> patterns) {
        root = new TrieNode('R', false);
        for (int i = 0; i < patterns.size(); i++) {
            String s = patterns.get(i);
            buildPatternsFromRootToTail(s, i);
        }
    }

    /**
     * Find out all patterns in trie from the target text string
     * 
     * @param text string to search from
     * @return A list of integer pairs, the first integer is the index 
     *         in the string, second index is the index of original patterns
     */
    public List<List<Integer>> searchAllMatches(String text) {
        return searchAllMatchesFromIndex(text, 0);
    }

    private List<List<Integer>> searchAllMatchesFromIndex(String text, int index) {
        if (index > text.length()) {
            return new ArrayList<>();
        }
        
        List<List<Integer>> matchedPairs = new ArrayList<>();
        TrieNode cur = root;
        int i = index;
        // starting from root to match text, until first mismatch
        while (cur != null && i < text.length()) {
            char c = text.charAt(i);

            i++;
            cur = cur.childNodes[c - 'a'];
            if (cur != null && cur.isLeaf) {
                matchedPairs.add(List.of(index, cur.index));
            }
        }

        matchedPairs.addAll(searchAllMatchesFromIndex(text, index + 1));

        return matchedPairs;
    }

    private void buildPatternsFromRootToTail(String string, int k) {
        TrieNode cur = root;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c != '*') {
                if (cur.childNodes[c - 'a'] == null) {
                    cur.childNodes[c - 'a'] = new TrieNode(c, false);
                }
                cur = cur.childNodes[c - 'a'];
            } else {
                // wild card matching, all child is created
            }
        }
        cur.isLeaf = true;
        cur.index = k;
    }

    private class TrieNode{
        char c;
        boolean isLeaf;
        int index;
        TrieNode[] childNodes;

        public TrieNode(char c, boolean isLeaf) {
            this.c = c;
            this.isLeaf = isLeaf;
            childNodes = new TrieNode[26];
        }
    }
}
