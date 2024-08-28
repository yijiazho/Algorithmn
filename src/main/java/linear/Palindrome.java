package linear;

public class Palindrome {
    // find shortest palindrome, by adding to head only
    public String shortestPalindrome(String s) {
        int l = s.length();
        if (l == 0) {
            return "";
        }

        // if s.substring(i, j + 1) is palindrome
        boolean[][] isPalindrome = new boolean[l][l];

        //initialize
        for (int i = 0; i < l; i++) {
            isPalindrome[i][i] = true;
        }

        // calculate the boolean[][]
        for (int i = l - 1; i >= 0; i--) {
            for (int j = i + 1; j < l; j++) {
                if (j == i + 1) {
                    isPalindrome[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    if (s.charAt(i) == s.charAt(j) && isPalindrome[i + 1][j - 1]) {
                        isPalindrome[i][j] = true;
                    }
                }
            }
        }

        // find the longest palindrome from head
        int tail = 0;
        for (int i = 1; i < l; i++) {
            if (isPalindrome[0][i]) {
                tail = i;
            }
        }

        // concat
        String substring = s.substring(tail + 1);
        String reverse = new StringBuilder(substring).reverse().toString();
        return reverse + s;
    }

    public boolean validPalindrome(String s) {
        int l = s.length();
        // isPalin[i][j][k] means if s.substring(i, j + 1) is palindrome
        // if remove k from this substring
        boolean[][][] isPalin = new boolean[l][l][2];

        for (int i = 0; i < l; i++) {
            isPalin[i][i][0] = true;
        }

        for (int i = 0; i < l - 1; i++) {
            isPalin[i][i + 1][1] = true;
        }

        for (int i = l - 1; i >= 0; i--) {
            for (int j = i + 1; j < l; j++) {
                if (j == i + 1) {
                    isPalin[i][j][0] = s.charAt(i) == s.charAt(j);
                } else {
                    isPalin[i][i][0] = isPalin[i][j][0] ||
                            (isPalin[i + 1][j - 1][0] && s.charAt(i) == s.charAt(j));
                }

                // remove 1, either s[i], s[j] or not remove
                isPalin[i][j][1] = isPalin[i][j][1] || isPalin[i + 1][j][0];
                isPalin[i][j][1] = isPalin[i][j][1] || isPalin[i][j - 1][0];
                isPalin[i][j][1] = isPalin[i][j][1] || (isPalin[i + 1][j - 1][1] && s.charAt(i) == s.charAt(j));
            }
        }

        return isPalin[0][l - 1][0] || isPalin[0][l - 1][1];
    }

    public static final void main(String[] args) {
        Palindrome palindrome = new Palindrome();
        String s = palindrome.shortestPalindrome("");
        System.out.println(s);
    }
}
