package dataStructure.cache;

public interface Client<K, V> {
    V get(K key);

    void put(K key, V value);
}
