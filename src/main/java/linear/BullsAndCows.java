package linear;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BullsAndCows {

    public String getHint(String secret, String guess) {
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
        for (Map.Entry<Character, Integer> entry: map1.entrySet()) {

            if (map2.containsKey(entry.getKey())) {
                B += Math.min(map1.get(entry.getKey()), map2.get(entry.getKey()));
            }
        }

        return A + "A" + B + "B";
    }

    public static final void main(String[] args) {
        BullsAndCows bullsAndCows = new BullsAndCows();
        String res = bullsAndCows.getHint("1807", "7810");
        System.out.println(res);
    }
}
