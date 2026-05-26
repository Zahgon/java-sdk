package dev.openfeature.sdk;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

/**
 * The EvaluationContext is a container for arbitrary contextual data
 * that can be used as a basis for dynamic evaluation.
 */
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public interface EvaluationContext extends Structure {

    String TARGETING_KEY = "targetingKey";

    String getTargetingKey();

    /**
     * Merges this EvaluationContext object with the second overriding the this in
     * case of conflict.
     *
     * @param overridingContext overriding context
     * @return resulting merged context
     */
    EvaluationContext merge(EvaluationContext overridingContext);

    /**
     * If the other object is an EvaluationContext, this method compares the results of asUnmodifiableMap() for
     * equality. Otherwise, it returns false.
     * <br>
     * <br>
     * Implementations of EvaluationContext are encouraged to delegate their equals() method to this method, or provide
     * a more optimized check with the same semantics.
     *
     * @param other the object to compare to
     * @return true if the other object is an EvaluationContext and has the same map representation, false otherwise
     */
    default boolean isEqualTo(Object other) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Recursively merges the overriding map into the base Value map.
     * The base map is mutated, the overriding map is not.
     * Null maps will cause no-op.
     *
     * @param newStructure function to create the right structure(s) for Values
     * @param base         base map to merge
     * @param overriding   overriding map to merge
     */
    static void mergeMaps(Function<Map<String, Value>, Structure> newStructure, Map<String, Value> base, Map<String, Value> overriding) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
