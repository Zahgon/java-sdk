package dev.openfeature.sdk;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * {@link MutableStructure} represents a potentially nested object type which is used to represent
 * structured data.
 * The MutableStructure is a Structure implementation which is not threadsafe, and whose attributes can
 * be modified after instantiation.
 */
@ToString
@SuppressWarnings({ "PMD.BeanMembersShouldSerialize", "checkstyle:MissingJavadocType" })
@EqualsAndHashCode(callSuper = true)
public class MutableStructure extends AbstractStructure {

    public MutableStructure() {
        super();
    }

    public MutableStructure(Map<String, Value> attributes) {
        super(attributes);
    }

    @Override
    public Set<String> keySet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // getters
    @Override
    public Value getValue(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // adders
    public MutableStructure add(String key, Value value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableStructure add(String key, Boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableStructure add(String key, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableStructure add(String key, Integer value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableStructure add(String key, Double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableStructure add(String key, Instant value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableStructure add(String key, Structure value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableStructure add(String key, List<Value> value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get all values.
     *
     * @return all attributes on the structure
     */
    @Override
    public Map<String, Value> asMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
