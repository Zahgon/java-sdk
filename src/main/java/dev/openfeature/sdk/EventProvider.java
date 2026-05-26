package dev.openfeature.sdk;

import dev.openfeature.sdk.internal.ConfigurableThreadFactory;
import dev.openfeature.sdk.internal.TriConsumer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;

/**
 * Abstract EventProvider. Providers must extend this class to support events.
 * Emit events with {@link #emit(ProviderEvent, ProviderEventDetails)}. Please
 * note that the SDK will automatically emit
 * {@link ProviderEvent#PROVIDER_READY } or
 * {@link ProviderEvent#PROVIDER_ERROR } accordingly when
 * {@link FeatureProvider#initialize(EvaluationContext)} completes successfully
 * or with error, so these events need not be emitted manually during
 * initialization.
 *
 * @see FeatureProvider
 */
@Slf4j
public abstract class EventProvider implements FeatureProvider {

    private EventProviderListener eventProviderListener;

    private final ExecutorService emitterExecutor = Executors.newCachedThreadPool(new ConfigurableThreadFactory("openfeature-event-emitter-thread", true));

    void setEventProviderListener(EventProviderListener eventProviderListener) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private TriConsumer<EventProvider, ProviderEvent, ProviderEventDetails> onEmit = null;

    /**
     * "Attach" this EventProvider to an SDK, which allows events to propagate from this provider.
     * No-op if the same onEmit is already attached.
     *
     * @param onEmit the function to run when a provider emits events.
     * @throws IllegalStateException if attempted to bind a new emitter for already bound provider
     */
    void attach(TriConsumer<EventProvider, ProviderEvent, ProviderEventDetails> onEmit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * "Detach" this EventProvider from an SDK, stopping propagation of all events.
     */
    void detach() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Stop the event emitter executor and block until either termination has completed
     * or timeout period has elapsed.
     */
    @Override
    public void shutdown() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Emit the specified {@link ProviderEvent}.
     *
     * @param event   The event type
     * @param details The details of the event
     */
    public Awaitable emit(final ProviderEvent event, final ProviderEventDetails details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Emit a {@link ProviderEvent#PROVIDER_READY} event.
     * Shorthand for {@link #emit(ProviderEvent, ProviderEventDetails)}
     *
     * @param details The details of the event
     */
    public Awaitable emitProviderReady(ProviderEventDetails details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Emit a
     * {@link ProviderEvent#PROVIDER_CONFIGURATION_CHANGED}
     * event. Shorthand for {@link #emit(ProviderEvent, ProviderEventDetails)}
     *
     * @param details The details of the event
     */
    public Awaitable emitProviderConfigurationChanged(ProviderEventDetails details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Emit a {@link ProviderEvent#PROVIDER_STALE} event.
     * Shorthand for {@link #emit(ProviderEvent, ProviderEventDetails)}
     *
     * @param details The details of the event
     */
    public Awaitable emitProviderStale(ProviderEventDetails details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Emit a {@link ProviderEvent#PROVIDER_ERROR} event.
     * Shorthand for {@link #emit(ProviderEvent, ProviderEventDetails)}
     *
     * @param details The details of the event
     */
    public Awaitable emitProviderError(ProviderEventDetails details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
