package dev.openfeature.sdk.exceptions;

import dev.openfeature.sdk.ErrorCode;
import lombok.experimental.UtilityClass;

@SuppressWarnings("checkstyle:MissingJavadocType")
@UtilityClass
public class ExceptionUtils {

    /**
     * Creates an Error for the specific error code.
     *
     * @param errorCode    the ErrorCode to use
     * @param errorMessage the error message to include in the returned error
     * @return the specific OpenFeatureError for the errorCode
     */
    public static OpenFeatureError instantiateErrorByErrorCode(ErrorCode errorCode, String errorMessage) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
