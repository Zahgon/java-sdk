package dev.openfeature.sdk.multiprovider;

import dev.openfeature.sdk.ErrorCode;
import dev.openfeature.sdk.exceptions.OpenFeatureError;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Represents an error from a single provider during multi-provider evaluation.
 *
 * <p>Captures the provider name, error code, error message, and optionally the original exception
 * that occurred during flag evaluation. This allows callers to inspect per-provider error details
 * when a multi-provider strategy exhausts all providers without a successful result.
 */
@Data
@Builder
@AllArgsConstructor
public class ProviderError {

    private String providerName;

    private ErrorCode errorCode;

    private String errorMessage;

    private Exception exception;

    /**
     * Create a ProviderError from an error-coded {@code ProviderEvaluation} result.
     *
     * @param providerName the name of the provider that returned the error
     * @param errorCode    the error code from the evaluation result
     * @param errorMessage the error message from the evaluation result (may be {@code null})
     * @return a new ProviderError
     */
    public static ProviderError fromResult(String providerName, ErrorCode errorCode, String errorMessage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Create a ProviderError from a thrown exception.
     *
     * @param providerName the name of the provider that threw the exception
     * @param exception    the exception that was thrown
     * @return a new ProviderError
     */
    public static ProviderError fromException(String providerName, Exception exception) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    /**
     * Build an aggregate error message from a list of provider errors.
     *
     * @param baseMessage the base message to use (e.g. "No provider successfully responded")
     * @param errors      the list of per-provider errors
     * @return an aggregate message including per-provider details
     */
    public static String buildAggregateMessage(String baseMessage, List<ProviderError> errors) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
