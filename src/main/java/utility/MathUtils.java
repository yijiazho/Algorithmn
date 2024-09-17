package utility;

public class MathUtils {

    public static int gcd(int x, int y) {
        if (x < y) {
            return gcd(y, x);
        }
        if (y == 0) {
            return x;
        }

        return gcd(y, x % y);
    }

    public static int lcm(int x, int y) {
        return Math.abs(x * y) / gcd(x, y);
    }
}
