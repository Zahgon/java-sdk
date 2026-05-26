package dev.openfeature.sdk;

import dev.openfeature.sdk.exceptions.OpenFeatureError;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class FeatureProviderStateManager implements EventProviderListener {

    private final FeatureProvider delegate;

    private final AtomicBoolean isInitialized = new AtomicBoolean();

    private final AtomicReference<ProviderState> state = new AtomicReference<>(ProviderState.NOT_READY);

    public FeatureProviderStateManager(FeatureProvider delegate) {
        this.delegate = delegate;
        if (delegate instanceof EventProvider) {
            ((EventProvider) delegate).setEventProviderListener(this);
        }
    }

    public void initialize(EvaluationContext evaluationContext) throws Exception {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void shutdown() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public void onEmit(ProviderEvent event, ProviderEventDetails details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private void setState(ProviderState state) {
        ProviderState oldState = this.state.getAndSet(state);
        if (oldState != state) {
            String providerName;
            if (delegate.getMetadata() == null || delegate.getMetadata().getName() == null) {
                providerName = "unknown";
            } else {
                providerName = delegate.getMetadata().getName();
            }
            log.info("Provider {} transitioned from state {} to state {}", providerName, oldState, state);
        }
    }

    public ProviderState getState() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    FeatureProvider getProvider() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean hasSameProvider(FeatureProvider featureProvider) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
