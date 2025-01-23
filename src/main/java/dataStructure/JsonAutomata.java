package dataStructure;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JsonAutomata {

    /**
     * Validates the given input string if it adheres to the structure of a simplified JSON.
     * string only contains 0, 1, a, b, the key must be valid non-empty string
     * the value can be boolean (T/F), null (N), number (0 and 1) and string.
     * @param s input string to be validated
     * @return whether it follows the simplified JSON structure
     */
    public boolean validateJson(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        State currentState = State.START;

        for (char c: s.toCharArray()) {
            switch (c) {
                case 'a':
                case 'b':
                    if (currentState == State.KEY_START || currentState == State.KEY) {
                         currentState = State.KEY;
                    } else if (currentState == State.STR_START) {
                        // do nothing
                    } else {
                        // cannot accept a/b in any other state, thus not a valid json
                        return false;
                    }
                    break;

                case '0':
                case '1':
                    if (currentState == State.KEY_START || currentState == State.KEY) {
                        currentState = State.KEY;
                    } else if (currentState == State.COL || currentState == State.NUM) {
                        currentState = State.NUM;
                    } else if (currentState == State.STR_START) {
                        // do nothing

                    } else {
                        return false;
                    }
                    break;

                case 'T':
                case 'F':
                    if (currentState == State.COL) {
                        currentState = State.BOOL;
                    } else {
                        return false;
                    }
                    break;

                case 'N':
                    if (currentState == State.COL) {
                        currentState = State.NULL;
                    } else {
                        return false;
                    }
                    break;

                case ':':
                    if (currentState == State.KEY_END) {
                        currentState = State.COL;
                    } else {
                        return false;
                    }
                    break;

                case '"':
                    if (currentState == State.OBJ_START || currentState == State.COMMA) {
                        currentState = State.KEY_START;
                    } else if (currentState == State.KEY) {
                        currentState = State.KEY_END;
                    } else if (currentState == State.COL) {
                        currentState = State.STR_START;
                    } else if (currentState == State.STR_START) {
                        currentState = State.STR_END;
                    } else {
                        return false;
                    }
                    break;

                case '{':
                    if (currentState == State.START) {
                        currentState = State.OBJ_START;
                    } else {
                        return false;
                    }
                    break;

                case '}':
                    if (currentState == State.BOOL || currentState == State.NUM || currentState == State.NULL || currentState == State.STR_END || currentState == State.COL) {
                        currentState = State.END;
                    } else {
                        return false;
                    }
                    break;

                case ',':
                    if (currentState == State.BOOL || currentState == State.NUM || currentState == State.NULL || currentState == State.STR_END) {
                        currentState = State.COMMA;
                    } else {
                        return false;
                    }
                    break;

                default:
                    // other chars that not accepted
                    return false;
            }
        }

        return currentState == State.END;
    }


    public static final void main (String[] args) {
        // String test = "{\"a0\":\"\",\"b1\":}";
        JsonAutomata jsonAutomata = new JsonAutomata();

        System.out.println("Enter input to validate:");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String input = reader.readLine();
                System.out.println(jsonAutomata.validateJson(input));
            }
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }
    }
}

enum State {
    START,
    END,
    OBJ_START,
    OBJ_END,
    KEY_START,
    KEY,
    KEY_END,
    COL,
    BOOL,
    NUM,
    NULL,
    STR_START,
    STR_END,
    COMMA
}