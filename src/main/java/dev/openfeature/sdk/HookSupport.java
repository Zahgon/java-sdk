package dev.openfeature.sdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

/**
 * Helper class to run hooks. Initialize {@link HookSupportData} by calling setHooks, setHookContexts
 * & updateEvaluationContext in this exact order.
 */
@Slf4j
class HookSupport {

    /**
     * Sets the {@link Hook}-{@link HookContext}-{@link Pair} list in the given data object with {@link HookContext}
     * set to null. Filters hooks by supported {@link FlagValueType}.
     *
     * @param hookSupportData the data object to modify
     * @param hooks           the hooks to set
     * @param type            the flag value type to filter unsupported hooks
     */
    public void setHooks(HookSupportData hookSupportData, List<Hook> hooks, FlagValueType type) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Creates & sets a {@link HookContext} for every {@link Hook}-{@link HookContext}-{@link Pair}
     * in the given data object with a new {@link HookData} instance.
     *
     * @param hookSupportData the data object to modify
     * @param sharedContext   the shared context from which the new {@link HookContext} is created
     */
    public void setHookContexts(HookSupportData hookSupportData, SharedHookContext sharedContext, LayeredEvaluationContext evaluationContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void executeBeforeHooks(HookSupportData data) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public void executeErrorHooks(HookSupportData data, Exception error) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    // after hooks can throw in order to do validation
    public <T> void executeAfterHooks(HookSupportData data, FlagEvaluationDetails<T> details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public <T> void executeAfterAllHooks(HookSupportData data, FlagEvaluationDetails<T> details) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
