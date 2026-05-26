package dev.openfeature.sdk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({ "PMD.BeanMembersShouldSerialize", "checkstyle:MissingJavadocType" })
abstract class AbstractStructure implements Structure {

    protected final Map<String, Value> attributes;

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    AbstractStructure() {
        this.attributes = new HashMap<>();
    }

    AbstractStructure(Map<String, Value> attributes) {
        this.attributes = attributes;
    }

    /**
     * Returns an unmodifiable representation of the internal attribute map.
     *
     * @return immutable map
     */
    public Map<String, Value> asUnmodifiableMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get all values as their underlying primitives types.
     *
     * @return all attributes on the structure into a Map
     */
    @Override
    public Map<String, Object> asObjectMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(Object object) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
