package dev.openfeature.sdk;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * {@link ImmutableStructure} represents a potentially nested object type which
 * is used to represent
 * structured data.
 * The ImmutableStructure is a Structure implementation which is threadsafe, and
 * whose attributes can
 * not be modified after instantiation. All references are clones.
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings({ "PMD.BeanMembersShouldSerialize", "checkstyle:MissingJavadocType" })
public final class ImmutableStructure extends AbstractStructure {

    /**
     * create an immutable structure with the empty attributes.
     */
    public ImmutableStructure() {
        super();
    }

    /**
     * create immutable structure with the given attributes.
     *
     * @param attributes attributes.
     */
    public ImmutableStructure(Map<String, Value> attributes) {
        super(copyAttributes(attributes, null));
    }

    ImmutableStructure(String targetingKey, Map<String, Value> attributes) {
        super(copyAttributes(attributes, targetingKey));
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

    /**
     * Get all values.
     *
     * @return all attributes on the structure
     */
    @Override
    public Map<String, Value> asMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private static Map<String, Value> copyAttributes(Map<String, Value> in) {
        return copyAttributes(in, null);
    }

    private static Map<String, Value> copyAttributes(Map<String, Value> in, String targetingKey) {
        Map<String, Value> copy;
        if (in != null) {
            var numMappings = in.size();
            if (targetingKey != null) {
                numMappings++;
            }
            copy = HashMapUtils.forEntries(numMappings);
            for (Entry<String, Value> entry : in.entrySet()) {
                var key = entry.getKey();
                var value = entry.getValue();
                Value cloned = value == null ? null : value.clone();
                copy.put(key, cloned);
            }
        } else {
            copy = new HashMap<>(targetingKey == null ? 0 : 1);
        }
        if (targetingKey != null) {
            copy.put(EvaluationContext.TARGETING_KEY, new Value(targetingKey));
        }
        return copy;
    }
}
