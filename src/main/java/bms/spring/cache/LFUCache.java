package bms.spring.cache;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache<K, V> {

    private final int capacity;
    private int minFrequency;
    private final HashMap<K, V> keyToValue;
    private final HashMap<K, Integer> keyToFrequency;
    private final HashMap<Integer, LinkedHashSet<K>> frequencyToKeys;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.minFrequency = 0;
        this.keyToValue = new HashMap<>();
        this.keyToFrequency = new HashMap<>();
        this.frequencyToKeys = new HashMap<>();
    }

    public V get(K key) {
        if (!keyToValue.containsKey(key)) {
            return null; // Key not found
        }
        V value = keyToValue.get(key);
        updateFrequency(key);
        return value;
    }

    public void put(K key, V value) {
        if (capacity == 0) {
            return;
        }
        if (keyToValue.containsKey(key)) {
            keyToValue.put(key, value);
            updateFrequency(key);
        } else {
            if (keyToValue.size() >= capacity) {
                evictLFU();
            }
            keyToValue.put(key, value);
            keyToFrequency.put(key, 1);
            frequencyToKeys.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            minFrequency = 1;
        }
    }

    private void updateFrequency(K key) {
        int frequency = keyToFrequency.get(key);
        keyToFrequency.put(key, frequency + 1);

        frequencyToKeys.get(frequency).remove(key);
        if (frequency == minFrequency && frequencyToKeys.get(frequency).isEmpty()) {
            minFrequency++;
        }

        frequencyToKeys.computeIfAbsent(frequency + 1, k -> new LinkedHashSet<>()).add(key);
    }

    private void evictLFU() {
        K keyToEvict = frequencyToKeys.get(minFrequency).iterator().next();
        frequencyToKeys.get(minFrequency).remove(keyToEvict);

        if (frequencyToKeys.get(minFrequency).isEmpty()) {
            frequencyToKeys.remove(minFrequency);
        }
        keyToValue.remove(keyToEvict);
        keyToFrequency.remove(keyToEvict);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<K, V> kvMap : keyToValue.entrySet()) {
            sb.append(kvMap.getKey()).append(":").append(kvMap.getValue()).append(";");

        }
        sb.append("}");
        return sb.toString();
    }

}
