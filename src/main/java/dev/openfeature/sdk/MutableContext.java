package dev.openfeature.sdk;

import dev.openfeature.sdk.internal.ExcludeFromGeneratedCoverageReport;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * The EvaluationContext is a container for arbitrary contextual data
 * that can be used as a basis for dynamic evaluation.
 * The MutableContext is an EvaluationContext implementation which is not threadsafe, and whose attributes can
 * be modified after instantiation.
 */
@ToString
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class MutableContext implements EvaluationContext {

    @Delegate(excludes = DelegateExclusions.class)
    private final MutableStructure structure;

    public MutableContext() {
        this(new HashMap<>());
    }

    public MutableContext(String targetingKey) {
        this(targetingKey, new HashMap<>());
    }

    public MutableContext(Map<String, Value> attributes) {
        this(null, new HashMap<>(attributes));
    }

    /**
     * Create a mutable context with given targetingKey and attributes provided. TargetingKey should be non-null
     * to be accepted. Empty string is a valid targeting key value.
     *
     * @param targetingKey targeting key
     * @param attributes   evaluation context attributes
     */
    public MutableContext(String targetingKey, Map<String, Value> attributes) {
        this.structure = new MutableStructure(new HashMap<>(attributes));
        if (targetingKey != null) {
            this.structure.attributes.put(TARGETING_KEY, new Value(targetingKey));
        }
    }

    // override @Delegate methods so that we can use "add" methods and still return MutableContext, not Structure
    public MutableContext add(String key, Boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableContext add(String key, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableContext add(String key, Integer value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableContext add(String key, Double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableContext add(String key, Instant value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableContext add(String key, Structure value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableContext add(String key, List<Value> value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Override or set targeting key for this mutable context. Value should be non-null to be accepted.
     * Empty string is a valid targeting key value.
     */
    public MutableContext setTargetingKey(String targetingKey) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve targetingKey from the context.
     */
    @Override
    public String getTargetingKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Merges this EvaluationContext objects with the second overriding the in case of conflict.
     *
     * @param overridingContext overriding context
     * @return resulting merged context
     */
    @Override
    public EvaluationContext merge(EvaluationContext overridingContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Equality for EvaluationContext implementations is defined in terms of their resolved
     * attribute maps. Two contexts are considered equal if their {@link #asMap()} representations
     * contain the same key/value pairs, regardless of how the context was constructed or layered.
     *
     * @param o the object to compare with this context
     * @return true if the other object is an EvaluationContext whose resolved attributes match
     */
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Hidden class to tell Lombok not to copy these methods over via delegation.
     */
    @SuppressWarnings("all")
    private static class DelegateExclusions {

        @ExcludeFromGeneratedCoverageReport
        public <T extends Structure> Map<String, Value> merge(Function<Map<String, Value>, Structure> newStructure, Map<String, Value> base, Map<String, Value> overriding) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MutableStructure add(String ignoredKey, Boolean ignoredValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MutableStructure add(String ignoredKey, Double ignoredValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MutableStructure add(String ignoredKey, String ignoredValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MutableStructure add(String ignoredKey, Value ignoredValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MutableStructure add(String ignoredKey, Integer ignoredValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MutableStructure add(String ignoredKey, List<Value> ignoredValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MutableStructure add(String ignoredKey, Structure ignoredValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        public MutableStructure add(String ignoredKey, Instant ignoredValue) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
