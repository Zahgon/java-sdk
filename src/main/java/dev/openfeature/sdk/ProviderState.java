package dev.openfeature.sdk;

/**
 * Indicates the state of the provider.
 */
public enum ProviderState {

    READY, NOT_READY, ERROR, STALE, FATAL;

    /**
     * Returns true if the passed ProviderEvent maps to this ProviderState.
     *
     * @param event event to compare
     * @return boolean if matches.
     */
    boolean matchesEvent(ProviderEvent event) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
