package dev.openfeature.sdk;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * The details of a particular event.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
public class EventDetails extends ProviderEventDetails {

    private String domain;

    private String providerName;

    static EventDetails fromProviderEventDetails(ProviderEventDetails providerEventDetails, String providerName) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    static EventDetails fromProviderEventDetails(ProviderEventDetails providerEventDetails, String providerName, String domain) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
