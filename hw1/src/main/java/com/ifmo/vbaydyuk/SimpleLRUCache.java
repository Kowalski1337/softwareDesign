package com.ifmo.vbaydyuk;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("WeakerAccess")
public class SimpleLRUCache<K, V> extends AbstractLRUCache<K, V> implements LRUCache<K, V> {
    private final Map<K, Node<K, V>> cache = new HashMap<>();
    private Node<K, V> leastRecentlyUsed = null;
    private Node<K, V> mostRecentlyUsed = null;
    private int currentSize = 0;

    public SimpleLRUCache() {

    }

    public SimpleLRUCache(int capacity) {
        super(capacity);
    }

    public SimpleLRUCache(@Nonnull Map<K, V> map, int capacity) {
        super(capacity);
        map.forEach(super::put);
    }

    public SimpleLRUCache(@Nonnull Map<K, V> map) {
        map.forEach(super::put);
    }

    @Override
    protected int doSize() {
        return currentSize;
    }

    @Override
    protected void doPut(@Nonnull K key, @Nonnull V value) {
        Node<K, V> existingValue = cache.get(key);
        if (existingValue != null) {
            existingValue.setValue(value);
            updateMRU(existingValue);
        } else {
            Node<K, V> newMSU = new Node<>(key, value, mostRecentlyUsed);
            if (mostRecentlyUsed != null) mostRecentlyUsed.next = newMSU;
            cache.put(key, newMSU);
            mostRecentlyUsed = newMSU;

            if (currentSize == capacity) {
                cache.remove(leastRecentlyUsed.key);
                leastRecentlyUsed = leastRecentlyUsed.next;
                leastRecentlyUsed.prev = null;
            } else {
                if (currentSize == 0) {
                    leastRecentlyUsed = newMSU;
                }
                ++currentSize;
            }
        }
    }

    @Override
    @Nullable
    protected V doGet(@Nonnull K key) {
        Node<K, V> res = cache.get(key);
        if (res == null) return null;

        updateMRU(res);

        return res.value;
    }

    @Override
    protected void doRemove(@Nonnull K key) {
        Node<K, V> toRemove = cache.remove(key);
        if (toRemove == null) return;

        currentSize--;

        if (currentSize == 0) {
            mostRecentlyUsed = null;
            leastRecentlyUsed = null;
            return;
        }

        if (mostRecentlyUsed.key == toRemove.key) {
            mostRecentlyUsed.prev.next = null;
            mostRecentlyUsed = mostRecentlyUsed.prev;
        }

        if (leastRecentlyUsed.key == toRemove.key) {
            leastRecentlyUsed.next.prev = null;
            leastRecentlyUsed = leastRecentlyUsed.next;
        }
    }

    @Override
    protected K leastRecentlyUsed() {
        return leastRecentlyUsed == null ? null : leastRecentlyUsed.key;
    }

    @Override
    protected K mostRecentlyUsed() {
        return mostRecentlyUsed == null ? null : mostRecentlyUsed.key;
    }

    @Nullable
    @Override
    protected V justGet(@Nonnull K key) {
        Node<K, V> res = cache.get(key);
        return res == null ? null : res.getValue();
    }

    private void updateMRU(Node<K, V> newMRU) {
        if (newMRU == mostRecentlyUsed) {
            return;
        }

        if (newMRU == leastRecentlyUsed) {
            newMRU.next.prev = null;
            leastRecentlyUsed = newMRU.next;
        } else {
            newMRU.next.prev = newMRU.prev;
            newMRU.prev.next = newMRU.next;
        }

        newMRU.prev = mostRecentlyUsed;
        mostRecentlyUsed.next = newMRU;
        mostRecentlyUsed = newMRU;
        mostRecentlyUsed.next = null;
    }

    @Override
    public Map<K, V> getAsMap() {
        return cache.values().stream().collect(Collectors.toMap(Node::getKey, Node::getValue));
    }

    private static final class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> prev;
        private Node<K, V> next;

        private K getKey() {
            return key;
        }

        private V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        private Node(@Nonnull K key, @Nonnull V value, Node<K, V> prev) {
            this.key = key;
            this.value = value;
            this.prev = prev;
        }
    }
}
