package dataStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {

    public static final void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        List<Integer> res = list.stream()
                .filter((i) -> i > 0)
                .map(i -> 2 * i + 1)
                .collect(Collectors.toList());
    }
}
