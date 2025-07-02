package dataStructure;

import java.util.ArrayList;
import java.util.List;

public class NestedIntegerImpl implements NestedInteger {

    private Integer value;
    private final List<NestedInteger> list;
    private final boolean isSingleInteger;

    public NestedIntegerImpl(int val) {
        this.value = val;
        list = null;
        isSingleInteger = true;
    }

    public NestedIntegerImpl(List<NestedInteger> list) {
        this.list = new ArrayList<>(list);
        value = null;
        isSingleInteger = false;
    }

    @Override
    public boolean isInteger() {
        return isSingleInteger;
    }

    @Override
    public Integer getInteger() {
        if (isSingleInteger) {
            return value;
        } else {
            return null;
        }
    }

    @Override
    public void setInteger(int value) {
        if (isSingleInteger) {
            this.value = value;
        } else {
            throw new RuntimeException("Nested Integer holds a list. Cannot set the integer value");
        }
    }

    @Override
    public void add(NestedInteger ni) {
        if (!isSingleInteger) {
            list.add(ni);
        } else {
            throw new RuntimeException("Nested Integer holds a single integer. Cannot add a list to it.");
        }
    }

    @Override
    public List<NestedInteger> getList() {
        if (!isSingleInteger) {
            return list;
        } else {
            return null;
        }
    }
}
