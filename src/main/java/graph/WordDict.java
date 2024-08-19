package graph;

import java.util.*;
import java.util.stream.Collectors;

public class WordDict {
    class Tuple implements Comparable<Tuple> {
        int depth;
        String nodeName;

        public Tuple(final int depth, final String nodeName) {
            this.depth = depth;
            this.nodeName = nodeName;
        }

        @Override
        public int compareTo(Tuple that) {
            if (this.depth != that.depth) {
                return this.depth - that.depth;
            }
            return that.nodeName.compareTo(this.nodeName);
        }
    }
    public String longestWord(final String[] words) {


        // build a map
        // key = short word
        // value = list of long word with length + 1
        Map<String, List<String>> map = buildMap(words);

        // traverse the words and find the deepest one
        Map<String, Tuple> depth = new HashMap<>();
        Tuple maxDepthTuple = new Tuple(0, "");

        for (String word: words) {
            Tuple res = findDepth(map, word, depth);
            if (res.compareTo(maxDepthTuple) > 0) {
                maxDepthTuple = res;
            }
        }
        return maxDepthTuple.nodeName;
    }

    private Map<String, List<String>> buildMap(final String[] words) {
        Set<String> wordsSet = Arrays.stream(words).collect(Collectors.toSet());
        Map<String, List<String>> map = new HashMap<>();

        for (String word: words) {
            String last = word.substring(0, word.length() - 1);
            if (wordsSet.contains(last)) {
                List<String> list = map.getOrDefault(last, new ArrayList<>());
                list.add(word);
                map.put(last, list);
            }
        }

        return map;
    }

    // return a Tuple with the depth and nodename of deepest node
    private Tuple findDepth(Map<String, List<String>> map, String cur, Map<String, Tuple> depth) {
        if (!map.containsKey(cur) || map.get(cur).size() == 0) {
            Tuple tuple = new Tuple(1, cur);
            depth.put(cur, tuple);
            return tuple;
        }

        if (depth.containsKey(cur)) {
            return depth.get(cur);
        }

        Tuple maxDepthTuple = new Tuple(0, cur);
        for (String child: map.get(cur)) {
            Tuple childTuple = findDepth(map, child, depth);
            if (childTuple.compareTo(maxDepthTuple) > 0) {
                maxDepthTuple = childTuple;
            }
        }
        return new Tuple(maxDepthTuple.depth + 1, maxDepthTuple.nodeName);
    }

    public static final void main(String[] args) {
        String[] words = new String[] {"w", "wo", "wor", "worl", "world", "worldwonder"};
        String[] words2 = new String[] {"w", "wo", "wor", "sa", "saw", "sawe"};
        WordDict instance = new WordDict();
        String res = instance.longestWord(words2);
        System.out.println(res);

    }
}
