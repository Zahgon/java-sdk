package dev.openfeature.sdk;

import dev.openfeature.sdk.exceptions.ExceptionUtils;
import dev.openfeature.sdk.exceptions.FatalError;
import dev.openfeature.sdk.exceptions.GeneralError;
import dev.openfeature.sdk.exceptions.OpenFeatureError;
import dev.openfeature.sdk.exceptions.ProviderNotReadyError;
import dev.openfeature.sdk.internal.ObjectUtils;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenFeature Client implementation.
 * You should not instantiate this or reference this class.
 * Use the dev.openfeature.sdk.Client interface instead.
 *
 * @see Client
 * @deprecated // TODO: eventually we will make this non-public. See issue #872
 */
@Slf4j
@SuppressWarnings({ "PMD.DataflowAnomalyAnalysis", "PMD.BeanMembersShouldSerialize", "PMD.UnusedLocalVariable", "unchecked", "rawtypes" })
// TODO: eventually we will make this non-public. See issue #872
@Deprecated()
public class OpenFeatureClient implements Client {

    private final OpenFeatureAPI openfeatureApi;

    @Getter
    private final String domain;

    @Getter
    private final String version;

    private final ConcurrentLinkedQueue<Hook> clientHooks;

    private final AtomicReference<EvaluationContext> evaluationContext = new AtomicReference<>();

    private final HookSupport hookSupport;

    /**
     * Deprecated public constructor. Use OpenFeature.API.getClient() instead.
     *
     * @param openFeatureAPI Backing global singleton
     * @param domain         An identifier which logically binds clients with
     *                       providers (used by observability tools).
     * @param version        Version of the client (used by observability tools).
     * @deprecated Do not use this constructor. It's for internal use only.
     *         Clients created using it will not run event handlers.
     *         Use the OpenFeatureAPI's getClient factory method instead.
     */
    // TODO: eventually we will make this non-public. See issue #872
    @Deprecated()
    public OpenFeatureClient(OpenFeatureAPI openFeatureAPI, String domain, String version) {
        this.openfeatureApi = openFeatureAPI;
        this.domain = domain;
        this.version = version;
        this.hookSupport = new HookSupport();
        this.clientHooks = new ConcurrentLinkedQueue<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProviderState getProviderState() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void track(String trackingEventName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void track(String trackingEventName, EvaluationContext context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void track(String trackingEventName, TrackingEventDetails details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void track(String trackingEventName, EvaluationContext context, TrackingEventDetails details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenFeatureClient addHooks(Hook... hooks) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Hook> getHooks() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenFeatureClient setEvaluationContext(EvaluationContext evaluationContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EvaluationContext getEvaluationContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressFBWarnings(value = { "REC_CATCH_EXCEPTION" }, justification = "We don't want to allow any exception to reach the user. " + "Instead, we return an evaluation result with the appropriate error code.")
    private <T> FlagEvaluationDetails<T> evaluateFlag(FlagValueType type, String key, T defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        FlagEvaluationDetails<T> details = null;
        HookSupportData hookSupportData = new HookSupportData();
        FlagEvaluationOptions flagOptions;
        if (options == null) {
            flagOptions = FlagEvaluationOptions.EMPTY;
        } else {
            flagOptions = options;
        }
        hookSupportData.hints = Collections.unmodifiableMap(flagOptions.getHookHints());
        var context = new LayeredEvaluationContext(openfeatureApi.getEvaluationContext(), openfeatureApi.getTransactionContext(), evaluationContext.get(), ctx);
        hookSupportData.evaluationContext = context;
        try {
            final var stateManager = openfeatureApi.getFeatureProviderStateManager(this.domain);
            // provider must be accessed once to maintain a consistent reference
            final var provider = stateManager.getProvider();
            final var state = stateManager.getState();
            // Hooks are initialized as early as possible to enable the execution of error stages
            var mergedHooks = ObjectUtils.merge(provider.getProviderHooks(), flagOptions.getHooks(), clientHooks, openfeatureApi.getMutableHooks());
            hookSupport.setHooks(hookSupportData, mergedHooks, type);
            var sharedHookContext = new SharedHookContext(key, type, this.getMetadata(), provider.getMetadata(), defaultValue);
            hookSupport.setHookContexts(hookSupportData, sharedHookContext, context);
            hookSupport.executeBeforeHooks(hookSupportData);
            // "short circuit" if the provider is in NOT_READY or FATAL state
            if (ProviderState.NOT_READY.equals(state)) {
                throw new ProviderNotReadyError("Provider not yet initialized");
            }
            if (ProviderState.FATAL.equals(state)) {
                throw new FatalError("Provider is in an irrecoverable error state");
            }
            var providerEval = (ProviderEvaluation<T>) createProviderEvaluation(type, key, defaultValue, provider, hookSupportData.getEvaluationContext());
            details = FlagEvaluationDetails.from(providerEval, key);
            if (details.getErrorCode() != null) {
                var error = ExceptionUtils.instantiateErrorByErrorCode(details.getErrorCode(), details.getErrorMessage());
                enrichDetailsWithErrorDefaults(defaultValue, details);
                hookSupport.executeErrorHooks(hookSupportData, error);
            } else {
                hookSupport.executeAfterHooks(hookSupportData, details);
            }
        } catch (Exception e) {
            if (details == null) {
                details = FlagEvaluationDetails.<T>builder().flagKey(key).build();
            }
            if (e instanceof OpenFeatureError) {
                details.setErrorCode(((OpenFeatureError) e).getErrorCode());
            } else {
                details.setErrorCode(ErrorCode.GENERAL);
            }
            details.setErrorMessage(e.getMessage());
            enrichDetailsWithErrorDefaults(defaultValue, details);
            if (hookSupportData.getHooks() != null) {
                hookSupport.executeErrorHooks(hookSupportData, e);
            }
        } finally {
            if (hookSupportData.getHooks() != null) {
                hookSupport.executeAfterAllHooks(hookSupportData, details);
            }
        }
        return details;
    }

    private static <T> void enrichDetailsWithErrorDefaults(T defaultValue, FlagEvaluationDetails<T> details) {
        details.setValue(defaultValue);
        details.setReason(Reason.ERROR.toString());
    }

    private static void validateTrackingEventName(String str) {
        Objects.requireNonNull(str);
        if (str.isEmpty()) {
            throw new IllegalArgumentException("trackingEventName cannot be empty");
        }
    }

    private void invokeTrack(String trackingEventName, EvaluationContext context, TrackingEventDetails details) {
        openfeatureApi.getFeatureProviderStateManager(domain).getProvider().track(trackingEventName, mergeEvaluationContext(context), details);
    }

    /**
     * Merge invocation contexts with API, transaction and client contexts.
     * Does not merge before context.
     *
     * @param invocationContext invocation context
     * @return merged evaluation context
     */
    private EvaluationContext mergeEvaluationContext(EvaluationContext invocationContext) {
        final EvaluationContext apiContext = openfeatureApi.getEvaluationContext();
        final EvaluationContext clientContext = evaluationContext.get();
        final EvaluationContext transactionContext = openfeatureApi.getTransactionContext();
        return mergeContextMaps(apiContext, transactionContext, clientContext, invocationContext);
    }

    private EvaluationContext mergeContextMaps(EvaluationContext... contexts) {
        // avoid any unnecessary context instantiations and stream usage here; this is
        // called with every evaluation.
        Map merged = new HashMap<>();
        for (EvaluationContext evaluationContext : contexts) {
            if (evaluationContext != null && !evaluationContext.isEmpty()) {
                EvaluationContext.mergeMaps(ImmutableStructure::new, merged, evaluationContext.asUnmodifiableMap());
            }
        }
        return new ImmutableContext(merged);
    }

    private <T> ProviderEvaluation<?> createProviderEvaluation(FlagValueType type, String key, T defaultValue, FeatureProvider provider, EvaluationContext invocationContext) {
        switch(type) {
            case BOOLEAN:
                return provider.getBooleanEvaluation(key, (Boolean) defaultValue, invocationContext);
            case STRING:
                return provider.getStringEvaluation(key, (String) defaultValue, invocationContext);
            case INTEGER:
                return provider.getIntegerEvaluation(key, (Integer) defaultValue, invocationContext);
            case DOUBLE:
                return provider.getDoubleEvaluation(key, (Double) defaultValue, invocationContext);
            case OBJECT:
                return provider.getObjectEvaluation(key, (Value) defaultValue, invocationContext);
            default:
                throw new GeneralError("Unknown flag type");
        }
    }

    @Override
    public Boolean getBooleanValue(String key, Boolean defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Boolean getBooleanValue(String key, Boolean defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Boolean getBooleanValue(String key, Boolean defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Boolean> getBooleanDetails(String key, Boolean defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Boolean> getBooleanDetails(String key, Boolean defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Boolean> getBooleanDetails(String key, Boolean defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getStringValue(String key, String defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getStringValue(String key, String defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String getStringValue(String key, String defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<String> getStringDetails(String key, String defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<String> getStringDetails(String key, String defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<String> getStringDetails(String key, String defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Integer getIntegerValue(String key, Integer defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Integer getIntegerValue(String key, Integer defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Integer getIntegerValue(String key, Integer defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Integer> getIntegerDetails(String key, Integer defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Integer> getIntegerDetails(String key, Integer defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Integer> getIntegerDetails(String key, Integer defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Double getDoubleValue(String key, Double defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Double getDoubleValue(String key, Double defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Double getDoubleValue(String key, Double defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Double> getDoubleDetails(String key, Double defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Double> getDoubleDetails(String key, Double defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Double> getDoubleDetails(String key, Double defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Value getObjectValue(String key, Value defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Value getObjectValue(String key, Value defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Value getObjectValue(String key, Value defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Value> getObjectDetails(String key, Value defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Value> getObjectDetails(String key, Value defaultValue, EvaluationContext ctx) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public FlagEvaluationDetails<Value> getObjectDetails(String key, Value defaultValue, EvaluationContext ctx, FlagEvaluationOptions options) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public ClientMetadata getMetadata() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client onProviderReady(Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client onProviderConfigurationChanged(Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client onProviderError(Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client onProviderStale(Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client on(ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Client removeHandler(ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
