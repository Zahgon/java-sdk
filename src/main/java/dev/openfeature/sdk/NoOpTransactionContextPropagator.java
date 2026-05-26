package dev.openfeature.sdk;

/**
 * A {@link TransactionContextPropagator} that simply returns empty context.
 */
public class NoOpTransactionContextPropagator implements TransactionContextPropagator {

    /**
     * {@inheritDoc}
     *
     * @return empty immutable context
     */
    @Override
    public EvaluationContext getTransactionContext() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTransactionContext(EvaluationContext evaluationContext) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
