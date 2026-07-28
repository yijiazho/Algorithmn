package dataStructure.guess;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class GuessMachine {
    private static final String chars = "abcdefghijklmnopqrstuvwxyz";
    private final String secret;
    private final Set<String> dictionary = new HashSet<>();
    private final Random random = new Random();
    private final int limit;
    private int guessCount;

    public GuessMachine(int size, int limit) {
        // randomly generate some strings of length 6 in dictionary
        // and select a random string as secret
        for (int i = 0; i < size; i++) {
            while (true) {
                String str = generateRandomString();
                if (!dictionary.contains(str)) {
                    dictionary.add(str);
                    break;
                }
            }
        }
        // select a random string as secret
        int index = this.random.nextInt(size);
        this.secret = (String) dictionary.toArray()[index];
        guessCount = 0;
        this.limit = limit;
    }

    /**
     * Given a secret string and a guess string, return the matching hint. 'A' means
     * the number of characters in the guess that are in the correct position, and
     * 'B'
     * means the number of characters in the guess that are in the secret string but
     * are
     * in the wrong position.
     * 
     * @param guess the guess string
     * @return hint in format of "xAyB"
     */
    public String guessWithHint(String guess) {
        int l = secret.length();
        int A = 0;
        int B = 0;

        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();

        // calculate 2 maps
        for (int i = 0; i < l; i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                A++;
            } else {
                map1.put(secret.charAt(i), map1.getOrDefault(secret.charAt(i), 0) + 1);
                map2.put(guess.charAt(i), map2.getOrDefault(guess.charAt(i), 0) + 1);
            }
        }

        // compare 2 maps
        for (Map.Entry<Character, Integer> entry : map1.entrySet()) {

            if (map2.containsKey(entry.getKey())) {
                B += Math.min(map1.get(entry.getKey()), map2.get(entry.getKey()));
            }
        }

        return A + "A" + B + "B";
    }

    /**
     * Guess the secret string. If the guess is not in the dictionary, return -1. If
     * the guess is in the dictionary, return the number of characters that are in
     * the correct position. That is, if the guess is correct, return 6.
     * 
     * @param guess the guess string
     * @return the number of characters that are in the correct position, or -1 if
     *         the guess is not in the dictionary
     */
    public int guess(String guess) {
        if (guessCount >= limit) {
            throw new GuessLimitException("Guess limit exceeded");
        }

        guessCount++;
        if (!dictionary.contains(guess)) {
            return -1;
        }
        int count = 0;
        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                count++;
            }
        }
        return count;
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public String getSecret() {
        return secret;
    }

    private String generateRandomString() {
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 6; j++) {
            sb.append(chars.charAt(this.random.nextInt(chars.length())));
        }
        return sb.toString();
    }
}
