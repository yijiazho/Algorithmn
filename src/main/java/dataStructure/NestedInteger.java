package dataStructure;

import java.util.List;

public interface NestedInteger {
    // If we want to force the constructor, we can use either abstract class
    // or a factory for interface

    /**
     * If the NestedInteger holds a single integer, rather than a nested list
     * 
     * @return true if it's integer, false if it's list
     */
    boolean isInteger();

    /**
     * Returns the held integer it holds, if it's a list return null
     * 
     * @return the held integer
     */
    Integer getInteger();

    /**
     * Set the integer to hold a single integer
     * 
     * @param value
     */
    void setInteger(int value);

    /**
     * Add a nested list to this NestedInteger
     * 
     * @param ni
     */
    void add(NestedInteger ni);

    /**
     * get the list of NestedIntegers if it holds a list, otherwise return null
     * 
     * @return
     */
    List<NestedInteger> getList();
}
