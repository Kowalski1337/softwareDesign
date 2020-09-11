package com.ifmo.vbaydyuk.hw1;

import com.sun.istack.internal.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;

public interface LRUCache<K, V> {
    int MIN_CAPACITY = 1;
    int MAX_CAPACITY = 10000;

    @Nullable
    V get(@Nonnull K key);

    void put(@Nonnull K key, @Nonnull V value);

    void remove(@Nonnull K key);

    int size();

    default boolean isEmpty() {
        return size() == 0;
    }

    default boolean containsKey(K key) {
        return get(key) != null;
    }

    Map<K, V> getAsMap();
}
