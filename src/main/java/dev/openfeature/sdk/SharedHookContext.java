package dev.openfeature.sdk;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
class SharedHookContext<T> {

    private final String flagKey;

    private final FlagValueType type;

    private final ClientMetadata clientMetadata;

    private final Metadata providerMetadata;

    private final T defaultValue;

    public SharedHookContext(String flagKey, FlagValueType type, ClientMetadata clientMetadata, Metadata providerMetadata, T defaultValue) {
        this.flagKey = flagKey;
        this.type = type;
        this.clientMetadata = clientMetadata;
        this.providerMetadata = providerMetadata;
        this.defaultValue = defaultValue;
    }

    public HookContext<T> hookContextFor(EvaluationContext evaluationContext, HookData hookData) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
