package com.bhuwanupadhyay.demos.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serial;
import java.io.Serializable;

public class AppError implements Serializable {
    @Serial private static final long serialVersionUID = 2405172041950241677L;
    private int code;
    private String reason;
    private String message;
    @JsonIgnore private String[] errors;
    @JsonIgnore private String debuggingInfo;
    @JsonIgnore private Exception originalException;

    public AppError(int code, String reason, String message) {
        this.code = code;
        this.reason = reason;
        this.message = message;
    }

    public AppError(
            int code,
            String reason,
            String message,
            String[] errors,
            String debuggingInfo,
            Exception originalException) {
        this(code, reason, message);
        this.errors = errors;
        this.debuggingInfo = debuggingInfo;
        this.originalException = originalException;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getErrors() {
        return errors;
    }

    public String getDebuggingInfo() {
        return debuggingInfo;
    }

    public Exception getOriginalException() {
        return originalException;
    }
}
