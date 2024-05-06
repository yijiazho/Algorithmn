package utility;

public class Base62Encoder {
    private static final String BASE_62_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE = 62;


    private String encode(long input) {

        StringBuilder sb = new StringBuilder();
        while (input > 0) {
            int index = (int) input % BASE;
            sb.insert(0, BASE_62_CHARS.charAt(index));
            input = input / BASE;
        }
        return sb.toString();
    }

    private long decode(String input) {
        long res = 0L;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int index = indexOf(c);
            res = BASE * res + index;
        }
        return res;
    }

    private int indexOf(char c) {
        if (c >= '0' && c <= '9') {
            return (int) (c - '0');
        } else if (c >= 'a' && c <= 'z') {
            return 10 + (int) (c - 'a');
        } else {
            return 36 + (int) (c - 'A');
        }
    }


}
