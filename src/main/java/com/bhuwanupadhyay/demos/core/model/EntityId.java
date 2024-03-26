package com.bhuwanupadhyay.demos.core.model;

import java.util.Objects;

public record EntityId(String id) {

  public EntityId {
    Objects.requireNonNull(id, "Entity id is null");
  }
}
