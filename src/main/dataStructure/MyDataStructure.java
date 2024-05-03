import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;


class MyAppFeatureData implements Comparable<MyAppFeatureData> {

    private long userId;
    private long createdAt;

    @Override
    public int compareTo(MyAppFeatureData o) {
        return 0;
    }
}

public class MyDataStructure<T extends Comparable<T>>{
    Stack<T> stack =  new Stack<>();
    Queue<T> q = new LinkedList<>();

    PriorityQueue<T> pq = new PriorityQueue<>();
    public MyDataStructure() {

    }


}
