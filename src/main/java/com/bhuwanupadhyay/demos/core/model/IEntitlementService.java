package com.bhuwanupadhyay.demos.core.model;

public interface IEntitlementService {

    String authorize(AppHeaders headers, String... requiredRoles);
}
