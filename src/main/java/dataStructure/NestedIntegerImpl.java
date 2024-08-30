package dataStructure;

import java.util.ArrayList;
import java.util.List;

public class NestedIntegerImpl implements NestedInteger{

    Integer value;
    List<NestedInteger> list;

    public NestedIntegerImpl(int val) {
        this.value = val;
        list = null;
    }

    public NestedIntegerImpl(List<NestedInteger> list) {
        this.list = new ArrayList<>(list);
        value = null;
    }

    @Override
    public boolean isInteger() {
        return value != null;
    }

    @Override
    public Integer getInteger() {
        return value;
    }

    @Override
    public void setInteger(int value) {
        this.value = value;
    }

    @Override
    public void add(NestedInteger ni) {

    }

    @Override
    public List<NestedInteger> getList() {
        return null;
    }
}
