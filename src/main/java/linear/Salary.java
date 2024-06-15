package linear;

import java.util.Arrays;

public class Salary {

    public int minSalary(int[] ratings) {
        if (ratings.length == 1) {
            return 1;
        }

        int[] salaries = new int[ratings.length];
        Arrays.fill(salaries, 1);
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                salaries[i] = salaries[i - 1] + 1;
            }
        }

        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1] && salaries[i] <= salaries[i + 1]) {
                salaries[i] = salaries[i + 1] + 1;
            }
        }

        int sum = 0;
        for (int salary: salaries) {
            sum += salary;
        }
        return sum;
    }

    public static final void main(String[] args) {

    }
}
