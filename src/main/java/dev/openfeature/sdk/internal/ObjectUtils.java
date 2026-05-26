package dev.openfeature.sdk.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import lombok.experimental.UtilityClass;

@SuppressWarnings("checkstyle:MissingJavadocType")
@UtilityClass
public class ObjectUtils {

    /**
     * If the source param is null, return the default value.
     *
     * @param source       maybe null object
     * @param defaultValue thing to use if source is null
     * @param <T>          list type
     * @return resulting object
     */
    public static <T> List<T> defaultIfNull(List<T> source, Supplier<List<T>> defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If the source param is null, return the default value.
     *
     * @param source       maybe null object
     * @param defaultValue thing to use if source is null
     * @param <K>          map key type
     * @param <V>          map value type
     * @return resulting map
     */
    public static <K, V> Map<K, V> defaultIfNull(Map<K, V> source, Supplier<Map<K, V>> defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * If the source param is null, return the default value.
     *
     * @param source       maybe null object
     * @param defaultValue thing to use if source is null
     * @param <T>          type
     * @return resulting object
     */
    public static <T> T defaultIfNull(T source, Supplier<T> defaultValue) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Concatenate a bunch of lists.
     *
     * @param sources bunch of lists.
     * @param <T>     list type
     * @return resulting object
     */
    @SafeVarargs
    public static <T> List<T> merge(Collection<T>... sources) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
