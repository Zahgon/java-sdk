package dev.openfeature.sdk;

import dev.openfeature.sdk.internal.ExcludeFromGeneratedCoverageReport;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Delegate;

/**
 * MutableTrackingEventDetails represents data pertinent to a particular tracking event.
 */
@EqualsAndHashCode
@ToString
public class MutableTrackingEventDetails implements TrackingEventDetails {

    private final Number value;

    @Delegate(excludes = MutableTrackingEventDetails.DelegateExclusions.class)
    private final MutableStructure structure;

    public MutableTrackingEventDetails() {
        this.value = null;
        this.structure = new MutableStructure();
    }

    public MutableTrackingEventDetails(final Number value) {
        this.value = value;
        this.structure = new MutableStructure();
    }

    /**
     * Returns the optional tracking value.
     */
    public Optional<Number> getValue() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // override @Delegate methods so that we can use "add" methods and still return MutableTrackingEventDetails,
    // not Structure
    public MutableTrackingEventDetails add(String key, Boolean value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableTrackingEventDetails add(String key, String value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableTrackingEventDetails add(String key, Integer value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableTrackingEventDetails add(String key, Double value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableTrackingEventDetails add(String key, Instant value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableTrackingEventDetails add(String key, Structure value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableTrackingEventDetails add(String key, List<Value> value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public MutableTrackingEventDetails add(String key, Value value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @SuppressWarnings("all")
    private static class DelegateExclusions {

        @ExcludeFromGeneratedCoverageReport
        public <T extends Structure> Map<String, Value> merge(Function<Map<String, Value>, Structure> newStructure, Map<String, Value> base, Map<String, Value> overriding) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
