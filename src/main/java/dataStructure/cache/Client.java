package dataStructure.cache;

public interface Client<K, V> {
    V getById(K key);

    boolean insert(K key, V value);

    boolean update(K key, V value);

    V delete(K key);
}
