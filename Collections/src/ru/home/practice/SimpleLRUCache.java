package ru.home.practice;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by dima on 28.01.15.
 */
public class SimpleLRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;


    public SimpleLRUCache(int capacity) {
        super(capacity + 1, 1.1f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return this.size() > capacity;
    }

    public static void main(String[] args) {
        Map<Integer, String> m = new SimpleLRUCache<Integer, String>(2);
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        m.get(2);
        m.put(9, "e");
        System.out.println(m);
    }
}
