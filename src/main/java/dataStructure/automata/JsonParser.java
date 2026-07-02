package dataStructure.automata;

import java.util.LinkedList;
import java.util.Queue;

public class JsonParser {

    // the goal is to ;flatten the json provided

    /**
     * 
     * @param input Json string formatted like {"user" : {"id" : ...}}
     * @return flatten json string with one level
     */
    public String flatten(String input) {
        // if we meed '{' we go to next level, and pass the current key

        Queue<Character> queue = new LinkedList<>();
        for (char c : input.toCharArray()) {
            // do some validations here
            queue.offer(c);
        }
        String output = flattenObject(queue, "");
        return output;
    }

    private String flattenObject(Queue<Character> queue, String prefix) {
        String key = "";
        StringBuilder value = new StringBuilder();
        StringBuilder result = new StringBuilder();
        int count = 0;
        result.append('{');

        while (queue.isEmpty()) {
            char c = queue.poll();
            if (c == '{') {
                String next = flattenObject(queue, prefix + key.toString() + ".");

            } else if (c == '"') {
                // decide if it is start/end of the string value
                count++;

                // update the key or value here
                if (count % 2 == 0) {
                    if (count == 2) {
                        key = value.toString();
                        result.append('"').append(key).append('"').append(':');
                    } else {
                        result.append('"').append(value.toString()).append('"');
                    }
                    value = new StringBuilder();
                }
            } else if (c == '}') {
                // return to parent
                break;
            } else if (c == ':' || c == ',') {

            } else {
                // valid string
                value.append(c);
            }
        }
        result.append('}');
        return result.toString();
    }

    public static void main(String[] args) {
        JsonParser parser = new JsonParser();
        String input = "{\"key\":\"value\"}";
        String result = parser.flatten(input);
    }
}
