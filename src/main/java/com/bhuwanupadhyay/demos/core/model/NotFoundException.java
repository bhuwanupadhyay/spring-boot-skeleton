package com.bhuwanupadhyay.demos.core.model;

public class NotFoundException extends CoreException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
