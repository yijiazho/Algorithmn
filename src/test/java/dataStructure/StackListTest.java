package dataStructure;

import org.junit.jupiter.api.Test;

public class StackListTest {

    private StackList stackList;

    @Test
    public void testPushAndPop() {
        stackList = new StackList(2);

        stackList.push(1);
        stackList.push(2);
        stackList.push(3);
        stackList.push(4);
        stackList.push(5);

        assert (stackList.pop() == 5);
        assert (stackList.popAtStack(0) == 2);
        assert (stackList.popAtStack(2) == -1);
        assert (stackList.pop() == 4);

        stackList.push(6);
        stackList.push(7);
        assert (stackList.popAtStack(0) == 6);
        assert (stackList.pop() == 7);
    }
}
