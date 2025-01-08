package graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class WordSearch {

    private static final int[][] DIRECTIONS = new int[][] {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private int row;
    private int col;

    /**
     * Find words that exist in the board, with each word in the board being used at most once
     * @param board 2D array of characters, contain only lower case characters
     * @param words A list of lower case words
     * @return A list of word that exist in both board and words
     */
    public List<String> findWords(char[][] board, String[] words) {
        if (board == null || words == null || words.length == 0 || board.length == 0 || board[0].length == 0) {
            return null;
        }
        TrieNode root = buildTrie(words);
        this.row = board.length;
        this.col = board[0].length;

        Set<String> wordsInBoard = new HashSet<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                search(board, i, j, root, wordsInBoard);
            }
        }

        return wordsInBoard.stream().collect(Collectors.toList());
    }

    private void search(char[][] board, int i, int j, TrieNode cur, Set<String> res) {
        // if out of boundary, or is visiting, or the character not in the Trie, this is not a valid one
        if (i < 0 || j < 0 || i >= row || j >= col || board[i][j] == '#' || cur.nexts[board[i][j] - 'a'] == null) {
            return;
        }

        cur = cur.nexts[board[i][j] - 'a'];
        if (cur.isWord) {
            res.add(cur.word);
        }

        // mark as visiting temporarily
        char tmp = board[i][j];
        board[i][j] = '#';
        for (int[] dir: DIRECTIONS) {
            int ii = i + dir[0];
            int jj = j + dir[1];
            search(board, ii, jj, cur, res);
        }
        board[i][j] = tmp;

    }

    private TrieNode buildTrie(String[] words) {
        Trie trie = new Trie();
        TrieNode root = trie.root;

        for (String word: words) {
            trie.addWord(word);
        }
        return root;
    }
}

class Trie {
    TrieNode root;
    public Trie () {
        this.root = new TrieNode();
    }

    public void addWord (String s) {
        TrieNode cur = root;
        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            if (cur.nexts[idx] == null) {
                cur.nexts[idx] = new TrieNode();
            }
            cur = cur.nexts[idx];
        }
        cur.word = s;
        cur.isWord = true;
    }
}

class TrieNode {
    boolean isWord;
    String word;
    TrieNode[] nexts;

    public TrieNode () {
        this.isWord = false;
        this.word = "";
        this.nexts = new TrieNode[26];
    }
}