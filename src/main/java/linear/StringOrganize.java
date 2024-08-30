package linear;

import java.util.*;

public class StringOrganize {
    public String reorganizeString(String s) {
        Map<Character, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // sort the freq map in ascending order
        List<Character> characterList = new ArrayList<>(freqMap.keySet());
        Collections.sort(characterList, (c1, c2) -> freqMap.get(c1) - freqMap.get(c2));

        char[] res = new char[s.length()];
        int i = 0;
        int tailIndex = characterList.size() - 1;
        char tailCharacter = characterList.get(tailIndex);

        if (freqMap.get(tailCharacter) > (s.length() + 1) / 2) {
            return "";
        }

        while (i < s.length()) {
            res[i] = tailCharacter;
            freqMap.put(tailCharacter, freqMap.get(tailCharacter) - 1);
            if (freqMap.get(tailCharacter) == 0) {
                tailIndex--;
                if (tailIndex == -1) {
                    break;
                }
                tailCharacter = characterList.get(tailIndex);
            }
            i+= 2;
        }

        i = 1;
        while (i < s.length()) {
            res[i] = tailCharacter;
            freqMap.put(tailCharacter, freqMap.get(tailCharacter) - 1);
            if (freqMap.get(tailCharacter) == 0) {
                tailIndex--;
                if (tailIndex == -1) {
                    break;
                }
                tailCharacter = characterList.get(tailIndex);
            }
            i+= 2;
        }

        return new String(res);
    }

    public static final void main(String[] args) {
        StringOrganize stringOrganize = new StringOrganize();

        String res = stringOrganize.reorganizeString("aababcaabcd");
        System.out.println(res);
    }
}
