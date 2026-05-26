package dev.openfeature.sdk;

import dev.openfeature.sdk.internal.ConfigurableThreadFactory;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;

/**
 * Util class for storing and running handlers.
 */
@Slf4j
class EventSupport {

    public static final int SHUTDOWN_TIMEOUT_SECONDS = 3;

    // we use a v4 uuid as a "placeholder" for anonymous clients, since
    // ConcurrentHashMap doesn't support nulls
    private static final String DEFAULT_CLIENT_UUID = UUID.randomUUID().toString();

    private final Map<String, HandlerStore> handlerStores = new ConcurrentHashMap<>();

    private final HandlerStore globalHandlerStore = new HandlerStore();

    private final ExecutorService taskExecutor = Executors.newCachedThreadPool(new ConfigurableThreadFactory("openfeature-event-handler-thread", true));

    /**
     * Run all the event handlers associated with this domain.
     * If the domain is null, handlers attached to unnamed clients will run.
     *
     * @param domain       the domain to run event handlers for, or null
     * @param event        the event type
     * @param eventDetails the event details
     */
    public void runClientHandlers(String domain, ProviderEvent event, EventDetails eventDetails) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Run all the API (global) event handlers.
     *
     * @param event        the event type
     * @param eventDetails the event details
     */
    public void runGlobalHandlers(ProviderEvent event, EventDetails eventDetails) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a handler for the specified domain, or all unnamed clients.
     *
     * @param domain  the domain to add handlers for, or else unnamed
     * @param event   the event type
     * @param handler the handler function to run
     */
    public void addClientHandler(String domain, ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Remove a client event handler for the specified event type.
     *
     * @param domain  the domain of the client handler to remove, or null to remove
     *                from unnamed clients
     * @param event   the event type
     * @param handler the handler ref to be removed
     */
    public void removeClientHandler(String domain, ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Add a global event handler of the specified event type.
     *
     * @param event   the event type
     * @param handler the handler to be added
     */
    public void addGlobalHandler(ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Remove a global event handler for the specified event type.
     *
     * @param event   the event type
     * @param handler the handler ref to be removed
     */
    public void removeGlobalHandler(ProviderEvent event, Consumer<EventDetails> handler) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Get all domain names for which we have event handlers registered.
     *
     * @return set of domain names
     */
    public Set<String> getAllDomainNames() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Run the passed handler on the taskExecutor.
     *
     * @param handler      the handler to run
     * @param eventDetails the event details
     */
    public void runHandler(Consumer<EventDetails> handler, EventDetails eventDetails) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Stop the event handler task executor and block until either termination has completed
     * or timeout period has elapsed.
     */
    public void shutdown() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // Handler store maintains a set of handlers for each event type.
    // Each client in the SDK gets it's own handler store, which is lazily
    // instantiated when a handler is added to that client.
    static class HandlerStore {

        private final Map<ProviderEvent, Collection<Consumer<EventDetails>>> handlerMap;

        HandlerStore() {
            handlerMap = new ConcurrentHashMap<>();
            handlerMap.put(ProviderEvent.PROVIDER_READY, new ConcurrentLinkedQueue<>());
            handlerMap.put(ProviderEvent.PROVIDER_CONFIGURATION_CHANGED, new ConcurrentLinkedQueue<>());
            handlerMap.put(ProviderEvent.PROVIDER_ERROR, new ConcurrentLinkedQueue<>());
            handlerMap.put(ProviderEvent.PROVIDER_STALE, new ConcurrentLinkedQueue<>());
        }

        void addHandler(ProviderEvent event, Consumer<EventDetails> handler) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        void removeHandler(ProviderEvent event, Consumer<EventDetails> handler) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
