package Interval;

import java.util.*;

public class MaxProfits {


    /**
     * Find the maximum value of a sequence of jobs, making sure no
     * concurrent jobs are executed at the same time. Job has same start
     * time with another job with same end time is considered executable
     * @param jobs a list of jobs, with id, start, end and profit
     * @return a list of jobs representing the executing order to
     * maximize the profits
     */
    public List<Integer> maxProfits(List<Job> jobs) {
        if (jobs == null || jobs.size() == 0) {
            return null;
        }

        // sort by end time, since we want to update max(end) in order
        Collections.sort(jobs, (j1, j2) -> j1.end - j2.end);

        // the max profits possible at time equals start time of job
        // key = job end time, v = max profits possible
        TreeMap<Integer, Path> maxProfitPathByJob = new TreeMap<>();
        maxProfitPathByJob.put(0, new Path(0));

        Path maxProfitPath = new Path(0);

        // traverse and add
        for (Job job: jobs) {
             int floorKey = maxProfitPathByJob.floorKey(job.start);
             Path pathAtFloor = maxProfitPathByJob.get(floorKey);

             int lastProfit = maxProfitPathByJob.lastEntry().getValue().profits;

             // update if larger
             if (job.profit + pathAtFloor.profits > lastProfit) {
                 Path newPath = new Path(job.profit + pathAtFloor.profits, pathAtFloor.path);
                 newPath.path.add(job.id);
                 maxProfitPathByJob.put(job.end, newPath);

                 maxProfitPath = newPath;
             }
        }

        return maxProfitPath.path;
    }

    public static void main(String[] args) {

        MaxProfits maxProfits = new MaxProfits();
        List<Job> jobs = new ArrayList<>();
        jobs.add(new Job(1, 1, 2, 10));
        jobs.add(new Job(2, 3, 4, 15));
        jobs.add(new Job(3, 5, 20, 100));
        jobs.add(new Job(4, 2, 100, 1000));

        List<Integer> list = maxProfits.maxProfits(jobs);
        System.out.println(list);
    }
}

class Job {
    int id;
    int start;
    int end;
    int profit;

    public Job(int id, int start, int end, int profit) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.profit = profit;
    }

}

class Path {
    int profits;
    List<Integer> path;

    public Path(int profits) {
        this.profits = profits;
        this.path = new ArrayList<>();
    }

    public Path(int profits, List<Integer> path) {
        this.profits = profits;
        this.path = new ArrayList<>(path);
    }
}