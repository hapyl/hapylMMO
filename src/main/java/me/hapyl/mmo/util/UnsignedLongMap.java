package me.hapyl.mmo.util;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * A key to unsigned long map with increment and decrement methods.
 */
public class UnsignedLongMap<K> extends HashMap<K, Long> {

    public long increment(@Nonnull K k, long increment) {
        return compute(k, (kk, v) -> {
            if (v == null) {
                return increment;
            }

            return Math.max(v + increment, 0);
        });
    }

    @Override
    public void putAll(Map<? extends K, ? extends Long> m) {
        m.forEach((k, aLong) -> {
            if (k == null || aLong == null) {
                return;
            }

            this.put(k, aLong);
        });
    }

    @Override
    public Long putIfAbsent(K key, Long value) {
        return super.putIfAbsent(key, Math.max(value, 0));
    }

    @Override
    public Long put(K key, Long value) {
        return super.put(key, Math.max(value, 0));
    }

    public long decrement(@Nonnull K k, long decrement) {
        return increment(k, -decrement);
    }

    public long zero(@Nonnull K k) {
        put(k, 0L);
        return 0L;
    }

    public long max(@Nonnull K k) {
        put(k, Long.MAX_VALUE);
        return Long.MAX_VALUE;
    }

}
