package com.bhuwanupadhyay.demos.core.security;

import com.bhuwanupadhyay.demos.core.model.AppException;
import com.bhuwanupadhyay.demos.core.model.AppHeaders;
import com.bhuwanupadhyay.demos.core.model.Groups;
import com.bhuwanupadhyay.demos.core.model.IEntitlementService;
import com.bhuwanupadhyay.demos.core.model.ServiceRole;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class EntitlementService implements IEntitlementService {

    @Override
    public String authorize(AppHeaders headers, String... requiredRoles) {
        Groups groups = this.getGroups(headers);
        if (groups.anyMatch(requiredRoles)) {
            return groups.userId();
        } else {
            throw new AppException(
                    HttpStatus.FORBIDDEN.value(),
                    "Access Denied",
                    "The user is not authorized to perform this action.");
        }
    }

    private Groups getGroups(AppHeaders headers) {
        return new Groups(
                "test", new String[] {ServiceRole.VIEWER, ServiceRole.CREATOR, ServiceRole.ADMIN});
    }
}
