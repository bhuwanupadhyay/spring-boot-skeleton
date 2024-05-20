package com.bhuwanupadhyay.demos.model;

public class NotFoundException extends CoreException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
