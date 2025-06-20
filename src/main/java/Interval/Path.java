package interval;

import java.util.ArrayList;
import java.util.List;

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