package com.bhuwanupadhyay.demos.model;

public interface IEntitlementService {

    String authorize(AppHeaders headers, String... requiredRoles);
}
