package com.ifmo.vbaydyuk.hw1;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import com.ifmo.vbaydyuk.hw1.AbstractLRUCache;
import com.ifmo.vbaydyuk.hw1.LRUCache;
import com.ifmo.vbaydyuk.hw1.SimpleLRUCache;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LRUCacheTest {

    private static final Map<String, String> testMap = ImmutableMap.of(
            "1", "1",
            "2", "2",
            "3", "3",
            "4", "4",
            "5", "5"
    );

    @Test
    void testCapacityRange() {
        assertThrows(AssertionError.class, () -> new SimpleLRUCache<>(-1));
        assertThrows(AssertionError.class, () -> new SimpleLRUCache<>(100000));
    }

    @Test
    void testPut() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        Map<String, String> actual = lruCache.getAsMap();

        assertEquals(testMap, actual);
    }

    @Test
    void testSize() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>();

        assertEquals(0, lruCache.size());

        testMap.forEach(lruCache::put);

        lruCache.put("6", "6");
        lruCache.put("1", "2");

        assertEquals(6, lruCache.size());
    }

    @Test
    void testIsEmpty() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>();

        assertTrue(lruCache.isEmpty());

        testMap.forEach(lruCache::put);
        lruCache.put("6", "6");
        lruCache.put("1", "2");

        assertFalse(lruCache.isEmpty());
    }


    @Test
    void testContains() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        assertTrue(lruCache.containsKey("1"));
        assertTrue(lruCache.containsKey("5"));
        assertFalse(lruCache.containsKey("6"));
    }

    @Test
    void testGet() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        testMap.keySet().forEach(key -> assertEquals(testMap.get(key), lruCache.get(key)));

        assertNull(lruCache.get("6"));
    }

    @Test
    void testRewriting() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        String newValue = "New value";
        String keyToRewrite = "1";

        lruCache.put(keyToRewrite, newValue);

        assertEquals(newValue, lruCache.get(keyToRewrite));
    }

    @Test
    void testLeastRecentlyUsedAfterSingleAddition() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>();

        lruCache.put("1", "");

        assertEquals("1", lruCache.leastRecentlyUsed());
    }

    @Test
    void testLeastRecentlyUsedAfterMultipleAdditions() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        assertEquals("1", lruCache.leastRecentlyUsed());
    }


    @Test
    void testLeastRecentlyUsedAfterSingleChangingLeastRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        lruCache.containsKey("1");

        assertEquals("2", lruCache.leastRecentlyUsed());
    }

    @Test
    void testLeastRecentlyUsedAfterMultipleChangingLeastRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        lruCache.containsKey("1");
        lruCache.containsKey("1");
        lruCache.containsKey("2");
        lruCache.containsKey("3");
        lruCache.containsKey("4");
        lruCache.containsKey("5");
        lruCache.containsKey("2");
        lruCache.containsKey("1");

        assertEquals("3", lruCache.leastRecentlyUsed());
    }


    @Test
    void testMostRecentlyUsedAfterSingleAdding() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>();

        lruCache.put("1", "");

        assertEquals("1", lruCache.mostRecentlyUsed());
    }

    @Test
    void testMostRecentlyUsedAfterMultipleAdditions() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        assertEquals("5", lruCache.mostRecentlyUsed());
    }

    @Test
    void testMostRecentlyUsedAfterSingleChangingMostRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        lruCache.containsKey("4");

        assertEquals("4", lruCache.mostRecentlyUsed());
    }

    @Test
    void testMostRecentlyUsedAfterMultipleChangingMostRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap);

        lruCache.containsKey("1");
        lruCache.containsKey("1");
        lruCache.containsKey("2");
        lruCache.containsKey("3");
        lruCache.containsKey("4");
        lruCache.containsKey("2");
        lruCache.containsKey("1");

        assertEquals("1", lruCache.mostRecentlyUsed());
    }

    @Test
    void testAddingElementWithExistedKeyToFullCache() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap, 5);

        lruCache.put("1", "vovse ne odin");

        assertEquals(testMap.keySet(), lruCache.getAsMap().keySet());
    }

    @Test
    void testSingleDeletingLeastRecentlyUsedAfterExtraAddition() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(5);
        lruCache.put("0", "Remove me");

        testMap.forEach(lruCache::put);

        assertEquals(testMap, lruCache.getAsMap());
    }

    @Test
    void testMultipleDeletingLeastRecentlyUsedAfterExtraAdditions() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(5);
        lruCache.put("0", "Remove me");
        lruCache.put("-1", "Remove me to");

        testMap.forEach(lruCache::put);

        assertEquals(testMap, lruCache.getAsMap());
    }

    @Test
    void testDeletingLeastRecentlyUsedAfterMultipleChanging() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(testMap, 5);

        lruCache.put("6", "6");
        lruCache.put("2", "odin");
        lruCache.put("7", "7");

        assertEquals(ImmutableMap.of(
                "2", "odin",
                "4", "4",
                "5", "5",
                "6", "6",
                "7", "7"
        ), lruCache.getAsMap());
    }
}
