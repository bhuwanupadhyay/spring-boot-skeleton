package com.bhuwanupadhyay.demos.core.model;

public class BadRequestException extends CoreException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
