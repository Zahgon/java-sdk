package dev.openfeature.sdk;

import dev.openfeature.sdk.exceptions.GeneralError;
import dev.openfeature.sdk.exceptions.OpenFeatureError;
import dev.openfeature.sdk.internal.ConfigurableThreadFactory;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class ProviderRepository {

    private final Map<String, FeatureProviderStateManager> stateManagers = new ConcurrentHashMap<>();

    private final AtomicReference<FeatureProviderStateManager> defaultStateManger = new AtomicReference<>(new FeatureProviderStateManager(new NoOpProvider()));

    private final AtomicBoolean isShuttingDown = new AtomicBoolean(false);

    private final ExecutorService taskExecutor = Executors.newCachedThreadPool(new ConfigurableThreadFactory("openfeature-provider-thread", true));

    private final Object registerStateManagerLock = new Object();

    private final OpenFeatureAPI openFeatureAPI;

    public ProviderRepository(OpenFeatureAPI openFeatureAPI) {
        this.openFeatureAPI = openFeatureAPI;
    }

    FeatureProviderStateManager getFeatureProviderStateManager() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    FeatureProviderStateManager getFeatureProviderStateManager(String domain) {
        throw new UnsupportedOperationException("STUB: not implemented");
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

    public ProviderState getProviderState() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ProviderState getProviderState(FeatureProvider featureProvider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public ProviderState getProviderState(String domain) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public List<String> getDomainsForProvider(FeatureProvider provider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Set<String> getAllBoundDomains() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isDefaultProvider(FeatureProvider provider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Set the default provider.
     */
    public void setProvider(FeatureProvider provider, Consumer<FeatureProvider> afterSet, Consumer<FeatureProvider> afterInit, Consumer<FeatureProvider> afterShutdown, BiConsumer<FeatureProvider, OpenFeatureError> afterError, boolean waitForInit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a provider for a domain.
     *
     * @param domain      The domain to bind the provider to.
     * @param provider    The provider to set.
     * @param waitForInit When true, wait for initialization to finish, then returns.
     *                    Otherwise, initialization happens in the background.
     */
    public void setProvider(String domain, FeatureProvider provider, Consumer<FeatureProvider> afterSet, Consumer<FeatureProvider> afterInit, Consumer<FeatureProvider> afterShutdown, BiConsumer<FeatureProvider, OpenFeatureError> afterError, boolean waitForInit) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void prepareAndInitializeProvider(String domain, FeatureProvider newProvider, Consumer<FeatureProvider> afterSet, Consumer<FeatureProvider> afterInit, Consumer<FeatureProvider> afterShutdown, BiConsumer<FeatureProvider, OpenFeatureError> afterError, boolean waitForInit) {
        final FeatureProviderStateManager newStateManager;
        final FeatureProviderStateManager oldStateManager;
        synchronized (registerStateManagerLock) {
            if (isShuttingDown.get()) {
                throw new IllegalStateException("Provider cannot be set while repository is shutting down");
            }
            FeatureProviderStateManager existing = getExistingStateManagerForProvider(newProvider);
            if (existing == null) {
                newStateManager = new FeatureProviderStateManager(newProvider);
                // only run afterSet if new provider is not already attached
                afterSet.accept(newProvider);
            } else {
                newStateManager = existing;
            }
            // provider is set immediately, on this thread
            oldStateManager = domain != null ? this.stateManagers.put(domain, newStateManager) : this.defaultStateManger.getAndSet(newStateManager);
        }
        if (waitForInit) {
            initializeProvider(newStateManager, afterInit, afterShutdown, afterError, oldStateManager);
        } else {
            taskExecutor.submit(() -> {
                // initialization happens in a different thread if we're not waiting for it
                initializeProvider(newStateManager, afterInit, afterShutdown, afterError, oldStateManager);
            });
        }
    }

    private FeatureProviderStateManager getExistingStateManagerForProvider(FeatureProvider provider) {
        for (FeatureProviderStateManager stateManager : stateManagers.values()) {
            if (stateManager.hasSameProvider(provider)) {
                return stateManager;
            }
        }
        FeatureProviderStateManager defaultFeatureProviderStateManager = defaultStateManger.get();
        if (defaultFeatureProviderStateManager.hasSameProvider(provider)) {
            return defaultFeatureProviderStateManager;
        }
        return null;
    }

    private void initializeProvider(FeatureProviderStateManager newManager, Consumer<FeatureProvider> afterInit, Consumer<FeatureProvider> afterShutdown, BiConsumer<FeatureProvider, OpenFeatureError> afterError, FeatureProviderStateManager oldManager) {
        try {
            if (ProviderState.NOT_READY.equals(newManager.getState())) {
                newManager.initialize(openFeatureAPI.getEvaluationContext());
                afterInit.accept(newManager.getProvider());
            }
            shutDownOld(oldManager, afterShutdown);
        } catch (OpenFeatureError e) {
            log.error("Exception when initializing feature provider {}", newManager.getProvider().getClass().getName(), e);
            afterError.accept(newManager.getProvider(), e);
        } catch (Exception e) {
            log.error("Exception when initializing feature provider {}", newManager.getProvider().getClass().getName(), e);
            afterError.accept(newManager.getProvider(), new GeneralError(e));
        }
    }

    private void shutDownOld(FeatureProviderStateManager oldManager, Consumer<FeatureProvider> afterShutdown) {
        synchronized (registerStateManagerLock) {
            if (oldManager != null && !isStateManagerRegistered(oldManager)) {
                shutdownProvider(oldManager);
                afterShutdown.accept(oldManager.getProvider());
            }
        }
    }

    /**
     * Helper to check if manager is already known (registered).
     *
     * @param manager manager to check for registration
     * @return boolean true if already registered, false otherwise
     */
    private boolean isStateManagerRegistered(FeatureProviderStateManager manager) {
        return manager != null && (this.stateManagers.containsValue(manager) || this.defaultStateManger.get().equals(manager));
    }

    private void shutdownProvider(FeatureProviderStateManager manager) {
        if (manager == null) {
            return;
        }
        shutdownProvider(manager.getProvider());
    }

    private void shutdownProvider(FeatureProvider provider) {
        try {
            taskExecutor.submit(() -> {
                try {
                    provider.shutdown();
                } catch (Exception e) {
                    log.error("Exception when shutting down feature provider {}", provider.getClass().getName(), e);
                }
            });
        } catch (java.util.concurrent.RejectedExecutionException e) {
            try {
                provider.shutdown();
            } catch (Exception ex) {
                log.error("Exception when shutting down feature provider {}", provider.getClass().getName(), ex);
            }
        }
    }

    /**
     * Shuts down this repository which includes shutting down all FeatureProviders
     * that are registered,
     * including the default feature provider.
     */
    public void shutdown() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Prepares the repository for shutdown by marking it as shutting down and
     * collecting all managers that need to be shut down.
     *
     * <p>After this call, any attempt to set a provider will throw IllegalStateException.
     *
     * @return list of managers to shut down, or null if shutdown was already initiated
     */
    List<FeatureProviderStateManager> prepareShutdown() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Completes the shutdown by shutting down all providers and waiting for
     * pending tasks to complete.
     *
     * @param managersToShutdown the managers to shut down (from prepareShutdown)
     */
    void completeShutdown(List<FeatureProviderStateManager> managersToShutdown) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
