package dev.openfeature.sdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * LayeredEvaluationContext implements EvaluationContext by layering multiple contexts:
 * API-level, Transaction-level, Client-level, Invocation-level, and Hook-level.
 * The contexts are checked in that order for values, with Hook-level having the highest precedence.
 */
public class LayeredEvaluationContext implements EvaluationContext {

    private final EvaluationContext apiContext;

    private final EvaluationContext transactionContext;

    private final EvaluationContext clientContext;

    private final EvaluationContext invocationContext;

    private ArrayList<EvaluationContext> hookContexts;

    private String targetingKey;

    private Set<String> keySet = null;

    // Lazily computed resolved attribute map for this layered context.
    // This must be invalidated whenever the underlying layers change.
    private Map<String, Value> cachedMap;

    /**
     * Constructor for LayeredEvaluationContext.
     */
    public LayeredEvaluationContext(EvaluationContext apiContext, EvaluationContext transactionContext, EvaluationContext clientContext, EvaluationContext invocationContext) {
        this.apiContext = apiContext;
        this.transactionContext = transactionContext;
        this.clientContext = clientContext;
        this.invocationContext = invocationContext;
        if (invocationContext != null && invocationContext.getTargetingKey() != null) {
            this.targetingKey = invocationContext.getTargetingKey();
        } else if (clientContext != null && clientContext.getTargetingKey() != null) {
            this.targetingKey = clientContext.getTargetingKey();
        } else if (transactionContext != null && transactionContext.getTargetingKey() != null) {
            this.targetingKey = transactionContext.getTargetingKey();
        } else if (apiContext != null && apiContext.getTargetingKey() != null) {
            this.targetingKey = apiContext.getTargetingKey();
        } else {
            this.targetingKey = null;
        }
    }

    @Override
    public String getTargetingKey() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public EvaluationContext merge(EvaluationContext overridingContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean isEmpty() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private boolean areHookContextsEmpty() {
        if (hookContexts == null || hookContexts.isEmpty()) {
            return true;
        }
        for (int i = 0; i < hookContexts.size(); i++) {
            var current = hookContexts.get(i);
            if (!current.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Set<String> keySet() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Set<String> ensureKeySet() {
        if (this.keySet != null) {
            return this.keySet;
        }
        var keys = new HashSet<String>();
        if (hookContexts != null) {
            for (int i = 0; i < hookContexts.size(); i++) {
                var current = hookContexts.get(i);
                keys.addAll(current.keySet());
            }
        }
        if (invocationContext != null) {
            keys.addAll(invocationContext.keySet());
        }
        if (clientContext != null) {
            keys.addAll(clientContext.keySet());
        }
        if (transactionContext != null) {
            keys.addAll(transactionContext.keySet());
        }
        if (apiContext != null) {
            keys.addAll(apiContext.keySet());
        }
        this.keySet = keys;
        return keys;
    }

    private Value getFromContext(EvaluationContext context, String key) {
        if (context != null) {
            return context.getValue(key);
        }
        return null;
    }

    private Value getFromContext(ArrayList<EvaluationContext> context, String key) {
        if (context == null) {
            return null;
        }
        for (int i = context.size() - 1; i >= 0; i--) {
            var current = context.get(i);
            var value = getFromContext(current, key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    @Override
    public Value getValue(String key) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    private Map<String, Value> getResolvedMap() {
        if (cachedMap != null) {
            return cachedMap;
        }
        if (keySet != null && keySet.isEmpty()) {
            cachedMap = Collections.emptyMap();
            return cachedMap;
        }
        HashMap<String, Value> map;
        if (keySet != null) {
            // use helper to size the map based on expected entries
            map = HashMapUtils.forEntries(keySet.size());
        } else {
            map = new HashMap<>();
        }
        if (apiContext != null) {
            map.putAll(apiContext.asMap());
        }
        if (transactionContext != null) {
            map.putAll(transactionContext.asMap());
        }
        if (clientContext != null) {
            map.putAll(clientContext.asMap());
        }
        if (invocationContext != null) {
            map.putAll(invocationContext.asMap());
        }
        if (hookContexts != null) {
            for (int i = 0; i < hookContexts.size(); i++) {
                EvaluationContext hookContext = hookContexts.get(i);
                map.putAll(hookContext.asMap());
            }
        }
        cachedMap = Collections.unmodifiableMap(map);
        return cachedMap;
    }

    @Override
    public Map<String, Value> asMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, Value> asUnmodifiableMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public Map<String, Object> asObjectMap() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    void putHookContext(EvaluationContext context) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
