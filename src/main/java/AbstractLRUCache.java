import com.google.gwt.thirdparty.guava.common.annotations.VisibleForTesting;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("WeakerAccess")
public abstract class AbstractLRUCache<K, V> implements LRUCache<K, V> {

    protected final int capacity;

    protected AbstractLRUCache(int capacity) {
        assert capacity >= MIN_CAPACITY && capacity <= MAX_CAPACITY
                : String.format("Capacity should be in range (%d, %d)", MIN_CAPACITY, MAX_CAPACITY);
        this.capacity = capacity;
    }

    protected AbstractLRUCache() {
        this(MAX_CAPACITY);
    }

    @Override
    public void put(@Nonnull K key, @Nonnull V value) {
        int oldSize = size();
        boolean was = containsKey(key);
        K lru = leastRecentlyUsed();
        doPut(key, value);
        assert oldSize != capacity || was || !containsKey(lru)
                : "Most recently element should be removed if size was equals to capacity before adding and adding key wasn't found";
        if (oldSize == capacity) {
            assert size() == oldSize
                    : "Size shouldn't be changed after adding if it was equals capacity";
        } else {
            if (was)
                assert size() == oldSize
                        : "Size shouldn't be changed after adding if adding key was found";
            else
                assert size() == oldSize + 1
                        : "Size should be increased by 1";
        }

        V addedValue = justGet(key);
        assert addedValue != null && addedValue.equals(value)
                : String.format("Expected %s associated with %s but %s was found",
                value.toString(), key.toString(),
                (addedValue == null ? "null" : addedValue.toString()));
        assert key.equals(mostRecentlyUsed())
                : "Least recently used should be changed to just added or changed element";
    }

    @Override
    public void remove(@Nonnull K key) {
        int oldSize = size();
        doRemove(key);
        assert oldSize == 0 || oldSize == size() + 1 : "Size should be decreased by one after removing";
        assert justGet(key) == null : "Element should be removed";
    }

    @Override
    @Nullable
    public V get(@Nonnull K key) {
        int oldSize = size();
        V res = doGet(key);
        assert oldSize == size() : "Size should be immutable";
        assert res == null || key.equals(mostRecentlyUsed())
                : "Least recently used should be changed to just requested element if it was found";
        return res;
    }

    @Override
    public int size() {
        int res = doSize();
        assert res >= 0 : "Size should be positive.";
        assert res <= capacity : String.format("Size should be less than %d.", capacity);
        return res;
    }

    abstract protected int doSize();

    abstract protected void doPut(@Nonnull K key, @Nonnull V value);

    @Nullable
    abstract protected V doGet(@Nonnull K key);

    abstract protected void doRemove(@Nonnull K key);

    abstract protected K leastRecentlyUsed();

    abstract protected K mostRecentlyUsed();

    @Nullable
    abstract protected V justGet(@Nonnull K key);

}
