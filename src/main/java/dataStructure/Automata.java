package dataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Automata {
    /**
     * Return if a string is a valid number
     * @param s
     * @return
     */
    public boolean isNumber(String s) {
        /*
        s is valid if and only if it can be formed as
        (+/-) + A + dot + B + (E/e) + (+/-) + C
          1     2    3    4     5      6      7
         */

        /*
        Form a automata
        Start(0) -> State 1 (+/-)
        0 -> 2 (num)
        1 -> 2 (num)
        2 -> 2 (num)

        before dot

        0 -> 3 (dot)
        1 -> 3 (dot)
        2 -> 3 (dot)


        3 -> 4 (num)
        4 -> 4 (num)


        before exponential
        4 -> 5 (e/E)
        2 -> 5 (e/E)

        5 -> 6 (+/-)
        5 -> 7 (num)
        6 -> 7 (num)
        7 -> 7 (num)

        valid ending state is 2, 4, 7
         */


        // build automata, which is a list of map
        List<Map<AutomataKey, Integer>> automata = new ArrayList<>();
        // 0 -> 1, 2, 3
        automata.add(Map.of(AutomataKey.SIGN, 1, AutomataKey.NUM, 2, AutomataKey.DOT, 3));
        // 1 -> 2, 3
        automata.add(Map.of(AutomataKey.NUM, 2, AutomataKey.DOT, 3));
        // 2 -> 2, 3, 5
        automata.add(Map.of(AutomataKey.NUM, 2, AutomataKey.DOT, 3, AutomataKey.EXP, 5));
        // 3 -> 4
        automata.add(Map.of(AutomataKey.NUM, 4));
        // 4 ->  4, 5
        automata.add(Map.of(AutomataKey.NUM, 4, AutomataKey.EXP, 5));
        // 5 -> 6, 7
        automata.add(Map.of(AutomataKey.SIGN, 6, AutomataKey.NUM, 7));
        // 6 -> 7
        automata.add(Map.of(AutomataKey.NUM, 7));
        // 7 -> 7
        automata.add(Map.of(AutomataKey.NUM, 7));

        int state = 0;

        // traverse each character, and choose the automata map based on current state
        // select the key based on current char, if not in map return false
        // find the value, which is the next state
        for (char c: s.toCharArray()) {
            AutomataKey key;
            if (c == '+' || c == '-') {
                key = AutomataKey.SIGN;
            } else if (c == '.') {
                key = AutomataKey.DOT;
            } else if (c == 'e' || c == 'E') {
                key = AutomataKey.EXP;
            } else if (c >= '0' && c <= '9') {
                key = AutomataKey.NUM;
            } else {
                return false;
            }

            if (!automata.get(state).containsKey(key)) {
                return false;
            }
            state = automata.get(state).get(key);
        }

        // return if the final state in the valid list
        return state == 2 || state == 4 || state == 7;
    }


    public static final void main(String[] args) {
        Automata automata = new Automata();

        System.out.println(automata.isNumber("3.")); // true
        System.out.println(automata.isNumber("0.1")); // true
        System.out.println(automata.isNumber("abc")); // false
        System.out.println(automata.isNumber("1a")); // false
        System.out.println(automata.isNumber("2e10")); // true
        System.out.println(automata.isNumber("-90e3")); // true
        System.out.println(automata.isNumber("1e")); // false
        System.out.println(automata.isNumber("e3")); // false
        System.out.println(automata.isNumber("6e-1")); // true
        System.out.println(automata.isNumber("99e2.5")); // false
        System.out.println(automata.isNumber("53.5e93")); // true
        System.out.println(automata.isNumber("--6")); // false
        System.out.println(automata.isNumber("-+3")); // false
        System.out.println(automata.isNumber("95a54e53")); // false
    }

}

enum AutomataKey {
    NUM,
    SIGN,
    DOT,
    EXP
}