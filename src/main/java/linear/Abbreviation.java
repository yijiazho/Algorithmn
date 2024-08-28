package linear;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Abbreviation {

    /**
     * Find the unique abbreviations of the original list, the abbreviations
     * should have lesser length
     * @param words
     * @return
     */
    public List<String> wordsAbbreviation(List<String> words) {
        // make sure if two words has the same abbreviation, they will be next
        // to each other
        Collections.sort(words, (s1, s2) -> {
            if (s1.length() != s2.length()) {
                return s1.length() - s2.length();
            }
            if (s1.charAt(0) != s2.charAt(0)) {
                return s1.charAt(0) - s2.charAt(0);
            }
            int l = s1.length();
            if (s1.charAt(l - 1) != s2.charAt(l - 1)) {
                return s1.charAt(l - 1) - s2.charAt(l - 1);
            }
            for (int i = 0; i < l - 1; i++) {
                if (s1.charAt(i) != s2.charAt(i)) {
                    return s1.charAt(i) - s2.charAt(i);
                }
            }
            return 0;
        });
        List<String> abbreviations = new ArrayList<>();

        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            int l = word.length();
            if (abbreviations.isEmpty()) {
                // add initial abbreviation
                String initialAbbreviation = getAbbreviation(word, 0);
                abbreviations.add(initialAbbreviation);
            } else {
                String lastWord = words.get(i - 1);
                if (word.length() != lastWord.length() ||
                    word.charAt(0) != lastWord.charAt(0) ||
                    word.charAt(word.length() - 1) != lastWord.charAt(lastWord.length() - 1)) {
                    // add initial abbreviation
                    String initialAbbreviation = getAbbreviation(word, 0);
                    abbreviations.add(initialAbbreviation);
                } else {
                    // remove tail from the abbreviations
                    abbreviations.remove(abbreviations.size() - 1);

                    // for lastword and word, find first mismatch at index
                    int index = firstMismatch(word, lastWord);

                    // concat [0, index], [index + 1, l - 2] [l - 1, l - 1]

                    // if l - 2 == index + 1, add lastword, and word to abbreviations

                    // else add word.substring(0, index + 1) + String.valueOf(l - 2 - index) + word.substring(l - 1)
                    // for lastword and word
                    if (l - 2 == index + 1) {
                        abbreviations.add(lastWord);
                        abbreviations.add(word);
                    } else {
                        abbreviations.add(getAbbreviation(lastWord, index));
                        abbreviations.add(getAbbreviation(word, index));
                    }
                }
            }

        }

        return abbreviations;
    }

    private int firstMismatch(String s1, String s2) {
        int i = 0;
        while (i < s1.length()) {
            if (s1.charAt(i) != s2.charAt(i)) {
                return i;
            }
            i++;
        }
        return i;
    }

    private String getAbbreviation(String s, int startIndex) {
        int l = s.length();
        if (l == 2) {
            return s;
        }
        if (l - 2 == startIndex + 1) {
            return s;
        }
        return s.substring(0, startIndex + 1) + String.valueOf(l - 2 - startIndex) + s.substring(l - 1);
    }

    public static final void main(String[] args) {
        Abbreviation abbreviation = new Abbreviation();
        List<String> input = new ArrayList<>();
        input.add("like");
        input.add("god");
        input.add("internal");
        input.add("interval");
        input.add("me");

        List<String> output = abbreviation.wordsAbbreviation(input);

        System.out.println(output);
    }

}
