package dataStructure.guess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class GuessStrategy {

    private static final int WORD_LENGTH = 6;
    private final GuessMachine guessMachine;

    // Key is each word, the value is a list of size 7, where each element is a set
    // of words that have that number of similarity with the key word. The index of
    // the list is the similarity score.
    private final Map<String, List<Set<String>>> graph = new HashMap<>();

    public GuessStrategy(int size, int limit) {
        this.guessMachine = new GuessMachine(size, limit);
    }

    /**
     * Guess the secret in a strategy so that the guess count is minimized.
     * 
     * @param guessMachine the guess machine, which contains the secret and the
     *                     dictionary. We can use the guessMachine.getDictionary()
     *                     to get the dictionary, and the secret is guaranteed to be
     *                     in the dictionary. Each word in the dictionary is a
     *                     6-character string. We can use the
     *                     guessMachine.guess(String guess) to make a guess as long
     *                     as the guess limit is not exceeded. If the guess is not
     *                     in the dictionary, it will return -1. If the guess is in
     *                     the dictionary, it will return the number of characters
     *                     that are in the correct position. That is, if the guess
     *                     is correct, it will return 6.
     * 
     */
    public void guess(GuessMachine guessMachine) {
        Set<String> dictionary = guessMachine.getDictionary();

        buildGraph();

        // Each vertex is connected to other N - 1 vertices because each two words have
        // a similarity score between 0 and 6. Each time we make a guess, we can limit
        // the next guess to the set with index of similarity score.
        // Use a BFS approach to find the secret, starting from a random word
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        String startWord = dictionary.iterator().next();
        queue.offer(startWord);
        visited.add(startWord);

        while (!queue.isEmpty()) {
            String word = queue.poll();
            int similarity;
            try {
                similarity = guessMachine.guess(word);
            } catch (GuessLimitException e) {
                System.out.println("Guess limit exceeded");
                return;
            }

            if (similarity == WORD_LENGTH) {
                System.out.println("Found the secret: " + word);
                return;
            }

            for (String nextWord : graph.get(word).get(similarity)) {
                if (visited.add(nextWord)) {
                    queue.offer(nextWord);
                }
            }
        }

        System.out.println("The secret was not in the dictionary");
    }

    /**
     * Build a graph from the dictionary, where each vertex is a word in the
     * dictionary, and each edge is the similarity between two words. The similarity
     * is defined as the number of characters at the same position in the two words.
     * If the two words are the same, the similarity is 6
     */
    private void buildGraph() {
        Set<String> dictionary = guessMachine.getDictionary();

        for (String word : dictionary) {
            graph.computeIfAbsent(word, k -> {
                List<Set<String>> list = new ArrayList<>(WORD_LENGTH + 1);
                for (int i = 0; i <= WORD_LENGTH; i++) {
                    list.add(new HashSet<>());
                }
                return list;
            });
        }

        for (String word : dictionary) {
            for (String otherWord : dictionary) {
                if (word.equals(otherWord)) {
                    continue;
                }
                int similarity = getSimilarity(word, otherWord);
                graph.get(word).get(similarity).add(otherWord);
                graph.get(otherWord).get(similarity).add(word);
            }
        }
    }

    private int getSimilarity(String word1, String word2) {
        int similarity = 0;
        for (int i = 0; i < WORD_LENGTH; i++) {
            if (word1.charAt(i) == word2.charAt(i)) {
                similarity++;
            }
        }
        return similarity;
    }
}
