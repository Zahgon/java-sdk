package dev.openfeature.sdk;

import dev.openfeature.sdk.exceptions.OpenFeatureError;
import dev.openfeature.sdk.internal.AutoCloseableLock;
import dev.openfeature.sdk.internal.AutoCloseableReentrantReadWriteLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

/**
 * A global singleton which holds base configuration for the OpenFeature
 * library.
 * Configuration here will be shared across all {@link Client}s.
 */
@Slf4j
@SuppressWarnings("PMD.UnusedLocalVariable")
public class OpenFeatureAPI implements EventBus<OpenFeatureAPI> {

    // package-private multi-read/single-write lock
    static AutoCloseableReentrantReadWriteLock lock = new AutoCloseableReentrantReadWriteLock();

    private final ConcurrentLinkedQueue<Hook> apiHooks;

    private ProviderRepository providerRepository;

    private EventSupport eventSupport;

    private final AtomicReference<EvaluationContext> evaluationContext = new AtomicReference<>();

    private TransactionContextPropagator transactionContextPropagator;

    protected OpenFeatureAPI() {
        apiHooks = new ConcurrentLinkedQueue<>();
        providerRepository = new ProviderRepository(this);
        eventSupport = new EventSupport();
        transactionContextPropagator = new NoOpTransactionContextPropagator();
    }

    private static class SingletonHolder {

        private static final OpenFeatureAPI INSTANCE = new OpenFeatureAPI();
    }

    /**
     * Provisions the {@link OpenFeatureAPI} singleton (if needed) and returns it.
     *
     * @return The singleton instance.
     */
    public static OpenFeatureAPI getInstance() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get metadata about the default provider.
     *
     * @return the provider metadata
     */
    public Metadata getProviderMetadata() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get metadata about a registered provider using the client name.
     * An unbound or empty client name will return metadata from the default provider.
     *
     * @param domain an identifier which logically binds clients with providers
     * @return the provider metadata
     */
    public Metadata getProviderMetadata(String domain) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * A factory function for creating new, OpenFeature client.
     * Clients can contain their own state (e.g. logger, hook, context).
     * Multiple clients can be used to segment feature flag configuration.
     * All un-named or unbound clients use the default provider.
     *
     * @return a new client instance
     */
    public Client getClient() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * A factory function for creating new domainless OpenFeature client.
     * Clients can contain their own state (e.g. logger, hook, context).
     * Multiple clients can be used to segment feature flag configuration.
     * If there is already a provider bound to this domain, this provider will be used.
     * Otherwise, the default provider is used until a provider is assigned to that domain.
     *
     * @param domain an identifier which logically binds clients with providers
     * @return a new client instance
     */
    public Client getClient(String domain) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * A factory function for creating new domainless OpenFeature client.
     * Clients can contain their own state (e.g. logger, hook, context).
     * Multiple clients can be used to segment feature flag configuration.
     * If there is already a provider bound to this domain, this provider will be used.
     * Otherwise, the default provider is used until a provider is assigned to that domain.
     *
     * @param domain  a identifier which logically binds clients with providers
     * @param version a version identifier
     * @return a new client instance
     */
    public Client getClient(String domain, String version) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the global evaluation context, which will be used for all evaluations.
     *
     * @param evaluationContext the context
     * @return api instance
     */
    public OpenFeatureAPI setEvaluationContext(EvaluationContext evaluationContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Gets the global evaluation context, which will be used for all evaluations.
     *
     * @return evaluation context
     */
    public EvaluationContext getEvaluationContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Return the transaction context propagator.
     */
    public TransactionContextPropagator getTransactionContextPropagator() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the transaction context propagator.
     *
     * @throws IllegalArgumentException if {@code transactionContextPropagator} is null
     */
    public void setTransactionContextPropagator(TransactionContextPropagator transactionContextPropagator) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns the currently defined transaction context using the registered transaction
     * context propagator.
     *
     * @return {@link EvaluationContext} The current transaction context
     */
    EvaluationContext getTransactionContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the transaction context using the registered transaction context propagator.
     */
    public void setTransactionContext(EvaluationContext evaluationContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the default provider.
     */
    public void setProvider(FeatureProvider provider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a provider for a domain.
     *
     * @param domain   The domain to bind the provider to.
     * @param provider The provider to set.
     */
    public void setProvider(String domain, FeatureProvider provider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Sets the default provider and waits for its initialization to complete.
     *
     * <p>Note: If the provider fails during initialization, an {@link OpenFeatureError} will be thrown.
     * It is recommended to wrap this call in a try-catch block to handle potential initialization failures gracefully.
     *
     * @param provider the {@link FeatureProvider} to set as the default.
     * @throws OpenFeatureError if the provider fails during initialization.
     */
    public void setProviderAndWait(FeatureProvider provider) throws OpenFeatureError {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a provider for a domain and wait for initialization to finish.
     *
     * <p>Note: If the provider fails during initialization, an {@link OpenFeatureError} will be thrown.
     * It is recommended to wrap this call in a try-catch block to handle potential initialization failures gracefully.
     *
     * @param domain   The domain to bind the provider to.
     * @param provider The provider to set.
     * @throws OpenFeatureError if the provider fails during initialization.
     */
    public void setProviderAndWait(String domain, FeatureProvider provider) throws OpenFeatureError {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void attachEventProvider(FeatureProvider provider) {
        if (provider instanceof EventProvider) {
            ((EventProvider) provider).attach(this::runHandlersForProvider);
        }
    }

    private void emitReady(FeatureProvider provider) {
        runHandlersForProvider(provider, ProviderEvent.PROVIDER_READY, ProviderEventDetails.builder().build());
    }

    private void detachEventProvider(FeatureProvider provider) {
        if (provider instanceof EventProvider) {
            ((EventProvider) provider).detach();
        }
    }

    private void emitError(FeatureProvider provider, OpenFeatureError exception) {
        runHandlersForProvider(provider, ProviderEvent.PROVIDER_ERROR, ProviderEventDetails.builder().message(exception.getMessage()).build());
    }

    private void emitErrorAndThrow(FeatureProvider provider, OpenFeatureError exception) throws OpenFeatureError {
        this.emitError(provider, exception);
        throw exception;
    }

    /**
     * Return the default provider.
     */
    public FeatureProvider getProvider() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Fetch a provider for a domain. If not found, return the default.
     *
     * @param domain The domain to look for.
     * @return A named {@link FeatureProvider}
     */
    public FeatureProvider getProvider(String domain) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Adds hooks for globally, used for all evaluations.
     * Hooks are run in the order they're added in the before stage. They are run in reverse order for all other stages.
     *
     * @param hooks The hook to add.
     */
    public void addHooks(Hook... hooks) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Fetch the hooks associated to this client.
     *
     * @return A list of {@link Hook}s.
     */
    public List<Hook> getHooks() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Returns a reference to the collection of {@link Hook}s.
     *
     * @return The collection of {@link Hook}s.
     */
    Collection<Hook> getMutableHooks() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Removes all hooks.
     */
    public void clearHooks() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Shut down and reset the current status of OpenFeature API.
     * This call cleans up all active providers and attempts to shut down internal
     * event handling mechanisms.
     * Once shut down is complete, API is reset and ready to use again.
     */
    public void shutdown() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenFeatureAPI onProviderReady(Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenFeatureAPI onProviderConfigurationChanged(Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenFeatureAPI onProviderStale(Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenFeatureAPI onProviderError(Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenFeatureAPI on(ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public OpenFeatureAPI removeHandler(ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void removeHandler(String domain, ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void addHandler(String domain, ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    FeatureProviderStateManager getFeatureProviderStateManager(String domain) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Runs the handlers associated with a particular provider.
     *
     * @param provider the provider from where this event originated
     * @param event    the event type
     * @param details  the event details
     */
    private void runHandlersForProvider(FeatureProvider provider, ProviderEvent event, ProviderEventDetails details) {
        try (AutoCloseableLock ignored = lock.readLockAutoCloseable()) {
            List<String> domainsForProvider = providerRepository.getDomainsForProvider(provider);
            final String providerName = Optional.ofNullable(provider.getMetadata()).map(Metadata::getName).orElse(null);
            // run the global handlers
            eventSupport.runGlobalHandlers(event, EventDetails.fromProviderEventDetails(details, providerName));
            // run the handlers associated with domains for this provider
            domainsForProvider.forEach(domain -> eventSupport.runClientHandlers(domain, event, EventDetails.fromProviderEventDetails(details, providerName, domain)));
            if (providerRepository.isDefaultProvider(provider)) {
                // run handlers for clients that have no bound providers (since this is the default)
                Set<String> allDomainNames = eventSupport.getAllDomainNames();
                Set<String> boundDomains = providerRepository.getAllBoundDomains();
                allDomainNames.removeAll(boundDomains);
                allDomainNames.forEach(domain -> eventSupport.runClientHandlers(domain, event, EventDetails.fromProviderEventDetails(details, providerName, domain)));
            }
        }
    }
}
