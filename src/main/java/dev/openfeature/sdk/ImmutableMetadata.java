package dev.openfeature.sdk;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * Immutable Flag Metadata representation. Implementation is backed by a {@link Map} and immutability is provided
 * through builder and accessors.
 */
@Slf4j
@EqualsAndHashCode
public class ImmutableMetadata {

    public static final ImmutableMetadata EMPTY = new ImmutableMetadata(Collections.emptyMap());

    private final Map<String, Object> metadata;

    private ImmutableMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    /**
     * Retrieve a {@link String} value for the given key. A {@code null} value is returned if the key does not exist
     * or if the value is of a different type.
     *
     * @param key flag metadata key to retrieve
     */
    public String getString(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve a {@link Integer} value for the given key. A {@code null} value is returned if the key does not exist
     * or if the value is of a different type.
     *
     * @param key flag metadata key to retrieve
     */
    public Integer getInteger(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve a {@link Long} value for the given key. A {@code null} value is returned if the key does not exist
     * or if the value is of a different type.
     *
     * @param key flag metadata key to retrieve
     */
    public Long getLong(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve a {@link Float} value for the given key. A {@code null} value is returned if the key does not exist
     * or if the value is of a different type.
     *
     * @param key flag metadata key to retrieve
     */
    public Float getFloat(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve a {@link Double} value for the given key. A {@code null} value is returned if the key does not exist
     * or if the value is of a different type.
     *
     * @param key flag metadata key to retrieve
     */
    public Double getDouble(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Retrieve a {@link Boolean} value for the given key. A {@code null} value is returned if the key does not exist
     * or if the value is of a different type.
     *
     * @param key flag metadata key to retrieve
     */
    public Boolean getBoolean(final String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Generic value retrieval for the given key.
     */
    public <T> T getValue(final String key, final Class<T> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public Map<String, Object> asUnmodifiableMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public boolean isNotEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Obtain a builder for {@link ImmutableMetadata}.
     */
    public static ImmutableMetadataBuilder builder() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Immutable builder for {@link ImmutableMetadata}.
     */
    public static class ImmutableMetadataBuilder {

        private final Map<String, Object> metadata;

        private ImmutableMetadataBuilder() {
            metadata = new HashMap<>();
        }

        /**
         * Add String value to the metadata.
         *
         * @param key   flag metadata key to add
         * @param value flag metadata value to add
         */
        public ImmutableMetadataBuilder addString(final String key, final String value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Add Integer value to the metadata.
         *
         * @param key   flag metadata key to add
         * @param value flag metadata value to add
         */
        public ImmutableMetadataBuilder addInteger(final String key, final Integer value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Add Long value to the metadata.
         *
         * @param key   flag metadata key to add
         * @param value flag metadata value to add
         */
        public ImmutableMetadataBuilder addLong(final String key, final Long value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Add Float value to the metadata.
         *
         * @param key   flag metadata key to add
         * @param value flag metadata value to add
         */
        public ImmutableMetadataBuilder addFloat(final String key, final Float value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Add Double value to the metadata.
         *
         * @param key   flag metadata key to add
         * @param value flag metadata value to add
         */
        public ImmutableMetadataBuilder addDouble(final String key, final Double value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Add Boolean value to the metadata.
         *
         * @param key   flag metadata key to add
         * @param value flag metadata value to add
         */
        public ImmutableMetadataBuilder addBoolean(final String key, final Boolean value) {
            throw new UnsupportedOperationException("STUB: not implemented");
        }

        /**
         * Retrieve {@link ImmutableMetadata} with provided key,value pairs.
         */
        public ImmutableMetadata build() {
            throw new UnsupportedOperationException("STUB: not implemented");
        }
    }
}
