package dataStructure;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class TimeMap {

    private final Map<String, TreeMap<Integer, String>> map;

    public TimeMap() {
        map = new HashMap<>();
    }

    public void set(String key, String value, int timestamp) {
        // sorted by key, which is timestamp
        TreeMap<Integer, String> timeToValue = map.getOrDefault(key, new TreeMap<>());
        timeToValue.put(timestamp, value);
        map.put(key, timeToValue);
    }

    public String get(String key, int timestamp) {
        if (!map.containsKey(key)) {
            return "";
        }
        TreeMap<Integer, String> timeToValue = map.get(key);
        Integer floorKey = timeToValue.floorKey(timestamp);
        if (floorKey == null) {
            return "";
        }
        return timeToValue.get(floorKey);
    }
}
