package dev.openfeature.sdk.multiprovider;

import dev.openfeature.sdk.EvaluationContext;
import dev.openfeature.sdk.EventProvider;
import dev.openfeature.sdk.FeatureProvider;
import dev.openfeature.sdk.Metadata;
import dev.openfeature.sdk.ProviderEvaluation;
import dev.openfeature.sdk.Value;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * <b>Experimental:</b> Provider implementation for multi-provider.
 *
 * <p>This provider delegates flag evaluations to multiple underlying providers using a configurable
 * {@link Strategy}. It also exposes combined metadata containing the original metadata of each
 * underlying provider.
 */
@Slf4j
public class MultiProvider extends EventProvider {

    @Getter
    private static final String NAME = "multiprovider";

    // Use CPU count as upper bound for init threads.
    public static final int INIT_THREADS_COUNT = Runtime.getRuntime().availableProcessors();

    private final Map<String, FeatureProvider> providers;

    private final Strategy strategy;

    private MultiProviderMetadata metadata;

    /**
     * Constructs a MultiProvider with the given list of FeatureProviders, by default uses
     * {@link FirstMatchStrategy}.
     *
     * @param providers the list of FeatureProviders to initialize the MultiProvider with
     */
    public MultiProvider(List<FeatureProvider> providers) {
        this(providers, new FirstMatchStrategy());
    }

    /**
     * Constructs a MultiProvider with the given list of FeatureProviders and a strategy.
     *
     * @param providers the list of FeatureProviders to initialize the MultiProvider with
     * @param strategy  the strategy (if {@code null}, {@link FirstMatchStrategy} is used)
     */
    public MultiProvider(List<FeatureProvider> providers, Strategy strategy) {
        this.providers = buildProviders(providers);
        this.strategy = Objects.requireNonNull(strategy, "strategy must not be null");
    }

    protected static Map<String, FeatureProvider> buildProviders(List<FeatureProvider> providers) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Initialize the provider.
     *
     * @param evaluationContext evaluation context
     * @throws Exception on error (e.g. wrapped {@link java.util.concurrent.ExecutionException}
     *                   from a failing provider)
     */
    @Override
    public void initialize(EvaluationContext evaluationContext) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressFBWarnings(value = "EI_EXPOSE_REP")
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
    public ProviderEvaluation<Value> getObjectEvaluation(String key, Value defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void shutdown() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
