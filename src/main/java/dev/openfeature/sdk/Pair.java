package dev.openfeature.sdk;

import lombok.Setter;
import lombok.ToString;

@ToString
class Pair<K, V> {

    private final K key;

    @Setter
    private V value;

    private Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public V getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static <K, V> Pair<K, V> of(K key, V value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
