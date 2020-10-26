package com.ifmo.vbaydyuk.hw1;

import com.google.gwt.thirdparty.guava.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasEntry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LRUCacheTest {
    private static final Map<String, String> TEST_MAP = ImmutableMap.of(
            "1", "1",
            "2", "2",
            "3", "3",
            "4", "4",
            "5", "5"
    );
    private static final int TOO_BIG_CAPACITY = 100_000;
    private static final int NEGATIVE_CAPACITY = -1;

    @Test
    void testCapacityRange() {
        assertThrows(AssertionError.class, () -> new SimpleLRUCache<>(NEGATIVE_CAPACITY));
        assertThrows(AssertionError.class, () -> new SimpleLRUCache<>(TOO_BIG_CAPACITY));
    }

    @Test
    void testPut() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        Map<String, String> actual = lruCache.getAsMap();

        assertEquals(TEST_MAP, actual);
    }

    @Test
    void testSize() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>();

        assertEquals(0, lruCache.size());

        TEST_MAP.forEach(lruCache::put);

        lruCache.put("6", "6");
        lruCache.put("1", "2");

        assertEquals(6, lruCache.size());
    }

    @Test
    void testIsEmpty() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>();

        assertTrue(lruCache.isEmpty());

        TEST_MAP.forEach(lruCache::put);
        lruCache.put("6", "6");
        lruCache.put("1", "2");

        assertFalse(lruCache.isEmpty());
    }


    @Test
    void testContains() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        assertTrue(lruCache.containsKey("1"));
        assertTrue(lruCache.containsKey("5"));
        assertFalse(lruCache.containsKey("6"));
    }

    @Test
    void testGet() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        TEST_MAP.keySet().forEach(key -> assertEquals(TEST_MAP.get(key), lruCache.get(key)));

        assertNull(lruCache.get("6"));
    }

    @Test
    void testRewriting() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

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
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        assertEquals("1", lruCache.leastRecentlyUsed());
    }


    @Test
    void testLeastRecentlyUsedAfterSingleChangingLeastRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        lruCache.containsKey("1");

        assertEquals("2", lruCache.leastRecentlyUsed());
    }

    @Test
    void testLeastRecentlyUsedAfterMultipleChangingLeastRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

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
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        assertEquals("5", lruCache.mostRecentlyUsed());
    }

    @Test
    void testMostRecentlyUsedAfterSingleChangingMostRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        lruCache.containsKey("4");

        assertEquals("4", lruCache.mostRecentlyUsed());
    }

    @Test
    void testMostRecentlyUsedAfterMultipleChangingMostRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

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
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP, 5);

        lruCache.put("1", "vovse ne odin");

        assertEquals(TEST_MAP.keySet(), lruCache.getAsMap().keySet());
    }

    @Test
    void testSingleDeletingLeastRecentlyUsedAfterExtraAddition() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(5);
        lruCache.put("0", "Remove me");

        TEST_MAP.forEach(lruCache::put);

        assertEquals(TEST_MAP, lruCache.getAsMap());
    }

    @Test
    void testMultipleDeletingLeastRecentlyUsedAfterExtraAdditions() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(5);
        lruCache.put("0", "Remove me");
        lruCache.put("-1", "Remove me to");

        TEST_MAP.forEach(lruCache::put);

        assertEquals(TEST_MAP, lruCache.getAsMap());
    }

    @Test
    void testDeletingLeastRecentlyUsedAfterMultipleChanging() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP, 5);

        lruCache.put("6", "6");
        lruCache.put("2", "odin");
        lruCache.put("7", "7");

        assertThat(lruCache.getAsMap(), allOf(
                hasEntry("2", "odin"),
                hasEntry("4", "4"),
                hasEntry("5", "5"),
                hasEntry("6", "6"),
                hasEntry("7", "7")
        ));
    }


    @Test
    void testRemove() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        lruCache.remove("1");

        assertFalse(lruCache.containsKey("1"));
        assertEquals(4, lruCache.size());
    }

    @Test
    void testRemoveAll() {
        LRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        lruCache.remove("1");
        lruCache.remove("2");
        lruCache.remove("3");
        lruCache.remove("4");
        lruCache.remove("5");

        TEST_MAP.keySet().forEach(key -> assertFalse(lruCache.containsKey(key)));

        assertTrue(lruCache.isEmpty());
    }

    @Test
    void testSingleRemovingLeastRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        lruCache.remove("1");

        assertEquals("2", lruCache.leastRecentlyUsed());
    }

    @Test
    void testSingleRemovingMostRecentlyUsed() {
        AbstractLRUCache<String, String> lruCache = new SimpleLRUCache<>(TEST_MAP);

        lruCache.remove("5");

        assertEquals("4", lruCache.mostRecentlyUsed());
    }
}
