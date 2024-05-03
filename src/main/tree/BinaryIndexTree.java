
import java.util.LinkedList;
import java.util.List;

public class BinaryIndexTree {

    //parent of bit[x] is bit[y], where y = x - (x & (-x))
    // bit[x] = sum from [y, x)
    int[] bit;
    int[] array;

    public BinaryIndexTree(int[] nums) {
        int size = nums.length;
        bit = new int[size + 1];
        array = new int[size];
        for (int i = 0; i < size; i++) {
            update(i, nums[i]);
            array[i] = nums[i];
        }
    }

    public void update(int index, int val) {
        int target = val - array[index];
        array[index] = val;
        index++;
        while (index < bit.length) {
            bit[index] += target;
            index += index & (-index);
        }
    }

    // return sum from left to right, both inclusive
    public int sum(int left, int right) {
        return query(right + 1) - query(left);
    }

    // return prefix sum from 0 to index, [0, index)
    private int query(int index) {
        int sum = 0;
        while (index > 0) {
            sum += bit[index];
            index -= index & (-index);
        }
        return sum;
    }


    public static void main(String[] args) {
        int[] array = {1, 3, 5, 7, 9, 11};
        BinaryIndexTree instance = new BinaryIndexTree(array);


        System.out.println(instance.query(2));
        System.out.println(instance.query(6));
        System.out.println(instance.sum(2,5));

        instance.update(2, 15);
        System.out.println("*********************");
        for (int i = 0; i < 6; i++) {
            System.out.println(instance.bit[i + 1]);
        }
        System.out.println(instance.query(6));
        System.out.println(instance.sum(2,5));
    }
}
