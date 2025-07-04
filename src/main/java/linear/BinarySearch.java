package linear;

public class BinarySearch {

    /**
     * In an non decresing array, find out the first index that is smaller or equal
     * to target value
     * 
     * @param array  non empty non decresign array
     * @param target target value
     * @return the first index smaller or equal to target
     */
    public int find(int[] array, int target) {
        int l = array.length;
        int left = 0;
        int right = l - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (array[mid] >= target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

}
