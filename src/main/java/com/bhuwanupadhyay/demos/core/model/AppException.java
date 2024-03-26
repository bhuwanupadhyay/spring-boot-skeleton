package com.bhuwanupadhyay.demos.core.model;

import com.google.common.base.Strings;

public class AppException extends RuntimeException {

    private final AppError error;
    private final Exception originalException;

    public AppException(int status, String reason, String message) {
        this(status, reason, message, null, null, null);
    }

    public AppException(int status, String reason, String message, Exception originalException) {
        this(status, reason, message, null, originalException, null);
    }

    public AppException(
            int status,
            String reason,
            String message,
            String debuggingInfo,
            Exception originalException,
            String[] errors) {
        super(sanitizeString(message), originalException);
        String sanitizedReason = sanitizeString(reason);
        this.originalException = originalException;
        String errorMessage = this.getMessage();
        this.error =
                new AppError(
                        status,
                        sanitizedReason,
                        errorMessage,
                        errors,
                        debuggingInfo,
                        originalException);
    }

    private static String sanitizeString(String msg) {
        return Strings.isNullOrEmpty(msg) ? "" : msg.replace('\n', '_').replace('\r', '_');
    }

    public Exception getOriginalException() {
        return originalException;
    }

    public AppError getError() {
        return error;
    }
}
