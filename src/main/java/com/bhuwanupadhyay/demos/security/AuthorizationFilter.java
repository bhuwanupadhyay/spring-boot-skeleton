package com.bhuwanupadhyay.demos.security;

import com.bhuwanupadhyay.demos.model.AppHeaders;
import com.bhuwanupadhyay.demos.model.IAuthorizationFilter;
import com.bhuwanupadhyay.demos.model.IEntitlementService;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component("authorizationFilter")
@RequestScope
public class AuthorizationFilter implements IAuthorizationFilter {

    private final AppHeaders headers;
    private final IEntitlementService entitlementService;

    public AuthorizationFilter(AppHeaders headers, IEntitlementService entitlementService) {
        this.headers = headers;
        this.entitlementService = entitlementService;
    }

    @Override
    public boolean hasRole(String... requiredRoles) {
        String user = entitlementService.authorize(headers, requiredRoles);
        headers.put(AppHeaders.USER, user);
        return true;
    }
}
