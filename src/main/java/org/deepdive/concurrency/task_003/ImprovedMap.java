package org.deepdive.concurrency.task_003;

import java.util.HashMap;

public class ImprovedMap<K, V> extends HashMap<K, V> {

    private final HashMap<K, V> map;

    ImprovedMap() {
        map = new HashMap<>();
    }

    @Override
    public synchronized V put(K key, V value) {
        return map.put(key, value);
    }

    @Override
    public synchronized V get(Object key) {
        return map.get(key);
    }

    public int size() {
        return map.size();
    }
}
