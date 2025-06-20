package interval;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MaxProfitsTest {

    private MaxProfits maxProfits;

    @BeforeEach
    public void setup() {
        maxProfits = new MaxProfits();
    }

    @Test
    public void testMaxProfits() {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(0, 1, 2, 10));
        jobs.add(new Job(1, 3, 4, 15));
        jobs.add(new Job(2, 5, 20, 100));
        jobs.add(new Job(3, 2, 10, 50));

        List<Integer> result = maxProfits.maxProfits(jobs);
        assertEquals(List.of(0, 1, 2), result);
    }

    @Test
    public void testMaxProfitsWithShorterPath() {
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(0, 1, 2, 10));
        jobs.add(new Job(1, 3, 4, 15));
        jobs.add(new Job(2, 5, 20, 100));
        jobs.add(new Job(3, 2, 100, 1000));

        List<Integer> result = maxProfits.maxProfits(jobs);
        assertEquals(List.of(0, 3), result);
    }
}
