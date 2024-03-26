package com.bhuwanupadhyay.demos.core.model;

public interface IAuthorizationFilter {
    boolean hasRole(String... requiredRoles);
}
