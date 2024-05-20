package com.bhuwanupadhyay.demos.model;

public interface IAuthorizationFilter {
    boolean hasRole(String... requiredRoles);
}
