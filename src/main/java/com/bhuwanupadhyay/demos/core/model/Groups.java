package com.bhuwanupadhyay.demos.core.model;

public record Groups(String userId, String[] roles) {

  public boolean anyMatch(String... requiredRoles) {
    for (String requiredRole : requiredRoles) {
      for (String role : roles) {
        if (role.equals(requiredRole)) {
          return true;
        }
      }
    }
    return false;
  }
}
