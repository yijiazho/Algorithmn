package utility;

import java.util.Random;

public class TestUtil {
    
    public static int[] generateRandomArray(int n) {
        Random rand = new Random(42);
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt();
        }
        return arr;
    }
}
