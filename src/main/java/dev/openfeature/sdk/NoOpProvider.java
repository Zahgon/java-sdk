package dev.openfeature.sdk;

import lombok.Getter;

/**
 * A {@link FeatureProvider} that simply returns the default values passed to it.
 */
public class NoOpProvider implements FeatureProvider {

    public static final String PASSED_IN_DEFAULT = "Passed in default";

    @Getter
    private final String name = "No-op Provider";

    // The Noop provider is ALWAYS NOT_READY, otherwise READY handlers would run immediately when attached.
    @Override
    public ProviderState getState() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Metadata getMetadata() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ProviderEvaluation<Boolean> getBooleanEvaluation(String key, Boolean defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ProviderEvaluation<String> getStringEvaluation(String key, String defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ProviderEvaluation<Integer> getIntegerEvaluation(String key, Integer defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ProviderEvaluation<Double> getDoubleEvaluation(String key, Double defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ProviderEvaluation<Value> getObjectEvaluation(String key, Value defaultValue, EvaluationContext invocationContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
