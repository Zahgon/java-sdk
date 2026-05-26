package dev.openfeature.sdk;

import java.util.HashMap;
import java.util.Map;

/**
 * Default implementation of HookData.
 */
public class DefaultHookData implements HookData {

    private Map<String, Object> data;

    @Override
    public void set(String key, Object value) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Object get(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
